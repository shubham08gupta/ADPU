package com.cmos.adpu;

import android.os.Bundle;
import android.app.Activity;

public class FatSnfRangeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fat_snf_range);
        getActionBar().hide();
        BaseActivity.dataInput = new DataInput(this);
    }

}
