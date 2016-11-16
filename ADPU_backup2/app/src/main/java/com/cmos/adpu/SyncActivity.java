package com.cmos.adpu;

import android.os.Bundle;
import android.app.Activity;

public class SyncActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sync);
        BaseActivity.dataInput = new DataInput(this);
    }

}
