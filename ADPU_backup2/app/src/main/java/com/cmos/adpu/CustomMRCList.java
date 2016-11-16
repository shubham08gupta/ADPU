package com.cmos.adpu;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Pankaj on 28/11/2014.
 */
public class CustomMRCList extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] fat;
    private final String[] snf;
    private final String rate_chart;
    ManualRateChartTable manualRateChartTable;
    RateChartDBAdapter rateChartDBAdapter;

    public CustomMRCList(Activity context, String[] fat, String[] snf, String rate_chart) {
        super(context, R.layout.mrc_list_item, fat);
        this.context = context;
        this.fat = fat;
        this.snf = snf;
        this.rate_chart = rate_chart;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final int pos = position;
        LayoutInflater inflater = context.getLayoutInflater();
        final View rowView= inflater.inflate(R.layout.mrc_list_item, null, true);
        ((TextView) rowView.findViewById(R.id.fat_mrc)).setText(fat[position]);
        ((TextView) rowView.findViewById(R.id.snf_mrc)).setText(snf[position]);
        if(position == 0) {
            ((EditText) rowView.findViewById(R.id.rate_mrc)).setText("Rate");
            rowView.findViewById(R.id.rate_mrc).setBackgroundColor(0x0000000);
        }
        else {
            //((EditText) rowView.findViewById(R.id.rate_mrc)).setText("0.0");
            manualRateChartTable = new ManualRateChartTable(context, (EditText) rowView.findViewById(R.id.rate_mrc));
            manualRateChartTable.execute(fat[position], snf[position], rate_chart);
        }
        if(position == DataInput.item_selected) {
            EditText editText = (EditText)rowView.findViewById(R.id.rate_mrc);
            DataInput.item_textView = editText;
            editText.setBackgroundResource(R.drawable.focused);
            editText.requestFocus();
        }
        ((EditText) rowView.findViewById(R.id.rate_mrc)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                rateChartDBAdapter = new RateChartDBAdapter(context);
                rateChartDBAdapter.open();
                if(((EditText) rowView.findViewById(R.id.rate_mrc)).getText().toString().length() > 0)
                    rateChartDBAdapter.updateEntry(fat[pos], snf[pos], String.format("%.2f", Float.parseFloat(((EditText) rowView.findViewById(R.id.rate_mrc)).getText().toString())), rate_chart);
                else
                    rateChartDBAdapter.updateEntry(fat[pos], snf[pos], String.format("%.2f", 0.00f), rate_chart);
                rateChartDBAdapter.close();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return rowView;
    }
}
