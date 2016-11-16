package com.cmos.adpu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import java.math.BigDecimal;

/**
 * Created by Pankaj on 02/10/2015.
 */
public class DataInput {

    public static Boolean data_input = true;
    public static Boolean first_time_selected = true;
    public static String machine_selected = "Keyboard";
    public static int item_selected = 1, tab_selected = 0, last_tab = 0;
    public static EditText item_textView;
    public static Boolean machine_selection = false;
    public static String object_selected = "None";
    public static Boolean save_collections = false;
    public static String print_cancel = "pass";
    public static String pass_mpass = "pass";
    public static Boolean weight_entry, analyzer_entry;
    Context context;
    EditText code, weight, fat, snf, added_water, day, month, year, name, mobile, email1, email2, shift, day1, month1, year1, day2, month2, year2, rateType,
            std_cow_rate, per_cow_fat, std_cow_fat, per_cow_snf, std_cow_snf, std_buffalo_rate, per_buffalo_fat, std_buffalo_fat, per_buffalo_snf, std_buffalo_snf,
            cow_fat_from, cow_fat_to, cow_snf_from, cow_snf_to, buffalo_fat_from, buffalo_fat_to, buffalo_snf_from, buffalo_snf_to, dairy_name, footer, pass,
            dairy_code, route_code, village_code, center_code, oldpass, newpass, repeatpass;
    DatePicker datePicker, datePicker1, datePicker2;
    Button button, print, button1, button2, button3, button4, button5, button6;
    DrawerLayout drawerLayout;
    Boolean exit = false;
    ListView listView;
    ScrollView scroll;
    LinearLayout linearLayout1, linearLayout2, linearLayout3;
    //RadioGroup radioGroup;
    Integer weight_counter = 0, analyzer_counter = 0;
    Spinner sanalyzer, sweight, sprinter, sshift_time, smeasurement_unit;
    RelativeLayout rl_analyzer, rl_weight, rl_printer, rl_shift_time, rl_measurement_unit;
    CustomMRCList customMRCList;
    FileArrayAdapter fileArrayAdapter;

    DataInput(Context context) {
        this.context = context;
    }

    DataInput(Context context, Button button) {
        this.context = context;
        this.button = button;
    }

    DataInput(Context context, ListView listView) {
        this.context = context;
        this.listView = listView;
    }

    DataInput(Context context, ListView listView, CustomMRCList customMRCList) {
        this.context = context;
        this.listView = listView;
        this.customMRCList = customMRCList;
    }

    DataInput(Context context, ListView listView, FileArrayAdapter fileArrayAdapter) {
        this.context = context;
        this.listView = listView;
        this.fileArrayAdapter = fileArrayAdapter;
    }

    DataInput(Context context, EditText code, DatePicker datePicker, EditText day, EditText month, EditText year, EditText shift, Button button) {
        this.context = context;
        this.datePicker = datePicker;
        this.code = code;
        this.day = day;
        this.month = month;
        this.year = year;
        this.shift = shift;
        this.button = button;
    }

    DataInput(Context context, DatePicker datePicker, EditText day, EditText month, EditText year, EditText shift, ScrollView scroll, Button button) {
        this.context = context;
        this.datePicker = datePicker;
        this.day = day;
        this.month = month;
        this.year = year;
        this.shift = shift;
        this.button = button;
        this.scroll = scroll;
    }

    DataInput(Context context, DatePicker datePicker1, DatePicker datePicker2, EditText day1, EditText month1, EditText year1, EditText day2, EditText month2, EditText year2, EditText code, ScrollView scroll, Button button) {
        this.context = context;
        this.datePicker1 = datePicker1;
        this.datePicker2 = datePicker2;
        this.day1 = day1;
        this.month1 = month1;
        this.year1 = year1;
        this.day2 = day2;
        this.month2 = month2;
        this.year2 = year2;
        this.code = code;
        this.button = button;
        this.scroll = scroll;
    }

    DataInput(Context context, EditText code, EditText weight, EditText fat, EditText snf, EditText added_water, DrawerLayout drawerLayout, ListView listView, Button button, Button print, EditText pass) {
        this.context = context;
        this.code = code;
        this.weight = weight;
        this.fat = fat;
        this.snf = snf;
        this.added_water = added_water;
        this.drawerLayout = drawerLayout;
        this.listView = listView;
        this.button = button;
        this.print = print;
        this.pass = pass;
    }

    DataInput(Context context, EditText code, EditText name, EditText mobile, EditText email1, EditText email2, Button button) {
        this.context = context;
        this.code = code;
        this.name = name;
        this.mobile = mobile;
        this.email1 = email1;
        this.email2 = email2;
        this.button = button;
    }

    DataInput(Context context, LinearLayout linearLayout1, LinearLayout linearLayout2, LinearLayout linearLayout3, Button button1, Button button2, Button button3, Button button4, Button button5, Button button6) {
        this.context = context;
        this.linearLayout1 = linearLayout1;
        this.linearLayout2 = linearLayout2;
        this.linearLayout3 = linearLayout3;
        this.button1 = button1;
        this.button2 = button2;
        this.button3 = button3;
        this.button4 = button4;
        this.button5 = button5;
        this.button6 = button6;
    }

    DataInput(Context context, DatePicker datePicker, EditText day, EditText month, EditText year, EditText rateType, Button button) {
        this.context = context;
        this.datePicker = datePicker;
        this.day = day;
        this.month = month;
        this.year = year;
        this.rateType = rateType;
        this.button = button;
    }

    DataInput(Context context, EditText std_cow_rate, EditText per_cow_fat, EditText std_cow_fat, EditText per_cow_snf, EditText std_cow_snf, EditText std_buffalo_rate, EditText per_buffalo_fat, EditText std_buffalo_fat, EditText per_buffalo_snf, EditText std_buffalo_snf, Button button) {
        this.context = context;
        this.std_cow_rate = std_cow_rate;
        this.per_cow_fat = per_cow_fat;
        this.std_cow_fat = std_cow_fat;
        this.per_cow_snf = per_cow_snf;
        this.std_cow_snf = std_cow_snf;
        this.std_buffalo_rate = std_buffalo_rate;
        this.per_buffalo_fat = per_buffalo_fat;
        this.std_buffalo_fat = std_buffalo_fat;
        this.per_buffalo_snf = per_buffalo_snf;
        this.std_buffalo_snf = std_buffalo_snf;
        this.button = button;
    }

    DataInput(Context context, EditText cow_fat_from, EditText cow_fat_to, EditText cow_snf_from, EditText cow_snf_to, EditText buffalo_fat_from, EditText buffalo_fat_to, EditText buffalo_snf_from, EditText buffalo_snf_to, Spinner sanalyzer, Spinner sweight, Spinner sprinter, Spinner sshift_time, Spinner smeasurement_unit, RelativeLayout rl_analyzer, RelativeLayout rl_weight, RelativeLayout rl_printer, RelativeLayout rl_shift_time, RelativeLayout rl_measurement_unit, EditText dairy_name, EditText footer, Button button) {
        this.context = context;
        this.cow_fat_from = cow_fat_from;
        this.cow_fat_to = cow_fat_to;
        this.cow_snf_from = cow_snf_from;
        this.cow_snf_to = cow_snf_to;
        this.buffalo_fat_from = buffalo_fat_from;
        this.buffalo_fat_to = buffalo_fat_to;
        this.buffalo_snf_from = buffalo_snf_from;
        this.buffalo_snf_to = buffalo_snf_to;
        this.sanalyzer = sanalyzer;
        this.sweight = sweight;
        this.sprinter = sprinter;
        this.sshift_time = sshift_time;
        this.smeasurement_unit = smeasurement_unit;
        this.rl_analyzer = rl_analyzer;
        this.rl_weight = rl_weight;
        this.rl_printer = rl_printer;
        this.rl_shift_time = rl_shift_time;
        this.rl_measurement_unit = rl_measurement_unit;
        this.dairy_name = dairy_name;
        this.footer = footer;
        this.button = button;
    }

    DataInput(Context context, EditText dairy_code, EditText route_code, EditText village_code, EditText center_code, Button button) {
        this.context = context;
        this.dairy_code = dairy_code;
        this.route_code = route_code;
        this.village_code = village_code;
        this.center_code = center_code;
        this.button = button;
    }

    DataInput(Context context, EditText oldpass, EditText newpass, EditText repeatpass, Button button) {
        this.context = context;
        this.oldpass = oldpass;
        this.newpass = newpass;
        this.repeatpass = repeatpass;
        this.button = button;
    }

    void display_data(String data) {
        try {
            switch (data) {
                case "$":
                    machine_selection = true;
                    break;
                case "k":
                    if (machine_selection)
                        machine_selected = "Keyboard";
                    machine_selection = false;
                    break;
                case "w":
                    if (machine_selection)
                        machine_selected = "Weight";
                    machine_selection = false;
                    break;
                case "a":
                    if (machine_selection)
                        machine_selected = "Analyzer";
                    machine_selection = false;
                    break;
                case "l":
                    if (!machine_selection) {
                        switch (object_selected) {
                            case "MONTH_SR":
                                object_selected = "DAY_SR";
                                this.month.setBackgroundResource(R.drawable.unfocused);
                                this.day.setBackgroundResource(R.drawable.focused);
                                break;
                            case "YEAR_SR":
                                object_selected = "MONTH_SR";
                                this.year.setBackgroundResource(R.drawable.unfocused);
                                this.month.setBackgroundResource(R.drawable.focused);
                                break;
                            case "SHIFT_SR":
                                object_selected = "YEAR_SR";
                                this.shift.setBackgroundResource(R.drawable.unfocused);
                                this.year.setBackgroundResource(R.drawable.focused);
                                break;
                            case "MONTH1_ML":
                                object_selected = "DAY1_ML";
                                this.month1.setBackgroundResource(R.drawable.unfocused);
                                this.day1.setBackgroundResource(R.drawable.focused);
                                break;
                            case "YEAR1_ML":
                                object_selected = "MONTH1_ML";
                                this.year1.setBackgroundResource(R.drawable.unfocused);
                                this.month1.setBackgroundResource(R.drawable.focused);
                                break;
                            case "DAY2_ML":
                                object_selected = "YEAR1_ML";
                                this.day2.setBackgroundResource(R.drawable.unfocused);
                                this.year1.setBackgroundResource(R.drawable.focused);
                                break;
                            case "MONTH2_ML":
                                object_selected = "DAY2_ML";
                                this.month2.setBackgroundResource(R.drawable.unfocused);
                                this.day2.setBackgroundResource(R.drawable.focused);
                                break;
                            case "YEAR2_ML":
                                object_selected = "MONTH2_ML";
                                this.year2.setBackgroundResource(R.drawable.unfocused);
                                this.month2.setBackgroundResource(R.drawable.focused);
                                break;
                            case "CODE_ML":
                                object_selected = "YEAR2_ML";
                                this.code.setBackgroundResource(R.drawable.unfocused);
                                this.year2.setBackgroundResource(R.drawable.focused);
                                break;
                            case "MONTH1_PS":
                                object_selected = "DAY1_PS";
                                this.month1.setBackgroundResource(R.drawable.unfocused);
                                this.day1.setBackgroundResource(R.drawable.focused);
                                break;
                            case "YEAR1_PS":
                                object_selected = "MONTH1_PS";
                                this.year1.setBackgroundResource(R.drawable.unfocused);
                                this.month1.setBackgroundResource(R.drawable.focused);
                                break;
                            case "DAY2_PS":
                                object_selected = "YEAR1_PS";
                                this.day2.setBackgroundResource(R.drawable.unfocused);
                                this.year1.setBackgroundResource(R.drawable.focused);
                                break;
                            case "MONTH2_PS":
                                object_selected = "DAY2_PS";
                                this.month2.setBackgroundResource(R.drawable.unfocused);
                                this.day2.setBackgroundResource(R.drawable.focused);
                                break;
                            case "YEAR2_PS":
                                object_selected = "MONTH2_PS";
                                this.year2.setBackgroundResource(R.drawable.unfocused);
                                this.month2.setBackgroundResource(R.drawable.focused);
                                break;
                            case "EDIT1":
                                object_selected = "SHOW1";
                                button1.setBackgroundResource(R.drawable.btn_focused);
                                button4.setBackgroundResource(R.drawable.btn_unfocused);
                                break;
                            case "EDIT2":
                                object_selected = "SHOW2";
                                button2.setBackgroundResource(R.drawable.btn_focused);
                                button5.setBackgroundResource(R.drawable.btn_unfocused);
                                break;
                            case "EDIT3":
                                object_selected = "SHOW3";
                                button3.setBackgroundResource(R.drawable.btn_focused);
                                button6.setBackgroundResource(R.drawable.btn_unfocused);
                                break;
                            case "DAY_DS":
                                object_selected = "CODE_DS";
                                code.setBackgroundResource(R.drawable.focused);
                                day.setBackgroundResource(R.drawable.unfocused);
                                first_time_selected = true;
                                break;
                            case "MONTH_DS":
                                object_selected = "DAY_DS";
                                day.setBackgroundResource(R.drawable.focused);
                                month.setBackgroundResource(R.drawable.unfocused);
                                break;
                            case "YEAR_DS":
                                object_selected = "MONTH_DS";
                                month.setBackgroundResource(R.drawable.focused);
                                year.setBackgroundResource(R.drawable.unfocused);
                                break;
                            case "SHIFT_DS":
                                object_selected = "YEAR_DS";
                                year.setBackgroundResource(R.drawable.focused);
                                shift.setBackgroundResource(R.drawable.unfocused);
                                break;
                            case "NAME_AFM":
                                if (name.getText().toString().length() > 0)
                                    name.setText(name.getText().toString().substring(0, name.getText().toString().length() - 1));
                                break;
                            case "MOBILE_AFM":
                                if (mobile.getText().toString().length() > 0)
                                    mobile.setText(mobile.getText().toString().substring(0, mobile.getText().toString().length() - 1));
                                break;
                            case "EMAIL1_AFM":
                                if (email1.getText().toString().length() > 0)
                                    email1.setText(email1.getText().toString().substring(0, email1.getText().toString().length() - 1));
                                break;
                            case "EMAIL2_AFM":
                                this.email1.setBackgroundResource(R.drawable.focused);
                                this.email2.setBackgroundResource(R.drawable.unfocused);
                                object_selected = "EMAIL1_AFM";
                                first_time_selected = true;
                                break;
                            case "MONTH_ERC":
                                object_selected = "DAY_ERC";
                                this.month.setBackgroundResource(R.drawable.unfocused);
                                this.day.setBackgroundResource(R.drawable.focused);
                                break;
                            case "YEAR_ERC":
                                object_selected = "MONTH_ERC";
                                this.year.setBackgroundResource(R.drawable.unfocused);
                                this.month.setBackgroundResource(R.drawable.focused);
                                break;
                            case "RATE_TYPE_ERC":
                                object_selected = "YEAR_ERC";
                                this.rateType.setBackgroundResource(R.drawable.unfocused);
                                this.year.setBackgroundResource(R.drawable.focused);
                                break;
                            case "COW_FAT_TO":
                                object_selected = "COW_FAT_FROM";
                                first_time_selected = true;
                                this.cow_fat_from.setBackgroundResource(R.drawable.focused);
                                this.cow_fat_to.setBackgroundResource(R.drawable.unfocused);
                                break;
                            case "COW_SNF_TO":
                                object_selected = "COW_SNF_FROM";
                                first_time_selected = true;
                                this.cow_snf_from.setBackgroundResource(R.drawable.focused);
                                this.cow_snf_to.setBackgroundResource(R.drawable.unfocused);
                                break;
                            case "BUFFALO_FAT_TO":
                                object_selected = "BUFFALO_FAT_FROM";
                                first_time_selected = true;
                                this.buffalo_fat_from.setBackgroundResource(R.drawable.focused);
                                this.buffalo_fat_to.setBackgroundResource(R.drawable.unfocused);
                                break;
                            case "BUFFALO_SNF_TO":
                                object_selected = "BUFFALO_SNF_FROM";
                                first_time_selected = true;
                                this.buffalo_snf_from.setBackgroundResource(R.drawable.focused);
                                this.buffalo_snf_to.setBackgroundResource(R.drawable.unfocused);
                                break;
                            case "DAIRY_NAME":
                                if (dairy_name.getText().toString().length() > 0)
                                    dairy_name.setText(dairy_name.getText().toString().substring(0, dairy_name.getText().toString().length() - 1));
                                break;
                            case "Footer":
                                if (footer.getText().toString().length() > 0)
                                    footer.setText(footer.getText().toString().substring(0, footer.getText().toString().length() - 1));
                                break;
                            case "SANALYZER":
                                if (sanalyzer.getSelectedItemPosition() != 0)
                                    sanalyzer.setSelection(sanalyzer.getSelectedItemPosition() - 1);
                                break;
                            case "SWEIGHT":
                                if (sweight.getSelectedItemPosition() != 0)
                                    sweight.setSelection(sweight.getSelectedItemPosition() - 1);
                                break;
                            case "SPRINTER":
                                if (sprinter.getSelectedItemPosition() != 0)
                                    sprinter.setSelection(sprinter.getSelectedItemPosition() - 1);
                                break;
                            case "SSHIFT_TIME":
                                if (sshift_time.getSelectedItemPosition() != 0)
                                    sshift_time.setSelection(sshift_time.getSelectedItemPosition() - 1);
                                break;
                            case "SMEASUREMENT_UNIT":
                                if (smeasurement_unit.getSelectedItemPosition() != 0)
                                    smeasurement_unit.setSelection(smeasurement_unit.getSelectedItemPosition() - 1);
                                break;
                            case "MAN_RC_ENTRY":
                            case "SHOW_RATE_CHART":
                                if (tab_selected != 0) {
                                    tab_selected--;
                                    item_selected = 1;
                                    ((Activity) context).getActionBar().setSelectedNavigationItem(tab_selected);
                                }
                                break;
                            default:
                                Toast.makeText(this.context, "Last Object Reached", Toast.LENGTH_SHORT).show();
                        }
                    } else
                        machine_selection = false;
                    break;
                case "r":
                    if (!machine_selection) {
                        switch (object_selected) {
                            case "DAY_SR":
                                object_selected = "MONTH_SR";
                                this.day.setBackgroundResource(R.drawable.unfocused);
                                this.month.setBackgroundResource(R.drawable.focused);
                                break;
                            case "MONTH_SR":
                                object_selected = "YEAR_SR";
                                this.month.setBackgroundResource(R.drawable.unfocused);
                                this.year.setBackgroundResource(R.drawable.focused);
                                break;
                            case "YEAR_SR":
                                object_selected = "SHIFT_SR";
                                this.year.setBackgroundResource(R.drawable.unfocused);
                                this.shift.setBackgroundResource(R.drawable.focused);
                                break;
                            case "DAY1_ML":
                                object_selected = "MONTH1_ML";
                                this.day1.setBackgroundResource(R.drawable.unfocused);
                                this.month1.setBackgroundResource(R.drawable.focused);
                                break;
                            case "MONTH1_ML":
                                object_selected = "YEAR1_ML";
                                this.month1.setBackgroundResource(R.drawable.unfocused);
                                this.year1.setBackgroundResource(R.drawable.focused);
                                break;
                            case "YEAR1_ML":
                                object_selected = "DAY2_ML";
                                this.year1.setBackgroundResource(R.drawable.unfocused);
                                this.day2.setBackgroundResource(R.drawable.focused);
                                break;
                            case "DAY2_ML":
                                object_selected = "MONTH2_ML";
                                this.day2.setBackgroundResource(R.drawable.unfocused);
                                this.month2.setBackgroundResource(R.drawable.focused);
                                break;
                            case "MONTH2_ML":
                                object_selected = "YEAR2_ML";
                                this.month2.setBackgroundResource(R.drawable.unfocused);
                                this.year2.setBackgroundResource(R.drawable.focused);
                                break;
                            case "YEAR2_ML":
                                object_selected = "CODE_ML";
                                first_time_selected = true;
                                this.year2.setBackgroundResource(R.drawable.unfocused);
                                this.code.setBackgroundResource(R.drawable.focused);
                                break;
                            case "DAY1_PS":
                                object_selected = "MONTH1_PS";
                                this.day1.setBackgroundResource(R.drawable.unfocused);
                                this.month1.setBackgroundResource(R.drawable.focused);
                                break;
                            case "MONTH1_PS":
                                object_selected = "YEAR1_PS";
                                this.month1.setBackgroundResource(R.drawable.unfocused);
                                this.year1.setBackgroundResource(R.drawable.focused);
                                break;
                            case "YEAR1_PS":
                                object_selected = "DAY2_PS";
                                this.year1.setBackgroundResource(R.drawable.unfocused);
                                this.day2.setBackgroundResource(R.drawable.focused);
                                break;
                            case "DAY2_PS":
                                object_selected = "MONTH2_PS";
                                this.day2.setBackgroundResource(R.drawable.unfocused);
                                this.month2.setBackgroundResource(R.drawable.focused);
                                break;
                            case "MONTH2_PS":
                                object_selected = "YEAR2_PS";
                                this.month2.setBackgroundResource(R.drawable.unfocused);
                                this.year2.setBackgroundResource(R.drawable.focused);
                                break;
                            case "SHOW1":
                                object_selected = "EDIT1";
                                button1.setBackgroundResource(R.drawable.btn_unfocused);
                                button4.setBackgroundResource(R.drawable.btn_focused);
                                break;
                            case "SHOW2":
                                object_selected = "EDIT2";
                                button2.setBackgroundResource(R.drawable.btn_unfocused);
                                button5.setBackgroundResource(R.drawable.btn_focused);
                                break;
                            case "SHOW3":
                                object_selected = "EDIT3";
                                button3.setBackgroundResource(R.drawable.btn_unfocused);
                                button6.setBackgroundResource(R.drawable.btn_focused);
                                break;
                            case "CODE_DS":
                                object_selected = "DAY_DS";
                                code.setBackgroundResource(R.drawable.unfocused);
                                day.setBackgroundResource(R.drawable.focused);
                                break;
                            case "DAY_DS":
                                object_selected = "MONTH_DS";
                                day.setBackgroundResource(R.drawable.unfocused);
                                month.setBackgroundResource(R.drawable.focused);
                                break;
                            case "MONTH_DS":
                                object_selected = "YEAR_DS";
                                month.setBackgroundResource(R.drawable.unfocused);
                                year.setBackgroundResource(R.drawable.focused);
                                break;
                            case "YEAR_DS":
                                object_selected = "SHIFT_DS";
                                year.setBackgroundResource(R.drawable.unfocused);
                                shift.setBackgroundResource(R.drawable.focused);
                                break;
                            case "EMAIL1_AFM":
                                this.email1.setBackgroundResource(R.drawable.unfocused);
                                this.email2.setBackgroundResource(R.drawable.focused);
                                object_selected = "EMAIL2_AFM";
                                first_time_selected = true;
                                break;
                            case "DAY_ERC":
                                object_selected = "MONTH_ERC";
                                this.day.setBackgroundResource(R.drawable.unfocused);
                                this.month.setBackgroundResource(R.drawable.focused);
                                break;
                            case "MONTH_ERC":
                                object_selected = "YEAR_ERC";
                                this.month.setBackgroundResource(R.drawable.unfocused);
                                this.year.setBackgroundResource(R.drawable.focused);
                                break;
                            case "YEAR_ERC":
                                object_selected = "RATE_TYPE_ERC";
                                this.year.setBackgroundResource(R.drawable.unfocused);
                                this.rateType.setBackgroundResource(R.drawable.focused);
                                break;
                            case "COW_FAT_FROM":
                                object_selected = "COW_FAT_TO";
                                first_time_selected = true;
                                this.cow_fat_from.setBackgroundResource(R.drawable.unfocused);
                                this.cow_fat_to.setBackgroundResource(R.drawable.focused);
                                break;
                            case "COW_SNF_FROM":
                                object_selected = "COW_SNF_TO";
                                first_time_selected = true;
                                this.cow_snf_from.setBackgroundResource(R.drawable.unfocused);
                                this.cow_snf_to.setBackgroundResource(R.drawable.focused);
                                break;
                            case "BUFFALO_FAT_FROM":
                                object_selected = "BUFFALO_FAT_TO";
                                first_time_selected = true;
                                this.buffalo_fat_from.setBackgroundResource(R.drawable.unfocused);
                                this.buffalo_fat_to.setBackgroundResource(R.drawable.focused);
                                break;
                            case "BUFFALO_SNF_FROM":
                                object_selected = "BUFFALO_SNF_TO";
                                first_time_selected = true;
                                this.buffalo_snf_from.setBackgroundResource(R.drawable.unfocused);
                                this.buffalo_snf_to.setBackgroundResource(R.drawable.focused);
                                break;
                            case "SANALYZER":
                                if (sanalyzer.getSelectedItemPosition() + 1 != sanalyzer.getCount())
                                    sanalyzer.setSelection(sanalyzer.getSelectedItemPosition() + 1);
                                break;
                            case "SWEIGHT":
                                if (sweight.getSelectedItemPosition() + 1 != sweight.getCount())
                                    sweight.setSelection(sweight.getSelectedItemPosition() + 1);
                                break;
                            case "SPRINTER":
                                if (sprinter.getSelectedItemPosition() + 1 != sprinter.getCount())
                                    sprinter.setSelection(sprinter.getSelectedItemPosition() + 1);
                                break;
                            case "SSHIFT_TIME":
                                if (sshift_time.getSelectedItemPosition() + 1 != sshift_time.getCount())
                                    sshift_time.setSelection(sshift_time.getSelectedItemPosition() + 1);
                                break;
                            case "SMEASUREMENT_UNIT":
                                if (smeasurement_unit.getSelectedItemPosition() + 1 != smeasurement_unit.getCount())
                                    smeasurement_unit.setSelection(smeasurement_unit.getSelectedItemPosition() + 1);
                                break;
                            case "MAN_RC_ENTRY":
                            case "SHOW_RATE_CHART":
                                if (tab_selected != ((Activity) context).getActionBar().getTabCount() - 1) {
                                    tab_selected++;
                                    item_selected = 1;
                                    ((Activity) context).getActionBar().setSelectedNavigationItem(tab_selected);
                                }
                                break;
                            default:
                                Toast.makeText(this.context, "Last Object Reached", Toast.LENGTH_SHORT).show();
                        }
                    } else
                        machine_selection = false;
                    break;
                case "u":
                    if (!machine_selection) {
                        switch (object_selected) {
                            case "WEIGHT_BA":
                                this.code.setBackgroundResource(R.drawable.focused);
                                code.setTextColor(0xFFFFFFFF);
                                this.weight.setBackgroundResource(R.drawable.unfocused);
                                weight.setTextColor(0xFF000000);
                                object_selected = "CODE_BA";
                                first_time_selected = true;
                                break;
                            case "FAT_BA":
                                if (weight_entry) {
                                    this.weight.setBackgroundResource(R.drawable.focused);
                                    weight.setTextColor(0xFFFFFFFF);
                                    this.fat.setBackgroundResource(R.drawable.unfocused);
                                    fat.setTextColor(0xFF000000);
                                    object_selected = "WEIGHT_BA";
                                } else {
                                    this.code.setBackgroundResource(R.drawable.focused);
                                    code.setTextColor(0xFFFFFFFF);
                                    this.fat.setBackgroundResource(R.drawable.unfocused);
                                    fat.setTextColor(0xFF000000);
                                    object_selected = "CODE_BA";
                                }
                                first_time_selected = true;
                                break;
                            case "SNF_BA":
                                this.fat.setBackgroundResource(R.drawable.focused);
                                fat.setTextColor(0xFFFFFFFF);
                                this.snf.setBackgroundResource(R.drawable.unfocused);
                                snf.setTextColor(0xFF000000);
                                object_selected = "FAT_BA";
                                first_time_selected = true;
                                break;
                            case "Member Ledger":
                                this.listView.getChildAt(1).setBackgroundColor(0xFF000000);
                                this.listView.getChildAt(0).setBackgroundColor(0xFF037DA6);
                                object_selected = "Shift Reports";
                                break;
                            case "Payment Ledger":
                                this.listView.getChildAt(2).setBackgroundColor(0xFF000000);
                                this.listView.getChildAt(1).setBackgroundColor(0xFF037DA6);
                                object_selected = "Member Ledger";
                                break;
                            case "Duplication Slip":
                                this.listView.getChildAt(3).setBackgroundColor(0xFF000000);
                                this.listView.getChildAt(2).setBackgroundColor(0xFF037DA6);
                                object_selected = "Payment Ledger";
                                break;
                            case "Add Farmer/Member":
                                this.listView.getChildAt(4).setBackgroundColor(0xFF000000);
                                this.listView.getChildAt(3).setBackgroundColor(0xFF037DA6);
                                object_selected = "Duplication Slip";
                                break;
                            case "Settings":
                                this.listView.getChildAt(5).setBackgroundColor(0xFF000000);
                                this.listView.getChildAt(4).setBackgroundColor(0xFF037DA6);
                                object_selected = "Add Farmer/Member";
                                break;
                            case "Set Password":
                                this.listView.getChildAt(6).setBackgroundColor(0xFF000000);
                                this.listView.getChildAt(5).setBackgroundColor(0xFF037DA6);
                                object_selected = "Settings";
                                break;
                            case "Add Rate Chart":
                                this.listView.getChildAt(7).setBackgroundColor(0xFF000000);
                                this.listView.getChildAt(6).setBackgroundColor(0xFF037DA6);
                                object_selected = "Set Password";
                                break;
                            case "Sync":
                                this.listView.getChildAt(8).setBackgroundColor(0xFF000000);
                                this.listView.getChildAt(7).setBackgroundColor(0xFF037DA6);
                                object_selected = "Add Rate Chart";
                                break;
                            case "Device Info":
                                this.listView.getChildAt(9).setBackgroundColor(0xFF000000);
                                this.listView.getChildAt(8).setBackgroundColor(0xFF037DA6);
                                object_selected = "Sync";
                                break;
                            case "DAY_SR":
                            case "DAY_DS":
                            case "DAY_ERC":
                                this.datePicker.updateDate(this.datePicker.getYear(), this.datePicker.getMonth(), this.datePicker.getDayOfMonth() + 1);
                                break;
                            case "MONTH_SR":
                            case "MONTH_DS":
                            case "MONTH_ERC":
                                this.datePicker.updateDate(this.datePicker.getYear(), this.datePicker.getMonth() + 1, this.datePicker.getDayOfMonth());
                                break;
                            case "YEAR_SR":
                            case "YEAR_DS":
                            case "YEAR_ERC":
                                this.datePicker.updateDate(this.datePicker.getYear() + 1, this.datePicker.getMonth(), this.datePicker.getDayOfMonth());
                                break;
                            case "SHIFT_SR":
                            case "SHIFT_DS":
                                //this.radioGroup.check(this.radioGroup.getChildAt(0).getId());
                                this.shift.setText(R.string.morning);
                                break;
                            case "RATE_TYPE_ERC":
                                if (rateType.getText().toString().equals("File"))
                                    rateType.setText("Formula");
                                else if (rateType.getText().toString().equals("Manual"))
                                    rateType.setText("File");
                                else if (rateType.getText().toString().equals("Online"))
                                    rateType.setText("Manual");
                                break;
                            case "NAME_AFM":
                                this.code.setBackgroundResource(R.drawable.focused);
                                this.name.setBackgroundResource(R.drawable.unfocused);
                                object_selected = "CODE_AFM";
                                first_time_selected = true;
                                break;
                            case "MOBILE_AFM":
                                this.name.setBackgroundResource(R.drawable.focused);
                                this.mobile.setBackgroundResource(R.drawable.unfocused);
                                object_selected = "NAME_AFM";
                                first_time_selected = true;
                                break;
                            case "EMAIL1_AFM":
                                this.mobile.setBackgroundResource(R.drawable.focused);
                                this.email1.setBackgroundResource(R.drawable.unfocused);
                                object_selected = "MOBILE_AFM";
                                first_time_selected = true;
                                break;
                            case "SCROLL_SR":
                            case "SCROLL_ML":
                            case "SCROLL_PS":
                                scroll.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Integer nextScroll = scroll.getScrollY() - scroll.getHeight();
                                        if (nextScroll < scroll.getTop() - 73)
                                            scroll.scrollTo(0, scroll.getTop() - 73);
                                        else
                                            scroll.scrollTo(0, nextScroll);
                                    }
                                });
                                break;
                            case "DAY1_ML":
                            case "DAY1_PS":
                                this.datePicker1.updateDate(this.datePicker1.getYear(), this.datePicker1.getMonth(), this.datePicker1.getDayOfMonth() + 1);
                                break;
                            case "MONTH1_ML":
                            case "MONTH1_PS":
                                this.datePicker1.updateDate(this.datePicker1.getYear(), this.datePicker1.getMonth() + 1, this.datePicker1.getDayOfMonth());
                                break;
                            case "YEAR1_ML":
                            case "YEAR1_PS":
                                this.datePicker1.updateDate(this.datePicker1.getYear() + 1, this.datePicker1.getMonth(), this.datePicker1.getDayOfMonth());
                                break;
                            case "DAY2_ML":
                            case "DAY2_PS":
                                this.datePicker2.updateDate(this.datePicker2.getYear(), this.datePicker2.getMonth(), this.datePicker2.getDayOfMonth() + 1);
                                break;
                            case "MONTH2_ML":
                            case "MONTH2_PS":
                                this.datePicker2.updateDate(this.datePicker2.getYear(), this.datePicker2.getMonth() + 1, this.datePicker2.getDayOfMonth());
                                break;
                            case "YEAR2_ML":
                            case "YEAR2_PS":
                                this.datePicker2.updateDate(this.datePicker2.getYear() + 1, this.datePicker2.getMonth(), this.datePicker2.getDayOfMonth());
                                break;
                            case "SHOW2":
                            case "EDIT2":
                                object_selected = "SHOW1";
                                button2.setBackgroundResource(R.drawable.btn_unfocused);
                                button5.setBackgroundResource(R.drawable.btn_unfocused);
                                button1.setBackgroundResource(R.drawable.btn_focused);
                                linearLayout2.setBackgroundResource(R.drawable.unfocused);
                                linearLayout1.setBackgroundResource(R.drawable.focused);
                                break;
                            case "SHOW3":
                            case "EDIT3":
                                object_selected = "SHOW2";
                                button3.setBackgroundResource(R.drawable.btn_unfocused);
                                button6.setBackgroundResource(R.drawable.btn_unfocused);
                                button2.setBackgroundResource(R.drawable.btn_focused);
                                linearLayout3.setBackgroundResource(R.drawable.unfocused);
                                linearLayout2.setBackgroundResource(R.drawable.focused);
                                break;
                            case "EMAIL2_AFM":
                                email2.setText("@gmail.com");
                                break;
                            case "PER_COW_FAT":
                                object_selected = "STD_COW_RATE";
                                first_time_selected = true;
                                std_cow_rate.setTextColor(0xFFFFFFFF);
                                per_cow_fat.setTextColor(0xFF000000);
                                std_cow_rate.setBackgroundResource(R.drawable.focused);
                                per_cow_fat.setBackgroundResource(R.drawable.unfocused);
                                break;
                            case "STD_COW_FAT":
                                object_selected = "PER_COW_FAT";
                                first_time_selected = true;
                                per_cow_fat.setTextColor(0xFFFFFFFF);
                                std_cow_fat.setTextColor(0xFF000000);
                                per_cow_fat.setBackgroundResource(R.drawable.focused);
                                std_cow_fat.setBackgroundResource(R.drawable.unfocused);
                                break;
                            case "STD_COW_SNF":
                                object_selected = "STD_COW_FAT";
                                first_time_selected = true;
                                std_cow_fat.setTextColor(0xFFFFFFFF);
                                std_cow_snf.setTextColor(0xFF000000);
                                std_cow_fat.setBackgroundResource(R.drawable.focused);
                                std_cow_snf.setBackgroundResource(R.drawable.unfocused);
                                break;
                            case "STD_BUF_RATE":
                                object_selected = "STD_COW_SNF";
                                first_time_selected = true;
                                std_cow_snf.setTextColor(0xFFFFFFFF);
                                std_buffalo_rate.setTextColor(0xFF000000);
                                std_cow_snf.setBackgroundResource(R.drawable.focused);
                                std_buffalo_rate.setBackgroundResource(R.drawable.unfocused);
                                break;
                            case "PER_BUF_FAT":
                                object_selected = "STD_BUF_RATE";
                                first_time_selected = true;
                                std_buffalo_rate.setTextColor(0xFFFFFFFF);
                                per_buffalo_fat.setTextColor(0xFF000000);
                                std_buffalo_rate.setBackgroundResource(R.drawable.focused);
                                per_buffalo_fat.setBackgroundResource(R.drawable.unfocused);
                                break;
                            case "STD_BUF_FAT":
                                object_selected = "PER_BUF_FAT";
                                first_time_selected = true;
                                per_buffalo_fat.setTextColor(0xFFFFFFFF);
                                std_buffalo_fat.setTextColor(0xFF000000);
                                per_buffalo_fat.setBackgroundResource(R.drawable.focused);
                                std_buffalo_fat.setBackgroundResource(R.drawable.unfocused);
                                break;
                            case "STD_BUF_SNF":
                                object_selected = "STD_BUF_FAT";
                                first_time_selected = true;
                                std_buffalo_fat.setTextColor(0xFFFFFFFF);
                                std_buffalo_snf.setTextColor(0xFF000000);
                                std_buffalo_fat.setBackgroundResource(R.drawable.focused);
                                std_buffalo_snf.setBackgroundResource(R.drawable.unfocused);
                                break;
                            case "COW_SNF_FROM":
                                object_selected = "COW_FAT_FROM";
                                first_time_selected = true;
                                this.cow_snf_from.setBackgroundResource(R.drawable.unfocused);
                                this.cow_fat_from.setBackgroundResource(R.drawable.focused);
                                break;
                            case "COW_SNF_TO":
                                object_selected = "COW_FAT_TO";
                                first_time_selected = true;
                                this.cow_snf_to.setBackgroundResource(R.drawable.unfocused);
                                this.cow_fat_to.setBackgroundResource(R.drawable.focused);
                                break;
                            case "BUFFALO_FAT_FROM":
                                object_selected = "COW_SNF_FROM";
                                first_time_selected = true;
                                this.buffalo_fat_from.setBackgroundResource(R.drawable.unfocused);
                                this.cow_snf_from.setBackgroundResource(R.drawable.focused);
                                break;
                            case "BUFFALO_FAT_TO":
                                object_selected = "COW_SNF_TO";
                                first_time_selected = true;
                                this.buffalo_fat_to.setBackgroundResource(R.drawable.unfocused);
                                this.cow_snf_to.setBackgroundResource(R.drawable.focused);
                                break;
                            case "BUFFALO_SNF_FROM":
                                object_selected = "BUFFALO_FAT_FROM";
                                first_time_selected = true;
                                this.buffalo_snf_from.setBackgroundResource(R.drawable.unfocused);
                                this.buffalo_fat_from.setBackgroundResource(R.drawable.focused);
                                break;
                            case "BUFFALO_SNF_TO":
                                object_selected = "BUFFALO_FAT_TO";
                                first_time_selected = true;
                                this.buffalo_snf_to.setBackgroundResource(R.drawable.unfocused);
                                this.buffalo_fat_to.setBackgroundResource(R.drawable.focused);
                                break;
                            case "SANALYZER":
                                object_selected = "BUFFALO_SNF_FROM";
                                first_time_selected = true;
                                this.rl_analyzer.setBackgroundResource(R.drawable.unfocused);
                                this.buffalo_snf_from.setBackgroundResource(R.drawable.focused);
                                break;
                            case "SWEIGHT":
                                object_selected = "SANALYZER";
                                this.rl_weight.setBackgroundResource(R.drawable.unfocused);
                                this.rl_analyzer.setBackgroundResource(R.drawable.focused);
                                break;
                            case "SPRINTER":
                                object_selected = "SWEIGHT";
                                this.rl_printer.setBackgroundResource(R.drawable.unfocused);
                                this.rl_weight.setBackgroundResource(R.drawable.focused);
                                break;
                            case "SSHIFT_TIME":
                                object_selected = "SPRINTER";
                                this.rl_shift_time.setBackgroundResource(R.drawable.unfocused);
                                this.rl_printer.setBackgroundResource(R.drawable.focused);
                                break;
                            case "SMEASUREMENT_UNIT":
                                object_selected = "SSHIFT_TIME";
                                this.rl_measurement_unit.setBackgroundResource(R.drawable.unfocused);
                                this.rl_shift_time.setBackgroundResource(R.drawable.focused);
                                break;
                            case "DAIRY_NAME":
                                object_selected = "SMEASUREMENT_UNIT";
                                this.dairy_name.setBackgroundResource(R.drawable.unfocused);
                                this.rl_measurement_unit.setBackgroundResource(R.drawable.focused);
                                break;
                            case "FOOTER":
                                object_selected = "DAIRY_NAME";
                                first_time_selected = true;
                                this.footer.setBackgroundResource(R.drawable.unfocused);
                                this.dairy_name.setBackgroundResource(R.drawable.focused);
                                break;
                            case "MAN_RC_ENTRY":
                                if (item_selected != 1) {
                                    item_selected--;
                                    first_time_selected = true;
                                    //listView.setAdapter(customMRCList);
                                    customMRCList.notifyDataSetChanged();
                                    listView.setSelection(item_selected - 1);
                                }
                                break;
                            case "SHOW_RATE_CHART":
                                if (item_selected != 1) {
                                    item_selected--;
                                    listView.setSelection(item_selected - 1);
                                }
                                break;
                            case "FILE_RATE_CHART":
                                if (item_selected != 0) {
                                    item_selected--;
                                    fileArrayAdapter.notifyDataSetChanged();
                                    listView.setSelection(item_selected);
                                }
                                break;
                            case "ROUTE_CODE":
                                this.dairy_code.setBackgroundResource(R.drawable.focused);
                                dairy_code.setTextColor(0xFFFFFFFF);
                                this.route_code.setBackgroundResource(R.drawable.unfocused);
                                route_code.setTextColor(0xFF000000);
                                object_selected = "DAIRY_CODE";
                                first_time_selected = true;
                                break;
                            case "VILLAGE_CODE":
                                this.route_code.setBackgroundResource(R.drawable.focused);
                                route_code.setTextColor(0xFFFFFFFF);
                                this.village_code.setBackgroundResource(R.drawable.unfocused);
                                village_code.setTextColor(0xFF000000);
                                object_selected = "ROUTE_CODE";
                                first_time_selected = true;
                                break;
                            case "CENTER_CODE":
                                this.village_code.setBackgroundResource(R.drawable.focused);
                                village_code.setTextColor(0xFFFFFFFF);
                                this.center_code.setBackgroundResource(R.drawable.unfocused);
                                center_code.setTextColor(0xFF000000);
                                object_selected = "VILLAGE_CODE";
                                first_time_selected = true;
                                break;
                            case "NEW_PASS":
                                this.oldpass.setBackgroundResource(R.drawable.focused);
                                oldpass.setTextColor(0xFFFFFFFF);
                                this.newpass.setBackgroundResource(R.drawable.unfocused);
                                newpass.setTextColor(0xFF000000);
                                object_selected = "OLD_PASS";
                                first_time_selected = true;
                                break;
                            case "REPEAT_PASS":
                                this.newpass.setBackgroundResource(R.drawable.focused);
                                newpass.setTextColor(0xFFFFFFFF);
                                this.repeatpass.setBackgroundResource(R.drawable.unfocused);
                                repeatpass.setTextColor(0xFF000000);
                                object_selected = "NEW_PASS";
                                first_time_selected = true;
                                break;
                            default:
                                Toast.makeText(this.context, "Last Object Reached", Toast.LENGTH_SHORT).show();
                        }
                    } else
                        machine_selection = false;
                    break;
                case "d":
                    if (!machine_selection) {
                        switch (object_selected) {
                            case "CODE_BA":
                                if (weight_entry) {
                                    this.code.setBackgroundResource(R.drawable.unfocused);
                                    code.setTextColor(0xFF000000);
                                    this.weight.setBackgroundResource(R.drawable.focused);
                                    weight.setTextColor(0xFFFFFFFF);
                                    object_selected = "WEIGHT_BA";
                                    first_time_selected = true;
                                } else if (analyzer_entry) {
                                    this.code.setBackgroundResource(R.drawable.unfocused);
                                    code.setTextColor(0xFF000000);
                                    this.fat.setBackgroundResource(R.drawable.focused);
                                    fat.setTextColor(0xFFFFFFFF);
                                    object_selected = "FAT_BA";
                                    first_time_selected = true;
                                }
                                break;
                            case "WEIGHT_BA":
                                if (analyzer_entry) {
                                    this.weight.setBackgroundResource(R.drawable.unfocused);
                                    weight.setTextColor(0xFF000000);
                                    this.fat.setBackgroundResource(R.drawable.focused);
                                    fat.setTextColor(0xFFFFFFFF);
                                    object_selected = "FAT_BA";
                                    first_time_selected = true;
                                }
                                break;
                            case "FAT_BA":
                                this.fat.setBackgroundResource(R.drawable.unfocused);
                                fat.setTextColor(0xFF000000);
                                this.snf.setBackgroundResource(R.drawable.focused);
                                snf.setTextColor(0xFFFFFFFF);
                                object_selected = "SNF_BA";
                                first_time_selected = true;
                                break;
                            case "Shift Reports":
                                this.listView.getChildAt(0).setBackgroundColor(0xFF000000);
                                this.listView.getChildAt(1).setBackgroundColor(0xFF037DA6);
                                object_selected = "Member Ledger";
                                break;
                            case "Member Ledger":
                                this.listView.getChildAt(1).setBackgroundColor(0xFF000000);
                                this.listView.getChildAt(2).setBackgroundColor(0xFF037DA6);
                                object_selected = "Payment Ledger";
                                break;
                            case "Payment Ledger":
                                this.listView.getChildAt(2).setBackgroundColor(0xFF000000);
                                this.listView.getChildAt(3).setBackgroundColor(0xFF037DA6);
                                object_selected = "Duplication Slip";
                                break;
                            case "Duplication Slip":
                                this.listView.getChildAt(3).setBackgroundColor(0xFF000000);
                                this.listView.getChildAt(4).setBackgroundColor(0xFF037DA6);
                                object_selected = "Add Farmer/Member";
                                break;
                            case "Add Farmer/Member":
                                this.listView.getChildAt(4).setBackgroundColor(0xFF000000);
                                this.listView.getChildAt(5).setBackgroundColor(0xFF037DA6);
                                object_selected = "Settings";
                                break;
                            case "Settings":
                                this.listView.getChildAt(5).setBackgroundColor(0xFF000000);
                                this.listView.getChildAt(6).setBackgroundColor(0xFF037DA6);
                                object_selected = "Set Password";
                                break;
                            case "Set Password":
                                this.listView.getChildAt(6).setBackgroundColor(0xFF000000);
                                this.listView.getChildAt(7).setBackgroundColor(0xFF037DA6);
                                object_selected = "Add Rate Chart";
                                break;
                            case "Add Rate Chart":
                                this.listView.getChildAt(7).setBackgroundColor(0xFF000000);
                                this.listView.getChildAt(8).setBackgroundColor(0xFF037DA6);
                                object_selected = "Sync";
                                break;
                            case "Sync":
                                this.listView.getChildAt(8).setBackgroundColor(0xFF000000);
                                this.listView.getChildAt(9).setBackgroundColor(0xFF037DA6);
                                object_selected = "Device Info";
                                break;
                            case "DAY_SR":
                            case "DAY_DS":
                            case "DAY_ERC":
                                this.datePicker.updateDate(this.datePicker.getYear(), this.datePicker.getMonth(), this.datePicker.getDayOfMonth() - 1);
                                break;
                            case "MONTH_SR":
                            case "MONTH_DS":
                            case "MONTH_ERC":
                                this.datePicker.updateDate(this.datePicker.getYear(), this.datePicker.getMonth() - 1, this.datePicker.getDayOfMonth());
                                break;
                            case "YEAR_SR":
                            case "YEAR_DS":
                            case "YEAR_ERC":
                                this.datePicker.updateDate(this.datePicker.getYear() - 1, this.datePicker.getMonth(), this.datePicker.getDayOfMonth());
                                break;
                            case "SHIFT_SR":
                            case "SHIFT_DS":
                                //this.radioGroup.check(this.radioGroup.getChildAt(1).getId());
                                this.shift.setText(R.string.evening);
                                break;
                            case "RATE_TYPE_ERC":
                                if (rateType.getText().toString().equals("Formula"))
                                    rateType.setText("File");
                                else if (rateType.getText().toString().equals("File"))
                                    rateType.setText("Manual");
                                else if (rateType.getText().toString().equals("Manual")) {
                                    rateType.setText("Online");
                                }
                                break;
                            case "CODE_AFM":
                                this.code.setBackgroundResource(R.drawable.unfocused);
                                this.name.setBackgroundResource(R.drawable.focused);
                                object_selected = "NAME_AFM";
                                first_time_selected = true;
                                break;
                            case "NAME_AFM":
                                this.name.setBackgroundResource(R.drawable.unfocused);
                                this.mobile.setBackgroundResource(R.drawable.focused);
                                object_selected = "MOBILE_AFM";
                                first_time_selected = true;
                                break;
                            case "MOBILE_AFM":
                                this.mobile.setBackgroundResource(R.drawable.unfocused);
                                this.email1.setBackgroundResource(R.drawable.focused);
                                object_selected = "EMAIL1_AFM";
                                first_time_selected = true;
                                break;
                            case "SCROLL_SR":
                            case "SCROLL_ML":
                            case "SCROLL_PS":
                                scroll.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        Integer nextScroll = scroll.getScrollY() + scroll.getHeight();
                                        if (nextScroll > scroll.getChildAt(0).getHeight())
                                            scroll.scrollTo(0, scroll.getChildAt(0).getHeight());
                                        else
                                            scroll.scrollTo(0, nextScroll);
                                    }
                                });
                                break;
                            case "DAY1_ML":
                            case "DAY1_PS":
                                this.datePicker1.updateDate(this.datePicker1.getYear(), this.datePicker1.getMonth(), this.datePicker1.getDayOfMonth() - 1);
                                break;
                            case "MONTH1_ML":
                            case "MONTH1_PS":
                                this.datePicker1.updateDate(this.datePicker1.getYear(), this.datePicker1.getMonth() - 1, this.datePicker1.getDayOfMonth());
                                break;
                            case "YEAR1_ML":
                            case "YEAR1_PS":
                                this.datePicker1.updateDate(this.datePicker1.getYear() - 1, this.datePicker1.getMonth(), this.datePicker1.getDayOfMonth());
                                break;
                            case "DAY2_ML":
                            case "DAY2_PS":
                                this.datePicker2.updateDate(this.datePicker2.getYear(), this.datePicker2.getMonth(), this.datePicker2.getDayOfMonth() - 1);
                                break;
                            case "MONTH2_ML":
                            case "MONTH2_PS":
                                this.datePicker2.updateDate(this.datePicker2.getYear(), this.datePicker2.getMonth() - 1, this.datePicker2.getDayOfMonth());
                                break;
                            case "YEAR2_ML":
                            case "YEAR2_PS":
                                this.datePicker2.updateDate(this.datePicker2.getYear() - 1, this.datePicker2.getMonth(), this.datePicker2.getDayOfMonth());
                                break;
                            case "SHOW1":
                            case "EDIT1":
                                object_selected = "SHOW2";
                                button1.setBackgroundResource(R.drawable.btn_unfocused);
                                button4.setBackgroundResource(R.drawable.btn_unfocused);
                                button2.setBackgroundResource(R.drawable.btn_focused);
                                linearLayout1.setBackgroundResource(R.drawable.unfocused);
                                linearLayout2.setBackgroundResource(R.drawable.focused);
                                break;
                            case "SHOW2":
                            case "EDIT2":
                                object_selected = "SHOW3";
                                button2.setBackgroundResource(R.drawable.btn_unfocused);
                                button5.setBackgroundResource(R.drawable.btn_unfocused);
                                button3.setBackgroundResource(R.drawable.btn_focused);
                                linearLayout2.setBackgroundResource(R.drawable.unfocused);
                                linearLayout3.setBackgroundResource(R.drawable.focused);
                                break;
                            case "EMAIL2_AFM":
                                email2.setText("@yahoo.com");
                                break;
                            case "STD_COW_RATE":
                                object_selected = "PER_COW_FAT";
                                first_time_selected = true;
                                std_cow_rate.setTextColor(0xFF000000);
                                per_cow_fat.setTextColor(0xFFFFFFFF);
                                std_cow_rate.setBackgroundResource(R.drawable.unfocused);
                                per_cow_fat.setBackgroundResource(R.drawable.focused);
                                break;
                            case "PER_COW_FAT":
                                object_selected = "STD_COW_FAT";
                                first_time_selected = true;
                                per_cow_fat.setTextColor(0xFF000000);
                                std_cow_fat.setTextColor(0xFFFFFFFF);
                                per_cow_fat.setBackgroundResource(R.drawable.unfocused);
                                std_cow_fat.setBackgroundResource(R.drawable.focused);
                                break;
                            case "STD_COW_FAT":
                                object_selected = "STD_COW_SNF";
                                first_time_selected = true;
                                std_cow_fat.setTextColor(0xFF000000);
                                std_cow_snf.setTextColor(0xFFFFFFFF);
                                std_cow_fat.setBackgroundResource(R.drawable.unfocused);
                                std_cow_snf.setBackgroundResource(R.drawable.focused);
                                break;
                            case "STD_COW_SNF":
                                object_selected = "STD_BUF_RATE";
                                first_time_selected = true;
                                std_cow_snf.setTextColor(0xFF000000);
                                std_buffalo_rate.setTextColor(0xFFFFFFFF);
                                std_cow_snf.setBackgroundResource(R.drawable.unfocused);
                                std_buffalo_rate.setBackgroundResource(R.drawable.focused);
                                break;
                            case "STD_BUF_RATE":
                                object_selected = "PER_BUF_FAT";
                                first_time_selected = true;
                                std_buffalo_rate.setTextColor(0xFF000000);
                                per_buffalo_fat.setTextColor(0xFFFFFFFF);
                                std_buffalo_rate.setBackgroundResource(R.drawable.unfocused);
                                per_buffalo_fat.setBackgroundResource(R.drawable.focused);
                                break;
                            case "PER_BUF_FAT":
                                object_selected = "STD_BUF_FAT";
                                first_time_selected = true;
                                per_buffalo_fat.setTextColor(0xFF000000);
                                std_buffalo_fat.setTextColor(0xFFFFFFFF);
                                per_buffalo_fat.setBackgroundResource(R.drawable.unfocused);
                                std_buffalo_fat.setBackgroundResource(R.drawable.focused);
                                break;
                            case "STD_BUF_FAT":
                                object_selected = "STD_BUF_SNF";
                                first_time_selected = true;
                                std_buffalo_fat.setTextColor(0xFF000000);
                                std_buffalo_snf.setTextColor(0xFFFFFFFF);
                                std_buffalo_fat.setBackgroundResource(R.drawable.unfocused);
                                std_buffalo_snf.setBackgroundResource(R.drawable.focused);
                                break;
                            case "COW_FAT_FROM":
                                object_selected = "COW_SNF_FROM";
                                first_time_selected = true;
                                this.cow_snf_from.setBackgroundResource(R.drawable.focused);
                                this.cow_fat_from.setBackgroundResource(R.drawable.unfocused);
                                break;
                            case "COW_FAT_TO":
                                object_selected = "COW_SNF_TO";
                                first_time_selected = true;
                                this.cow_snf_to.setBackgroundResource(R.drawable.focused);
                                this.cow_fat_to.setBackgroundResource(R.drawable.unfocused);
                                break;
                            case "COW_SNF_FROM":
                                object_selected = "BUFFALO_FAT_FROM";
                                first_time_selected = true;
                                this.buffalo_fat_from.setBackgroundResource(R.drawable.focused);
                                this.cow_snf_from.setBackgroundResource(R.drawable.unfocused);
                                break;
                            case "COW_SNF_TO":
                                object_selected = "BUFFALO_FAT_TO";
                                first_time_selected = true;
                                this.buffalo_fat_to.setBackgroundResource(R.drawable.focused);
                                this.cow_snf_to.setBackgroundResource(R.drawable.unfocused);
                                break;
                            case "BUFFALO_FAT_FROM":
                                object_selected = "BUFFALO_SNF_FROM";
                                first_time_selected = true;
                                this.buffalo_snf_from.setBackgroundResource(R.drawable.focused);
                                this.buffalo_fat_from.setBackgroundResource(R.drawable.unfocused);
                                break;
                            case "BUFFALO_FAT_TO":
                                object_selected = "BUFFALO_SNF_TO";
                                first_time_selected = true;
                                this.buffalo_snf_to.setBackgroundResource(R.drawable.focused);
                                this.buffalo_fat_to.setBackgroundResource(R.drawable.unfocused);
                                break;
                            case "BUFFALO_SNF_FROM":
                                first_time_selected = true;
                                object_selected = "SANALYZER";
                                this.rl_analyzer.setBackgroundResource(R.drawable.focused);
                                this.buffalo_snf_from.setBackgroundResource(R.drawable.unfocused);
                                break;
                            case "BUFFALO_SNF_TO":
                                object_selected = "SANALYZER";
                                this.rl_analyzer.setBackgroundResource(R.drawable.focused);
                                this.buffalo_snf_to.setBackgroundResource(R.drawable.unfocused);
                                break;
                            case "SANALYZER":
                                object_selected = "SWEIGHT";
                                this.rl_weight.setBackgroundResource(R.drawable.focused);
                                this.rl_analyzer.setBackgroundResource(R.drawable.unfocused);
                                break;
                            case "SWEIGHT":
                                object_selected = "SPRINTER";
                                this.rl_printer.setBackgroundResource(R.drawable.focused);
                                this.rl_weight.setBackgroundResource(R.drawable.unfocused);
                                break;
                            case "SPRINTER":
                                object_selected = "SSHIFT_TIME";
                                this.rl_shift_time.setBackgroundResource(R.drawable.focused);
                                this.rl_printer.setBackgroundResource(R.drawable.unfocused);
                                break;
                            case "SSHIFT_TIME":
                                object_selected = "SMEASUREMENT_UNIT";
                                this.rl_measurement_unit.setBackgroundResource(R.drawable.focused);
                                this.rl_shift_time.setBackgroundResource(R.drawable.unfocused);
                                break;
                            case "SMEASUREMENT_UNIT":
                                object_selected = "DAIRY_NAME";
                                first_time_selected = true;
                                this.rl_measurement_unit.setBackgroundResource(R.drawable.unfocused);
                                this.dairy_name.setBackgroundResource(R.drawable.focused);
                                break;
                            case "DAIRY_NAME":
                                object_selected = "FOOTER";
                                first_time_selected = true;
                                this.footer.setBackgroundResource(R.drawable.focused);
                                this.dairy_name.setBackgroundResource(R.drawable.unfocused);
                                break;
                            case "MAN_RC_ENTRY":
                                if (item_selected != listView.getCount()) {
                                    item_selected++;
                                    first_time_selected = true;
                                    //listView.setAdapter(customMRCList);
                                    customMRCList.notifyDataSetChanged();
                                    listView.setSelection(item_selected - 1);
                                }
                                break;
                            case "SHOW_RATE_CHART":
                                if (item_selected != listView.getCount()) {
                                    item_selected++;
                                    listView.setSelection(item_selected - 1);
                                }
                                break;
                            case "FILE_RATE_CHART":
                                if (item_selected != listView.getCount()) {
                                    item_selected++;
                                    fileArrayAdapter.notifyDataSetChanged();
                                    listView.setSelection(item_selected);
                                }
                                break;
                            case "DAIRY_CODE":
                                this.dairy_code.setBackgroundResource(R.drawable.unfocused);
                                dairy_code.setTextColor(0xFF000000);
                                this.route_code.setBackgroundResource(R.drawable.focused);
                                route_code.setTextColor(0xFFFFFFFF);
                                object_selected = "ROUTE_CODE";
                                first_time_selected = true;
                                break;
                            case "ROUTE_CODE":
                                this.route_code.setBackgroundResource(R.drawable.unfocused);
                                route_code.setTextColor(0xFF000000);
                                this.village_code.setBackgroundResource(R.drawable.focused);
                                village_code.setTextColor(0xFFFFFFFF);
                                object_selected = "VILLAGE_CODE";
                                first_time_selected = true;
                                break;
                            case "VILLAGE_CODE":
                                this.village_code.setBackgroundResource(R.drawable.unfocused);
                                village_code.setTextColor(0xFF000000);
                                this.center_code.setBackgroundResource(R.drawable.focused);
                                center_code.setTextColor(0xFFFFFFFF);
                                object_selected = "CENTER_CODE";
                                first_time_selected = true;
                                break;
                            case "OLD_PASS":
                                this.oldpass.setBackgroundResource(R.drawable.unfocused);
                                oldpass.setTextColor(0xFF000000);
                                this.newpass.setBackgroundResource(R.drawable.focused);
                                newpass.setTextColor(0xFFFFFFFF);
                                object_selected = "NEW_PASS";
                                first_time_selected = true;
                                break;
                            case "NEW_PASS":
                                this.newpass.setBackgroundResource(R.drawable.unfocused);
                                newpass.setTextColor(0xFF000000);
                                this.repeatpass.setBackgroundResource(R.drawable.focused);
                                repeatpass.setTextColor(0xFFFFFFFF);
                                object_selected = "REPEAT_PASS";
                                first_time_selected = true;
                                break;
                            default:
                                Toast.makeText(this.context, "Last Object Reached", Toast.LENGTH_SHORT).show();
                        }
                    } else
                        machine_selection = false;
                    break;
                case "e":
                    if (!machine_selection) {
                        if (object_selected.equals("SCROLL_SR")) {
                            object_selected = "DAY_SR";
                            this.day.setBackgroundResource(R.drawable.focused);
                        } else if (object_selected.equals("SCROLL_ML")) {
                            object_selected = "DAY1_ML";
                            this.day1.setBackgroundResource(R.drawable.focused);
                        } else if (object_selected.equals("SCROLL_PS")) {
                            object_selected = "DAY1_PS";
                            this.day1.setBackgroundResource(R.drawable.focused);
                        } else if (object_selected.equals("POPUP_PRINT_PREVIEW")) {
                            print_cancel = "cancel";
                            print.callOnClick();
                            break;
                        } else if ((context.getClass().getSimpleName().equals("BaseActivity")) && (!exit)) {
                            if (this.drawerLayout.isDrawerOpen(this.listView)) {
                                this.drawerLayout.closeDrawer(this.listView);
                                object_selected = "CODE_BA";
                                this.code.setBackgroundResource(R.drawable.focused);
                                this.weight.setBackgroundResource(R.drawable.unfocused);
                                this.fat.setBackgroundResource(R.drawable.unfocused);
                                this.snf.setBackgroundResource(R.drawable.unfocused);
                            } else {
                                Toast.makeText(context, "Please press 'ESC' again to exit", Toast.LENGTH_SHORT).show();
                                exit = true;
                                new Handler().postDelayed(new Runnable() {
                                    public void run() {
                                        exit = false;
                                    }
                                }, 2000);
                            }
                        } else
                            ((Activity) this.context).finish();
                        if (save_collections)
                            save_collections = false;
                        break;
                    } else
                        machine_selection = false;
                case "m":
                case "M":
                    if (!machine_selection && context.getClass().getSimpleName().equals("BaseActivity")) {
                        if (this.drawerLayout.isDrawerOpen(this.listView)) {
                            this.drawerLayout.closeDrawer(this.listView);
                            object_selected = "CODE_BA";
                            this.code.setBackgroundResource(R.drawable.focused);
                            this.weight.setBackgroundResource(R.drawable.unfocused);
                            this.fat.setBackgroundResource(R.drawable.unfocused);
                            this.snf.setBackgroundResource(R.drawable.unfocused);
                        } else {
                            this.listView.getChildAt(0).setBackgroundColor(0xFF037DA6);
                            this.listView.getChildAt(1).setBackgroundColor(0xFF000000);
                            this.listView.getChildAt(2).setBackgroundColor(0xFF000000);
                            this.listView.getChildAt(3).setBackgroundColor(0xFF000000);
                            this.listView.getChildAt(4).setBackgroundColor(0xFF000000);
                            this.listView.getChildAt(5).setBackgroundColor(0xFF000000);
                            this.listView.getChildAt(6).setBackgroundColor(0xFF000000);
                            this.listView.getChildAt(7).setBackgroundColor(0xFF000000);
                            this.listView.getChildAt(8).setBackgroundColor(0xFF000000);
                            this.drawerLayout.openDrawer(this.listView);
                            object_selected = "Shift Reports";
                        }
                    } else if (!machine_selection && context.getClass().getSimpleName().equals("addFarmerMember")) {
                        switch (object_selected) {
                            case "NAME_AFM":
                                if (first_time_selected) {
                                    name.setText(data);
                                    first_time_selected = false;
                                } else
                                    name.setText(name.getText() + data);
                                break;
                            case "MOBILE_AFM":
                                if (data.matches("\\+?\\d+?")) {
                                    if (first_time_selected) {
                                        mobile.setText(data);
                                        first_time_selected = false;
                                    } else
                                        mobile.setText(mobile.getText() + data);
                                }
                                break;
                            case "EMAIL1_AFM":
                                if (first_time_selected) {
                                    email1.setText(data);
                                    first_time_selected = false;
                                } else
                                    email1.setText(email1.getText() + data);
                                break;
                        }
                    } else
                        machine_selection = false;
                    break;
                case "p":
                case "P":
                    if (!machine_selection) {
                        switch (object_selected) {
                            case "PRINT_PREVIEW":
                                button.callOnClick();
                                break;
                            case "CODE_DS":
                            case "DAY_DS":
                            case "MONTH_DS":
                            case "YEAR_DS":
                            case "SHIFT_DS":
                            case "SCROLL_SR":
                            case "SCROLL_ML":
                            case "SCROLL_PS":
                                button.callOnClick();
                                break;
                            case "POPUP_PRINT_PREVIEW":
                                if (!(print_cancel.equals("pass") || print_cancel.equals("mpass") || print_cancel.equals("rate"))) {
                                    print_cancel = "print";
                                    print.callOnClick();
                                }
                                break;
                            case "NAME_AFM":
                                if (first_time_selected) {
                                    name.setText(data);
                                    first_time_selected = false;
                                } else
                                    name.setText(name.getText() + data);
                                break;
                            case "MOBILE_AFM":
                                if (data.matches("\\+?\\d+?")) {
                                    if (first_time_selected) {
                                        mobile.setText(data);
                                        first_time_selected = false;
                                    } else
                                        mobile.setText(mobile.getText() + data);
                                }
                                break;
                            case "EMAIL1_AFM":
                                if (first_time_selected) {
                                    email1.setText(data);
                                    first_time_selected = false;
                                } else
                                    email1.setText(email1.getText() + data);
                                break;
                            default:
                                Toast.makeText(this.context, "No Object Selected", Toast.LENGTH_SHORT).show();
                        }
                    } else
                        machine_selection = false;
                    break;
                case "]":
                    if (!machine_selection) {
                        switch (object_selected) {
                            case "CODE_BA":
                            case "WEIGHT_BA":
                            case "FAT_BA":
                            case "SNF_BA":
                                if (code.getText().toString().equals("") || weight.getText().toString().equals("") || fat.getText().toString().equals("") || snf.getText().toString().equals("")) {
                                    Toast.makeText(context, "Please fill all mandatory fields.", Toast.LENGTH_SHORT).show();
                                } else {
                                    print_cancel = "cancel";
                                    button.callOnClick();
                                }
                                break;
                            case "CODE_AFM":
                            case "NAME_AFM":
                            case "MOBILE_AFM":
                            case "EMAIL1_AFM":
                            case "EMAIL2_AFM":
                                if (code.getText().toString().equals("") || name.getText().toString().equals("") || mobile.getText().toString().equals("") || email1.getText().toString().equals("") || email2.getText().toString().equals("")) {
                                    Toast.makeText(context, "Please fill all mandatory fields.", Toast.LENGTH_SHORT).show();
                                } else {
                                    button.callOnClick();
                                }
                                break;
                            case "Shift Reports":
                                openActivity(1);
                                break;
                            case "Member Ledger":
                                openActivity(2);
                                break;
                            case "Payment Ledger":
                                openActivity(3);
                                break;
                            case "Duplication Slip":
                                openActivity(4);
                                break;
                            case "Add Farmer/Member":
                                openActivity(5);
                                break;
                            case "Settings":
                                openActivity(6);
                                break;
                            case "Set Password":
                                openActivity(7);
                                break;
                            case "Add Rate Chart":
                                openActivity(8);
                                break;
                            case "Sync":
                                openActivity(9);
                                break;
                            case "Device Info":
                                openActivity(10);
                            case "DAY_SR":
                            case "MONTH_SR":
                            case "YEAR_SR":
                            case "SHIFT_SR":
                                this.day.setBackgroundResource(R.drawable.unfocused);
                                this.month.setBackgroundResource(R.drawable.unfocused);
                                this.year.setBackgroundResource(R.drawable.unfocused);
                                this.shift.setBackgroundResource(R.drawable.unfocused);
                                object_selected = "UPDATE_SR";
                                button.callOnClick();
                                break;
                            case "DAY1_ML":
                            case "MONTH1_ML":
                            case "YEAR1_ML":
                            case "DAY2_ML":
                            case "MONTH2_ML":
                            case "YEAR2_ML":
                            case "CODE_ML":
                                this.day1.setBackgroundResource(R.drawable.unfocused);
                                this.month1.setBackgroundResource(R.drawable.unfocused);
                                this.year1.setBackgroundResource(R.drawable.unfocused);
                                this.day2.setBackgroundResource(R.drawable.unfocused);
                                this.month2.setBackgroundResource(R.drawable.unfocused);
                                this.year2.setBackgroundResource(R.drawable.unfocused);
                                this.code.setBackgroundResource(R.drawable.unfocused);
                                object_selected = "UPDATE_ML";
                                button.callOnClick();
                                break;
                            case "DAY1_PS":
                            case "MONTH1_PS":
                            case "YEAR1_PS":
                            case "DAY2_PS":
                            case "MONTH2_PS":
                            case "YEAR2_PS":
                                this.day1.setBackgroundResource(R.drawable.unfocused);
                                this.month1.setBackgroundResource(R.drawable.unfocused);
                                this.year1.setBackgroundResource(R.drawable.unfocused);
                                this.day2.setBackgroundResource(R.drawable.unfocused);
                                this.month2.setBackgroundResource(R.drawable.unfocused);
                                this.year2.setBackgroundResource(R.drawable.unfocused);
                                this.code.setBackgroundResource(R.drawable.unfocused);
                                object_selected = "UPDATE_PS";
                                button.callOnClick();
                                break;
                            case "SHOW1":
                                button1.callOnClick();
                                break;
                            case "SHOW2":
                                button2.callOnClick();
                                break;
                            case "SHOW3":
                                button3.callOnClick();
                                break;
                            case "EDIT1":
                                button4.callOnClick();
                                break;
                            case "EDIT2":
                                button5.callOnClick();
                                break;
                            case "EDIT3":
                                button6.callOnClick();
                                break;
                            case "DAY_ERC":
                            case "MONTH_ERC":
                            case "YEAR_ERC":
                            case "RATE_TYPE_ERC":
                                button.callOnClick();
                                break;
                            case "STD_COW_RATE":
                            case "PER_COW_FAT":
                            case "STD_COW_FAT":
                            case "STD_COW_SNF":
                            case "STD_BUF_RATE":
                            case "PER_BUF_FAT":
                            case "STD_BUF_FAT":
                            case "STD_BUF_SNF":
                                button.callOnClick();
                                break;
                            case "COW_FAT_FROM":
                            case "COW_FAT_TO":
                            case "COW_SNF_FROM":
                            case "COW_SNF_TO":
                            case "BUFFALO_FAT_FROM":
                            case "BUFFALO_FAT_TO":
                            case "BUFFALO_SNF_FROM":
                            case "BUFFALO_SNF_TO":
                            case "SANALYZER":
                            case "SWEIGHT":
                            case "SPRINTER":
                            case "SSHIFT_TIME":
                            case "SMEASUREMENT_UNIT":
                            case "DAIRY_NAME":
                            case "FOOTER":
                                button.callOnClick();
                                break;
                            case "FILE_RATE_CHART":
                                listView.performItemClick(listView.getAdapter().getView(item_selected, null, null), item_selected, listView.getAdapter().getItemId(item_selected));
                                break;
                            case "POPUP_PRINT_PREVIEW":
                                if (print_cancel.equals("pass") || print_cancel.equals("mpass") || print_cancel.equals("rate")) {
                                    pass_mpass = print_cancel;
                                    print_cancel = "print";
                                    print.callOnClick();
                                }
                                break;
                            case "DAIRY_CODE":
                            case "ROUTE_CODE":
                            case "VILLAGE_CODE":
                            case "CENTER_CODE":
                                button.callOnClick();
                                break;
                            case "OLD_PASS":
                            case "NEW_PASS":
                            case "REPEAT_PASS":
                                button.callOnClick();
                                break;
                            default:
                                Toast.makeText(this.context, "No Object Selected", Toast.LENGTH_SHORT).show();
                        }
                    } else
                        machine_selection = false;
                    break;
                case "c":
                case "C":
                    if (!machine_selection) {
                        switch (object_selected) {
                            case "CODE_ML":
                            case "CODE_BA":
                            case "CODE_DS":
                            case "CODE_AFM":
                                code.setText("000");
                                first_time_selected = true;
                                break;
                            case "WEIGHT_BA":
                                weight.setText("0.0");
                                first_time_selected = true;
                                break;
                            case "FAT_BA":
                                fat.setText("0.0");
                                first_time_selected = true;
                                break;
                            case "SNF_BA":
                                snf.setText("0.0");
                                first_time_selected = true;
                                break;
                            case "NAME_AFM":
                                if (first_time_selected) {
                                    name.setText(data);
                                    first_time_selected = false;
                                } else
                                    name.setText(name.getText() + data);
                                break;
                            case "MOBILE_AFM":
                                if (data.matches("\\+?\\d+?")) {
                                    if (first_time_selected) {
                                        mobile.setText(data);
                                        first_time_selected = false;
                                    } else
                                        mobile.setText(mobile.getText() + data);
                                }
                                break;
                            case "EMAIL1_AFM":
                                if (first_time_selected) {
                                    email1.setText(data);
                                    first_time_selected = false;
                                } else
                                    email1.setText(email1.getText() + data);
                                break;
                            case "STD_COW_RATE":
                                std_cow_rate.setText("0.0");
                                first_time_selected = true;
                                break;
                            case "PER_COW_FAT":
                                per_cow_fat.setText("0");
                                per_cow_snf.setText("0");
                                first_time_selected = true;
                                break;
                            case "STD_COW_FAT":
                                std_cow_fat.setText("0.0");
                                first_time_selected = true;
                                break;
                            case "STD_COW_SNF":
                                std_cow_snf.setText("0.0");
                                first_time_selected = true;
                                break;
                            case "STD_BUF_RATE":
                                std_buffalo_rate.setText("0.0");
                                first_time_selected = true;
                                break;
                            case "PER_BUF_FAT":
                                per_buffalo_fat.setText("0");
                                per_buffalo_snf.setText("0");
                                first_time_selected = true;
                                break;
                            case "STD_BUF_FAT":
                                std_buffalo_fat.setText("0.0");
                                first_time_selected = true;
                                break;
                            case "STD_BUF_SNF":
                                std_buffalo_snf.setText("0.0");
                                first_time_selected = true;
                                break;
                            case "COW_FAT_FROM":
                                cow_fat_from.setText("0.0");
                                first_time_selected = true;
                                break;
                            case "COW_FAT_TO":
                                cow_fat_to.setText("0.0");
                                first_time_selected = true;
                                break;
                            case "COW_SNF_FROM":
                                cow_snf_from.setText("0.0");
                                first_time_selected = true;
                                break;
                            case "COW_SNF_TO":
                                cow_snf_to.setText("0.0");
                                first_time_selected = true;
                                break;
                            case "BUFFALO_FAT_FROM":
                                buffalo_fat_from.setText("0.0");
                                first_time_selected = true;
                                break;
                            case "BUFFALO_FAT_TO":
                                buffalo_fat_to.setText("0.0");
                                first_time_selected = true;
                                break;
                            case "BUFFALO_SNF_FROM":
                                buffalo_snf_from.setText("0.0");
                                first_time_selected = true;
                                break;
                            case "BUFFALO_SNF_TO":
                                buffalo_snf_to.setText("0.0");
                                first_time_selected = true;
                                break;
                            case "DAIRY_NAME":
                                std_buffalo_snf.setText("Dairy Name");
                                first_time_selected = true;
                                break;
                            case "Footer":
                                std_buffalo_snf.setText("Footer");
                                first_time_selected = true;
                                break;
                            case "MAN_RC_ENTRY":
                                item_textView.setText("0.0");
                                first_time_selected = true;
                                break;
                            case "POPUP_PRINT_PREVIEW":
                                if (print_cancel.equals("pass") || print_cancel.equals("mpass") || print_cancel.equals("rate"))
                                    pass.setText("");
                                break;
                            case "DAIRY_CODE":
                                dairy_code.setText("");
                                break;
                            case "ROUTE_CODE":
                                route_code.setText("");
                                break;
                            case "VILLAGE_CODE":
                                village_code.setText("");
                                break;
                            case "CENTER_CODE":
                                center_code.setText("");
                                break;
                            case "OLD_PASS":
                                oldpass.setText("");
                                break;
                            case "NEW_PASS":
                                newpass.setText("");
                                break;
                            case "REPEAT_PASS":
                                repeatpass.setText("");
                                break;
                        }
                    } else
                        machine_selection = false;
                    break;
                default:
                    if (!machine_selection) {
                        if (machine_selected.equals("Keyboard")) {
                            if (first_time_selected) {
                                switch (object_selected) {
                                    case "CODE_ML":
                                    case "CODE_BA":
                                    case "CODE_DS":
                                    case "CODE_AFM":
                                        if (data.matches("^[0-9]*$")) {
                                            code.setText(data);
                                            first_time_selected = false;
                                        }
                                        break;
                                    case "WEIGHT_BA":
                                        if (data.matches("^[0-9/.]*$")) {
                                            if (data.equals("."))
                                                weight.setText("0" + data);
                                            else
                                                weight.setText(data);
                                            first_time_selected = false;
                                        }
                                        break;
                                    case "FAT_BA":
                                        if (data.matches("^[0-9/.]*$")) {
                                            if (data.equals("."))
                                                fat.setText("0" + data);
                                            else
                                                fat.setText(data);
                                            first_time_selected = false;
                                        }
                                        break;
                                    case "SNF_BA":
                                        if (data.matches("^[0-9/.]*$")) {
                                            if (data.equals("."))
                                                snf.setText("0" + data);
                                            else
                                                snf.setText(data);
                                            first_time_selected = false;
                                        }
                                        break;
                                    case "NAME_AFM":
                                        name.setText(data);
                                        first_time_selected = false;
                                        break;
                                    case "MOBILE_AFM":
                                        if (data.matches("\\+?\\d+?")) {
                                            mobile.setText(data);
                                            first_time_selected = false;
                                        }
                                        break;
                                    case "EMAIL1_AFM":
                                        email1.setText(data);
                                        first_time_selected = false;
                                        break;
                                    case "STD_COW_RATE":
                                        if (data.matches("^[0-9/.]*$")) {
                                            if (data.equals("."))
                                                std_cow_rate.setText("0" + data);
                                            else
                                                std_cow_rate.setText(data);
                                            first_time_selected = false;
                                        }
                                        break;
                                    case "PER_COW_FAT":
                                        if (data.matches("^[0-9]*$")) {
                                            per_cow_fat.setText(data);
                                            per_cow_snf.setText(String.valueOf(100 - Integer.valueOf(per_cow_fat.getText().toString())));
                                            first_time_selected = false;
                                        }
                                        break;
                                    case "STD_COW_FAT":
                                        if (data.matches("^[0-9/.]*$")) {
                                            if (data.equals("."))
                                                std_cow_fat.setText("0" + data);
                                            else
                                                std_cow_fat.setText(data);
                                            first_time_selected = false;
                                        }
                                        break;
                                    case "STD_COW_SNF":
                                        if (data.matches("^[0-9/.]*$")) {
                                            if (data.equals("."))
                                                std_cow_snf.setText("0" + data);
                                            else
                                                std_cow_snf.setText(data);
                                            first_time_selected = false;
                                        }
                                        break;
                                    case "STD_BUF_RATE":
                                        if (data.matches("^[0-9/.]*$")) {
                                            if (data.equals("."))
                                                std_buffalo_rate.setText("0" + data);
                                            else
                                                std_buffalo_rate.setText(data);
                                            first_time_selected = false;
                                        }
                                        break;
                                    case "PER_BUF_FAT":
                                        if (data.matches("^[0-9]*$")) {
                                            per_buffalo_fat.setText(data);
                                            per_buffalo_snf.setText(String.valueOf(100 - Integer.valueOf(per_buffalo_fat.getText().toString())));
                                            first_time_selected = false;
                                        }
                                        break;
                                    case "STD_BUF_FAT":
                                        if (data.matches("^[0-9/.]*$")) {
                                            if (data.equals("."))
                                                std_buffalo_fat.setText("0" + data);
                                            else
                                                std_buffalo_fat.setText(data);
                                            first_time_selected = false;
                                        }
                                        break;
                                    case "STD_BUF_SNF":
                                        if (data.matches("^[0-9/.]*$")) {
                                            if (data.equals("."))
                                                std_buffalo_snf.setText("0" + data);
                                            else
                                                std_buffalo_snf.setText(data);
                                            first_time_selected = false;
                                        }
                                        break;
                                    case "COW_FAT_FROM":
                                        if (data.matches("^[0-9/.]*$")) {
                                            if (data.equals("."))
                                                cow_fat_from.setText("0" + data);
                                            else
                                                cow_fat_from.setText(data);
                                            first_time_selected = false;
                                        }
                                        break;
                                    case "COW_FAT_TO":
                                        if (data.matches("^[0-9/.]*$")) {
                                            if (data.equals("."))
                                                cow_fat_to.setText("0" + data);
                                            else
                                                cow_fat_to.setText(data);
                                            first_time_selected = false;
                                            buffalo_fat_from.setText(String.valueOf(new BigDecimal(cow_fat_to.getText().toString()).add(new BigDecimal("0.1"))));
                                        }
                                        break;
                                    case "COW_SNF_FROM":
                                        if (data.matches("^[0-9/.]*$")) {
                                            if (data.equals("."))
                                                cow_snf_from.setText("0" + data);
                                            else
                                                cow_snf_from.setText(data);
                                            first_time_selected = false;
                                        }
                                        break;
                                    case "COW_SNF_TO":
                                        if (data.matches("^[0-9/.]*$")) {
                                            if (data.equals("."))
                                                cow_snf_to.setText("0" + data);
                                            else
                                                cow_snf_to.setText(data);
                                            first_time_selected = false;
                                        }
                                        break;
                                    case "BUFFALO_FAT_TO":
                                        if (data.matches("^[0-9/.]*$")) {
                                            if (data.equals("."))
                                                buffalo_fat_to.setText("0" + data);
                                            else
                                                buffalo_fat_to.setText(data);
                                            first_time_selected = false;
                                        }
                                        break;
                                    case "BUFFALO_SNF_FROM":
                                        if (data.matches("^[0-9/.]*$")) {
                                            if (data.equals("."))
                                                buffalo_snf_from.setText("0" + data);
                                            else
                                                buffalo_snf_from.setText(data);
                                            first_time_selected = false;
                                        }
                                        break;
                                    case "BUFFALO_SNF_TO":
                                        if (data.matches("^[0-9/.]*$")) {
                                            if (data.equals("."))
                                                buffalo_snf_to.setText("0" + data);
                                            else
                                                buffalo_snf_to.setText(data);
                                            first_time_selected = false;
                                        }
                                        break;
                                    case "DAIRY_NAME":
                                        dairy_name.setText(data);
                                        first_time_selected = false;
                                        break;
                                    case "FOOTER":
                                        footer.setText(data);
                                        first_time_selected = false;
                                        break;
                                    case "MAN_RC_ENTRY":
                                        if (data.matches("^[0-9/.]*$")) {
                                            if (data.equals("."))
                                                item_textView.setText("0" + data);
                                            else
                                                item_textView.setText(data);
                                            first_time_selected = false;
                                        }
                                        break;
                                    case "POPUP_PRINT_PREVIEW":
                                        if (print_cancel.equals("pass") || print_cancel.equals("mpass") || print_cancel.equals("rate"))
                                            pass.setText(pass.getText() + data);
                                        break;
                                    case "DAIRY_CODE":
                                        dairy_code.setText(data);
                                        first_time_selected = false;
                                        break;
                                    case "ROUTE_CODE":
                                        route_code.setText(data);
                                        first_time_selected = false;
                                        break;
                                    case "VILLAGE_CODE":
                                        village_code.setText(data);
                                        first_time_selected = false;
                                        break;
                                    case "CENTER_CODE":
                                        center_code.setText(data);
                                        first_time_selected = false;
                                        break;
                                    case "OLD_PASS":
                                        oldpass.setText(data);
                                        first_time_selected = false;
                                        break;
                                    case "NEW_PASS":
                                        newpass.setText(data);
                                        first_time_selected = false;
                                        break;
                                    case "REPEAT_PASS":
                                        repeatpass.setText(data);
                                        first_time_selected = false;
                                        break;
                                }
                            } else {
                                switch (object_selected) {
                                    case "CODE_ML":
                                    case "CODE_BA":
                                    case "CODE_DS":
                                    case "CODE_AFM":
                                        if (data.matches("^[0-9]*$") && code.getText().length() < 3) {
                                            code.setText(code.getText() + data);
                                        }
                                        break;
                                    case "WEIGHT_BA":
                                        if (data.matches("^[0-9/.]*$")) {
                                            if (weight.getText().length() < 7) {
                                                if (!weight.getText().toString().contains(".")) {
                                                    if ((weight.getText().length() == 4) && (!data.equals(".")))
                                                        weight.setText(this.weight.getText() + "." + data);
                                                    else
                                                        weight.setText(this.weight.getText() + data);
                                                } else {
                                                    if (weight.getText().toString().split("\\.").length == 1 && !(data.equals(".")))
                                                        weight.setText(this.weight.getText() + data);
                                                    else if ((this.weight.getText().toString().split("\\.")[1].length() < 2) && !(data.equals(".")))
                                                        weight.setText(this.weight.getText() + data);
                                                }
                                            }
                                        }
                                        break;
                                    case "FAT_BA":
                                        if (data.matches("^[0-9/.]*$")) {
                                            if (fat.getText().length() < 5) {
                                                if (!fat.getText().toString().contains(".")) {
                                                    if ((fat.getText().length() == 2) && (!data.equals(".")))
                                                        fat.setText(this.fat.getText() + "." + data);
                                                    else
                                                        fat.setText(this.fat.getText() + data);
                                                } else {
                                                    if (fat.getText().toString().split("\\.").length == 1 && !(data.equals(".")))
                                                        fat.setText(this.fat.getText() + data);
                                                    else if ((this.fat.getText().toString().split("\\.")[1].length() < 2) && !(data.equals(".")))
                                                        fat.setText(this.fat.getText() + data);
                                                }
                                            }
                                            break;
                                        }
                                    case "SNF_BA":
                                        if (data.matches("^[0-9/.]*$")) {
                                            if (snf.getText().length() < 5) {
                                                if (!snf.getText().toString().contains(".")) {
                                                    if ((snf.getText().length() == 2) && (!data.equals(".")))
                                                        snf.setText(this.snf.getText() + "." + data);
                                                    else
                                                        snf.setText(this.snf.getText() + data);
                                                } else {
                                                    if (snf.getText().toString().split("\\.").length == 1 && !(data.equals(".")))
                                                        snf.setText(this.snf.getText() + data);
                                                    else if ((this.snf.getText().toString().split("\\.")[1].length() < 2) && !(data.equals(".")))
                                                        snf.setText(this.snf.getText() + data);
                                                }
                                            }
                                        }
                                        break;
                                    case "NAME_AFM":
                                        name.setText(name.getText() + data);
                                        break;
                                    case "MOBILE_AFM":
                                        if (data.matches("^[0-9]*$")) {
                                            mobile.setText(mobile.getText() + data);
                                        }
                                        break;
                                    case "EMAIL1_AFM":
                                        email1.setText(email1.getText() + data);
                                        break;
                                    case "STD_COW_RATE":
                                        if (data.matches("^[0-9/.]*$")) {
                                            if (std_cow_rate.getText().length() < 6) {
                                                if (!std_cow_rate.getText().toString().contains(".")) {
                                                    if ((std_cow_rate.getText().length() == 3) && (!data.equals(".")))
                                                        std_cow_rate.setText(this.std_cow_rate.getText() + "." + data);
                                                    else
                                                        std_cow_rate.setText(this.std_cow_rate.getText() + data);
                                                } else {
                                                    if (std_cow_rate.getText().toString().split("\\.").length == 1 && !(data.equals(".")))
                                                        std_cow_rate.setText(this.std_cow_rate.getText() + data);
                                                    else if ((this.std_cow_rate.getText().toString().split("\\.")[1].length() < 2) && !(data.equals(".")))
                                                        std_cow_rate.setText(this.std_cow_rate.getText() + data);
                                                }
                                            }
                                        }
                                        break;
                                    case "PER_COW_FAT":
                                        if (data.matches("^[0-9]*$")) {
                                            if ((per_cow_fat.getText().length() < 3) && !(data.equals("."))) {
                                                per_cow_fat.setText(per_cow_fat.getText() + data);
                                            }
                                            if (Integer.valueOf(per_cow_fat.getText().toString()) > 100)
                                                per_cow_fat.setText("100");
                                            per_cow_snf.setText(String.valueOf(100 - Integer.valueOf(per_cow_fat.getText().toString())));
                                        }
                                        break;
                                    case "STD_COW_FAT":
                                        if (data.matches("^[0-9/.]*$")) {
                                            if (std_cow_fat.getText().length() < 4) {
                                                if (!std_cow_fat.getText().toString().contains(".")) {
                                                    if ((std_cow_fat.getText().length() == 2) && (!data.equals(".")))
                                                        std_cow_fat.setText(this.std_cow_fat.getText() + "." + data);
                                                    else
                                                        std_cow_fat.setText(this.std_cow_fat.getText() + data);
                                                } else {
                                                    if (std_cow_fat.getText().toString().split("\\.").length == 1 && !(data.equals(".")))
                                                        std_cow_fat.setText(this.std_cow_fat.getText() + data);
                                                    else if ((this.std_cow_fat.getText().toString().split("\\.")[1].length() < 1) && !(data.equals(".")))
                                                        std_cow_fat.setText(this.std_cow_fat.getText() + data);
                                                }
                                            }
                                        }
                                        break;
                                    case "STD_COW_SNF":
                                        if (data.matches("^[0-9/.]*$")) {
                                            if (std_cow_snf.getText().length() < 4) {
                                                if (!std_cow_snf.getText().toString().contains(".")) {
                                                    if ((std_cow_snf.getText().length() == 2) && (!data.equals(".")))
                                                        std_cow_snf.setText(this.std_cow_snf.getText() + "." + data);
                                                    else
                                                        std_cow_snf.setText(this.std_cow_snf.getText() + data);
                                                } else {
                                                    if (std_cow_snf.getText().toString().split("\\.").length == 1 && !(data.equals(".")))
                                                        std_cow_snf.setText(this.std_cow_snf.getText() + data);
                                                    else if ((this.std_cow_snf.getText().toString().split("\\.")[1].length() < 1) && !(data.equals(".")))
                                                        std_cow_snf.setText(this.std_cow_snf.getText() + data);
                                                }
                                            }
                                        }
                                        break;
                                    case "STD_BUF_RATE":
                                        if (data.matches("^[0-9/.]*$")) {
                                            if (std_buffalo_rate.getText().length() < 6) {
                                                if (!std_buffalo_rate.getText().toString().contains(".")) {
                                                    if ((std_buffalo_rate.getText().length() == 3) && (!data.equals(".")))
                                                        std_buffalo_rate.setText(this.std_buffalo_rate.getText() + "." + data);
                                                    else
                                                        std_buffalo_rate.setText(this.std_buffalo_rate.getText() + data);
                                                } else {
                                                    if (std_buffalo_rate.getText().toString().split("\\.").length == 1 && !(data.equals(".")))
                                                        std_buffalo_rate.setText(this.std_buffalo_rate.getText() + data);
                                                    else if ((this.std_buffalo_rate.getText().toString().split("\\.")[1].length() < 2) && !(data.equals(".")))
                                                        std_buffalo_rate.setText(this.std_buffalo_rate.getText() + data);
                                                }
                                            }
                                        }
                                        break;
                                    case "PER_BUF_FAT":
                                        if (data.matches("^[0-9]*$")) {
                                            if ((per_buffalo_fat.getText().length() < 3) && !(data.equals("."))) {
                                                per_buffalo_fat.setText(per_buffalo_fat.getText() + data);
                                            }
                                            if (Integer.valueOf(per_buffalo_fat.getText().toString()) > 100)
                                                per_buffalo_fat.setText("100");
                                            per_buffalo_snf.setText(String.valueOf(100 - Integer.valueOf(per_buffalo_fat.getText().toString())));
                                        }
                                        break;
                                    case "STD_BUF_FAT":
                                        if (data.matches("^[0-9/.]*$")) {
                                            if (std_buffalo_fat.getText().length() < 4) {
                                                if (!std_buffalo_fat.getText().toString().contains(".")) {
                                                    if ((std_buffalo_fat.getText().length() == 2) && (!data.equals(".")))
                                                        std_buffalo_fat.setText(this.std_buffalo_fat.getText() + "." + data);
                                                    else
                                                        std_buffalo_fat.setText(this.std_buffalo_fat.getText() + data);
                                                } else {
                                                    if (std_buffalo_fat.getText().toString().split("\\.").length == 1 && !(data.equals(".")))
                                                        std_buffalo_fat.setText(this.std_buffalo_fat.getText() + data);
                                                    else if ((this.std_buffalo_fat.getText().toString().split("\\.")[1].length() < 1) && !(data.equals(".")))
                                                        std_buffalo_fat.setText(this.std_buffalo_fat.getText() + data);
                                                }
                                            }
                                        }
                                        break;
                                    case "STD_BUF_SNF":
                                        if (data.matches("^[0-9/.]*$")) {
                                            if (std_buffalo_snf.getText().length() < 4) {
                                                if (!std_buffalo_snf.getText().toString().contains(".")) {
                                                    if ((std_buffalo_snf.getText().length() == 2) && (!data.equals(".")))
                                                        std_buffalo_snf.setText(this.std_buffalo_snf.getText() + "." + data);
                                                    else
                                                        std_buffalo_snf.setText(this.std_buffalo_snf.getText() + data);
                                                } else {
                                                    if (std_buffalo_snf.getText().toString().split("\\.").length == 1 && !(data.equals(".")))
                                                        std_buffalo_snf.setText(this.std_buffalo_snf.getText() + data);
                                                    else if ((this.std_buffalo_snf.getText().toString().split("\\.")[1].length() < 1) && !(data.equals(".")))
                                                        std_buffalo_snf.setText(this.std_buffalo_snf.getText() + data);
                                                }
                                            }
                                        }
                                        break;
                                    case "COW_FAT_FROM":
                                        if (data.matches("^[0-9/.]*$")) {
                                            if (cow_fat_from.getText().length() < 4) {
                                                if (!cow_fat_from.getText().toString().contains(".")) {
                                                    if ((cow_fat_from.getText().length() == 2) && (!data.equals(".")))
                                                        cow_fat_from.setText(this.cow_fat_from.getText() + "." + data);
                                                    else
                                                        cow_fat_from.setText(this.cow_fat_from.getText() + data);
                                                } else {
                                                    if (cow_fat_from.getText().toString().split("\\.").length == 1 && !(data.equals(".")))
                                                        cow_fat_from.setText(this.cow_fat_from.getText() + data);
                                                    else if ((this.cow_fat_from.getText().toString().split("\\.")[1].length() < 1) && !(data.equals(".")))
                                                        cow_fat_from.setText(this.cow_fat_from.getText() + data);
                                                }
                                            }
                                        }
                                        break;
                                    case "COW_FAT_TO":
                                        if (data.matches("^[0-9/.]*$")) {
                                            if (cow_fat_to.getText().length() < 4) {
                                                if (!cow_fat_to.getText().toString().contains(".")) {
                                                    if ((cow_fat_to.getText().length() == 2) && (!data.equals(".")))
                                                        cow_fat_to.setText(this.cow_fat_to.getText() + "." + data);
                                                    else
                                                        cow_fat_to.setText(this.cow_fat_to.getText() + data);
                                                } else {
                                                    if (cow_fat_to.getText().toString().split("\\.").length == 1 && !(data.equals(".")))
                                                        cow_fat_to.setText(this.cow_fat_to.getText() + data);
                                                    else if ((this.cow_fat_to.getText().toString().split("\\.")[1].length() < 1) && !(data.equals(".")))
                                                        cow_fat_to.setText(this.cow_fat_to.getText() + data);
                                                }
                                            }
                                            buffalo_fat_from.setText(String.valueOf(new BigDecimal(cow_fat_to.getText().toString()).add(new BigDecimal("0.1"))));
                                        }
                                        break;
                                    case "COW_SNF_FROM":
                                        if (data.matches("^[0-9/.]*$")) {
                                            if (cow_snf_from.getText().length() < 4) {
                                                if (!cow_snf_from.getText().toString().contains(".")) {
                                                    if ((cow_snf_from.getText().length() == 2) && (!data.equals(".")))
                                                        cow_snf_from.setText(this.cow_snf_from.getText() + "." + data);
                                                    else
                                                        cow_snf_from.setText(this.cow_snf_from.getText() + data);
                                                } else {
                                                    if (cow_snf_from.getText().toString().split("\\.").length == 1 && !(data.equals(".")))
                                                        cow_snf_from.setText(this.cow_snf_from.getText() + data);
                                                    else if ((this.cow_snf_from.getText().toString().split("\\.")[1].length() < 1) && !(data.equals(".")))
                                                        cow_snf_from.setText(this.cow_snf_from.getText() + data);
                                                }
                                            }
                                        }
                                        break;
                                    case "COW_SNF_TO":
                                        if (data.matches("^[0-9/.]*$")) {
                                            if (cow_snf_to.getText().length() < 4) {
                                                if (!cow_snf_to.getText().toString().contains(".")) {
                                                    if ((cow_snf_to.getText().length() == 2) && (!data.equals(".")))
                                                        cow_snf_to.setText(this.cow_snf_to.getText() + "." + data);
                                                    else
                                                        cow_snf_to.setText(this.cow_snf_to.getText() + data);
                                                } else {
                                                    if (cow_snf_to.getText().toString().split("\\.").length == 1 && !(data.equals(".")))
                                                        cow_snf_to.setText(this.cow_snf_to.getText() + data);
                                                    else if ((this.cow_snf_to.getText().toString().split("\\.")[1].length() < 1) && !(data.equals(".")))
                                                        cow_snf_to.setText(this.cow_snf_to.getText() + data);
                                                }
                                            }
                                        }
                                        break;
                                    case "BUFFALO_FAT_TO":
                                        if (data.matches("^[0-9/.]*$")) {
                                            if (buffalo_fat_to.getText().length() < 4) {
                                                if (!buffalo_fat_to.getText().toString().contains(".")) {
                                                    if ((buffalo_fat_to.getText().length() == 2) && (!data.equals(".")))
                                                        buffalo_fat_to.setText(this.buffalo_fat_to.getText() + "." + data);
                                                    else
                                                        buffalo_fat_to.setText(this.buffalo_fat_to.getText() + data);
                                                } else {
                                                    if (buffalo_fat_to.getText().toString().split("\\.").length == 1 && !(data.equals(".")))
                                                        buffalo_fat_to.setText(this.buffalo_fat_to.getText() + data);
                                                    else if ((this.buffalo_fat_to.getText().toString().split("\\.")[1].length() < 1) && !(data.equals(".")))
                                                        buffalo_fat_to.setText(this.buffalo_fat_to.getText() + data);
                                                }
                                            }
                                        }
                                        break;
                                    case "BUFFALO_SNF_FROM":
                                        if (data.matches("^[0-9/.]*$")) {
                                            if (buffalo_snf_from.getText().length() < 4) {
                                                if (!buffalo_snf_from.getText().toString().contains(".")) {
                                                    if ((buffalo_snf_from.getText().length() == 2) && (!data.equals(".")))
                                                        buffalo_snf_from.setText(this.buffalo_snf_from.getText() + "." + data);
                                                    else
                                                        buffalo_snf_from.setText(this.buffalo_snf_from.getText() + data);
                                                } else {
                                                    if (buffalo_snf_from.getText().toString().split("\\.").length == 1 && !(data.equals(".")))
                                                        buffalo_snf_from.setText(this.buffalo_snf_from.getText() + data);
                                                    else if ((this.buffalo_snf_from.getText().toString().split("\\.")[1].length() < 1) && !(data.equals(".")))
                                                        buffalo_snf_from.setText(this.buffalo_snf_from.getText() + data);
                                                }
                                            }
                                        }
                                        break;
                                    case "BUFFALO_SNF_TO":
                                        if (data.matches("^[0-9/.]*$")) {
                                            if (buffalo_snf_to.getText().length() < 4) {
                                                if (!buffalo_snf_to.getText().toString().contains(".")) {
                                                    if ((buffalo_snf_to.getText().length() == 2) && (!data.equals(".")))
                                                        buffalo_snf_to.setText(this.buffalo_snf_to.getText() + "." + data);
                                                    else
                                                        buffalo_snf_to.setText(this.buffalo_snf_to.getText() + data);
                                                } else {
                                                    if (buffalo_snf_to.getText().toString().split("\\.").length == 1 && !(data.equals(".")))
                                                        buffalo_snf_to.setText(this.buffalo_snf_to.getText() + data);
                                                    else if ((this.buffalo_snf_to.getText().toString().split("\\.")[1].length() < 1) && !(data.equals(".")))
                                                        buffalo_snf_to.setText(this.buffalo_snf_to.getText() + data);
                                                }
                                            }
                                        }
                                        break;
                                    case "DAIRY_NAME":
                                        dairy_name.setText(dairy_name.getText() + data);
                                        break;
                                    case "FOOTER":
                                        footer.setText(footer.getText() + data);
                                        break;
                                    case "MAN_RC_ENTRY":
                                        if (data.matches("^[0-9/.]*$")) {
                                            if (item_textView.getText().length() < 6) {
                                                if (!item_textView.getText().toString().contains(".")) {
                                                    if ((item_textView.getText().length() == 3) && (!data.equals(".")))
                                                        item_textView.setText(this.item_textView.getText() + "." + data);
                                                    else
                                                        item_textView.setText(this.item_textView.getText() + data);
                                                } else {
                                                    if (item_textView.getText().toString().split("\\.").length == 1 && !(data.equals(".")))
                                                        item_textView.setText(this.item_textView.getText() + data);
                                                    else if ((this.item_textView.getText().toString().split("\\.")[1].length() < 2) && !(data.equals(".")))
                                                        item_textView.setText(this.item_textView.getText() + data);
                                                }
                                            }
                                        }
                                        break;
                                    case "POPUP_PRINT_PREVIEW":
                                        if (print_cancel.equals("pass") || print_cancel.equals("mpass") || print_cancel.equals("rate"))
                                            pass.setText(pass.getText() + data);
                                        break;
                                    case "DAIRY_CODE":
                                        dairy_code.setText(dairy_code.getText() + data);
                                        break;
                                    case "ROUTE_CODE":
                                        route_code.setText(route_code.getText() + data);
                                        break;
                                    case "VILLAGE_CODE":
                                        village_code.setText(village_code.getText() + data);
                                        break;
                                    case "CENTER_CODE":
                                        center_code.setText(center_code.getText() + data);
                                        break;
                                    case "OLD_PASS":
                                        oldpass.setText(oldpass.getText() + data);
                                        break;
                                    case "NEW_PASS":
                                        newpass.setText(newpass.getText() + data);
                                        break;
                                    case "REPEAT_PASS":
                                        repeatpass.setText(repeatpass.getText() + data);
                                        break;
                                }
                            }
                        }
                        if (machine_selected.equals("Weight")) {
                            if (weight_entry)
                                return;
                            switch (object_selected) {
                                case "CODE_BA":
                                case "WEIGHT_BA":
                                case "FAT_BA":
                                case "SNF_BA":
                                    if (weight_counter == 0)
                                        weight.setText(data);
                                    else if (weight_counter == 4)
                                        weight.setText(weight.getText() + "." + data);
                                    else
                                        weight.setText(weight.getText() + data);
                                    break;
                            }
                            if (weight_counter == 5) {
                                machine_selected = "Keyboard";
                                weight_counter = 0;
                            } else
                                weight_counter++;
                        } else if (machine_selected.equals("Analyzer")) {
                            if (analyzer_entry)
                                return;
                            switch (object_selected) {
                                case "CODE_BA":
                                case "WEIGHT_BA":
                                case "FAT_BA":
                                case "SNF_BA":
                                    if (analyzer_counter == 0)
                                        fat.setText(data);
                                    else if (analyzer_counter == 1 || analyzer_counter == 3)
                                        fat.setText(fat.getText() + data);
                                    else if (analyzer_counter == 2)
                                        fat.setText(fat.getText() + "." + data);
                                    else if (analyzer_counter == 4)
                                        snf.setText(data);
                                    else if (analyzer_counter == 5 || analyzer_counter == 7)
                                        snf.setText(snf.getText() + data);
                                    else if (analyzer_counter == 6)
                                        snf.setText(snf.getText() + "." + data);
                                    else if (analyzer_counter == 8)
                                        added_water.setText(data);
                                    else
                                        added_water.setText(added_water.getText() + data);
                                    break;
                            }
                            if (analyzer_counter == 9) {
                                machine_selected = "Keyboard";
                                analyzer_counter = 0;
                            } else
                                analyzer_counter++;
                        }
                    } else
                        machine_selection = false;
            }
        } catch (NullPointerException ex) {
            //Toast.makeText(context, ex.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    void openActivity(int paramInt) {
        this.drawerLayout.closeDrawer(this.listView);
        this.listView.getChildAt(0).setBackgroundColor(0xFF037DA6);
        this.listView.getChildAt(1).setBackgroundColor(0xFF000000);
        this.listView.getChildAt(2).setBackgroundColor(0xFF000000);
        this.listView.getChildAt(3).setBackgroundColor(0xFF000000);
        this.listView.getChildAt(4).setBackgroundColor(0xFF000000);
        this.listView.getChildAt(5).setBackgroundColor(0xFF000000);
        this.listView.getChildAt(6).setBackgroundColor(0xFF000000);
        this.listView.getChildAt(7).setBackgroundColor(0xFF000000);
        this.listView.getChildAt(8).setBackgroundColor(0xFF000000);
        this.listView.getChildAt(9).setBackgroundColor(0xFF000000);
        BaseActivity.position = paramInt;
        final Intent intent;
        switch (paramInt) {
            case 1:
                intent = new Intent(this.context, ShiftReportsActivity.class);
                context.startActivity(intent);
                break;
            case 2:
                intent = new Intent(this.context, MemberLedgerActivity.class);
                context.startActivity(intent);
                break;
            case 3:
                intent = new Intent(this.context, PaymentSummaryActivity.class);
                context.startActivity(intent);
                break;
            case 4:
                intent = new Intent(this.context, DuplicateSlipActivity.class);
                context.startActivity(intent);
                break;
            case 5:
                intent = new Intent(this.context, AddFarmerMember.class);
                context.startActivity(intent);
                break;
            case 6:
                DataInput.print_cancel = "pass";
                button.callOnClick();
                break;
            case 7:
                intent = new Intent(this.context, ResetPasswordActivity.class);
                context.startActivity(intent);
                break;
            case 8:
                DataInput.print_cancel = "rate";
                button.callOnClick();
                break;
            case 9:
                intent = new Intent(this.context, SyncActivity.class);
                context.startActivity(intent);
                break;
            case 10:
                DataInput.print_cancel = "mpass";
                button.callOnClick();
                break;
            default:
        }
        /*Intent intent = new Intent(this.context, PrintActivity.class);
        context.startActivity(intent);*/
    }
}
