package com.cmos.adpu;

import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class DuplicateSlipActivity extends Activity {
    CollectionsDBAdapter collectionsDBAdapter;
    DatePicker dateDP;
    PrintableText printableText;
    SettingsDBAdapter settingsDBAdapter;
    EditText code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_duplicate_slip);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        collectionsDBAdapter = new CollectionsDBAdapter(getApplicationContext());
        collectionsDBAdapter.open();
        DataInput.object_selected = "CODE_DS";
        code = (EditText)findViewById(R.id.ds_code);
        ((EditText)findViewById(R.id.ds_shift)).setText("Evening");
        final Calendar c = Calendar.getInstance();
        final int maxYear = c.get(Calendar.YEAR);
        final int maxMonth = c.get(Calendar.MONTH);
        final int maxDay = c.get(Calendar.DAY_OF_MONTH);
        dateDP = ((DatePicker)findViewById(R.id.ds_date_dp));
        dateDP.init(maxYear, maxMonth, maxDay, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if (dayOfMonth < 10)
                    ((EditText) findViewById(R.id.ds_day)).setText("0" + ((Integer) dayOfMonth).toString());
                else
                    ((EditText) findViewById(R.id.ds_day)).setText(((Integer) dayOfMonth).toString());
                if (monthOfYear + 1 < 10)
                    ((EditText) findViewById(R.id.ds_month)).setText("0" + ((Integer) (monthOfYear + 1)).toString());
                else
                    ((EditText) findViewById(R.id.ds_month)).setText(((Integer) (monthOfYear + 1)).toString());
                ((EditText) findViewById(R.id.ds_year)).setText(((Integer) year).toString());
                if (year > maxYear)
                    view.updateDate(maxYear, maxMonth, maxDay);

                if (monthOfYear > maxMonth && year == maxYear)
                    view.updateDate(maxYear, maxMonth, maxDay);

                if (dayOfMonth > maxDay && year == maxYear && monthOfYear == maxMonth)
                    view.updateDate(maxYear, maxMonth, maxDay);
            }
        });
        BaseActivity.dataInput = new DataInput(this, ((EditText)findViewById(R.id.ds_code)), dateDP, (EditText)findViewById(R.id.ds_day), (EditText)findViewById(R.id.ds_month), (EditText)findViewById(R.id.ds_year), (EditText)findViewById(R.id.ds_shift), (Button)findViewById(R.id.ds_print_preview));
        findViewById(R.id.ds_print_preview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = getResult();
                if (!(result.equals("RECORD DOES NOT EXIST.")) && !(result.equals("ERROR!!")) && !(result.equals(""))) {
                    BaseActivity.print_data = result;
                    DataInput.data_input = false;
                    BaseActivity.i = 0;
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            BaseActivity.characteristicTX.setValue("\r\n");
                            BaseActivity.mBluetoothLeService.writeCharacteristic(BaseActivity.characteristicTX);
                        }
                    }, 100);
                }
            }
        });
        ((EditText)findViewById(R.id.ds_code)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateResult();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        ((EditText)findViewById(R.id.ds_day)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateResult();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        ((EditText)findViewById(R.id.ds_month)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateResult();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        ((EditText)findViewById(R.id.ds_year
        )).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateResult();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        ((EditText)findViewById(R.id.ds_shift)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateResult();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        ((EditText)findViewById(R.id.ds_code)).setText("1");
    }

    @Override
    public void onResume() {
        super.onResume();
        code = (EditText)findViewById(R.id.ml_code);
        BaseActivity.dataInput = new DataInput(this, ((EditText)findViewById(R.id.ds_code)), dateDP, (EditText)findViewById(R.id.ds_day), (EditText)findViewById(R.id.ds_month), (EditText)findViewById(R.id.ds_year), (EditText)findViewById(R.id.ds_shift), (Button)findViewById(R.id.ds_print_preview));
        if(dateDP.getDayOfMonth() < 10)
            ((EditText)findViewById(R.id.ds_day)).setText("0" + ((Integer) dateDP.getDayOfMonth()).toString());
        else
            ((EditText)findViewById(R.id.ds_day)).setText(((Integer) dateDP.getDayOfMonth()).toString());
        if(dateDP.getMonth() + 1 < 10)
            ((EditText)findViewById(R.id.ds_month)).setText("0" + ((Integer) (dateDP.getMonth() + 1)).toString());
        else
            ((EditText)findViewById(R.id.ds_month)).setText(((Integer) (dateDP.getMonth() + 1)).toString());
        ((EditText)findViewById(R.id.ds_year)).setText(((Integer) dateDP.getYear()).toString());
        findViewById(R.id.ds_code).setBackgroundResource(R.drawable.focused);
        findViewById(R.id.ds_day).setBackgroundResource(R.drawable.unfocused);
        findViewById(R.id.ds_month).setBackgroundResource(R.drawable.unfocused);
        findViewById(R.id.ds_year).setBackgroundResource(R.drawable.unfocused);
        findViewById(R.id.ds_shift).setBackgroundResource(R.drawable.unfocused);
        DataInput.object_selected = "CODE_DS";
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        collectionsDBAdapter.close();
    }

    public void updateResult(){
        ((TextView)findViewById(R.id.ds_result)).setText(getResult());
    }

    public String getResult() {
        String result = collectionsDBAdapter.getSingleEntry(((EditText) findViewById(R.id.ds_code)).getText().toString(), ((EditText) findViewById(R.id.ds_day)).getText().toString() + "/" + ((EditText) findViewById(R.id.ds_month)).getText().toString() + "/" + ((EditText) findViewById(R.id.ds_year)).getText().toString() + " " + ((EditText) findViewById(R.id.ds_shift)).getText().toString());
        if (!(result.equals("RECORD DOES NOT EXIST.")) && !(result.equals("ERROR!!")) && !(result.equals(""))) {
            String[] result1 = result.split(",");
            settingsDBAdapter = new SettingsDBAdapter(getApplicationContext());
            settingsDBAdapter.open();
            final String print_text = "Duplicate Slip\r\n" + settingsDBAdapter.getSingleEntry("dairyname") +
                    "\r\nDate: " + ((EditText) findViewById(R.id.ds_day)).getText().toString() + "/" + ((EditText) findViewById(R.id.ds_month)).getText().toString() + "/" + ((EditText) findViewById(R.id.ds_year)).getText().toString() +
                    "\r\nShift: "  + ((EditText) findViewById(R.id.ds_shift)).getText().toString() +
                    "\r\nCode: " + ((EditText) findViewById(R.id.ds_code)).getText().toString() +
                    "\r\nName: " + result1[0] +
                    "\r\nQuantity: " + result1[2] + "KG" +
                    "\r\nFat: " + result1[3] + "%" +
                    "\r\nSNF: " + result1[4] + "%" +
                    "\r\nRate: " + result1[5] +
                    "\r\nAmount: " + result1[6] +
                    "\r\n--------------------------------" +
                    "\r\n" + settingsDBAdapter.getSingleEntry("footer") + "\r\n\r\n\r\n\r\n";
            settingsDBAdapter.close();
            return print_text;
        }
        else
            return result;
    }
}
