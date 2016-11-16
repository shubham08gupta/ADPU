package com.cmos.adpu;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;

public class RateChartOnlineActivity extends Activity {

    SettingsDBAdapter settingsDBAdapter;
    RateChartDBAdapter rateChartDBAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate_chart_online);

        checkInternetAvailability();

        settingsDBAdapter = new SettingsDBAdapter(getApplicationContext());
        settingsDBAdapter.open();
        rateChartDBAdapter = new RateChartDBAdapter(getApplicationContext());
        rateChartDBAdapter.open();
        String cow_fat_range_from = settingsDBAdapter.getSingleEntry("cow_fat_range_from");
        String buffalo_fat_range_to = settingsDBAdapter.getSingleEntry("buffalo_fat_range_to");
        String cow_snf_range_from = settingsDBAdapter.getSingleEntry("cow_snf_range_from");
        String buffalo_snf_range_to = settingsDBAdapter.getSingleEntry("buffalo_snf_range_to");

        //Toast.makeText(getApplicationContext(),cow_fat_range_from + "  " + buffalo_fat_range_to + "  " + cow_snf_range_from + "  " + buffalo_snf_range_to,Toast.LENGTH_LONG).show();
        Log.v("RateChartOnlineActivity", cow_fat_range_from + "  " + buffalo_fat_range_to + "  " + cow_snf_range_from + "  " + buffalo_snf_range_to);
        Log.v("RateChartOnlineActivity", "" + Float.parseFloat(String.format(Locale.ENGLISH, "%.1f", Float.parseFloat(cow_fat_range_from))) + "   " +
                Float.parseFloat(String.format(Locale.ENGLISH, "%.1f", Float.parseFloat(buffalo_fat_range_to))) + "   " +
                Float.parseFloat(String.format(Locale.ENGLISH, "%.1f", Float.parseFloat(cow_snf_range_from))) + "   " +
                Float.parseFloat(String.format(Locale.ENGLISH, "%.1f", Float.parseFloat(buffalo_snf_range_to)))
        );

        int k = 0;
        for (Float i = Float.parseFloat(String.format(Locale.ENGLISH, "%.1f", Float.parseFloat(cow_fat_range_from)));
             i <= Float.parseFloat(String.format(Locale.ENGLISH, "%.1f", Float.parseFloat(buffalo_fat_range_to))); i = i + 0.1f) {

            for (Float j = Float.parseFloat(String.format(Locale.ENGLISH, "%.1f", Float.parseFloat(cow_snf_range_from)));
                 j <= Float.parseFloat(String.format(Locale.ENGLISH, "%.1f", Float.parseFloat(buffalo_snf_range_to)));
                 j = j + 0.1f) {

                rateChartDBAdapter.updateEntry(i.toString(), j.toString(), "0.0", getIntent().getStringExtra("RATE_CHART"));
            }
        }

        rateChartDBAdapter.close();
        settingsDBAdapter.close();
    }

    private void checkInternetAvailability() {

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            // internet is available
            DownloadRCAsyncTask downloadRCAsyncTask = new DownloadRCAsyncTask(this, getIntent().getStringExtra("RATE_CHART"));
            downloadRCAsyncTask.execute();
        } else {
            Toast.makeText(getApplicationContext(), "No network Connection available", Toast.LENGTH_LONG).show();
        }
    }

}
