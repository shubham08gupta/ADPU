package com.cmos.adpu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AddRateChartActivity extends Activity {
    SettingsDBAdapter settingsDBAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_rate_chart);
        getActionBar().setTitle("RATE CHARTS");
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        BaseActivity.dataInput = new DataInput(this,
                (LinearLayout) findViewById(R.id.arc_rate_chart_1_ll),
                (LinearLayout) findViewById(R.id.arc_rate_chart_2_ll),
                (LinearLayout) findViewById(R.id.arc_rate_chart_3_ll),
                (Button) findViewById(R.id.rc1_show_btn),
                (Button) findViewById(R.id.rc2_show_btn),
                (Button) findViewById(R.id.rc3_show_btn),
                (Button) findViewById(R.id.rc1_edit_btn),
                (Button) findViewById(R.id.rc2_edit_btn),
                (Button) findViewById(R.id.rc3_edit_btn));

        DataInput.object_selected = "SHOW1";
        settingsDBAdapter = new SettingsDBAdapter(this);
        settingsDBAdapter.open();

        ((TextView) findViewById(R.id.rc1_type_tv)).setText(settingsDBAdapter.getSingleEntry("ratechart1"));
        ((TextView) findViewById(R.id.rc1_date_tv)).setText(settingsDBAdapter.getSingleEntry("rc1_date"));
        ((TextView) findViewById(R.id.rc2_type_tv)).setText(settingsDBAdapter.getSingleEntry("ratechart2"));
        ((TextView) findViewById(R.id.rc2_date_tv)).setText(settingsDBAdapter.getSingleEntry("rc2_date"));
        ((TextView) findViewById(R.id.rc3_type_tv)).setText(settingsDBAdapter.getSingleEntry("ratechart3"));
        ((TextView) findViewById(R.id.rc3_date_tv)).setText(settingsDBAdapter.getSingleEntry("rc3_date"));

        settingsDBAdapter.close();

        findViewById(R.id.rc1_show_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
                Intent intent = new Intent(getApplicationContext(), ShowRateChartActivity.class);
                intent.putExtra("RATE_CHART", "1");
                startActivity(intent);
            }
        });
        findViewById(R.id.rc2_show_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
                Intent intent = new Intent(getApplicationContext(), ShowRateChartActivity.class);
                intent.putExtra("RATE_CHART", "2");
                startActivity(intent);
            }
        });
        findViewById(R.id.rc3_show_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
                Intent intent = new Intent(getApplicationContext(), ShowRateChartActivity.class);
                intent.putExtra("RATE_CHART", "3");
                startActivity(intent);
            }
        });
        findViewById(R.id.rc1_edit_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
                Intent intent = new Intent(getApplicationContext(), EditRateChartActivity.class);
                intent.putExtra("RATE_CHART", "1");
                startActivity(intent);
            }
        });
        findViewById(R.id.rc2_edit_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
                Intent intent = new Intent(getApplicationContext(), EditRateChartActivity.class);
                intent.putExtra("RATE_CHART", "2");
                startActivity(intent);
            }
        });
        findViewById(R.id.rc3_edit_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
                Intent intent = new Intent(getApplicationContext(), EditRateChartActivity.class);
                intent.putExtra("RATE_CHART", "3");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        BaseActivity.dataInput = new DataInput(this,
                (LinearLayout) findViewById(R.id.arc_rate_chart_1_ll),
                (LinearLayout) findViewById(R.id.arc_rate_chart_2_ll),
                (LinearLayout) findViewById(R.id.arc_rate_chart_3_ll),
                (Button) findViewById(R.id.rc1_show_btn),
                (Button) findViewById(R.id.rc2_show_btn),
                (Button) findViewById(R.id.rc3_show_btn),
                (Button) findViewById(R.id.rc1_edit_btn),
                (Button) findViewById(R.id.rc2_edit_btn),
                (Button) findViewById(R.id.rc3_edit_btn));

        DataInput.object_selected = "SHOW1";
        settingsDBAdapter = new SettingsDBAdapter(this);
        settingsDBAdapter.open();

        ((TextView) findViewById(R.id.rc1_type_tv)).setText(settingsDBAdapter.getSingleEntry("ratechart1"));
        ((TextView) findViewById(R.id.rc1_date_tv)).setText(settingsDBAdapter.getSingleEntry("rc1_date"));
        ((TextView) findViewById(R.id.rc2_type_tv)).setText(settingsDBAdapter.getSingleEntry("ratechart2"));
        ((TextView) findViewById(R.id.rc2_date_tv)).setText(settingsDBAdapter.getSingleEntry("rc2_date"));
        ((TextView) findViewById(R.id.rc3_type_tv)).setText(settingsDBAdapter.getSingleEntry("ratechart3"));
        ((TextView) findViewById(R.id.rc3_date_tv)).setText(settingsDBAdapter.getSingleEntry("rc3_date"));
        settingsDBAdapter.close();
    }

    public void refresh() {
        findViewById(R.id.arc_rate_chart_1_ll).setBackgroundResource(R.drawable.focused);
        findViewById(R.id.arc_rate_chart_2_ll).setBackgroundResource(R.drawable.unfocused);
        findViewById(R.id.arc_rate_chart_3_ll).setBackgroundResource(R.drawable.unfocused);
        findViewById(R.id.rc1_show_btn).setBackgroundResource(R.drawable.btn_focused);
        findViewById(R.id.rc2_show_btn).setBackgroundResource(R.drawable.btn_unfocused);
        findViewById(R.id.rc3_show_btn).setBackgroundResource(R.drawable.btn_unfocused);
        findViewById(R.id.rc1_edit_btn).setBackgroundResource(R.drawable.btn_unfocused);
        findViewById(R.id.rc2_edit_btn).setBackgroundResource(R.drawable.btn_unfocused);
        findViewById(R.id.rc3_edit_btn).setBackgroundResource(R.drawable.btn_unfocused);
    }
}
