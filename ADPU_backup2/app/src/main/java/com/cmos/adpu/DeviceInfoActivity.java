package com.cmos.adpu;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DeviceInfoActivity extends Activity {
    SettingsDBAdapter settingsDBAdapter;
    private static final String DEVICE_INFO_ACTIVITY = "DeviceInfoActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_info);
        getActionBar().setTitle("Menu > Device Info");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        DataInput.object_selected = "DAIRY_CODE";
        BaseActivity.dataInput = new DataInput(this,
                (EditText)findViewById(R.id.s_dairycode_et),
                (EditText)findViewById(R.id.s_routecode_et),
                (EditText)findViewById(R.id.s_village_et),
                (EditText)findViewById(R.id.s_center_et),
                (Button)findViewById(R.id.s_save_d));
        settingsDBAdapter = new SettingsDBAdapter(getApplicationContext());
        settingsDBAdapter.open();
        /*settingsDBAdapter.updateEntry("dairycode", "AM");
        settingsDBAdapter.updateEntry("routecode", "DS");
        settingsDBAdapter.updateEntry("villagecode", "NS");
        settingsDBAdapter.updateEntry("centercode", "01");*/
        if(!(settingsDBAdapter.getSingleEntry("dairycode").equals("RECORD DOES NOT EXIST.") || settingsDBAdapter.getSingleEntry("dairycode").equals("ERROR!!")))
            ((EditText) findViewById(R.id.s_dairycode_et)).setText(settingsDBAdapter.getSingleEntry("dairycode"));
        if(!(settingsDBAdapter.getSingleEntry("routecode").equals("RECORD DOES NOT EXIST.") || settingsDBAdapter.getSingleEntry("routecode").equals("ERROR!!")))
            ((EditText) findViewById(R.id.s_routecode_et)).setText(settingsDBAdapter.getSingleEntry("routecode"));
        if(!(settingsDBAdapter.getSingleEntry("villagecode").equals("RECORD DOES NOT EXIST.") || settingsDBAdapter.getSingleEntry("villagecode").equals("ERROR!!")))
            ((EditText) findViewById(R.id.s_village_et)).setText(settingsDBAdapter.getSingleEntry("villagecode"));
        if(!(settingsDBAdapter.getSingleEntry("centercode").equals("RECORD DOES NOT EXIST.") || settingsDBAdapter.getSingleEntry("centercode").equals("ERROR!!")))
            ((EditText) findViewById(R.id.s_center_et)).setText(settingsDBAdapter.getSingleEntry("centercode"));
        settingsDBAdapter.close();

        findViewById(R.id.s_save_d).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((EditText) findViewById(R.id.s_dairycode_et)).getText().toString().length() == 3 &&
                        ((EditText) findViewById(R.id.s_routecode_et)).getText().toString().length() == 3 &&
                        ((EditText) findViewById(R.id.s_village_et)).getText().toString().length() == 3 &&
                        ((EditText) findViewById(R.id.s_center_et)).getText().toString().length() == 3){
                    settingsDBAdapter = new SettingsDBAdapter(getApplicationContext());
                    settingsDBAdapter.open();
                    settingsDBAdapter.updateEntry("dairycode", ((EditText) findViewById(R.id.s_dairycode_et)).getText().toString());
                    settingsDBAdapter.updateEntry("routecode", ((EditText) findViewById(R.id.s_routecode_et)).getText().toString());
                    settingsDBAdapter.updateEntry("villagecode", ((EditText) findViewById(R.id.s_village_et)).getText().toString());
                    settingsDBAdapter.updateEntry("centercode", ((EditText) findViewById(R.id.s_center_et)).getText().toString());
                    settingsDBAdapter.close();
                    Toast.makeText(DeviceInfoActivity.this, "Successfully done", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(DeviceInfoActivity.this, "Please fill correct codes!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
