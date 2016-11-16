package com.cmos.adpu;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;

public class EditRateChartActivity extends Activity {
    SettingsDBAdapter settingsDBAdapter;
    DatePicker dateDP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.activity_edit_rate_chart);
        DataInput.object_selected = "DAY_ERC";
        BaseActivity.dataInput = new DataInput(this, (DatePicker)findViewById(R.id.erc_date_dp), (EditText)findViewById(R.id.day_erc), (EditText)findViewById(R.id.month_erc), (EditText)findViewById(R.id.year_erc), (EditText)findViewById(R.id.select_type_erc), (Button)findViewById(R.id.erc_info_btn));
        getActionBar().setTitle("Settings Rate Chart " + getIntent().getStringExtra("RATE_CHART"));
        settingsDBAdapter = new SettingsDBAdapter(this);
        settingsDBAdapter.open();
        String act_date = settingsDBAdapter.getSingleEntry("rc"+ getIntent().getStringExtra("RATE_CHART") +"_date");
        final String rate_type = settingsDBAdapter.getSingleEntry("ratechart" + getIntent().getStringExtra("RATE_CHART"));
        ((EditText)findViewById(R.id.select_type_erc)).setText(rate_type);
        final int act_day = Integer.parseInt(act_date.substring(0, 2));
        final int act_month = Integer.parseInt(act_date.substring(3, 5));
        final int act_year = Integer.parseInt(act_date.substring(6, 10));
        dateDP = ((DatePicker)findViewById(R.id.erc_date_dp));
        dateDP.init(act_year, act_month - 1, act_day, new DatePicker.OnDateChangedListener() {

            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                if (dayOfMonth < 10)
                    ((EditText) findViewById(R.id.day_erc)).setText("0" + ((Integer) dayOfMonth).toString());
                else
                    ((EditText) findViewById(R.id.day_erc)).setText(((Integer) dayOfMonth).toString());
                if (monthOfYear + 1 < 10)
                    ((EditText) findViewById(R.id.month_erc)).setText("0" + ((Integer) (monthOfYear + 1)).toString());
                else
                    ((EditText) findViewById(R.id.month_erc)).setText(((Integer) (monthOfYear + 1)).toString());
                ((EditText) findViewById(R.id.year_erc)).setText(((Integer) year).toString());
            }
        });
        settingsDBAdapter.close();
        findViewById(R.id.erc_info_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsDBAdapter = new SettingsDBAdapter(getApplicationContext());
                settingsDBAdapter.open();
                settingsDBAdapter.updateEntry("rc" + getIntent().getStringExtra("RATE_CHART") + "_date", ((EditText) findViewById(R.id.day_erc)).getText().toString() + "/" + ((EditText) findViewById(R.id.month_erc)).getText().toString() + "/" + ((EditText) findViewById(R.id.year_erc)).getText().toString());
                settingsDBAdapter.updateEntry("ratechart" + getIntent().getStringExtra("RATE_CHART"), ((EditText)findViewById(R.id.select_type_erc)).getText().toString());
                settingsDBAdapter.close();
                Intent intent;
                switch (((EditText)findViewById(R.id.select_type_erc)).getText().toString()) {
                    case "Formula":
                        intent = new Intent(getApplicationContext(), FormulaRateChartActivity.class);
                        break;
                    case "Manual":
                        intent = new Intent(getApplicationContext(), ManualRateChartActivity.class);
                        break;
                    case "File":
                        intent = new Intent(getApplicationContext(), FileRateChartActivity.class);
                        break;
                    case "Online":
                        intent = new Intent(getApplicationContext(), RateChartOnlineActivity.class);
                        break;
                    default:
                        intent = new Intent(getApplicationContext(), ShowRateChartActivity.class);
                }
                intent.putExtra("RATE_CHART", getIntent().getStringExtra("RATE_CHART"));
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();
        DataInput.object_selected = "DAY_ERC";
        BaseActivity.dataInput = new DataInput(this, (DatePicker)findViewById(R.id.erc_date_dp), (EditText)findViewById(R.id.day_erc), (EditText)findViewById(R.id.month_erc), (EditText)findViewById(R.id.year_erc), (EditText)findViewById(R.id.select_type_erc), (Button)findViewById(R.id.erc_info_btn));
        if(dateDP.getDayOfMonth() < 10)
            ((EditText)findViewById(R.id.day_erc)).setText("0" + ((Integer) dateDP.getDayOfMonth()).toString());
        else
            ((EditText)findViewById(R.id.day_erc)).setText(((Integer) dateDP.getDayOfMonth()).toString());
        if(dateDP.getMonth() + 1 < 10)
            ((EditText)findViewById(R.id.month_erc)).setText("0" + ((Integer) (dateDP.getMonth() + 1)).toString());
        else
            ((EditText)findViewById(R.id.month_erc)).setText(((Integer) (dateDP.getMonth() + 1)).toString());
        ((EditText) findViewById(R.id.year_erc)).setText(((Integer) dateDP.getYear()).toString());
    }
}
