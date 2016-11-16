package com.cmos.adpu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class InitializationAsyncTask extends AsyncTask<String, Void, String> {
    SettingsDBAdapter settingsDBAdapter;
    Context context;
    Intent intent;

    InitializationAsyncTask(Context context) {
        this.context = context;
        this.intent = new Intent(this.context, BaseActivity.class);
    }

    protected String doInBackground(String... params) {
        settingsDBAdapter = new SettingsDBAdapter(context);
        settingsDBAdapter.open();
        if(settingsDBAdapter.getSingleEntry("cow_fat_range_to").equals("no such table")) {
            settingsDBAdapter.updateEntry("cow_fat_range_to", "4.9");
            settingsDBAdapter.updateEntry("cow_snf_range_to", "10.0");
            settingsDBAdapter.updateEntry("cow_fat_range_from", "2.0");
            settingsDBAdapter.updateEntry("cow_snf_range_from", "5.0");
            settingsDBAdapter.updateEntry("buffalo_fat_range_to", "9.0");
            settingsDBAdapter.updateEntry("buffalo_snf_range_to", "10.0");
            settingsDBAdapter.updateEntry("buffalo_fat_range_from", "5.0");
            settingsDBAdapter.updateEntry("buffalo_snf_range_from", "5.0");
            settingsDBAdapter.updateEntry("analyzer", "0");
            settingsDBAdapter.updateEntry("printer", "0");
            settingsDBAdapter.updateEntry("shifttime", "0");
            settingsDBAdapter.updateEntry("measurementunit", "0");
            settingsDBAdapter.updateEntry("dairyname", "Dairy Name");
            settingsDBAdapter.updateEntry("weight", "0");
            settingsDBAdapter.updateEntry("footer", "Footer");
            settingsDBAdapter.updateEntry("ratechart1", "Formula");
            settingsDBAdapter.updateEntry("ratechart2", "Formula");
            settingsDBAdapter.updateEntry("ratechart3", "Formula");
            settingsDBAdapter.updateEntry("rc1_date", "01/01/2016");
            settingsDBAdapter.updateEntry("rc2_date", "31/12/2015");
            settingsDBAdapter.updateEntry("rc3_date", "30/12/2015");
            settingsDBAdapter.updateEntry("rc1_std_cow_rate", "40.0");
            settingsDBAdapter.updateEntry("rc1_per_cow_fat", "50");
            settingsDBAdapter.updateEntry("rc1_per_cow_snf", "50");
            settingsDBAdapter.updateEntry("rc1_std_cow_fat", "6.0");
            settingsDBAdapter.updateEntry("rc1_std_cow_snf", "6.0");
            settingsDBAdapter.updateEntry("rc1_std_buffalo_rate", "40.0");
            settingsDBAdapter.updateEntry("rc1_per_buffalo_fat", "50");
            settingsDBAdapter.updateEntry("rc1_per_buffalo_snf", "50");
            settingsDBAdapter.updateEntry("rc1_std_buffalo_fat", "10.0");
            settingsDBAdapter.updateEntry("rc1_std_buffalo_snf", "10.0");
            settingsDBAdapter.updateEntry("rc2_std_cow_rate", "40.0");
            settingsDBAdapter.updateEntry("rc2_per_cow_fat", "50");
            settingsDBAdapter.updateEntry("rc2_per_cow_snf", "50");
            settingsDBAdapter.updateEntry("rc2_std_cow_fat", "6.0");
            settingsDBAdapter.updateEntry("rc2_std_cow_snf", "6.0");
            settingsDBAdapter.updateEntry("rc2_std_buffalo_rate", "40.0");
            settingsDBAdapter.updateEntry("rc2_per_buffalo_fat", "50");
            settingsDBAdapter.updateEntry("rc2_per_buffalo_snf", "50");
            settingsDBAdapter.updateEntry("rc2_std_buffalo_fat", "10.0");
            settingsDBAdapter.updateEntry("rc2_std_buffalo_snf", "10.0");
            settingsDBAdapter.updateEntry("rc3_std_cow_rate", "40.0");
            settingsDBAdapter.updateEntry("rc3_per_cow_fat", "50");
            settingsDBAdapter.updateEntry("rc3_per_cow_snf", "50");
            settingsDBAdapter.updateEntry("rc3_std_cow_fat", "6.0");
            settingsDBAdapter.updateEntry("rc3_std_cow_snf", "6.0");
            settingsDBAdapter.updateEntry("rc3_std_buffalo_rate", "40.0");
            settingsDBAdapter.updateEntry("rc3_per_buffalo_fat", "50");
            settingsDBAdapter.updateEntry("rc3_per_buffalo_snf", "50");
            settingsDBAdapter.updateEntry("rc3_std_buffalo_fat", "10.0");
            settingsDBAdapter.updateEntry("rc3_std_buffalo_snf", "10.0");
            settingsDBAdapter.updateEntry("password", "1234");
            settingsDBAdapter.updateEntry("mpassword", "7890");
            settingsDBAdapter.updateEntry("dairycode", "AM");
            settingsDBAdapter.updateEntry("routecode", "DS");
            settingsDBAdapter.updateEntry("villagecode", "NS");
            settingsDBAdapter.updateEntry("centercode", "01");
        }
        String rate_chart;
        String rc1_date = settingsDBAdapter.getSingleEntry("rc1_date");
        String rc2_date = settingsDBAdapter.getSingleEntry("rc2_date");
        String rc3_date = settingsDBAdapter.getSingleEntry("rc3_date");
        Date date1, date2;
        Calendar c1, c2;
        c1 = Calendar.getInstance();
        c2 = Calendar.getInstance();
        c1.set(Integer.parseInt(rc1_date.substring(6)), Integer.parseInt(rc1_date.substring(3, 5)), Integer.parseInt(rc1_date.substring(0, 2)));
        c2.set(Integer.parseInt(rc2_date.substring(6)), Integer.parseInt(rc2_date.substring(3, 5)), Integer.parseInt(rc2_date.substring(0, 2)));
        date1 = c1.getTime();
        date2 = c2.getTime();
        if(date1.after(date2) || date1.equals(date2)) {
            c2.set(Integer.parseInt(rc3_date.substring(6)), Integer.parseInt(rc3_date.substring(3, 5)), Integer.parseInt(rc3_date.substring(0, 2)));
            date2 = c2.getTime();
            if(date1.after(date2) || date1.equals(date2))
                rate_chart = "1";
            else
                rate_chart = "3";
        }
        else {
            c1.set(Integer.parseInt(rc3_date.substring(6)), Integer.parseInt(rc3_date.substring(3, 5)), Integer.parseInt(rc3_date.substring(0, 2)));
            date1 = c1.getTime();
            if(date2.after(date1) || date2.equals(date1))
                rate_chart = "2";
            else
                rate_chart = "3";
        }
        intent.putExtra("RATE_CHART", rate_chart);
        intent.putExtra("RATE_TYPE", settingsDBAdapter.getSingleEntry("ratechart" + rate_chart));
        String time;
        switch (Integer.parseInt(settingsDBAdapter.getSingleEntry("shifttime"))) {
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
            Calendar c = Calendar.getInstance();
            Date date = format.parse(time);
            Date curDate = format.parse(String.valueOf(c.get(Calendar.HOUR_OF_DAY)) + ":" + String.valueOf(c.get(Calendar.MINUTE)));
            long shiftTimeMillis = date.getTime();
            long currentTimeMillis = curDate.getTime();
            if(currentTimeMillis <= shiftTimeMillis)
                intent.putExtra("SHIFT", context.getString(R.string.morning));
            else
                intent.putExtra("SHIFT", context.getString(R.string.evening));
        }
        catch (ParseException e){
            intent.putExtra("SHIFT", context.getString(R.string.morning));
        }
        intent.putExtra("DAIRY_NAME", settingsDBAdapter.getSingleEntry("dairyname"));
        intent.putExtra("STD_COW_RATE", settingsDBAdapter.getSingleEntry("rc" + rate_chart + "_std_cow_rate"));
        intent.putExtra("PER_COW_FAT", settingsDBAdapter.getSingleEntry("rc" + rate_chart + "_per_cow_fat"));
        intent.putExtra("STD_COW_FAT", settingsDBAdapter.getSingleEntry("rc" + rate_chart + "_std_cow_fat"));
        intent.putExtra("PER_COW_SNF", settingsDBAdapter.getSingleEntry("rc" + rate_chart + "_per_cow_snf"));
        intent.putExtra("STD_COW_SNF", settingsDBAdapter.getSingleEntry("rc" + rate_chart + "_std_cow_snf"));
        intent.putExtra("STD_BUFFALO_RATE", settingsDBAdapter.getSingleEntry("rc" + rate_chart + "_std_buffalo_rate"));
        intent.putExtra("PER_BUFFALO_FAT", settingsDBAdapter.getSingleEntry("rc" + rate_chart + "_per_buffalo_fat"));
        intent.putExtra("STD_BUFFALO_FAT", settingsDBAdapter.getSingleEntry("rc" + rate_chart + "_std_buffalo_fat"));
        intent.putExtra("PER_BUFFALO_SNF", settingsDBAdapter.getSingleEntry("rc" + rate_chart + "_per_buffalo_snf"));
        intent.putExtra("STD_BUFFALO_SNF", settingsDBAdapter.getSingleEntry("rc" + rate_chart + "_std_buffalo_snf"));
        intent.putExtra("ANALYZER",settingsDBAdapter.getSingleEntry("analyzer"));
        return "Success";
    }

    @Override
    protected void onPostExecute(String result) {

        if(result.equals("Success")){
            context.startActivity(intent);
            settingsDBAdapter.close();
            ((Activity)context).finish();
        }
    }
}
