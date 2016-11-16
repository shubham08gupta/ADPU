package com.cmos.adpu;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Pankaj on 10/01/2016.
 */
public class FileRateChartUpdate extends AsyncTask<String, Void, String> {
    Context context;
    RateChartDBAdapter rateChartDBAdapter;
    ProgressDialog progressDialog;

    FileRateChartUpdate(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        //progressDialog = ProgressDialog.show(context, "Please wait....", "Uploading...", true);
    }

    protected String doInBackground(String... params) {
        try {
            rateChartDBAdapter = new RateChartDBAdapter(context);
            rateChartDBAdapter.open();
            File file = new File(params[0]);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            int i = 0;
            while ((line = br.readLine()) != null) {
                String[] cell = line.split(",");
                if (i != 0)
                    if (Float.parseFloat(cell[0]) > 0.0f && Float.parseFloat(cell[0]) < 15.1f &&
                            Float.parseFloat(cell[1]) > 0.0f && Float.parseFloat(cell[1]) < 15.1f &&
                            Float.parseFloat(cell[2]) > 0.0f && Float.parseFloat(cell[2]) < 500.1f) {
                            rateChartDBAdapter.updateEntry(String.valueOf(Float.parseFloat(cell[0])), String.valueOf(Float.parseFloat(cell[1])),
                                String.valueOf(Float.parseFloat(cell[2])), params[1]);
                    } else {
                        Toast.makeText(context, "Wrong File Format!!", Toast.LENGTH_SHORT).show();
                        return "Wrong File Format!!";
                    }
                else
                    i++;
            }
        } catch (IOException e) {
            return e.getMessage();
        }
        return "Success";
    }

    @Override
    protected void onPostExecute(String result) {
        rateChartDBAdapter.close();
        //progressDialog.dismiss();
        Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
    }
}
