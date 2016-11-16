package com.cmos.adpu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class BaseActivity extends Activity {

    private static final String TAG = "BaseActivity";
    private static final long SCAN_PERIOD = 5000;
    protected static int position;
    static BluetoothGattCharacteristic characteristicTX;
    static DataInput dataInput;
    static BluetoothLeService mBluetoothLeService;
    static Integer i = -1;
    static String print_data;

    private static boolean isLaunch = true;
    private final SimpleDateFormat _sdfWatchTime = new SimpleDateFormat("HH:mm");
    protected FrameLayout frameLayout;
    protected String[] listArray = {
            "Shift Reports",
            "Member Ledger",
            "Payment Register",
            "Duplicate Slip",
            "Add Farmer/Member",
            "Settings",
            "Set Password",
            "Add Rate Chart",
            "Sync with Server",
            "Device Info"};

    protected ListView mDrawerList;
    String cattle_type;
    BroadcastReceiver _broadcastReceiver;
    MenuItem clock;
    AlertDialog alertDialog;
    AlertDialog.Builder alertDialogBox;
    EditText code, weight, fat, snf, added_water, pass;
    View subView;
    String current_drawer_item;
    String current_selection;
    boolean first_time_selected;
    ProgressDialog progressDialog;
    Button print_preview, print, enter;
    CollectionsDBAdapter collectionsDBAdapter;
    RateChartDBAdapter rateChartDBAdapter;
    MembersDBAdapter membersDBAdapter;
    SettingsDBAdapter settingsDBAdapter;
    PrintableText printableText;
    boolean clearData = true;

    String rateChart, chartType, dairy_name, std_cow_rate, per_cow_fat, std_cow_fat, per_cow_snf, std_cow_snf,
            std_buffalo_rate, per_buffalo_fat, std_buffalo_fat, per_buffalo_snf, std_buffalo_snf, analyzer;

    boolean weightTear = true;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private TextView _tvTime;
    private BluetoothGattCharacteristic characteristic;
    private CharSequence[] devices10;
    private CharSequence[] availableDevices;
    private String[] deviceAddresses;
    private Integer deviceCount = 0;
    private BluetoothAdapter mBluetoothAdapter;
    private String mDeviceAddress;

    // Code to manage Service lifecycle.
    private final ServiceConnection mServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName componentName, IBinder service) {
            mBluetoothLeService = ((BluetoothLeService.LocalBinder) service).getService();
            if (!mBluetoothLeService.initialize()) {
                Toast.makeText(BaseActivity.this, "Unable to initialize Bluetooth!!", Toast.LENGTH_SHORT).show();
                finish();
            }
            // Automatically connects to the device upon successful start-up initialization.
            mBluetoothLeService.connect(mDeviceAddress);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            mBluetoothLeService = null;
        }
    };
    private boolean mDeviceConnected;
    private DrawerLayout mDrawerLayout;
    private Handler mHandler;
    private boolean mScanning;
    private boolean mFound;
    private String shift;
    private Timer pooling_timer;
    private TimerTask pooling_task;
    private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
            //final BluetoothDevice foundDevice = device;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (device.getAddress().equals(settingsDBAdapter.getSingleEntry("macaddress"))) { //D0:39:72:BB:E8:A7//90:59:AF:16:2D:AF
                        mDeviceAddress = device.getAddress();
                        mFound = true;
                        //devices10 = null;
                        deviceCount = 0;
                        scanLeDevice(false);
                    } else if (deviceCount < 10) {
                        if (device.getName() != null && device.getName().length() > 0) {
                            if (!Arrays.asList(deviceAddresses).contains(device.getAddress())) {
                                devices10[deviceCount] = device.getName() + " (" + device.getAddress() + ")";
                                deviceAddresses[deviceCount] = device.getAddress();
                                deviceCount++;
                            }
                        } else {
                            if (!Arrays.asList(deviceAddresses).contains(device.getAddress())) {
                                devices10[deviceCount] = "Unknown Devices (" + device.getAddress() + ")";
                                deviceAddresses[deviceCount] = device.getAddress();
                                deviceCount++;
                            }
                        }
                    }
                }
            });
        }
    };
    private final BroadcastReceiver mGattUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (BluetoothLeService.ACTION_GATT_CONNECTED.equals(action)) {
                mDeviceConnected = true;
            } else if (BluetoothLeService.ACTION_GATT_DISCONNECTED.equals(action)) {
                mDeviceConnected = false;
                scanLeDevice(true);
                /*Intent intentMain = getIntent();
                finish();
                startActivity(intentMain);*/
            } else if (BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED.equals(action)) {
                //Show all the supported services and characteristics on the user interface.
                //displayGattServices(mBluetoothLeService.getSupportedGattServices());
                for (BluetoothGattService gattService : mBluetoothLeService.getSupportedGattServices()) {
                    if (gattService.getUuid().toString().equals("0000fff0-0000-1000-8000-00805f9b34fb")) {
                        for (BluetoothGattCharacteristic gattCharacteristic : gattService.getCharacteristics()) {
                            if (gattCharacteristic.getUuid().toString().equals("0000fff4-0000-1000-8000-00805f9b34fb")) {
                                characteristic = gattCharacteristic;
                                characteristicTX = gattService.getCharacteristic(UUID.fromString("0000fff1-0000-1000-8000-00805f9b34fb"));
                            }
                        }
                    }
                }
            } else if (BluetoothLeService.ACTION_DATA_AVAILABLE.equals(action)) {
                String extra_data = intent.getStringExtra(BluetoothLeService.EXTRA_DATA);
                if (weightTear) {
                    BaseActivity.print_data = "$a" + analyzer;
                    BaseActivity.i = 0;
                    DataInput.data_input = false;
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            BaseActivity.characteristicTX.setValue("$wT");
                            BaseActivity.mBluetoothLeService.writeCharacteristic(BaseActivity.characteristicTX);
                        }
                    }, 100);
                    weightTear = false;
                }
                if (extra_data != null && !clearData)
                    for (int k = 0; k < extra_data.length(); k++) {
                        dataInput.display_data(extra_data.substring(k, k + 1));
                    }
            }
        }
    };
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    private static IntentFilter makeGattUpdateIntentFilter() {
        final IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_CONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_DISCONNECTED);
        intentFilter.addAction(BluetoothLeService.ACTION_GATT_SERVICES_DISCOVERED);
        intentFilter.addAction(BluetoothLeService.ACTION_DATA_AVAILABLE);
        return intentFilter;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer_base_layout);
        DataInput.data_input = true;
        DataInput.object_selected = "CODE_BA";
        weightTear = true;
        printableText = new PrintableText();

        rateChart = getIntent().getStringExtra("RATE_CHART");
        chartType = getIntent().getStringExtra("RATE_TYPE");
        shift = getIntent().getStringExtra("SHIFT");
        dairy_name = getIntent().getStringExtra("DAIRY_NAME");
        std_cow_rate = getIntent().getStringExtra("STD_COW_RATE");
        per_cow_fat = getIntent().getStringExtra("PER_COW_FAT");
        std_cow_fat = getIntent().getStringExtra("STD_COW_FAT");
        per_cow_snf = getIntent().getStringExtra("PER_COW_SNF");
        std_cow_snf = getIntent().getStringExtra("STD_COW_SNF");
        std_buffalo_rate = getIntent().getStringExtra("STD_BUFFALO_RATE");
        per_buffalo_fat = getIntent().getStringExtra("PER_BUFFALO_FAT");
        std_buffalo_fat = getIntent().getStringExtra("STD_BUFFALO_FAT");
        per_buffalo_snf = getIntent().getStringExtra("PER_BUFFALO_SNF");
        std_buffalo_snf = getIntent().getStringExtra("STD_BUFFALO_SNF");
        analyzer = getIntent().getStringExtra("ANALYZER");

        settingsDBAdapter = new SettingsDBAdapter(this);
        settingsDBAdapter.open();
        //settingsDBAdapter.updateEntry("macaddress", "90:59:AF:16:2D:AF");
        frameLayout = (FrameLayout) findViewById(R.id.content_frame);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, listArray));
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                openActivity(position);
            }
        });
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,						/* host Activity */
                mDrawerLayout, 				/* DrawerLayout object */
                R.string.open_drawer,       /* "open drawer" description for accessibility */
                R.string.close_drawer)      /* "close drawer" description for accessibility */ {
            @Override
            public void onDrawerClosed(View drawerView) {
                if (!(dairy_name.equals("RECORD DOES NOT EXIST.") || dairy_name.equals("ERROR!!")))
                    getActionBar().setTitle(dairy_name);
                else
                    getActionBar().setTitle("Dairy Name");
                //DataInput.object_selected = "CODE_BA";
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(R.string.menu);
                DataInput.object_selected = "Shift Reports";
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
            }
        };
        mDrawerLayout.setDrawerListener(actionBarDrawerToggle);
        mHandler = new Handler();
        mFound = false;
        mDeviceConnected = false;
        current_selection = "code_name";
        getLayoutInflater().inflate(R.layout.activity_home, frameLayout);
        mDrawerList.setItemChecked(position, true);
        if (!(dairy_name.equals("RECORD DOES NOT EXIST.") || dairy_name.equals("ERROR!!")))
            setTitle(dairy_name);
        else
            setTitle("Dairy Name");
        code = ((EditText) findViewById(R.id.code_et));
        weight = ((EditText) findViewById(R.id.weight_et));
        fat = ((EditText) findViewById(R.id.fat_et));
        snf = ((EditText) findViewById(R.id.snf_et));
        added_water = ((EditText) findViewById(R.id.added_water_et));
        ((TextView) findViewById(R.id.rate_tv)).setText(((TextView) findViewById(R.id.rate_tv)).getText() + " (R" + rateChart + ")");
        pass = ((EditText) findViewById(R.id.pass));

        weight.setText("0.0");
        fat.setText("0.0");
        snf.setText("0.0");
        print_preview = (Button) findViewById(R.id.btn1);
        print = (Button) findViewById(R.id.btn2);
        enter = (Button) findViewById(R.id.print_entered_data);
        devices10 = new CharSequence[10];
        deviceAddresses = new String[10];
        DataInput.analyzer_entry = analyzer.equals("0");
        DataInput.weight_entry = settingsDBAdapter.getSingleEntry("weight").equals("0");
        dataInput = new DataInput(this, code, weight, fat, snf, added_water, mDrawerLayout, mDrawerList, print_preview, print, pass);
        first_time_selected = true;
        collectionsDBAdapter = new CollectionsDBAdapter(this);
        collectionsDBAdapter.open();
        //collectionsDBAdapter.deleteCollections("02/01/2016 Evening");
        //collectionsDBAdapter.delete_table();
        membersDBAdapter = new MembersDBAdapter(this);
        membersDBAdapter.open();
        ((TextView) findViewById(R.id.sno_et)).setText(collectionsDBAdapter.getSerialNumber());
        collectionsDBAdapter.close();
        ((TextView) findViewById(R.id.timer_tv)).setText(_sdfWatchTime.format(new Date()));
        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(this, "BLE is not supported!!", Toast.LENGTH_SHORT).show();
            finish();
        }
        final BluetoothManager bluetoothManager =
                (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
        mBluetoothAdapter = bluetoothManager.getAdapter();
        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "Error! Bluetooth is not supported.", Toast.LENGTH_SHORT).show();
            finish();
        }
        print_preview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DataInput.print_cancel.equals("pass") || DataInput.print_cancel.equals("mpass") || DataInput.print_cancel.equals("rate")) {
                    DataInput.object_selected = "POPUP_PRINT_PREVIEW";
                    alertDialogBox = new AlertDialog.Builder(BaseActivity.this);
                    LayoutInflater inflater = BaseActivity.this.getLayoutInflater();
                    subView = inflater.inflate(R.layout.passdialog, null);
                    alertDialogBox.setView(subView);
                    alertDialogBox.setPositiveButton("Enter", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String sPass = ((EditText) subView.findViewById(R.id.password)).getText().toString(), dbPass = "";
                            Intent intent;
                            if (DataInput.pass_mpass.equals("pass")) {
                                dbPass = settingsDBAdapter.getSingleEntry("password");
                                intent = new Intent(BaseActivity.this, SettingsActivity.class);
                            } else if (DataInput.pass_mpass.equals("rate")) {
                                dbPass = settingsDBAdapter.getSingleEntry("password");
                                intent = new Intent(BaseActivity.this, AddRateChartActivity.class);
                            } else {
                                dbPass = settingsDBAdapter.getSingleEntry("mpassword");
                                intent = new Intent(BaseActivity.this, DeviceInfoActivity.class);
                            }
                            alertDialogBox = null;
                            reset();
                            if (sPass.equals(dbPass)) {
                                startActivity(intent);
                            } else
                                Toast.makeText(BaseActivity.this, "Please enter correct password!!", Toast.LENGTH_SHORT).show();
                        }
                    });
                    alertDialogBox.setNegativeButton("Exit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            alertDialogBox = null;
                            reset();
                        }
                    });
                    alertDialog = alertDialogBox.show();
                    alertDialog.setCanceledOnTouchOutside(false);
                } else {
                    if (Float.parseFloat(weight.getText().toString()) == 0) {
                        Toast.makeText(BaseActivity.this, "Weight can't be '0'.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (Float.parseFloat(fat.getText().toString()) < Float.parseFloat(settingsDBAdapter.getSingleEntry("cow_fat_range_from"))) {
                        Toast.makeText(BaseActivity.this, "Low fat!!.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (Float.parseFloat(fat.getText().toString()) < Float.parseFloat(settingsDBAdapter.getSingleEntry("buffalo_fat_range_from"))) {
                        if (Float.parseFloat(snf.getText().toString()) < Float.parseFloat(settingsDBAdapter.getSingleEntry("cow_snf_range_from"))) {
                            Toast.makeText(BaseActivity.this, "Low SNF!!.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    } else {
                        if (Float.parseFloat(snf.getText().toString()) < Float.parseFloat(settingsDBAdapter.getSingleEntry("buffalo_snf_range_from"))) {
                            Toast.makeText(BaseActivity.this, "Low SNF!!.", Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    if (((EditText) findViewById(R.id.amount_et)).getText().toString().equals("0.00")) {
                        Toast.makeText(BaseActivity.this, "Amount can't be '0'.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    final Calendar c = Calendar.getInstance();
                    String kgLtr = "KG";
                    if (Integer.parseInt(settingsDBAdapter.getSingleEntry("measurementunit")) == 0)
                        kgLtr = "KG";
                    else
                        kgLtr = "Ltr";
                    final String print_text = dairy_name +
                            "\r\nDate: " + ((Integer) c.get(Calendar.DAY_OF_MONTH)).toString() + "/" + ((Integer) (c.get(Calendar.MONTH) + 1)).toString() + "/" + ((Integer) c.get(Calendar.YEAR)).toString() +
                            "\r\nShift: " + shift +
                            "\r\nCode: " + code.getText().toString() +
                            "\r\nName: " + ((TextView) findViewById(R.id.name_et)).getText().toString() +
                            "\r\n" + ((TextView) findViewById(R.id.weight_tv)).getText() + " " + weight.getText().toString() + kgLtr +
                            "\r\n" + ((TextView) findViewById(R.id.fat_tv)).getText() + " " + fat.getText().toString() + "%" +
                            "\r\nSNF: " + snf.getText().toString() + "%" +
                            "\r\nRate: " + ((EditText) findViewById(R.id.rate_et)).getText().toString() +
                            "\r\nAmount: " + ((EditText) findViewById(R.id.amount_et)).getText().toString() +
                            "\r\n--------------------------------" +
                            "\r\n" + settingsDBAdapter.getSingleEntry("footer") + "\r\n\r\n\r\n\r\n$wT";
                    DataInput.save_collections = true;
                    BaseActivity.print_data = print_text;
                    DataInput.object_selected = "POPUP_PRINT_PREVIEW";
                    //if (alertDialogBox == null) {
                    alertDialogBox = new AlertDialog.Builder(BaseActivity.this);
                    alertDialogBox.setMessage(print_text.substring(print_text.indexOf("\r\n") + 2, print_text.indexOf("\r\n--------------------------------") - 1));

                    alertDialogBox.setPositiveButton("PRINT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DataInput.data_input = false;
                            BaseActivity.i = 0;
                            String day = ((Integer) c.get(Calendar.DAY_OF_MONTH)).toString();
                            if (c.get(Calendar.DAY_OF_MONTH) < 10)
                                day = "0" + day;
                            String month = ((Integer) (c.get(Calendar.MONTH) + 1)).toString();
                            if ((c.get(Calendar.MONTH) + 1) < 10)
                                month = "0" + month;

                            String year = ((Integer) c.get(Calendar.YEAR)).toString();
                            collectionsDBAdapter = new CollectionsDBAdapter(getApplicationContext());
                            collectionsDBAdapter.open();

                            String result = collectionsDBAdapter.insertEntry(collectionsDBAdapter.getSerialNumber(),
                                    BaseActivity.print_data.substring(BaseActivity.print_data.lastIndexOf("Name: ") + 6, BaseActivity.print_data.lastIndexOf("Quantity: ") - 2),
                                    BaseActivity.print_data.substring(BaseActivity.print_data.lastIndexOf("Code: ") + 6, BaseActivity.print_data.lastIndexOf("Name: ") - 2),
                                    BaseActivity.print_data.substring(BaseActivity.print_data.lastIndexOf("Quantity: ") + 10, BaseActivity.print_data.lastIndexOf("Fat: ") - 4),
                                    BaseActivity.print_data.substring(BaseActivity.print_data.lastIndexOf("Fat: ") + 5, BaseActivity.print_data.lastIndexOf("SNF: ") - 3),
                                    BaseActivity.print_data.substring(BaseActivity.print_data.lastIndexOf("SNF: ") + 5, BaseActivity.print_data.lastIndexOf("Rate: ") - 3),
                                    BaseActivity.print_data.substring(BaseActivity.print_data.lastIndexOf("Rate: ") + 6, BaseActivity.print_data.lastIndexOf("Amount: ") - 2),
                                    BaseActivity.print_data.substring(BaseActivity.print_data.lastIndexOf("Amount: ") + 8, BaseActivity.print_data.lastIndexOf("--------------------------------") - 2),
                                    day + "/" + month + "/" + year + " " + shift);

                            if (result.equals("Success")) {

                                //check internet avaialbe or not
                                ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                                NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
                                boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();


                                // upload on server if internet available
                                if (isConnected) {
                                    UploadCollection uploadCollection = new UploadCollection(getApplicationContext());

                                    uploadCollection.execute(collectionsDBAdapter.getSerialNumber(),
                                            BaseActivity.print_data.substring(BaseActivity.print_data.lastIndexOf("Name: ") + 6, BaseActivity.print_data.lastIndexOf("Quantity: ") - 2),
                                            BaseActivity.print_data.substring(BaseActivity.print_data.lastIndexOf("Code: ") + 6, BaseActivity.print_data.lastIndexOf("Name: ") - 2),
                                            BaseActivity.print_data.substring(BaseActivity.print_data.lastIndexOf("Quantity: ") + 10, BaseActivity.print_data.lastIndexOf("Fat: ") - 4),
                                            BaseActivity.print_data.substring(BaseActivity.print_data.lastIndexOf("Fat: ") + 5, BaseActivity.print_data.lastIndexOf("SNF: ") - 3),
                                            BaseActivity.print_data.substring(BaseActivity.print_data.lastIndexOf("SNF: ") + 5, BaseActivity.print_data.lastIndexOf("Rate: ") - 3),
                                            BaseActivity.print_data.substring(BaseActivity.print_data.lastIndexOf("Rate: ") + 6, BaseActivity.print_data.lastIndexOf("Amount: ") - 2),
                                            BaseActivity.print_data.substring(BaseActivity.print_data.lastIndexOf("Amount: ") + 8, BaseActivity.print_data.lastIndexOf("--------------------------------") - 2),
                                            day + "-" + month + "-" + year, shift,
                                            settingsDBAdapter.getSingleEntry("dairycode") + settingsDBAdapter.getSingleEntry("routecode") + settingsDBAdapter.getSingleEntry("villagecode") + settingsDBAdapter.getSingleEntry("centercode"), cattle_type.substring(0, 1));

                                    BaseActivity.print_data = BaseActivity.print_data.substring(0, BaseActivity.print_data.indexOf("(") - 1) +
                                            BaseActivity.print_data.substring(BaseActivity.print_data.indexOf(")") + 2, BaseActivity.print_data.lastIndexOf("Fat: ") - 2) + " " +
                                            BaseActivity.print_data.substring(BaseActivity.print_data.indexOf("("), BaseActivity.print_data.indexOf(")") + 1) + "\r\nFat: " +
                                            BaseActivity.print_data.substring(BaseActivity.print_data.lastIndexOf(")") + 2, BaseActivity.print_data.lastIndexOf("SNF: ") - 2) + " " +
                                            BaseActivity.print_data.substring(BaseActivity.print_data.lastIndexOf("Fat: ")).substring(BaseActivity.print_data.substring(BaseActivity.print_data.lastIndexOf("Fat: ")).indexOf("("), BaseActivity.print_data.substring(BaseActivity.print_data.lastIndexOf("Fat: ")).lastIndexOf(")") + 1) + "\r\n" +
                                            BaseActivity.print_data.substring(BaseActivity.print_data.lastIndexOf("SNF: "));

                                } else {

                                    Toast.makeText(getApplicationContext(), "Internet not available. Please check your network connection", Toast.LENGTH_SHORT).show();
                                }


                                // passing values to be uploaded on server
                                /*
                                uploadCollection.execute(collectionsDBAdapter.getSerialNumber(),
                                        BaseActivity.print_data.substring(BaseActivity.print_data.lastIndexOf("Name: ") + 6, BaseActivity.print_data.lastIndexOf("Quantity: ") - 2),
                                        BaseActivity.print_data.substring(BaseActivity.print_data.lastIndexOf("Code: ") + 6, BaseActivity.print_data.lastIndexOf("Name: ") - 2),
                                        BaseActivity.print_data.substring(BaseActivity.print_data.lastIndexOf("Quantity: ") + 10, BaseActivity.print_data.lastIndexOf("Fat: ") - 4),
                                        BaseActivity.print_data.substring(BaseActivity.print_data.lastIndexOf("Fat: ") + 5, BaseActivity.print_data.lastIndexOf("SNF: ") - 3),
                                        BaseActivity.print_data.substring(BaseActivity.print_data.lastIndexOf("SNF: ") + 5, BaseActivity.print_data.lastIndexOf("Rate: ") - 3),
                                        BaseActivity.print_data.substring(BaseActivity.print_data.lastIndexOf("Rate: ") + 6, BaseActivity.print_data.lastIndexOf("Amount: ") - 2),
                                        BaseActivity.print_data.substring(BaseActivity.print_data.lastIndexOf("Amount: ") + 8, BaseActivity.print_data.lastIndexOf("--------------------------------") - 2),
                                        day + "/" + month + "/" + year + " " + shift,
                                        settingsDBAdapter.getSingleEntry("dairycode") + settingsDBAdapter.getSingleEntry("routecode") + settingsDBAdapter.getSingleEntry("villagecode") + settingsDBAdapter.getSingleEntry("centercode"));
                                */


                                String result_mobile = membersDBAdapter.getSingleEntry(((EditText) findViewById(R.id.code_et)).getText().toString());
                                if (result_mobile.equals("RECORD DOES NOT EXIST.") || result_mobile.equals("ERROR!!")) {

                                }
                                /*SmsManager.getDefault().sendTextMessage("+919136025431", null,
                                        "Collection Successful with\r\n" + BaseActivity.print_data.substring(BaseActivity.print_data.indexOf("Code: "), BaseActivity.print_data.lastIndexOf("--------------------------------") - 2),
                                        null, null);*/
                                else
                                    SmsManager.getDefault().sendTextMessage("+91" + result_mobile.split(",")[2], null,
                                            "Collection Successful with\r\n" + BaseActivity.print_data.substring(BaseActivity.print_data.indexOf("Code: "), BaseActivity.print_data.lastIndexOf("--------------------------------") - 2),
                                            null, null);
                                new Handler().postDelayed(new Runnable() {
                                    public void run() {
                                        BaseActivity.characteristicTX.setValue("\r\n");
                                        BaseActivity.mBluetoothLeService.writeCharacteristic(BaseActivity.characteristicTX);
                                    }
                                }, 100);
                            }

                            reset();
                            DataInput.first_time_selected = true;
                            DataInput.save_collections = false;
                            ((TextView) findViewById(R.id.sno_et)).setText(collectionsDBAdapter.getSerialNumber());
                            code.setText("000");
                            weight.setText("0.0");
                            fat.setText("0.0");
                            snf.setText("0.0");
                            added_water.setText("0");
                            alertDialogBox = null;
                        }
                    });
                    alertDialogBox.setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            DataInput.save_collections = false;
                            reset();
                            alertDialogBox = null;
                        }
                    });
                    alertDialog = alertDialogBox.show();
                    alertDialog.setCanceledOnTouchOutside(false);
                }
            }
        });
        print.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (alertDialogBox != null) {
                    if (DataInput.print_cancel.equals("print"))
                        alertDialog.getButton(DialogInterface.BUTTON_POSITIVE).callOnClick();
                    else
                        alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE).callOnClick();
                }
            }
        });
        ((EditText) findViewById(R.id.weight_et)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onChangeText();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ((EditText) findViewById(R.id.code_et)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String result_code = membersDBAdapter.getSingleEntry(((EditText) findViewById(R.id.code_et)).getText().toString());

                if (result_code.equals("RECORD DOES NOT EXIST.") || result_code.equals("ERROR!!"))
                    ((TextView) findViewById(R.id.name_et)).setText("Farmer Name");
                else
                    ((TextView) findViewById(R.id.name_et)).setText(result_code.split(",")[1]);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ((EditText) findViewById(R.id.fat_et)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onChangeText();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ((EditText) findViewById(R.id.snf_et)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onChangeText();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ((EditText) findViewById(R.id.pass)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (alertDialogBox != null && (DataInput.print_cancel.equals("pass") || DataInput.print_cancel.equals("mpass") || DataInput.print_cancel.equals("rate")))
                    ((EditText) subView.findViewById(R.id.password)).setText(pass.getText());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        if (!mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.enable();
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    scanLeDevice(true);
                }
            }, 2000);
        } else
            scanLeDevice(true);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        clock = menu.findItem(R.id.action_clock);
        clock.setTitle(_sdfWatchTime.format(new Date()));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pooling_timer != null) {
            pooling_task.cancel();
            pooling_timer = null;
        }
        if (mDeviceConnected)
            unbindService(mServiceConnection);
        mBluetoothLeService = null;
        unregisterReceiver(mGattUpdateReceiver);
        settingsDBAdapter.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        weightTear = true;
        dataInput = new DataInput(this, code, weight, fat, snf, added_water, mDrawerLayout, mDrawerList, print_preview, print, pass);
        DataInput.object_selected = "CODE_BA";
        DataInput.analyzer_entry = settingsDBAdapter.getSingleEntry("analyzer").equals("0");
        DataInput.weight_entry = settingsDBAdapter.getSingleEntry("weight").equals("0");
        registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter());
        if (mBluetoothLeService != null) {
            final boolean result = mBluetoothLeService.connect(mDeviceAddress);
        }
        pooling_timer = new Timer();
        pooling_task = new TimerTask() {
            @Override
            public void run() {
                if (characteristic != null && mBluetoothLeService != null)
                    if ((characteristic.getProperties() | BluetoothGattCharacteristic.PROPERTY_READ) > 0 && DataInput.data_input)
                        mBluetoothLeService.readCharacteristic(characteristic);
            }
        };
        pooling_timer.scheduleAtFixedRate(pooling_task, 0, 1);

        _broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context ctx, Intent intent) {
                if (intent.getAction().compareTo(Intent.ACTION_TIME_TICK) == 0) {
                    _tvTime = (TextView) findViewById(R.id.timer_tv);
                    _tvTime.setText(_sdfWatchTime.format(new Date()));
                }
            }
        };
        registerReceiver(_broadcastReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));

        switch (analyzer) {
            case "0":
                ((TextView) findViewById(R.id.fat_tv)).setText("Fat: (Manual)");
                break;
            case "1":
                ((TextView) findViewById(R.id.fat_tv)).setText("Fat: (ECO)");
                break;
            case "2":
                ((TextView) findViewById(R.id.fat_tv)).setText("Fat: (Lacto)");
                break;
            case "3":
                ((TextView) findViewById(R.id.fat_tv)).setText("Fat: (Lacto+)");
                break;
            case "4":
                ((TextView) findViewById(R.id.fat_tv)).setText("Fat: (Indiz)");
                break;
            default:
                ((TextView) findViewById(R.id.fat_tv)).setText("Fat");
        }
        switch (settingsDBAdapter.getSingleEntry("weight")) {
            case "0":
                ((TextView) findViewById(R.id.weight_tv)).setText("Quantity: (Manual)");
                break;
            case "1":
                ((TextView) findViewById(R.id.weight_tv)).setText("Quantity: (Auto)");
                break;
            default:
                ((TextView) findViewById(R.id.weight_tv)).setText("Quantity");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (_broadcastReceiver != null)
            unregisterReceiver(_broadcastReceiver);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        menu.findItem(R.id.action_clock).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(mDrawerList)) {
            mDrawerLayout.closeDrawer(mDrawerList);
        } else {
            finish();
        }
    }

    private void scanLeDevice(final boolean enable) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mScanning = false;
                    progressDialog.dismiss();
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                    if (!mFound && getApplicationContext() != null) {
                        alertDialogBox = new AlertDialog.Builder(BaseActivity.this);
                        alertDialogBox.setTitle("Unable to detect the device. Please select your device from th list else try again.");
                        //alertDialogBox.setMessage("Unable to detect the device. Please select your device from th list else try again.");
                        alertDialogBox.setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                devices10 = new CharSequence[10];
                                deviceCount = 0;
                                scanLeDevice(true);
                            }
                        });
                        alertDialogBox.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (mScanning)
                                    scanLeDevice(false);
                                finish();
                            }
                        });
                        availableDevices = new CharSequence[deviceCount];
                        for (int device_i = 0; device_i < deviceCount; device_i++)
                            availableDevices[device_i] = devices10[device_i];
                        alertDialogBox.setItems(availableDevices, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                mDeviceAddress = deviceAddresses[which];
                                mFound = true;
                                devices10 = new CharSequence[10];
                                deviceCount = 0;
                                settingsDBAdapter.updateEntry("macaddress", deviceAddresses[which]);
                                scanLeDevice(false);
                            }
                        });
                        alertDialog = alertDialogBox.show();
                        alertDialog.setCanceledOnTouchOutside(false);
                    }
                }
            }, SCAN_PERIOD);

            mScanning = true;
            progressDialog = ProgressDialog.show(BaseActivity.this, "Wait", "Searching for device...");
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            mScanning = false;
            progressDialog.dismiss();
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
            if (mFound) {
                Intent gattServiceIntent = new Intent(this, BluetoothLeService.class);
                bindService(gattServiceIntent, mServiceConnection, BIND_AUTO_CREATE);
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        clearData = false;
                    }
                }, 1000);
            }
        }
    }

    private void openActivity(int position) {
        this.mDrawerLayout.closeDrawer(this.mDrawerList);
        this.position = position;
        final Intent intent;
        switch (position) {
            case 0:
                intent = new Intent(this, ShiftReportsActivity.class);
                startActivity(intent);
                break;
            case 1:
                intent = new Intent(this, MemberLedgerActivity.class);
                startActivity(intent);
                break;
            case 2:
                intent = new Intent(this, PaymentSummaryActivity.class);
                startActivity(intent);
                break;
            case 3:
                intent = new Intent(this, DuplicateSlipActivity.class);
                startActivity(intent);
                break;
            case 4:
                intent = new Intent(this, AddFarmerMember.class);
                startActivity(intent);
                break;
            case 5:
                DataInput.print_cancel = "pass";
                print_preview.callOnClick();
                break;
            case 6:
                intent = new Intent(this, ResetPasswordActivity.class);
                startActivity(intent);
                break;
            case 7:
                DataInput.print_cancel = "rate";
                print_preview.callOnClick();
                break;
            case 8:
                intent = new Intent(this, SyncActivity.class);
                startActivity(intent);
                break;
            case 9:
                DataInput.print_cancel = "mpass";
                print_preview.callOnClick();
                break;
            default:
        }
        //startActivity(new Intent(getApplicationContext(), PrintActivity.class));
    }

    private void setStyle_et(EditText editText, String color) {
        if (color.equals("light")) {
            editText.setBackgroundColor(0xFFFFFFFF);
            editText.setTextColor(0xFF000000);
            editText.setHintTextColor(0xFF000000);
        } else {
            editText.setBackgroundColor(0xFF000000);
            editText.setTextColor(0xFFFFFFFF);
            editText.setHintTextColor(0xFFFFFFFF);
        }
    }

    void reset() {
        DataInput.object_selected = "CODE_BA";
        this.code.setBackgroundResource(R.drawable.focused);
        this.weight.setBackgroundResource(R.drawable.unfocused);
        this.fat.setBackgroundResource(R.drawable.unfocused);
        this.snf.setBackgroundResource(R.drawable.unfocused);
        this.code.setTextColor(0xFFFFFFFF);
        this.weight.setTextColor(0xFF000000);
        this.fat.setTextColor(0xFF000000);
        this.snf.setTextColor(0xFF000000);
        this.pass.setText("");
    }

    public void onChangeText() {

        Float fat_value = 0.0f, snf_value = 0.0f;
        try {

            if (fat.getText().toString().equals("")) {

                ((EditText) findViewById(R.id.rate_et)).setText("0.00");
                ((EditText) findViewById(R.id.amount_et)).setText("0.00");

            } else if (Float.parseFloat(fat.getText().toString()) < Float.parseFloat(settingsDBAdapter.getSingleEntry("cow_fat_range_from"))) {
                ((EditText) findViewById(R.id.rate_et)).setText("0.00");
                ((EditText) findViewById(R.id.amount_et)).setText("0.00");
                return;
            }
            if (Float.parseFloat(fat.getText().toString()) < Float.parseFloat(settingsDBAdapter.getSingleEntry("buffalo_fat_range_from"))) {
                cattle_type = "Cow";
                if (Float.parseFloat(snf.getText().toString()) < Float.parseFloat(settingsDBAdapter.getSingleEntry("cow_snf_range_from"))) {
                    ((EditText) findViewById(R.id.rate_et)).setText("0.00");
                    ((EditText) findViewById(R.id.amount_et)).setText("0.00");
                    return;
                }
            } else {
                cattle_type = "Buffalo";
                if (Float.parseFloat(snf.getText().toString()) < Float.parseFloat(settingsDBAdapter.getSingleEntry("buffalo_snf_range_from"))) {
                    ((EditText) findViewById(R.id.rate_et)).setText("0.00");
                    ((EditText) findViewById(R.id.amount_et)).setText("0.00");
                    return;
                }
            }

            fat_value = Float.parseFloat(fat.getText().toString());
            snf_value = Float.parseFloat(snf.getText().toString());

            if (Float.parseFloat(fat.getText().toString()) > Float.parseFloat(settingsDBAdapter.getSingleEntry("buffalo_fat_range_to")))

                fat_value = Float.parseFloat(settingsDBAdapter.getSingleEntry("buffalo_fat_range_to"));
            if (Float.parseFloat(fat.getText().toString()) < Float.parseFloat(settingsDBAdapter.getSingleEntry("buffalo_fat_range_from"))) {

                if (Float.parseFloat(snf.getText().toString()) > Float.parseFloat(settingsDBAdapter.getSingleEntry("cow_snf_range_to"))) {
                    snf_value = Float.parseFloat(settingsDBAdapter.getSingleEntry("cow_snf_range_to"));
                }
            } else {
                if (Float.parseFloat(snf.getText().toString()) > Float.parseFloat(settingsDBAdapter.getSingleEntry("buffalo_snf_range_to"))) {
                    snf_value = Float.parseFloat(settingsDBAdapter.getSingleEntry("buffalo_snf_range_to"));
                }
            }
        } catch (NumberFormatException e) {

        }

        switch (chartType) {
            case "Formula":
                if (cattle_type == null) {

                } else if (cattle_type.equals("Cow"))
                    ((EditText) findViewById(R.id.rate_et)).setText(printableText.convert(Float.valueOf(((Float.parseFloat(std_cow_rate) * Float.parseFloat(per_cow_fat) * fat_value) / (Float.parseFloat(std_cow_fat) * 100)) + ((Float.parseFloat(std_cow_rate) * Float.parseFloat(per_cow_snf) * snf_value) / (Float.parseFloat(std_cow_snf) * 100))).toString(), 4, 2));
                else
                    ((EditText) findViewById(R.id.rate_et)).setText(printableText.convert(Float.valueOf(((Float.parseFloat(std_buffalo_rate) * Float.parseFloat(per_buffalo_fat) * fat_value) / (Float.parseFloat(std_buffalo_fat) * 100)) + ((Float.parseFloat(std_buffalo_rate) * Float.parseFloat(per_buffalo_snf) * snf_value) / (Float.parseFloat(std_buffalo_snf) * 100))).toString(), 4, 2));
                break;
            case "Manual":
            case "File":
            case "Online":
                rateChartDBAdapter = new RateChartDBAdapter(this);
                rateChartDBAdapter.open();
                String getRate = rateChartDBAdapter.getSingleEntry(String.valueOf(fat_value), String.valueOf(snf_value), rateChart);
                if (!(getRate.equals("RECORD DOES NOT EXIST.") || getRate.equals("ERROR!!")))
                    ((EditText) findViewById(R.id.rate_et)).setText(getRate);
                else
                    ((EditText) findViewById(R.id.rate_et)).setText("0.00");
                rateChartDBAdapter.close();
                break;
        }
        ((EditText) findViewById(R.id.amount_et)).setText(printableText.convert(Float.valueOf(Float.parseFloat(weight.getText().toString()) * Float.parseFloat(((EditText) findViewById(R.id.rate_et)).getText().toString())).toString(), 6, 2));

        switch (analyzer) {
            case "0":
                ((TextView) findViewById(R.id.fat_tv)).setText("Fat: (Manual) (" + cattle_type + ")");
                break;
            case "1":
                ((TextView) findViewById(R.id.fat_tv)).setText("Fat: (ECO) (" + cattle_type + ")");
                break;
            case "2":
                ((TextView) findViewById(R.id.fat_tv)).setText("Fat: (Lacto) (" + cattle_type + ")");
                break;
            case "3":
                ((TextView) findViewById(R.id.fat_tv)).setText("Fat: (Lacto+) (" + cattle_type + ")");
                break;
            case "4":
                ((TextView) findViewById(R.id.fat_tv)).setText("Fat: (Indiz) (" + cattle_type + ")");
                break;
            default:
                ((TextView) findViewById(R.id.fat_tv)).setText("" + "Fat: (" + cattle_type + ")");
        }
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Base Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.cmos.adpu/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Base Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app URL is correct.
                Uri.parse("android-app://com.cmos.adpu/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
