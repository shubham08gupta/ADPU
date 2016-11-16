package com.cmos.adpu;

import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.Format;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PaymentSummaryActivity extends Activity {

    CollectionsDBAdapter collectionsDBAdapter;
    DatePicker dateDP1, dateDP2;
    PrintableText printableText;
    String where;
    SettingsDBAdapter settingsDBAdapter;
    Float rate_total = 0.0f, quantity_total = 0.0f, amount_total = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_summary);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        collectionsDBAdapter = new CollectionsDBAdapter(getApplicationContext());
        collectionsDBAdapter.open();
        printableText = new PrintableText();
        DataInput.object_selected = "DAY1_PS";
        final Calendar c = Calendar.getInstance();
        final int maxYear = c.get(Calendar.YEAR);
        final int maxMonth = c.get(Calendar.MONTH);
        final int maxDay = c.get(Calendar.DAY_OF_MONTH);
        dateDP1 = ((DatePicker)findViewById(R.id.ps_date1_dp));
        dateDP1.init(maxYear, maxMonth, maxDay, new DatePicker.OnDateChangedListener()
        {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                if(dayOfMonth < 10)
                    ((EditText)findViewById(R.id.ps_day1)).setText("0" + ((Integer)dayOfMonth).toString());
                else
                    ((EditText)findViewById(R.id.ps_day1)).setText(((Integer)dayOfMonth).toString());
                if(monthOfYear + 1 < 10)
                    ((EditText)findViewById(R.id.ps_month1)).setText("0" + ((Integer)(monthOfYear + 1)).toString());
                else
                    ((EditText)findViewById(R.id.ps_month1)).setText(((Integer)(monthOfYear + 1)).toString());
                ((EditText)findViewById(R.id.ps_year1)).setText(((Integer)year).toString());
                if (year > maxYear)
                    view.updateDate(maxYear, maxMonth, maxDay);

                if (monthOfYear > maxMonth && year == maxYear)
                    view.updateDate(maxYear, maxMonth, maxDay);

                if (dayOfMonth > maxDay && year == maxYear && monthOfYear == maxMonth)
                    view.updateDate(maxYear, maxMonth, maxDay);
            }
        });
        dateDP2 = ((DatePicker)findViewById(R.id.ps_date2_dp));
        dateDP2.init(maxYear, maxMonth, maxDay, new DatePicker.OnDateChangedListener()
        {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth)
            {
                if(dayOfMonth < 10)
                    ((EditText)findViewById(R.id.ps_day2)).setText("0" + ((Integer)dayOfMonth).toString());
                else
                    ((EditText)findViewById(R.id.ps_day2)).setText(((Integer)dayOfMonth).toString());
                if(monthOfYear + 1 < 10)
                    ((EditText)findViewById(R.id.ps_month2)).setText("0" + ((Integer)(monthOfYear + 1)).toString());
                else
                    ((EditText)findViewById(R.id.ps_month2)).setText(((Integer)(monthOfYear + 1)).toString());
                ((EditText)findViewById(R.id.ps_year2)).setText(((Integer)year).toString());
                if (year > maxYear)
                    view.updateDate(maxYear, maxMonth, maxDay);

                if (monthOfYear > maxMonth && year == maxYear)
                    view.updateDate(maxYear, maxMonth, maxDay);

                if (dayOfMonth > maxDay && year == maxYear && monthOfYear == maxMonth)
                    view.updateDate(maxYear, maxMonth, maxDay);
            }
        });
        BaseActivity.dataInput = new DataInput(this, dateDP1, dateDP2, (EditText)findViewById(R.id.ps_day1), (EditText)findViewById(R.id.ps_month1), (EditText)findViewById(R.id.ps_year1), (EditText)findViewById(R.id.ps_day2), (EditText)findViewById(R.id.ps_month2), (EditText)findViewById(R.id.ps_year2), new EditText(this), (ScrollView)findViewById(R.id.scrollView_ps), (Button)findViewById(R.id.print_preview_ps));
        findViewById(R.id.print_preview_ps).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DataInput.object_selected.equals("UPDATE_PS")) {
                    updateTable();
                    DataInput.object_selected = "SCROLL_PS";
                }
                else {
                    String result = getData();
                    String result1;
                    settingsDBAdapter = new SettingsDBAdapter(getApplicationContext());
                    settingsDBAdapter.open();
                    if (!(result.equals("RECORD DOES NOT EXIST.")) && !(result.equals("ERROR!!")) && !(result.equals(""))) {
                        result1 = String.valueOf((char) 27) + String.valueOf((char) 33) + String.valueOf((char) 1) + settingsDBAdapter.getSingleEntry("dairyname") +
                                "\r\nPayment Ledger\r\n" +
                                "------------------------------------\r\n" +
                                "Code Name  Quantity Avg.Rate  Amount\r\n" +
                                "-------------------------------------\r\n";
                        String[] arrayOfString = result.split(",\\.\\.,");
                        printableText = new PrintableText();

                        for (int i = 0; i < arrayOfString.length; i++) {
                            if (i != 0) {
                                result1 = result1 + "\r\n";
                            }
                            String[] result2 = arrayOfString[i].split(",");
                            result1 = result1 + printableText.convert(result2[0], 3, 0) + " ";
                            if(result2[1].length() > 7)
                                result1 = result1 + result2[1].substring(0, 7) + " ";
                            else
                                result1 = result1 + result2[1] + " ";
                            result1 = result1 + printableText.convert(String.format("%.2f", Float.parseFloat(result2[2])), 4, 2) + " ";
                            result1 = result1 + printableText.convert(String.format("%.2f", Float.parseFloat(result2[3])), 4, 2) + " ";
                            result1 = result1 + printableText.convert(String.format("%.2f", Float.parseFloat(result2[4])), 5, 2) + " ";
                        }
                        result1 = result1 + "\r\n-------------------------------------\r\nAverage Rate: " + String.format("%.2f", rate_total / arrayOfString.length);
                        result1 = result1 + "\r\nTotal Quantity: " + String.format("%.2f", quantity_total);
                        result1 = result1 + "\r\nTotal Amount: " + String.format("%.2f", amount_total);
                        result1 = result1 + "\r\n\r\n-------------------------------------\r\n" +
                                settingsDBAdapter.getSingleEntry("footer") +
                                "\r\n-------------------------------------" + String.valueOf((char) 27) + String.valueOf((char) 33) + String.valueOf((char) 0) + "\r\n\r\n\r\n";
                        DataInput.data_input = false;
                        BaseActivity.i = 0;
                        BaseActivity.print_data = result1;
                        new Handler().postDelayed(new Runnable() {
                            public void run() {
                                BaseActivity.characteristicTX.setValue("\r\n");
                                BaseActivity.mBluetoothLeService.writeCharacteristic(BaseActivity.characteristicTX);
                            }
                        }, 100);
                    }
                    settingsDBAdapter.close();
                }
            }
        });

        ((EditText)findViewById(R.id.ps_day1)).addTextChangedListener(new TextWatcher() {
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
        ((EditText)findViewById(R.id.ps_day2)).addTextChangedListener(new TextWatcher() {
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
        ((EditText)findViewById(R.id.ps_month1)).addTextChangedListener(new TextWatcher() {
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
        ((EditText)findViewById(R.id.ps_month2)).addTextChangedListener(new TextWatcher() {
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
        ((EditText)findViewById(R.id.ps_year1)).addTextChangedListener(new TextWatcher() {
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
        ((EditText)findViewById(R.id.ps_year2)).addTextChangedListener(new TextWatcher() {
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
    public void onResume() {
        super.onResume();
        BaseActivity.dataInput = new DataInput(this, dateDP1, dateDP2, (EditText)findViewById(R.id.ps_day1), (EditText)findViewById(R.id.ps_month1), (EditText)findViewById(R.id.ps_year1), (EditText)findViewById(R.id.ps_day2), (EditText)findViewById(R.id.ps_month2), (EditText)findViewById(R.id.ps_year2), new EditText(this), (ScrollView)findViewById(R.id.scrollView_ps), (Button)findViewById(R.id.print_preview_ps));
        if(dateDP1.getDayOfMonth() < 10)
            ((EditText)findViewById(R.id.ps_day1)).setText("0" + ((Integer) dateDP1.getDayOfMonth()).toString());
        else
            ((EditText)findViewById(R.id.ps_day1)).setText(((Integer) dateDP1.getDayOfMonth()).toString());
        if(dateDP1.getMonth() + 1 < 10)
            ((EditText)findViewById(R.id.ps_month1)).setText("0" + ((Integer) (dateDP1.getMonth() + 1)).toString());
        else
            ((EditText)findViewById(R.id.ps_month1)).setText(((Integer) (dateDP1.getMonth() + 1)).toString());
        ((EditText)findViewById(R.id.ps_year1)).setText(((Integer) dateDP1.getYear()).toString());
        if(dateDP2.getDayOfMonth() < 10)
            ((EditText)findViewById(R.id.ps_day2)).setText("0" + ((Integer) dateDP2.getDayOfMonth()).toString());
        else
            ((EditText)findViewById(R.id.ps_day2)).setText(((Integer) dateDP2.getDayOfMonth()).toString());
        if(dateDP2.getMonth() + 1 < 10)
            ((EditText)findViewById(R.id.ps_month2)).setText("0" + ((Integer) (dateDP2.getMonth() + 1)).toString());
        else
            ((EditText)findViewById(R.id.ps_month2)).setText(((Integer) (dateDP2.getMonth() + 1)).toString());
        ((EditText)findViewById(R.id.ps_year2)).setText(((Integer) dateDP2.getYear()).toString());
        findViewById(R.id.ps_day1).setBackgroundResource(R.drawable.focused);
        findViewById(R.id.ps_month1).setBackgroundResource(R.drawable.unfocused);
        findViewById(R.id.ps_year1).setBackgroundResource(R.drawable.unfocused);
        findViewById(R.id.ps_day2).setBackgroundResource(R.drawable.unfocused);
        findViewById(R.id.ps_month2).setBackgroundResource(R.drawable.unfocused);
        findViewById(R.id.ps_year2).setBackgroundResource(R.drawable.unfocused);
        DataInput.object_selected = "DAY1_PS";
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        collectionsDBAdapter.close();
    }

    public String getData() {
        String result = "", mresult = "", eresult = "";
        Date date1, date2;
        Calendar c1, c2;
        c1 = Calendar.getInstance();
        c2 = Calendar.getInstance();
        c1.set(dateDP1.getYear(), dateDP1.getMonth(), dateDP1.getDayOfMonth());
        c2.set(dateDP2.getYear(), dateDP2.getMonth(), dateDP2.getDayOfMonth());
        date1 = c1.getTime();
        date2 = c2.getTime();
        List<String> code = new ArrayList<>(), name = new ArrayList<>();
        List<Float> quantity = new ArrayList<>(), rate = new ArrayList<>(), amount = new ArrayList<>();
        List<Integer> count = new ArrayList<>();
        while(date1.before(date2) || date1.equals(date2)) {
            String day, month, year;
            if(c1.get(Calendar.DAY_OF_MONTH) < 10)
                day = "0" + ((Integer)c1.get(Calendar.DAY_OF_MONTH)).toString();
            else
                day = ((Integer)c1.get(Calendar.DAY_OF_MONTH)).toString();
            if(c1.get(Calendar.MONTH) + 1 < 10)
                month = "0" + ((Integer)(c1.get(Calendar.MONTH) + 1)).toString();
            else
                month = ((Integer)(c1.get(Calendar.MONTH) + 1)).toString();
            year = ((Integer)c1.get(Calendar.YEAR)).toString();
            mresult = collectionsDBAdapter.getMultipleEntry(day + "/" + month + "/" + year + " Morning");
            eresult = collectionsDBAdapter.getMultipleEntry(day + "/" + month + "/" + year + " Evening");
            if(!(mresult.equals("RECORD DOES NOT EXIST.")) && !(mresult.equals("ERROR!!"))) {
                String[] arrayOfString = mresult.split(",\\.\\.,");
                for (int i = 0; i < arrayOfString.length; i++) {
                    String[] result2 = arrayOfString[i].split(",");
                    if(code.contains(printableText.convert(result2[1], 3, 0))) {
                        int index = code.indexOf(printableText.convert(result2[1], 3, 0));
                        quantity.set(index, quantity.get(index) + Float.parseFloat(getFloatString(result2[2])));
                        rate.set(index, rate.get(index) + Float.parseFloat(result2[5]));
                        amount.set(index, amount.get(index) + Float.parseFloat(getFloatString(result2[2])) * Float.parseFloat(result2[5]));
                        count.set(index, count.get(index) + 1);
                    }
                    else {
                        code.add(printableText.convert(result2[1], 3, 0));
                        name.add(result2[0]);
                        quantity.add(Float.parseFloat(getFloatString(result2[2])));
                        rate.add(Float.parseFloat(result2[5]));
                        amount.add(Float.parseFloat(getFloatString(result2[2])) * Float.parseFloat(result2[5]));
                        count.add(1);
                    }
                }
            }
            if(!(eresult.equals("RECORD DOES NOT EXIST.")) && !(eresult.equals("ERROR!!"))) {
                String[] arrayOfString = eresult.split(",\\.\\.,");
                for (int i = 0; i < arrayOfString.length; i++) {
                    String[] result2 = arrayOfString[i].split(",");
                    if(code.contains(printableText.convert(result2[1], 3, 0))) {
                        int index = code.indexOf(printableText.convert(result2[1], 3, 0));
                        quantity.set(index, quantity.get(index) + Float.parseFloat(getFloatString(result2[2])));
                        rate.set(index, rate.get(index) + Float.parseFloat(result2[5]));
                        amount.set(index, amount.get(index) + Float.parseFloat(getFloatString(result2[2])) * Float.parseFloat(result2[5]));
                        count.set(index, count.get(index) + 1);
                    }
                    else {
                        code.add(printableText.convert(result2[1], 3, 0));
                        name.add(result2[0]);
                        quantity.add(Float.parseFloat(getFloatString(result2[2])));
                        rate.add(Float.parseFloat(result2[5]));
                        amount.add(Float.parseFloat(getFloatString(result2[2])) * Float.parseFloat(result2[5]));
                        count.add(1);
                    }
                }
            }
            c1.set(c1.get(Calendar.YEAR), c1.get(Calendar.MONTH), c1.get(Calendar.DAY_OF_MONTH) + 1);
            date1 = c1.getTime();
        }
        for(int j = 0; j < code.size(); j++) {
            if(result.equals(""))
                result = result + code.get(j) + "," + name.get(j) + "," + quantity.get(j) + "," + String.valueOf(rate.get(j)/count.get(j)) + "," + amount.get(j);
            else
                result = result + ",..," + code.get(j) + "," + name.get(j) + "," + quantity.get(j) + "," + String.valueOf(rate.get(j)/count.get(j)) + "," + amount.get(j);
        }
        return result;
    }

    public void updateTable() {
        String result = getData();
        TableLayout tableLayout = (TableLayout)findViewById(R.id.printable_text_tb_ps);
        tableLayout.removeAllViews();
        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT,TableLayout.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams rowParams1 = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        if (!(result.equals("RECORD DOES NOT EXIST.")) && !(result.equals("ERROR!!")) && !(result.equals(""))) {
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
            textView3.setText("Name");
            textView3.setTextSize(18);
            textView3.setTextColor(0xFF000000);
            tableRow1.addView(textView3);
            textView4.setLayoutParams(rowParams1);
            textView4.setText("Quantity");
            textView4.setTextSize(18);
            textView4.setTextColor(0xFF000000);
            tableRow1.addView(textView4);
            textView5.setLayoutParams(rowParams1);
            textView5.setText("Avg.Rate");
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
            rate_total = 0.0f;
            quantity_total = 0.0f;
            amount_total = 0.0f;
            for (int i = 0; i < arrayOfString.length; i++)
            {
                String[] result2 = arrayOfString[i].split(",");
                rate_total = rate_total + Float.parseFloat(result2[3]);
                quantity_total = quantity_total + Float.parseFloat(result2[2]);
                amount_total = amount_total + Float.parseFloat(result2[4]);
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
                textView12.setText(printableText.convert(result2[0], 3, 0));
                textView12.setTextSize(18);
                textView12.setTextColor(0xFF000000);
                tableRow11.addView(textView12);
                textView13.setLayoutParams(rowParams1);
                if(result2[1].length() > 7)
                    textView13.setText(result2[1].substring(0,7));
                else
                    textView13.setText(result2[1]);
                textView13.setTextSize(18);
                textView13.setTextColor(0xFF000000);
                tableRow11.addView(textView13);
                textView14.setLayoutParams(rowParams1);
                textView14.setText(String.format("%.2f", Float.parseFloat(result2[2])));
                textView14.setTextSize(18);
                textView14.setTextColor(0xFF000000);
                tableRow11.addView(textView14);
                textView15.setLayoutParams(rowParams1);
                textView15.setText(String.format("%.2f", Float.parseFloat(result2[3])));
                textView15.setTextSize(18);
                textView15.setTextColor(0xFF000000);
                tableRow11.addView(textView15);
                textView16.setLayoutParams(rowParams1);
                textView16.setText(String.format("%.2f", Float.parseFloat(result2[4])));
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
            textView7.setText("Total Quantity: " + String.format("%.2f", quantity_total));
            textView7.setTextSize(18);
            textView7.setTextColor(0xFF000000);
            textView7.setPadding(10, 0, 0, 0);
            tableRow7.addView(textView7);
            tableLayout.addView(tableRow7);
            TableRow tableRow8 = new TableRow(getApplicationContext());
            tableRow8.setLayoutParams(tableParams);
            TextView textView8 = new TextView(getApplicationContext());
            textView8.setLayoutParams(rowParams2);
            textView8.setText("Average Rate: " + String.format("%.2f", rate_total / arrayOfString.length));
            textView8.setTextSize(18);
            textView8.setTextColor(0xFF000000);
            textView8.setPadding(10, 0, 0, 0);
            tableRow8.addView(textView8);
            tableLayout.addView(tableRow8);
            TableRow tableRow9 = new TableRow(getApplicationContext());
            tableRow9.setLayoutParams(tableParams);
            TextView textView9 = new TextView(getApplicationContext());
            textView9.setLayoutParams(rowParams2);
            textView9.setText("Total Amount: " + String.format("%.2f", amount_total));
            textView9.setTextSize(18);
            textView9.setTextColor(0xFF000000);
            textView9.setPadding(10, 0, 0, 0);
            tableRow9.addView(textView9);
            tableLayout.addView(tableRow9);
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

    public String getFloatString (String input) {
        if(input.contains("("))
            return input.substring(input.lastIndexOf(")") + 2);
        return "";
    }
}
