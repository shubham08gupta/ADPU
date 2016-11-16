package com.cmos.adpu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AddFarmerMember extends Activity {
    MembersDBAdapter membersDBAdapter;
    SettingsDBAdapter settingsDBAdapter;
    Button uploadFromFile;
    EditText code, name, mob, emailFormer, emailLater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_farmer_member);

        uploadFromFile = (Button) findViewById(R.id.upload_from_file);
        code = (EditText) findViewById(R.id.code_et_afm);
        name = (EditText) findViewById(R.id.name_et_afm);
        mob = (EditText) findViewById(R.id.mobile_et_afm);
        emailFormer = (EditText) findViewById(R.id.email1_et_afm);
        emailLater = (EditText) findViewById(R.id.email2_et_afm);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        //getActionBar().hide();
        DataInput.object_selected = "CODE_AFM";
        membersDBAdapter = new MembersDBAdapter(this);
        membersDBAdapter.open();
        ((EditText) findViewById(R.id.code_et_afm)).setText(membersDBAdapter.getCode());
        BaseActivity.dataInput = new DataInput(this,
                (EditText) findViewById(R.id.code_et_afm),
                (EditText) findViewById(R.id.name_et_afm),
                (EditText) findViewById(R.id.mobile_et_afm),
                (EditText) findViewById(R.id.email1_et_afm),
                (EditText) findViewById(R.id.email2_et_afm),
                (Button) findViewById(R.id.save_member));

        findViewById(R.id.save_member).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String result = membersDBAdapter.updateEntry(((EditText) findViewById(R.id.code_et_afm)).getText().toString(),
                        ((EditText) findViewById(R.id.name_et_afm)).getText().toString(),
                        ((EditText) findViewById(R.id.mobile_et_afm)).getText().toString(),
                        ((EditText) findViewById(R.id.email1_et_afm)).getText().toString() + ((EditText) findViewById(R.id.email2_et_afm)).getText().toString(),
                        "add_user.png");
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
                if (result.equals("Member added Successfully.") || result.equals("Member updated Successfully.")) {
                    settingsDBAdapter = new SettingsDBAdapter(getApplicationContext());
                    settingsDBAdapter.open();
                    UploadMember uploadMember = new UploadMember(getApplicationContext());
                    uploadMember.execute(((EditText) findViewById(R.id.code_et_afm)).getText().toString(),
                            ((EditText) findViewById(R.id.name_et_afm)).getText().toString(),
                            ((EditText) findViewById(R.id.mobile_et_afm)).getText().toString(),
                            ((EditText) findViewById(R.id.email1_et_afm)).getText().toString() + ((EditText) findViewById(R.id.email2_et_afm)).getText().toString(),
                            "add_user.png",
                            settingsDBAdapter.getSingleEntry("macaddress"));
                    /*SmsManager sms = SmsManager.getDefault();
                    sms.sendTextMessage("+91" + ((EditText) findViewById(R.id.mobile_et_afm)).getText().toString(), null,
                            "Hi " + ((EditText) findViewById(R.id.name_et_afm)).getText().toString() + "\nWelcome to " + settingsDBAdapter.getSingleEntry("dairyname") + ", you have been registered with Member Code " + ((EditText) findViewById(R.id.code_et_afm)).getText().toString() + ".",
                            null, null);*/
                    ((EditText) findViewById(R.id.code_et_afm)).setText(membersDBAdapter.getCode());
                    ((EditText) findViewById(R.id.name_et_afm)).setText("");
                    ((EditText) findViewById(R.id.mobile_et_afm)).setText("");
                    ((EditText) findViewById(R.id.email1_et_afm)).setText("");
                    DataInput.object_selected = "CODE_AFM";
                    findViewById(R.id.code_et_afm).setBackgroundResource(R.drawable.focused);
                    findViewById(R.id.name_et_afm).setBackgroundResource(R.drawable.unfocused);
                    findViewById(R.id.mobile_et_afm).setBackgroundResource(R.drawable.unfocused);
                    findViewById(R.id.email1_et_afm).setBackgroundResource(R.drawable.unfocused);
                    findViewById(R.id.email2_et_afm).setBackgroundResource(R.drawable.unfocused);
                    settingsDBAdapter.close();
                }
            }
        });

        ((EditText) findViewById(R.id.code_et_afm)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String result = membersDBAdapter.getSingleEntry(((EditText) findViewById(R.id.code_et_afm)).getText().toString());
                if (!result.equals("RECORD DOES NOT EXIST.") && !result.equals("ERROR!!")) {
                    String[] result1 = result.split(",");
                    ((EditText) findViewById(R.id.name_et_afm)).setText(result1[1]);
                    ((EditText) findViewById(R.id.mobile_et_afm)).setText(result1[2]);
                    ((EditText) findViewById(R.id.email1_et_afm)).setText(result1[3].split("@")[0]);
                    ((EditText) findViewById(R.id.email2_et_afm)).setText("@" + result1[3].split("@")[1]);
                } else {
                    ((EditText) findViewById(R.id.name_et_afm)).setText("");
                    ((EditText) findViewById(R.id.mobile_et_afm)).setText("");
                    ((EditText) findViewById(R.id.email1_et_afm)).setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // handling onclick of upload button
        uploadFromFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), FileRateChartActivity.class);
                i.putExtra("AddFarmer", "true");
                i.putExtra("AddFarmerCode", code.getText().toString());
                startActivity(i);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        membersDBAdapter.close();
    }
}
