package com.cmos.adpu;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TableLayout;

/**
 * Created by Pankaj on 19/12/2015.
 */
public class test1 extends AsyncTask<String, Void, String> {
    Context context;
    TableLayout tableLayout;
    SettingsDBAdapter settingsDBAdapter;
    test1 t1;

    test1(Context context, TableLayout tableLayout, SettingsDBAdapter settingsDBAdapter) {
        this.context = context;
        this.tableLayout = tableLayout;
        this.settingsDBAdapter = settingsDBAdapter;
    }

    protected String doInBackground(String... params) {
        for (float i = Float.parseFloat(settingsDBAdapter.getSingleEntry("cow_fat_range_from")); i <= Float.parseFloat(settingsDBAdapter.getSingleEntry("buffalo_fat_range_to")); i = i + 0.1f) {
            t1 = new test1(context, tableLayout, settingsDBAdapter);
            //t1.execute();
        }
        return  "";
    }
}
