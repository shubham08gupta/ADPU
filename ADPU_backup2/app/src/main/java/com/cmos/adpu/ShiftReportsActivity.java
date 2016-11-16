package com.cmos.adpu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.DatePicker.OnDateChangedListener;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ShiftReportsActivity extends Activity {
    CollectionsDBAdapter collectionsDBAdapter;
    DatePicker dateDP;
    PrintableText printableText;
    String where;
    SettingsDBAdapter settingsDBAdapter;
    Float fat_total = 0.0f, snf_total = 0.0f, quantity_total = 0.0f, amount_total = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift_reports);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        DataInput.object_selected = "DAY_SR";
        //Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/lineprinter.ttf");
        //((TextView)findViewById(R.id.printable_text_tv)).setTypeface(typeFace);
        settingsDBAdapter = new SettingsDBAdapter(this);
        settingsDBAdapter.open();
        Integer shiftTime = Integer.parseInt(settingsDBAdapter.getSingleEntry("shifttime"));
        settingsDBAdapter.close();
        final ScrollView scrollView = (ScrollView)findViewById(R.id.scrollView_sr);
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                //Toast.makeText(getApplicationContext(), scrollView.getTop() + " " + scrollView.getBottom(), Toast.LENGTH_SHORT).show();
            }
        });
        String time;
        switch (shiftTime) {
            case 0:
                time = "10:00";
                break;
            case 1:
                time = "11:00";
                break;
            case 2:
                time = "12:00";
                break;
            case 3:
                time = "13:00";
                break;
            case 4:
                time = "14:00";
                break;
            case 5:
                time = "15:00";
                break;
            case 6:
                time = "16:00";
                break;
            default:
                time = "12:00";
        }
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        try {
            Date date = format.parse(time);
            long shiftTimeMillis = date.getTime();
            long currentTimeMillis = System.currentTimeMillis();
            if(currentTimeMillis <= shiftTimeMillis)
                ((EditText)findViewById(R.id.select_shift_sr)).setText(R.string.morning);
            else
                ((EditText)findViewById(R.id.select_shift_sr)).setText(R.string.evening);
        }
        catch (ParseException e){
            ((EditText)findViewById(R.id.select_shift_sr)).setText(R.string.morning);
        }
        final Calendar c = Calendar.getInstance();
        final int maxYear = c.get(Calendar.YEAR);
        final int maxMonth = c.get(Calendar.MONTH);
        final int maxDay = c.get(Calendar.DAY_OF_MONTH);
        dateDP = ((DatePicker)findViewById(R.id.sr_date_dp));
        dateDP.init(maxYear, maxMonth, maxDay, new OnDateChangedListener()
        {

            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                if(dayOfMonth < 10)
                    ((EditText)findViewById(R.id.day_sr)).setText("0" + ((Integer)dayOfMonth).toString());
                else
                    ((EditText)findViewById(R.id.day_sr)).setText(((Integer)dayOfMonth).toString());
                if(monthOfYear + 1 < 10)
                    ((EditText)findViewById(R.id.month_sr)).setText("0" + ((Integer)(monthOfYear + 1)).toString());
                else
                    ((EditText)findViewById(R.id.month_sr)).setText(((Integer)(monthOfYear + 1)).toString());
                ((EditText)findViewById(R.id.year_sr)).setText(((Integer)year).toString());
                if (year > maxYear)
                    view.updateDate(maxYear, maxMonth, maxDay);

                if (monthOfYear > maxMonth && year == maxYear)
                    view.updateDate(maxYear, maxMonth, maxDay);

                if (dayOfMonth > maxDay && year == maxYear && monthOfYear == maxMonth)
                    view.updateDate(maxYear, maxMonth, maxDay);
            }});
        BaseActivity.dataInput = new DataInput(this, dateDP, (EditText)findViewById(R.id.day_sr), (EditText)findViewById(R.id.month_sr), (EditText)findViewById(R.id.year_sr), (EditText)findViewById(R.id.select_shift_sr), (ScrollView)findViewById(R.id.scrollView_sr), (Button)findViewById(R.id.print_preview_sr));
        findViewById(R.id.print_preview_sr).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DataInput.object_selected.equals("UPDATE_SR")) {
                    updateTable();
                    DataInput.object_selected = "SCROLL_SR";
                }
                else {
                    collectionsDBAdapter = new CollectionsDBAdapter(ShiftReportsActivity.this);
                    collectionsDBAdapter.open();
                    //where = ((EditText)findViewById(R.id.day_sr)).getText().toString() + "/" + ((EditText)findViewById(R.id.month_sr)).getText().toString() + "/" + ((EditText)findViewById(R.id.year_sr)).getText().toString() + " " + ((RadioButton)findViewById(((RadioGroup) findViewById(R.id.shift_selector_sr)).getCheckedRadioButtonId())).getText();
                    where = ((EditText)findViewById(R.id.day_sr)).getText().toString() + "/" + ((EditText)findViewById(R.id.month_sr)).getText().toString() + "/" + ((EditText)findViewById(R.id.year_sr)).getText().toString() + " " + ((EditText)findViewById(R.id.select_shift_sr)).getText();
                    String result = collectionsDBAdapter.getMultipleEntry(where);
                    collectionsDBAdapter.close();
                    String result1;
                    if (!(result.equals("RECORD DOES NOT EXIST.")) && !(result.equals("ERROR!!"))) {
                        settingsDBAdapter = new SettingsDBAdapter(getApplicationContext());
                        settingsDBAdapter.open();
                        result1 = settingsDBAdapter.getSingleEntry("dairyname") +
                                "\r\nShift Summary       " + where.substring(0, 12) + "\r\n" +
                                "--------------------------------\r\n" +
                                "Code Fat   SNF  Quantity  Amount\r\n" +
                                "--------------------------------\r\n";
                        String[] arrayOfString = result.split(",\\.\\.,");
                        printableText = new PrintableText();
                        for (int i = 0; i < arrayOfString.length; i++)
                        {
                            if (i != 0) {
                                result1 = result1 + "\r\n";
                            }
                            String[] result2 = arrayOfString[i].split(",");
                            result1 = result1 + ShiftReportsActivity.this.printableText.convert(result2[1], 3, 0) + " ";
                            result1 = result1 + ShiftReportsActivity.this.printableText.convert(getFloatString(result2[3]), 2, 2) + " ";
                            result1 = result1 + ShiftReportsActivity.this.printableText.convert(result2[4], 2, 2) + " ";
                            result1 = result1 + ShiftReportsActivity.this.printableText.convert(getFloatString(result2[2]), 4, 2) + " ";
                            result1 = result1 + ShiftReportsActivity.this.printableText.convert(Float.valueOf(Float.parseFloat(getFloatString(result2[2])) * Float.parseFloat(result2[5])).toString(), 5, 2);
                        }
                        result1 = result1 + "\r\n--------------------------------\r\nAverage Fat: " + printableText.convert(String.valueOf(fat_total / quantity_total), 2, 2);
                        result1 = result1 + "\r\nAverage SNF: " + printableText.convert(String.valueOf(snf_total / quantity_total), 2, 2);
                        result1 = result1 + "\r\nTotal Quantity: " + printableText.convert(String.valueOf(quantity_total), 8, 2);
                        result1 = result1 + "\r\nTotal Amount: " + printableText.convert(String.valueOf(amount_total), 10, 2);
                        result1 = result1 + "\r\n\r\n--------------------------------\r\n" +
                                settingsDBAdapter.getSingleEntry("footer") +
                                "\r\n--------------------------------\r\n\r\n\r\n";
                        DataInput.data_input = false;
                        BaseActivity.i = 0;
                        BaseActivity.print_data = result1;
                        new Handler().postDelayed(new Runnable()
                        {
                            public void run()
                            {
                                BaseActivity.characteristicTX.setValue("\r\n");
                                BaseActivity.mBluetoothLeService.writeCharacteristic(BaseActivity.characteristicTX);
                            }
                        }, 100);
                        settingsDBAdapter.close();
                    }
                }
            }
        });
        ((EditText)findViewById(R.id.day_sr)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //updateTable();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ((EditText)findViewById(R.id.month_sr)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //updateTable();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ((EditText)findViewById(R.id.year_sr)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //updateTable();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ((EditText)findViewById(R.id.select_shift_sr)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //updateTable();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onResume()
    {
        super.onResume();
        BaseActivity.dataInput = new DataInput(this, dateDP, (EditText)findViewById(R.id.day_sr), (EditText)findViewById(R.id.month_sr), (EditText)findViewById(R.id.year_sr), (EditText)findViewById(R.id.select_shift_sr), (ScrollView)findViewById(R.id.scrollView_sr), (Button)findViewById(R.id.print_preview_sr));
        if(dateDP.getDayOfMonth() < 10)
            ((EditText)findViewById(R.id.day_sr)).setText("0" + ((Integer) dateDP.getDayOfMonth()).toString());
        else
            ((EditText)findViewById(R.id.day_sr)).setText(((Integer) dateDP.getDayOfMonth()).toString());
        if(dateDP.getMonth() + 1 < 10)
            ((EditText)findViewById(R.id.month_sr)).setText("0" + ((Integer) (dateDP.getMonth() + 1)).toString());
        else
            ((EditText)findViewById(R.id.month_sr)).setText(((Integer) (dateDP.getMonth() + 1)).toString());
        String asdf = ((Integer) dateDP.getYear()).toString();
        try {
            ((EditText) findViewById(R.id.year_sr)).setText(((Integer) dateDP.getYear()).toString());
        }
        catch (Exception e){
            String asdfas = e.getMessage();
        }
        findViewById(R.id.day_sr).setBackgroundResource(R.drawable.focused);
        findViewById(R.id.month_sr).setBackgroundResource(R.drawable.unfocused);
        findViewById(R.id.year_sr).setBackgroundResource(R.drawable.unfocused);
        findViewById(R.id.select_shift_sr).setBackgroundResource(R.drawable.unfocused);
        //findViewById(R.id.shift_selector_sr).setBackgroundResource(R.drawable.unfocused);
        DataInput.object_selected = "DAY_SR";
        //((RadioGroup)findViewById(R.id.shift_selector_sr)).check(((RadioGroup) findViewById(R.id.shift_selector_sr)).getChildAt(0).getId());
    }

    public String getFloatString (String input) {
        if(input.contains("("))
            return input.substring(input.lastIndexOf(")") + 2);
        return "";
    }

    public void updateTable() {
        collectionsDBAdapter = new CollectionsDBAdapter(ShiftReportsActivity.this);
        collectionsDBAdapter.open();
        //where = ((EditText)findViewById(R.id.day_sr)).getText().toString() + "/" + ((EditText)findViewById(R.id.month_sr)).getText().toString() + "/" + ((EditText)findViewById(R.id.year_sr)).getText().toString() + " " + ((RadioButton)findViewById(((RadioGroup) findViewById(R.id.shift_selector_sr)).getCheckedRadioButtonId())).getText();
        where = ((EditText)findViewById(R.id.day_sr)).getText().toString() + "/" + ((EditText)findViewById(R.id.month_sr)).getText().toString() + "/" + ((EditText)findViewById(R.id.year_sr)).getText().toString() + " " + ((EditText)findViewById(R.id.select_shift_sr)).getText();
        String result = collectionsDBAdapter.getMultipleEntry(where);
        collectionsDBAdapter.close();
        TableLayout tableLayout = (TableLayout)findViewById(R.id.printable_text_tb);
        tableLayout.removeAllViews();
        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams rowParams1 = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        if (!(result.equals("RECORD DOES NOT EXIST.")) && !(result.equals("ERROR!!"))) {
            TableRow tableRow1 = new TableRow(getApplicationContext());
            tableRow1.setLayoutParams(tableParams);
            TextView textView1 = new TextView(getApplicationContext());
            TextView textView2 = new TextView(getApplicationContext());
            TextView textView3 = new TextView(getApplicationContext());
            TextView textView4 = new TextView(getApplicationContext());
            TextView textView5 = new TextView(getApplicationContext());
            TextView textView6 = new TextView(getApplicationContext());
            textView1.setPadding(10, 2, 10, 2);
            textView2.setPadding(10, 2, 10, 2);
            textView3.setPadding(10, 2, 10, 2);
            textView4.setPadding(10, 2, 10, 2);
            textView5.setPadding(10, 2, 10, 2);
            textView6.setPadding(10, 2, 10, 2);
            textView1.setLayoutParams(rowParams1);
            textView1.setText("SL#");
            textView1.setTextSize(18);
            textView1.setTextColor(0xFF000000);
            tableRow1.addView(textView1);
            textView2.setLayoutParams(rowParams1);
            textView2.setText("Code");
            textView2.setTextSize(18);
            textView2.setTextColor(0xFF000000);
            tableRow1.addView(textView2);
            textView3.setLayoutParams(rowParams1);
            textView3.setText("Fat");
            textView3.setTextSize(18);
            textView3.setTextColor(0xFF000000);
            tableRow1.addView(textView3);
            textView4.setLayoutParams(rowParams1);
            textView4.setText("SNF");
            textView4.setTextSize(18);
            textView4.setTextColor(0xFF000000);
            tableRow1.addView(textView4);
            textView5.setLayoutParams(rowParams1);
            textView5.setText("Quantity");
            textView5.setTextSize(18);
            textView5.setTextColor(0xFF000000);
            tableRow1.addView(textView5);
            textView6.setLayoutParams(rowParams1);
            textView6.setText("Amount");
            textView6.setTextSize(18);
            textView6.setTextColor(0xFF000000);
            tableRow1.addView(textView6);
            tableLayout.addView(tableRow1);
            String[] arrayOfString = result.split(",\\.\\.,");
            printableText = new PrintableText();
            fat_total = 0.0f;
            snf_total = 0.0f;
            quantity_total = 0.0f;
            amount_total = 0.0f;
            for (int i = 0; i < arrayOfString.length; i++)
            {
                String[] result2 = arrayOfString[i].split(",");
                fat_total = fat_total + (Float.valueOf(getFloatString(result2[3])) * Float.valueOf(getFloatString(result2[2])));
                snf_total = snf_total + (Float.valueOf(result2[4]) * Float.valueOf(getFloatString(result2[2])));
                quantity_total = quantity_total + Float.valueOf(getFloatString(result2[2]));
                amount_total = amount_total + (Float.valueOf(getFloatString(result2[2])) * Float.valueOf(result2[5]));
                TableRow tableRow11 = new TableRow(getApplicationContext());
                tableRow11.setLayoutParams(tableParams);
                TextView textView11 = new TextView(getApplicationContext());
                TextView textView12 = new TextView(getApplicationContext());
                TextView textView13 = new TextView(getApplicationContext());
                TextView textView14 = new TextView(getApplicationContext());
                TextView textView15 = new TextView(getApplicationContext());
                TextView textView16 = new TextView(getApplicationContext());
                textView11.setPadding(10,2,10,2);
                textView12.setPadding(10,2,10,2);
                textView13.setPadding(10, 2, 10, 2);
                textView14.setPadding(10, 2, 10, 2);
                textView15.setPadding(10, 2, 10, 2);
                textView16.setPadding(10, 2, 10, 2);
                textView11.setGravity(Gravity.RIGHT);
                textView12.setGravity(Gravity.RIGHT);
                textView13.setGravity(Gravity.RIGHT);
                textView14.setGravity(Gravity.RIGHT);
                textView15.setGravity(Gravity.RIGHT);
                textView16.setGravity(Gravity.RIGHT);
                textView11.setLayoutParams(rowParams1);
                textView11.setText(String.valueOf(i + 1));
                textView11.setTextSize(18);
                textView11.setTextColor(0xFF000000);
                tableRow11.addView(textView11);
                textView12.setLayoutParams(rowParams1);
                textView12.setText(ShiftReportsActivity.this.printableText.convert(result2[1], 3, 0));
                textView12.setTextSize(18);
                textView12.setTextColor(0xFF000000);
                tableRow11.addView(textView12);
                textView13.setLayoutParams(rowParams1);
                textView13.setText(ShiftReportsActivity.this.printableText.convert(getFloatString(result2[3]), 2, 2));
                textView13.setTextSize(18);
                textView13.setTextColor(0xFF000000);
                tableRow11.addView(textView13);
                textView14.setLayoutParams(rowParams1);
                textView14.setText(ShiftReportsActivity.this.printableText.convert(result2[4], 2, 2));
                textView14.setTextSize(18);
                textView14.setTextColor(0xFF000000);
                tableRow11.addView(textView14);
                textView15.setLayoutParams(rowParams1);
                textView15.setText(ShiftReportsActivity.this.printableText.convert(getFloatString(result2[2]), 4, 2));
                textView15.setTextSize(18);
                textView15.setTextColor(0xFF000000);
                tableRow11.addView(textView15);
                textView16.setLayoutParams(rowParams1);
                textView16.setText(ShiftReportsActivity.this.printableText.convert(Float.valueOf(Float.parseFloat(getFloatString(result2[2])) * Float.parseFloat(result2[5])).toString(), 5, 2));
                textView16.setTextSize(18);
                textView16.setTextColor(0xFF000000);
                tableRow11.addView(textView16);
                tableLayout.addView(tableRow11);
            }
            TableRow.LayoutParams rowParams2 = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
            rowParams2.span = 6;
            TableRow.LayoutParams rowParams3 = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 2);
            rowParams3.span = 6;
            TableRow tableRowLine = new TableRow(getApplicationContext());
            tableRowLine.setLayoutParams(tableParams);
            View line = new View(getApplicationContext());
            line.setBackgroundResource(R.color.background_material_dark);
            line.setLayoutParams(rowParams3);
            line.setPadding(10, 0, 0, 0);
            tableRowLine.addView(line);
            tableLayout.addView(tableRowLine);
            TableRow tableRow7 = new TableRow(getApplicationContext());
            tableRow7.setLayoutParams(tableParams);
            TextView textView7 = new TextView(getApplicationContext());
            textView7.setLayoutParams(rowParams2);
            textView7.setText("Average Fat: " + printableText.convert(String.valueOf(fat_total / quantity_total), 2, 2));
            textView7.setTextSize(18);
            textView7.setTextColor(0xFF000000);
            textView7.setPadding(10, 0, 0, 0);
            tableRow7.addView(textView7);
            tableLayout.addView(tableRow7);
            TableRow tableRow8 = new TableRow(getApplicationContext());
            tableRow8.setLayoutParams(tableParams);
            TextView textView8 = new TextView(getApplicationContext());
            textView8.setLayoutParams(rowParams2);
            textView8.setText("Average SNF: " + printableText.convert(String.valueOf(snf_total / quantity_total), 2, 2));
            textView8.setTextSize(18);
            textView8.setTextColor(0xFF000000);
            textView8.setPadding(10, 0, 0, 0);
            tableRow8.addView(textView8);
            tableLayout.addView(tableRow8);
            TableRow tableRow9 = new TableRow(getApplicationContext());
            tableRow9.setLayoutParams(tableParams);
            TextView textView9 = new TextView(getApplicationContext());
            textView9.setLayoutParams(rowParams2);
            textView9.setText("Total Quantity: " + printableText.convert(String.valueOf(quantity_total), 8, 2));
            textView9.setTextSize(18);
            textView9.setTextColor(0xFF000000);
            textView9.setPadding(10, 0, 0, 0);
            tableRow9.addView(textView9);
            tableLayout.addView(tableRow9);
            TableRow tableRow10 = new TableRow(getApplicationContext());
            tableRow10.setLayoutParams(tableParams);
            TextView textView10 = new TextView(getApplicationContext());
            textView10.setLayoutParams(rowParams2);
            textView10.setText("Total Amount: " + printableText.convert(String.valueOf(amount_total), 10, 2));
            textView10.setTextSize(18);
            textView10.setTextColor(0xFF000000);
            textView10.setPadding(10, 0, 0, 0);
            tableRow10.addView(textView10);
            tableLayout.addView(tableRow10);
        }
        else {
            TableRow tableRow1 = new TableRow(getApplicationContext());
            tableRow1.setLayoutParams(tableParams);
            TextView textView1 = new TextView(getApplicationContext());
            textView1.setLayoutParams(rowParams1);
            textView1.setText(result);
            textView1.setTextSize(18);
            textView1.setTextColor(0xFF000000);
            tableRow1.addView(textView1);
            tableLayout.addView(tableRow1);
        }
    }
}
