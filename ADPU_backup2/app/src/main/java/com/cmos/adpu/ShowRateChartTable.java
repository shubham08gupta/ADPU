package com.cmos.adpu;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import java.util.Locale;

/**
 * Created by Pankaj on 19/12/2015.
 */
public class ShowRateChartTable extends AsyncTask<String, Void, String> {
    SettingsDBAdapter settingsDBAdapter;
    RateChartDBAdapter rateChartDBAdapter;
    Context context;
    TextView rate;

    ShowRateChartTable(Context context, TextView rate) {
        this.context = context;
        this.rate = rate;
    }

    protected String doInBackground(String... params) {
        String type = params[3];
        settingsDBAdapter = new SettingsDBAdapter(context);
        settingsDBAdapter.open();
        rateChartDBAdapter = new RateChartDBAdapter(context);
        rateChartDBAdapter.open();
        float rate_calc;
        String rateValue;
        switch (type) {
            case "Formula":
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
                String cattle_type;
                if (Float.parseFloat(params[0]) < Float.parseFloat(settingsDBAdapter.getSingleEntry("buffalo_fat_range_from")))
                    cattle_type = "Cow";
                else
                    cattle_type = "Buffalo";
                Float fat_value = Float.parseFloat(params[0]), snf_value = Float.parseFloat(params[1]);
                if (Float.parseFloat(params[0]) < Float.parseFloat(settingsDBAdapter.getSingleEntry("buffalo_fat_range_from"))) {
                    if (Float.parseFloat(params[1]) > Float.parseFloat(settingsDBAdapter.getSingleEntry("cow_snf_range_to"))) {
                        snf_value = Float.parseFloat(settingsDBAdapter.getSingleEntry("cow_snf_range_to"));
                    }
                }
                if (cattle_type.equals("Cow"))
                    rate_calc = ((std_cow_rate * per_cow_fat * fat_value) / (std_cow_fat * 100)) +
                            ((std_cow_rate * per_cow_snf * snf_value) / (std_cow_snf * 100));
                else
                    rate_calc = ((std_buf_rate * per_buf_fat * fat_value) / (std_buf_fat * 100)) +
                            ((std_buf_rate * per_buf_snf * snf_value) / (std_buf_snf * 100));
                rateValue = String.format(Locale.ENGLISH, "%.2f", rate_calc);
                break;
            case "Manual":

            case "File":

            case "Online":
                String result = rateChartDBAdapter.getSingleEntry(String.format(Locale.ENGLISH, "%.1f", Float.parseFloat(params[0])), String.format(Locale.ENGLISH, "%.1f", Float.parseFloat(params[1])), params[2]);

                if (!result.equals("RECORD DOES NOT EXIST.") && !result.equals("ERROR!!")) {

                    //last entry ends with *, so to remove it
                    if (result.endsWith("*")) {
                        result = result.substring(0, result.length() - 1);
                    }
                    rate_calc = Float.parseFloat(result);
                    rateValue = String.format(Locale.ENGLISH, "%.2f", rate_calc);
                } else
                    rateValue = "0.0";
                break;
            default:
                rateValue = "0.0";
        }
        rateChartDBAdapter.close();
        settingsDBAdapter.close();
        return rateValue;
    }

    @Override
    protected void onPostExecute(String result) {
        rate.setText(result);
    }
}
