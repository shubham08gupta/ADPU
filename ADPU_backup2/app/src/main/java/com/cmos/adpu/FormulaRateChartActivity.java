package com.cmos.adpu;

import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class FormulaRateChartActivity extends Activity {
    SettingsDBAdapter settingsDBAdapter;
    String rate_chart;
    PrintableText printableText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formula_rate_chart);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        DataInput.object_selected = "STD_COW_RATE";
        BaseActivity.dataInput = new DataInput(this, (EditText)findViewById(R.id.frc_std_cow_rate_et), (EditText)findViewById(R.id.frc_per_cow_fat_et), (EditText)findViewById(R.id.frc_std_cow_fat_et), (EditText)findViewById(R.id.frc_per_cow_snf_et), (EditText)findViewById(R.id.frc_std_cow_snf_et), (EditText)findViewById(R.id.frc_std_buffalo_rate_et), (EditText)findViewById(R.id.frc_per_buffalo_fat_et), (EditText)findViewById(R.id.frc_std_buffalo_fat_et), (EditText)findViewById(R.id.frc_per_buffalo_snf_et), (EditText)findViewById(R.id.frc_std_buffalo_snf_et), (Button)findViewById(R.id.frc_save_btn));
        printableText = new PrintableText();
        rate_chart = getIntent().getStringExtra("RATE_CHART");
        settingsDBAdapter = new SettingsDBAdapter(this);
        settingsDBAdapter.open();
        ((EditText)findViewById(R.id.frc_std_cow_rate_et)).setText(settingsDBAdapter.getSingleEntry("rc" + rate_chart + "_std_cow_rate"));
        ((EditText)findViewById(R.id.frc_per_cow_fat_et)).setText(settingsDBAdapter.getSingleEntry("rc" + rate_chart + "_per_cow_fat"));
        ((EditText)findViewById(R.id.frc_std_cow_fat_et)).setText(settingsDBAdapter.getSingleEntry("rc" + rate_chart + "_std_cow_fat"));
        ((EditText)findViewById(R.id.frc_per_cow_snf_et)).setText(settingsDBAdapter.getSingleEntry("rc" + rate_chart + "_per_cow_snf"));
        ((EditText)findViewById(R.id.frc_std_cow_snf_et)).setText(settingsDBAdapter.getSingleEntry("rc" + rate_chart + "_std_cow_snf"));
        ((EditText)findViewById(R.id.frc_std_buffalo_rate_et)).setText(settingsDBAdapter.getSingleEntry("rc" + rate_chart + "_std_buffalo_rate"));
        ((EditText)findViewById(R.id.frc_per_buffalo_fat_et)).setText(settingsDBAdapter.getSingleEntry("rc" + rate_chart + "_per_buffalo_fat"));
        ((EditText)findViewById(R.id.frc_std_buffalo_fat_et)).setText(settingsDBAdapter.getSingleEntry("rc" + rate_chart + "_std_buffalo_fat"));
        ((EditText)findViewById(R.id.frc_per_buffalo_snf_et)).setText(settingsDBAdapter.getSingleEntry("rc" + rate_chart + "_per_buffalo_snf"));
        ((EditText)findViewById(R.id.frc_std_buffalo_snf_et)).setText(settingsDBAdapter.getSingleEntry("rc" + rate_chart + "_std_buffalo_snf"));
        ((TextView)findViewById(R.id.frc_kg_fat_cow_et)).setText(printableText.convert(String.valueOf((Float.valueOf(((EditText) findViewById(R.id.frc_std_cow_rate_et)).getText().toString()) * Float.valueOf(((EditText) findViewById(R.id.frc_per_cow_fat_et)).getText().toString())) / (Float.valueOf(((EditText) findViewById(R.id.frc_std_cow_fat_et)).getText().toString()))), 4, 2));
        ((TextView)findViewById(R.id.frc_kg_snf_cow_et)).setText(printableText.convert(String.valueOf((Float.valueOf(((EditText) findViewById(R.id.frc_std_cow_rate_et)).getText().toString()) * Float.valueOf(((EditText) findViewById(R.id.frc_per_cow_snf_et)).getText().toString())) / (Float.valueOf(((EditText) findViewById(R.id.frc_std_cow_snf_et)).getText().toString()))), 4, 2));
        ((TextView)findViewById(R.id.frc_kg_fat_buffalo_et)).setText(printableText.convert(String.valueOf((Float.valueOf(((EditText) findViewById(R.id.frc_std_buffalo_rate_et)).getText().toString()) * Float.valueOf(((EditText) findViewById(R.id.frc_per_buffalo_fat_et)).getText().toString())) / (Float.valueOf(((EditText) findViewById(R.id.frc_std_buffalo_fat_et)).getText().toString()))), 4, 2));
        ((TextView)findViewById(R.id.frc_kg_snf_buffalo_et)).setText(printableText.convert(String.valueOf((Float.valueOf(((EditText) findViewById(R.id.frc_std_buffalo_rate_et)).getText().toString()) * Float.valueOf(((EditText) findViewById(R.id.frc_per_buffalo_snf_et)).getText().toString())) / (Float.valueOf(((EditText) findViewById(R.id.frc_std_buffalo_snf_et)).getText().toString()))), 4, 2));
        settingsDBAdapter.close();

        findViewById(R.id.frc_save_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                settingsDBAdapter = new SettingsDBAdapter(getApplicationContext());
                settingsDBAdapter.open();
                settingsDBAdapter.updateEntry("rc" + rate_chart + "_std_cow_rate", ((EditText) findViewById(R.id.frc_std_cow_rate_et)).getText().toString());
                settingsDBAdapter.updateEntry("rc" + rate_chart + "_per_cow_fat", ((EditText) findViewById(R.id.frc_per_cow_fat_et)).getText().toString());
                settingsDBAdapter.updateEntry("rc" + rate_chart + "_std_cow_fat", ((EditText) findViewById(R.id.frc_std_cow_fat_et)).getText().toString());
                settingsDBAdapter.updateEntry("rc" + rate_chart + "_per_cow_snf", ((EditText) findViewById(R.id.frc_per_cow_snf_et)).getText().toString());
                settingsDBAdapter.updateEntry("rc" + rate_chart + "_std_cow_snf", ((EditText) findViewById(R.id.frc_std_cow_snf_et)).getText().toString());
                settingsDBAdapter.updateEntry("rc" + rate_chart + "_std_buffalo_rate", ((EditText) findViewById(R.id.frc_std_buffalo_rate_et)).getText().toString());
                settingsDBAdapter.updateEntry("rc" + rate_chart + "_per_buffalo_fat", ((EditText) findViewById(R.id.frc_per_buffalo_fat_et)).getText().toString());
                settingsDBAdapter.updateEntry("rc" + rate_chart + "_std_buffalo_fat", ((EditText) findViewById(R.id.frc_std_buffalo_fat_et)).getText().toString());
                settingsDBAdapter.updateEntry("rc" + rate_chart + "_per_buffalo_snf", ((EditText) findViewById(R.id.frc_per_buffalo_snf_et)).getText().toString());
                settingsDBAdapter.updateEntry("rc" + rate_chart + "_std_buffalo_snf", ((EditText) findViewById(R.id.frc_std_buffalo_snf_et)).getText().toString());
                settingsDBAdapter.close();
                Toast.makeText(FormulaRateChartActivity.this, "Successfully Updated!!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        ((EditText)findViewById(R.id.frc_std_cow_rate_et)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calcKGFatSNF();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ((EditText)findViewById(R.id.frc_per_cow_fat_et)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calcKGFatSNF();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ((EditText)findViewById(R.id.frc_std_cow_fat_et)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calcKGFatSNF();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ((EditText)findViewById(R.id.frc_per_cow_snf_et)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calcKGFatSNF();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ((EditText)findViewById(R.id.frc_std_cow_snf_et)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calcKGFatSNF();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ((EditText)findViewById(R.id.frc_std_buffalo_rate_et)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calcKGFatSNF();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ((EditText)findViewById(R.id.frc_per_buffalo_fat_et)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calcKGFatSNF();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ((EditText)findViewById(R.id.frc_std_buffalo_fat_et)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calcKGFatSNF();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ((EditText)findViewById(R.id.frc_per_buffalo_snf_et)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calcKGFatSNF();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        ((EditText)findViewById(R.id.frc_std_buffalo_snf_et)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                calcKGFatSNF();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void calcKGFatSNF() {
        float std_cow_rate = Float.valueOf(((EditText) findViewById(R.id.frc_std_cow_rate_et)).getText().toString());
        float per_cow_fat = Float.valueOf(((EditText) findViewById(R.id.frc_per_cow_fat_et)).getText().toString());
        float std_cow_fat = Float.valueOf(((EditText) findViewById(R.id.frc_std_cow_fat_et)).getText().toString());
        float per_cow_snf = Float.valueOf(((EditText) findViewById(R.id.frc_per_cow_snf_et)).getText().toString());
        float std_cow_snf = Float.valueOf(((EditText) findViewById(R.id.frc_std_cow_snf_et)).getText().toString());
        float std_buffalo_rate = Float.valueOf(((EditText) findViewById(R.id.frc_std_buffalo_rate_et)).getText().toString());
        float per_buffalo_fat = Float.valueOf(((EditText) findViewById(R.id.frc_per_buffalo_fat_et)).getText().toString());
        float std_buffalo_fat = Float.valueOf(((EditText) findViewById(R.id.frc_std_buffalo_fat_et)).getText().toString());
        float per_buffalo_snf = Float.valueOf(((EditText) findViewById(R.id.frc_per_buffalo_snf_et)).getText().toString());
        float std_buffalo_snf = Float.valueOf(((EditText) findViewById(R.id.frc_std_buffalo_snf_et)).getText().toString());
        if(std_cow_fat != 0.0f && std_cow_snf !=0.0f && std_buffalo_fat != 0.0f && std_buffalo_snf != 0.0f){
            ((TextView)findViewById(R.id.frc_kg_fat_cow_et)).setText(printableText.convert(String.valueOf((std_cow_rate * per_cow_fat) / (std_cow_fat)), 4, 2));
            ((TextView)findViewById(R.id.frc_kg_snf_cow_et)).setText(printableText.convert(String.valueOf((std_cow_rate * per_cow_snf) / (std_cow_snf)), 4, 2));
            ((TextView)findViewById(R.id.frc_kg_fat_buffalo_et)).setText(printableText.convert(String.valueOf((std_buffalo_rate * per_buffalo_fat) / (std_buffalo_fat)), 4, 2));
            ((TextView)findViewById(R.id.frc_kg_snf_buffalo_et)).setText(printableText.convert(String.valueOf((std_buffalo_rate * per_buffalo_snf) / (std_buffalo_snf)), 4, 2));
        }
        else {
            ((TextView)findViewById(R.id.frc_kg_fat_cow_et)).setText("0.0");
            ((TextView)findViewById(R.id.frc_kg_snf_cow_et)).setText("0.0");
            ((TextView)findViewById(R.id.frc_kg_fat_buffalo_et)).setText("0.0");
            ((TextView)findViewById(R.id.frc_kg_snf_buffalo_et)).setText("0.0");
        }
    }
}
