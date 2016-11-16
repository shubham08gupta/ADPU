package com.cmos.adpu;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

/**
 * Created by Pankaj on 19/12/2015.
 */
public class ManualRateChartTable extends AsyncTask<String, Void, String> {
    RateChartDBAdapter rateChartDBAdapter;
    Context context;
    EditText rate;

    ManualRateChartTable(Context context, EditText rate) {
        this.context = context;
        this.rate = rate;
    }

    protected String doInBackground(String... params) {
        rateChartDBAdapter = new RateChartDBAdapter(context);
        rateChartDBAdapter.open();
        float rate_calc;
        String result = rateChartDBAdapter.getSingleEntry(String.format("%.1f", Float.valueOf(params[0])), String.format("%.1f", Float.valueOf(params[1])), params[2]);
        rateChartDBAdapter.close();
        if(!result.equals("RECORD DOES NOT EXIST.") && !result.equals("ERROR!!")) {
            rate_calc = Float.parseFloat(result);
            return String.format("%.2f", rate_calc);
        }
        else
            return "0.00";
    }

    @Override
    protected void onPostExecute(String result) {
        rate.setText(result);
    }
}
