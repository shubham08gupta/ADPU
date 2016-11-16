package com.cmos.adpu;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Pankaj on 14/02/2016.
 */
public class UploadMember extends AsyncTask<String, Void, String> {
    Context context;
    String code;

    UploadMember(Context context){
        this.context = context;
    }

    @Override
    protected  void onPreExecute() {

    }

    protected String doInBackground(String... params) {
        try {
            code = params[0];
            DefaultHttpClient client = new DefaultHttpClient();
            HttpHost targetHost = new HttpHost("www.cmoselectronics.com", 80, "http");
            HttpGet request = new HttpGet("/amcu/member.php?code=" + params[0] + "&name=" + params[1] + "&mobile=" + params[2] + "&email=" + params[3] + "&image=" + params[4] + "&device=AMDSNS01");
            HttpResponse response = client.execute(targetHost, request);
            BufferedReader in = new BufferedReader
                    (new InputStreamReader(response.getEntity().getContent()));
            StringBuilder sb = new StringBuilder("");
            String line="";
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
        if(result.equals("Success")) {
            MembersDBAdapter membersDBAdapter = new MembersDBAdapter(context);
            membersDBAdapter.updateSyncFlag(code);
        }
        else{
            Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
        }
    }
}
