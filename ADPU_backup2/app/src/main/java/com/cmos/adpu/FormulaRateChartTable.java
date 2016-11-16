package com.cmos.adpu;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.Set;

/**
 * Created by Pankaj on 19/12/2015.
 */
public class FormulaRateChartTable extends AsyncTask<String, Void, String> {
    SettingsDBAdapter settingsDBAdapter;
    RateChartDBAdapter rateChartDBAdapter;
    View fragView;
    Activity activity;
    ProgressBar progressBar;
    CustomSRCList customSRCList;
    String[] fat, snf, rate;

    FormulaRateChartTable(View fragView, Activity activity, ProgressBar progressBar) {
        this.fragView = fragView;
        this.activity = activity;
        this.progressBar = progressBar;
    }

    protected String doInBackground(String... params) {
        String type = params[0];
        settingsDBAdapter = new SettingsDBAdapter(activity);
        settingsDBAdapter.open();
        rateChartDBAdapter = new RateChartDBAdapter(activity);
        rateChartDBAdapter.open();
        /*BigDecimal step1 = new BigDecimal("0.1");
        BigDecimal step2 = new BigDecimal("0.1");
        for(BigDecimal m = new BigDecimal("0.0"); m.compareTo(new BigDecimal("15.0")) <= 0; m = m.add(step1)) {
            for (BigDecimal n = new BigDecimal("0.0"); n.compareTo(new BigDecimal("15.0")) <= 0; n = n.add(step2)) {
                rateChartDBAdapter.updateEntry(String.format("%.1f", m), String.format("%.1f", n), String.format("%.2f", m.multiply(n.add(step1))), "3");
            }
        }*/
        float cow_fat_from = Float.parseFloat(settingsDBAdapter.getSingleEntry("cow_fat_range_from"));
        float buf_fat_to = Float.parseFloat(settingsDBAdapter.getSingleEntry("buffalo_fat_range_to"));
        float cow_snf_from = Float.parseFloat(settingsDBAdapter.getSingleEntry("cow_snf_range_from"));
        float buf_snf_to = Float.parseFloat(settingsDBAdapter.getSingleEntry("buffalo_snf_range_to"));
        float std_cow_rate = Float.parseFloat(settingsDBAdapter.getSingleEntry("rc" + params[2] + "_std_cow_rate"));
        float per_cow_fat = Float.parseFloat(settingsDBAdapter.getSingleEntry("rc" + params[2] + "_per_cow_fat"));
        float std_cow_fat = Float.parseFloat(settingsDBAdapter.getSingleEntry("rc" + params[2] + "_std_cow_fat"));
        float per_cow_snf = Float.parseFloat(settingsDBAdapter.getSingleEntry("rc" + params[2] + "_per_cow_snf"));
        float std_cow_snf = Float.parseFloat(settingsDBAdapter.getSingleEntry("rc" + params[2] + "_std_cow_snf"));
        float std_buf_rate = Float.parseFloat(settingsDBAdapter.getSingleEntry("rc" + params[2] + "_std_buffalo_rate"));
        float per_buf_fat = Float.parseFloat(settingsDBAdapter.getSingleEntry("rc" + params[2] + "_per_buffalo_fat"));
        float std_buf_fat = Float.parseFloat(settingsDBAdapter.getSingleEntry("rc" + params[2] + "_std_buffalo_fat"));
        float per_buf_snf = Float.parseFloat(settingsDBAdapter.getSingleEntry("rc" + params[2] + "_per_buffalo_snf"));
        float std_buf_snf = Float.parseFloat(settingsDBAdapter.getSingleEntry("rc" + params[2] + "_std_buffalo_snf"));
        Integer snfCount = (int)(buf_snf_to - cow_snf_from)*10 + 1;
        float fat_start_range = Float.parseFloat(params[1]);
        float fat_end_range = fat_start_range + 0.9f;
        fat_start_range = cow_fat_from > fat_start_range ? cow_fat_from : fat_start_range;
        fat_end_range = buf_fat_to < fat_end_range ? buf_fat_to : fat_end_range;
        Integer fatCount = (int)((float)(fat_end_range - fat_start_range)*10) + 1;
        fat = new String[snfCount*fatCount + 1];
        snf = new String[snfCount*fatCount + 1];
        rate = new String[snfCount*fatCount + 1];
        int k = 1;
        fat[0] = "Fat";
        snf[0] = "SNF";
        rate[0] = "Rate";
        for(float i = fat_start_range; i <= fat_end_range; i = i + 0.1f){
            for(float j = cow_snf_from; j <= buf_snf_to; j = j + 0.1f){
                float rate_calc;
                fat[k] = String.format("%.1f", i);
                snf[k] = String.format("%.1f", j);
                switch (type) {
                    case "Formula":
                        String cattle_type;
                        if (i < Float.parseFloat(settingsDBAdapter.getSingleEntry("buffalo_fat_range_from")))
                            cattle_type = "Cow";
                        else
                            cattle_type = "Buffalo";
                        Float fat_value = i, snf_value = j;
                        if (i < Float.parseFloat(settingsDBAdapter.getSingleEntry("buffalo_fat_range_from"))) {
                            if (j > Float.parseFloat(settingsDBAdapter.getSingleEntry("cow_snf_range_to"))) {
                                snf_value = Float.parseFloat(settingsDBAdapter.getSingleEntry("cow_snf_range_to"));
                            }
                        }
                        if (cattle_type.equals("Cow"))
                            rate_calc = ((std_cow_rate * per_cow_fat * fat_value) / (std_cow_fat * 100)) +
                                    ((std_cow_rate * per_cow_snf * snf_value) / (std_cow_snf * 100));
                        else
                            rate_calc = ((std_buf_rate * per_buf_fat * fat_value) / (std_buf_fat * 100)) +
                                    ((std_buf_rate * per_buf_snf * snf_value) / (std_buf_snf * 100));
                        rate[k] = String.format("%.2f", rate_calc);
                        break;
                    case "Manual":
                    case "File":
                        String result = rateChartDBAdapter.getSingleEntry(String.format("%.1f", i), String.format("%.1f", j), params[2]);
                        if(!result.equals("RECORD DOES NOT EXIST.") && !result.equals("ERROR!!")) {
                            rate_calc = Float.parseFloat(result);
                            rate[k] = String.format("%.2f", rate_calc);
                        }
                        else
                            rate[k] = "0.0";
                        break;
                    default:
                }
                k++;
            }
        }
        settingsDBAdapter.close();
        rateChartDBAdapter.close();
        return "Success";
    }

    @Override
    protected void onPostExecute(String result) {
        //customSRCList = new CustomSRCList(activity, fat, snf, rate);
        ListView listView = (ListView) fragView.findViewById(R.id.list_src);
        listView.setAdapter(customSRCList);
        progressBar.setVisibility(View.GONE);
    }
}
