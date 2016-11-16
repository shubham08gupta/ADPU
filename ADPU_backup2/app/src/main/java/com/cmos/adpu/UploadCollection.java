package com.cmos.adpu;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class UploadCollection extends AsyncTask<String, Void, String> {
    Context context;
    //String code, date_shift;
    String name, date, shift, uic, serialNumber, code, quantity, fat, snf, rate, amount, csrc, clr = "00.0", water = "00";

    UploadCollection(Context context) {
        this.context = context;
    }

    @Override
    protected void onPreExecute() {


    }

    protected String doInBackground(String... params) {
        try {

            // format is uic, producer code, date, shift, Csrc, FAT , SNF , CLR=00.00 , Water=00 , Rate , Qty ,amount, serial no (transaction id)
            /*
            code = params[2];
            date_shift = params[8];
            DefaultHttpClient client = new DefaultHttpClient();
            HttpHost targetHost = new HttpHost("www.true-milk.com", 80, "http");
            HttpGet request = new HttpGet("/netcoCH.php?values=NewDairy,collectioncentre1,"
                    + params[1] + "," + params[2] + ",919876543210,28-04-16,M,COW," + params[4].substring(params[4].lastIndexOf(")") + 1) +
                    "," + params[5] + ",00,00," + params[6] + "," + params[7] + "," + params[3] +
                    ",280416M001,UK000001,28-04-16,abc95@email.com");
            HttpResponse response = client.execute(targetHost, request);

            */
            serialNumber = params[0];
            name = params[1];
            code = params[2];
            quantity = params[3].substring(9);
            Log.e("shubham: ", params[4]);
            fat = params[4];
            if (fat.contains("Buffalo")){
                fat = params[4].substring(19);
            }else{
                fat = params[4].substring(15);
            }
            Log.e("shubham new: ", fat);
            snf = params[5];
            rate = params[6].trim();
            amount = params[7].trim();
            date = params[8];
            shift = params[9].substring(0, 1);// E or M
            uic = params[10];
            csrc = params[11];

            String url = "/demo/netcoCH.php?values=" + uic + "," + code + "," + date + "," + shift + "," + csrc + "," + fat + "," + snf + "," + clr + "," +
                    water + "," + rate + "," + quantity + "," + amount + "," + serialNumber;
            Log.v("url", url);
            DefaultHttpClient client = new DefaultHttpClient();
            HttpHost targetHost = new HttpHost("www.true-milk.com", 80, "http");
            HttpGet request = new HttpGet(url);
            HttpResponse response = client.execute(targetHost, request);


            BufferedReader in = new BufferedReader
                    (new InputStreamReader(response.getEntity().getContent()));
            StringBuilder sb = new StringBuilder("");
            String line = "";
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            in.close();
            return sb.toString();
        } catch (IOException e) {
            return e.getMessage();
        }
    }

    protected void onPostExecute(String result) {
        if (result.contains("New CollectionHistory inserted successfully")) {
            CollectionsDBAdapter collectionsDBAdapter = new CollectionsDBAdapter(context);
            collectionsDBAdapter.open();
            //collectionsDBAdapter.updateSyncFlag(code, date_shift);
            collectionsDBAdapter.updateSyncFlag(code, shift);
            collectionsDBAdapter.close();
        } else {
            Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
        }
    }
}
