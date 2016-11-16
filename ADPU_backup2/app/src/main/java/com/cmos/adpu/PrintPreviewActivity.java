package com.cmos.adpu;

import android.graphics.Typeface;
import android.os.Bundle;
import android.app.Activity;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class PrintPreviewActivity extends Activity {
    static String print_data;
    CollectionsDBAdapter collectionsDBAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_preview);
        BaseActivity.print_data = getIntent().getExtras().getString("PRINTABLE_TEXT");
        DataInput.object_selected = "PRINT_PREVIEW";
        final Calendar c = Calendar.getInstance();
        BaseActivity.dataInput = new DataInput(this, (Button)findViewById(R.id.print_pp));
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/lineprinter.ttf");
        ((TextView)findViewById(R.id.printable_text_tv)).setTypeface(typeFace);
        ((TextView)findViewById(R.id.printable_text_tv)).setText(getIntent().getExtras().getString("PRINTABLE_TEXT")
        + "\r\n" + getIntent().getExtras().getString("PRINTABLE_TEXT") + "\r\n" + getIntent().getExtras().getString("PRINTABLE_TEXT"));
        findViewById(R.id.print_pp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataInput.data_input = false;
                BaseActivity.i = 0;
                if(DataInput.save_collections){
                    String day = ((Integer)c.get(Calendar.DAY_OF_MONTH)).toString();
                    if(c.get(Calendar.DAY_OF_MONTH) < 10)
                        day = "0" + day;
                    String month = ((Integer)c.get(Calendar.MONTH)).toString();
                    if(c.get(Calendar.MONTH) < 10)
                        month = "0" + month;
                    String year = ((Integer)c.get(Calendar.YEAR)).toString();
                    collectionsDBAdapter = new CollectionsDBAdapter(getApplicationContext());
                    collectionsDBAdapter.open();
                    Toast.makeText(getApplicationContext(), BaseActivity.print_data.substring(BaseActivity.print_data.lastIndexOf("Code: ") + 6, BaseActivity.print_data.lastIndexOf("Name: ") - 2), Toast.LENGTH_SHORT).show();
                    String result = collectionsDBAdapter.insertEntry(collectionsDBAdapter.getSerialNumber(),
                            BaseActivity.print_data.substring(BaseActivity.print_data.lastIndexOf("Name: ") + 6, BaseActivity.print_data.lastIndexOf("Quantity: ") - 2),
                            BaseActivity.print_data.substring(BaseActivity.print_data.lastIndexOf("Code: ") + 6, BaseActivity.print_data.lastIndexOf("Name: ") - 2),
                            BaseActivity.print_data.substring(BaseActivity.print_data.lastIndexOf("Quantity: ") + 10, BaseActivity.print_data.lastIndexOf("Fat: ") - 4),
                            BaseActivity.print_data.substring(BaseActivity.print_data.lastIndexOf("Fat: ") + 5, BaseActivity.print_data.lastIndexOf("SNF: ") - 3),
                            BaseActivity.print_data.substring(BaseActivity.print_data.lastIndexOf("SNF: ") + 5, BaseActivity.print_data.lastIndexOf("Rate: ") - 3),
                            BaseActivity.print_data.substring(BaseActivity.print_data.lastIndexOf("Rate: ") + 6, BaseActivity.print_data.lastIndexOf("Amount: ") - 2),
                            BaseActivity.print_data.substring(BaseActivity.print_data.lastIndexOf("Amount: ") + 8),
                            day + "/" + month + "/" + year + " Evening");
                    Toast.makeText(getApplicationContext(),result,Toast.LENGTH_SHORT).show();
                    if(result.equals("Success")) {
                        new Handler().postDelayed(new Runnable()
                        {
                            public void run()
                            {
                                BaseActivity.characteristicTX.setValue("\r\n");
                                BaseActivity.mBluetoothLeService.writeCharacteristic(BaseActivity.characteristicTX);
                            }
                        }, 100);
                        DataInput.save_collections = false;
                        finish();
                    }
                }
                else {
                    new Handler().postDelayed(new Runnable()
                    {
                        public void run()
                        {
                            BaseActivity.characteristicTX.setValue("\r\n");
                            BaseActivity.mBluetoothLeService.writeCharacteristic(BaseActivity.characteristicTX);
                        }
                    }, 100);
                    DataInput.save_collections = false;
                    finish();
                }
            }
        });
    }
}
