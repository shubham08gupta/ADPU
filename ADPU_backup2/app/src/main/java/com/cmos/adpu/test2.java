package com.cmos.adpu;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TableLayout;

/**
 * Created by Pankaj on 19/12/2015.
 */
public class test2 extends AsyncTask<String, Void, String> {
    Context context;
    TableLayout tableLayout;
    SettingsDBAdapter settingsDBAdapter;

    test2(Context context, TableLayout tableLayout, SettingsDBAdapter settingsDBAdapter) {
        this.context = context;
        this.tableLayout = tableLayout;
        this.settingsDBAdapter = settingsDBAdapter;
    }

    protected String doInBackground(String... params) {
        for (float i = Float.parseFloat(settingsDBAdapter.getSingleEntry("cow_fat_range_from")); i <= Float.parseFloat(settingsDBAdapter.getSingleEntry("buffalo_fat_range_to")); i = i + 0.1f) {

        }
        return  "";
    }
}
