package com.cmos.adpu;

import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ResetPasswordActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        getActionBar().setTitle("Menu > Change Password");
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        DataInput.object_selected = "OLD_PASS";
        BaseActivity.dataInput = new DataInput(this,
                (EditText)findViewById(R.id.s_oldpass_et),
                (EditText)findViewById(R.id.s_newpass_et),
                (EditText)findViewById(R.id.s_repeatpass_et),
                (Button)findViewById(R.id.s_change_b));
        findViewById(R.id.s_change_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldpass = ((EditText)findViewById(R.id.s_oldpass_et)).getText().toString();
                String newpass = ((EditText)findViewById(R.id.s_newpass_et)).getText().toString();
                String repeatpass = ((EditText)findViewById(R.id.s_repeatpass_et)).getText().toString();
                SettingsDBAdapter settingsDBAdapter = new SettingsDBAdapter(getApplicationContext());
                settingsDBAdapter.open();
                if(oldpass.equals(settingsDBAdapter.getSingleEntry("password"))){
                    if(newpass.length() > 3){
                        if(newpass.equals(repeatpass)){
                            settingsDBAdapter.updateEntry("password", newpass);
                            Toast.makeText(ResetPasswordActivity.this, "Password changed successfully!!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                        else
                            Toast.makeText(ResetPasswordActivity.this, "New Password and Repeat Password should match!!", Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(ResetPasswordActivity.this, "Length of New Password should be 4 or more!!", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(ResetPasswordActivity.this, "Incorrect old password!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
