package com.cmos.adpu;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Pankaj on 19/12/2015.
 */
public class DownloadRCAsyncTask extends AsyncTask<String, Void, String> {
    private final String CLASS = "DownloadRCAsyncTask";
    SettingsDBAdapter settingsDBAdapter;
    RateChartDBAdapter rateChartDBAdapter;
    Context context;
    String ratechart;

    DownloadRCAsyncTask(Context context, String ratechart) {
        this.context = context;
        this.ratechart = ratechart;
    }

    protected String doInBackground(String... params) {
        try {
            Log.d(CLASS, "In this class");
            settingsDBAdapter = new SettingsDBAdapter(context);
            settingsDBAdapter.open();
            rateChartDBAdapter = new RateChartDBAdapter(context);
            rateChartDBAdapter.open();

            String cow_fat_range_from = settingsDBAdapter.getSingleEntry("cow_fat_range_from");
            String buffalo_fat_range_to = settingsDBAdapter.getSingleEntry("buffalo_fat_range_to");
            String cow_snf_range_from = settingsDBAdapter.getSingleEntry("cow_snf_range_from");
            String buffalo_snf_range_to = settingsDBAdapter.getSingleEntry("buffalo_snf_range_to");

            DefaultHttpClient client = new DefaultHttpClient();
            HttpHost targetHost = new HttpHost("true-milk.com", 80, "http");

            String[] flag = new String[20];
            int m = 0;
            StringBuilder sb = new StringBuilder();

            int z = 0, k;

            String[] result = null;
            int length;
            for (Float i = Float.parseFloat(cow_fat_range_from); i < Float.parseFloat(buffalo_fat_range_to); i = i + 0.10001f) {

                i = i * 10;
                z = Math.round(i);
                i = (float) z;
                i = i / 10;

                HttpGet request = new HttpGet("/demo/get_rate.php?uic=NSV061027026&fat=" + i + "&snfStart=" + cow_snf_range_from + "&snfEnd=" + buffalo_snf_range_to);
                HttpResponse response = client.execute(targetHost, request);

                BufferedReader in = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line = "";
                while ((line = in.readLine()) != null) {
                    sb.append(line);
                }
                in.close();
                k = 0;
                result = sb.toString().split("#")[1].split(",");
                length = result.length;
                Log.d(CLASS, "result length= " + length);

                for (Float j = Float.parseFloat(cow_snf_range_from); j < Float.parseFloat(buffalo_snf_range_to); j = j + 0.10001f) {

                    j = j * 10;
                    z = Math.round(j);
                    j = (float) z;
                    j = j / 10;

                    if (k <= length+1) {

                        Log.d(CLASS, i.toString() + "   " + j.toString() + "   " + result[k]);
                        rateChartDBAdapter.updateEntry(i.toString(), j.toString(), result[k], ratechart);

                    } else
                        break;
                    k++;
                }
            }
            Log.d(CLASS, "reached End of outer loop");
            rateChartDBAdapter.close();
            settingsDBAdapter.close();
        } catch (IOException e) {
            return e.getMessage();
        } catch (ArrayIndexOutOfBoundsException e) {
            return e.getMessage();
        }
        return "true";

    }

    @Override
    protected void onPostExecute(String result) {

        Log.d(CLASS, result);
        ((Activity) context).finish();

    }
}
