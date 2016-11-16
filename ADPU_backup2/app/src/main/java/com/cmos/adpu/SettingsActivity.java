package com.cmos.adpu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class SettingsActivity extends Activity {
    SettingsDBAdapter settingsDBAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getActionBar().setTitle("Menu > Settings");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        DataInput.object_selected = "COW_FAT_FROM";
        BaseActivity.dataInput = new DataInput(this,
                (EditText) findViewById(R.id.s_cow_fat_range_from_et),
                (EditText) findViewById(R.id.s_cow_fat_range_to_et),
                (EditText) findViewById(R.id.s_cow_snf_range_from_et),
                (EditText) findViewById(R.id.s_cow_snf_range_to_et),
                (EditText) findViewById(R.id.s_buffalo_fat_range_from_et),
                (EditText) findViewById(R.id.s_buffalo_fat_range_to_et),
                (EditText) findViewById(R.id.s_buffalo_snf_range_from_et),
                (EditText) findViewById(R.id.s_buffalo_snf_range_to_et),
                (Spinner) findViewById(R.id.s_analyzer_dd),
                (Spinner) findViewById(R.id.s_weight_dd),
                (Spinner) findViewById(R.id.s_printer_dd),
                (Spinner) findViewById(R.id.s_shifttime_dd),
                (Spinner) findViewById(R.id.s_measurementunit_dd),
                (RelativeLayout) findViewById(R.id.s_analyzer_rl),
                (RelativeLayout) findViewById(R.id.s_weight_rl),
                (RelativeLayout) findViewById(R.id.s_printer_rl),
                (RelativeLayout) findViewById(R.id.s_shifttime_rl),
                (RelativeLayout) findViewById(R.id.s_measurementunit_rl),
                (EditText) findViewById(R.id.s_dairyname_dd),
                (EditText) findViewById(R.id.s_footer_dd),
                (Button) findViewById(R.id.s_save_b));
        List<String> analyzers = new ArrayList<>();
        analyzers.add("Manual");
        analyzers.add("ECO Milk");
        analyzers.add("Lacto Scan");
        analyzers.add("Lacto+");
        analyzers.add("Indiz");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, analyzers);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((Spinner) findViewById(R.id.s_analyzer_dd)).setAdapter(arrayAdapter);
        List<String> weight = new ArrayList<>();
        weight.add("Manual");
        weight.add("Automatic");
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, weight);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((Spinner) findViewById(R.id.s_weight_dd)).setAdapter(arrayAdapter);
        List<String> printer = new ArrayList<>();
        printer.add("Off");
        printer.add("Thermal Printer");
        printer.add("PNP64");
        printer.add("Parallel Printer");
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, printer);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((Spinner) findViewById(R.id.s_printer_dd)).setAdapter(arrayAdapter);
        List<String> shifttime = new ArrayList<>();
        shifttime.add("10:00");
        shifttime.add("11:00");
        shifttime.add("12:00");
        shifttime.add("13:00");
        shifttime.add("14:00");
        shifttime.add("15:00");
        shifttime.add("16:00");
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, shifttime);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((Spinner) findViewById(R.id.s_shifttime_dd)).setAdapter(arrayAdapter);
        List<String> measurementunit = new ArrayList<>();
        measurementunit.add("Kilogram");
        measurementunit.add("Litre");
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, measurementunit);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        ((Spinner) findViewById(R.id.s_measurementunit_dd)).setAdapter(arrayAdapter);
        settingsDBAdapter = new SettingsDBAdapter(getApplicationContext());
        settingsDBAdapter.open();
        //settingsDBAdapter.delete_table();
        if (!(settingsDBAdapter.getSingleEntry("cow_fat_range_to").equals("RECORD DOES NOT EXIST.") || settingsDBAdapter.getSingleEntry("cow_fat_range_to").equals("ERROR!!")))
            ((TextView) findViewById(R.id.s_cow_fat_range_to_et)).setText(settingsDBAdapter.getSingleEntry("cow_fat_range_to"));
        if (!(settingsDBAdapter.getSingleEntry("cow_snf_range_to").equals("RECORD DOES NOT EXIST.") || settingsDBAdapter.getSingleEntry("cow_snf_range_to").equals("ERROR!!")))
            ((TextView) findViewById(R.id.s_cow_snf_range_to_et)).setText(settingsDBAdapter.getSingleEntry("cow_snf_range_to"));
        if (!(settingsDBAdapter.getSingleEntry("cow_fat_range_from").equals("RECORD DOES NOT EXIST.") || settingsDBAdapter.getSingleEntry("cow_fat_range_from").equals("ERROR!!")))
            ((TextView) findViewById(R.id.s_cow_fat_range_from_et)).setText(settingsDBAdapter.getSingleEntry("cow_fat_range_from"));
        if (!(settingsDBAdapter.getSingleEntry("cow_snf_range_from").equals("RECORD DOES NOT EXIST.") || settingsDBAdapter.getSingleEntry("cow_snf_range_from").equals("ERROR!!")))
            ((TextView) findViewById(R.id.s_cow_snf_range_from_et)).setText(settingsDBAdapter.getSingleEntry("cow_snf_range_from"));
        if (!(settingsDBAdapter.getSingleEntry("buffalo_fat_range_to").equals("RECORD DOES NOT EXIST.") || settingsDBAdapter.getSingleEntry("buffalo_fat_range_to").equals("ERROR!!")))
            ((TextView) findViewById(R.id.s_buffalo_fat_range_to_et)).setText(settingsDBAdapter.getSingleEntry("buffalo_fat_range_to"));
        if (!(settingsDBAdapter.getSingleEntry("buffalo_snf_range_to").equals("RECORD DOES NOT EXIST.") || settingsDBAdapter.getSingleEntry("buffalo_snf_range_to").equals("ERROR!!")))
            ((TextView) findViewById(R.id.s_buffalo_snf_range_to_et)).setText(settingsDBAdapter.getSingleEntry("buffalo_snf_range_to"));
        if (!(settingsDBAdapter.getSingleEntry("buffalo_fat_range_from").equals("RECORD DOES NOT EXIST.") || settingsDBAdapter.getSingleEntry("buffalo_fat_range_from").equals("ERROR!!")))
            ((TextView) findViewById(R.id.s_buffalo_fat_range_from_et)).setText(settingsDBAdapter.getSingleEntry("buffalo_fat_range_from"));
        if (!(settingsDBAdapter.getSingleEntry("buffalo_snf_range_from").equals("RECORD DOES NOT EXIST.") || settingsDBAdapter.getSingleEntry("buffalo_snf_range_from").equals("ERROR!!")))
            ((TextView) findViewById(R.id.s_buffalo_snf_range_from_et)).setText(settingsDBAdapter.getSingleEntry("buffalo_snf_range_from"));
        if (!(settingsDBAdapter.getSingleEntry("analyzer").equals("RECORD DOES NOT EXIST.") || settingsDBAdapter.getSingleEntry("analyzer").equals("ERROR!!")))
            ((Spinner) findViewById(R.id.s_analyzer_dd)).setSelection(Integer.parseInt(settingsDBAdapter.getSingleEntry("analyzer")));
        if (!(settingsDBAdapter.getSingleEntry("printer").equals("RECORD DOES NOT EXIST.") || settingsDBAdapter.getSingleEntry("printer").equals("ERROR!!")))
            ((Spinner) findViewById(R.id.s_printer_dd)).setSelection(Integer.parseInt(settingsDBAdapter.getSingleEntry("printer")));
        if (!(settingsDBAdapter.getSingleEntry("shifttime").equals("RECORD DOES NOT EXIST.") || settingsDBAdapter.getSingleEntry("shifttime").equals("ERROR!!")))
            ((Spinner) findViewById(R.id.s_shifttime_dd)).setSelection(Integer.parseInt(settingsDBAdapter.getSingleEntry("shifttime")));
        if (!(settingsDBAdapter.getSingleEntry("measurementunit").equals("RECORD DOES NOT EXIST.") || settingsDBAdapter.getSingleEntry("measurementunit").equals("ERROR!!")))
            ((Spinner) findViewById(R.id.s_measurementunit_dd)).setSelection(Integer.parseInt(settingsDBAdapter.getSingleEntry("measurementunit")));
        if (!(settingsDBAdapter.getSingleEntry("dairyname").equals("RECORD DOES NOT EXIST.") || settingsDBAdapter.getSingleEntry("dairyname").equals("ERROR!!")))
            ((TextView) findViewById(R.id.s_dairyname_dd)).setText(settingsDBAdapter.getSingleEntry("dairyname"));
        if (!(settingsDBAdapter.getSingleEntry("weight").equals("RECORD DOES NOT EXIST.") || settingsDBAdapter.getSingleEntry("weight").equals("ERROR!!")))
            ((Spinner) findViewById(R.id.s_weight_dd)).setSelection(Integer.parseInt(settingsDBAdapter.getSingleEntry("weight")));
        if (!(settingsDBAdapter.getSingleEntry("footer").equals("RECORD DOES NOT EXIST.") || settingsDBAdapter.getSingleEntry("footer").equals("ERROR!!")))
            ((TextView) findViewById(R.id.s_footer_dd)).setText(settingsDBAdapter.getSingleEntry("footer"));
        settingsDBAdapter.close();

        findViewById(R.id.s_save_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!((TextView) findViewById(R.id.s_dairyname_dd)).getText().toString().equals("")) {
                    settingsDBAdapter = new SettingsDBAdapter(getApplicationContext());
                    settingsDBAdapter.open();
                    settingsDBAdapter.updateEntry("cow_fat_range_to", ((TextView) findViewById(R.id.s_cow_fat_range_to_et)).getText().toString());
                    settingsDBAdapter.updateEntry("cow_snf_range_to", ((TextView) findViewById(R.id.s_cow_snf_range_to_et)).getText().toString());
                    settingsDBAdapter.updateEntry("cow_fat_range_from", ((TextView) findViewById(R.id.s_cow_fat_range_from_et)).getText().toString());
                    settingsDBAdapter.updateEntry("cow_snf_range_from", ((TextView) findViewById(R.id.s_cow_snf_range_from_et)).getText().toString());
                    settingsDBAdapter.updateEntry("buffalo_fat_range_to", ((TextView) findViewById(R.id.s_buffalo_fat_range_to_et)).getText().toString());
                    settingsDBAdapter.updateEntry("buffalo_snf_range_to", ((TextView) findViewById(R.id.s_buffalo_snf_range_to_et)).getText().toString());
                    settingsDBAdapter.updateEntry("buffalo_fat_range_from", ((TextView) findViewById(R.id.s_buffalo_fat_range_from_et)).getText().toString());
                    settingsDBAdapter.updateEntry("buffalo_snf_range_from", ((TextView) findViewById(R.id.s_buffalo_snf_range_from_et)).getText().toString());
                    settingsDBAdapter.updateEntry("analyzer", ((Long) ((Spinner) findViewById(R.id.s_analyzer_dd)).getSelectedItemId()).toString());
                    settingsDBAdapter.updateEntry("weight", ((Long) ((Spinner) findViewById(R.id.s_weight_dd)).getSelectedItemId()).toString());
                    settingsDBAdapter.updateEntry("printer", ((Long) ((Spinner) findViewById(R.id.s_printer_dd)).getSelectedItemId()).toString());
                    settingsDBAdapter.updateEntry("shifttime", ((Long) ((Spinner) findViewById(R.id.s_shifttime_dd)).getSelectedItemId()).toString());
                    settingsDBAdapter.updateEntry("measurementunit", ((Long) ((Spinner) findViewById(R.id.s_measurementunit_dd)).getSelectedItemId()).toString());
                    settingsDBAdapter.updateEntry("dairyname", ((TextView) findViewById(R.id.s_dairyname_dd)).getText().toString());
                    settingsDBAdapter.updateEntry("footer", ((TextView) findViewById(R.id.s_footer_dd)).getText().toString());
                    Toast.makeText(getApplicationContext(), "Settings are Successfully Saved!!", Toast.LENGTH_SHORT).show();
                    settingsDBAdapter.close();
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill dairy name.", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //((ImageView)findViewById(R.id.image_s)).setMaxHeight(((Spinner)findViewById(R.id.s_analyzer_dd)).getHeight());
    }

}
