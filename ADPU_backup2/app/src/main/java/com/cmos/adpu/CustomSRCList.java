package com.cmos.adpu;

import android.app.Activity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Pankaj on 28/11/2014.
 */
public class CustomSRCList extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] fat;
    private final String[] snf;
    private final String rate_chart;
    private final String type;
    ShowRateChartTable showRateChartTable;

    public CustomSRCList(Activity context, String[] fat, String[] snf, String rate_chart, String type) {
        super(context, R.layout.src_list_item, fat);
        this.context = context;
        this.fat = fat;
        this.snf = snf;
        this.rate_chart = rate_chart;
        this.type = type;
    }
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        final View rowView= inflater.inflate(R.layout.src_list_item, null, true);
        ((TextView) rowView.findViewById(R.id.fat_src)).setText(fat[position]);
        ((TextView) rowView.findViewById(R.id.snf_src)).setText(snf[position]);
        if(position == 0) {
            ((TextView) rowView.findViewById(R.id.rate_src)).setText("Rate");
            rowView.findViewById(R.id.rate_src).setBackgroundColor(0x0000000);
        }
        else {
            showRateChartTable = new ShowRateChartTable(context, (TextView) rowView.findViewById(R.id.rate_src));
            showRateChartTable.execute(fat[position], snf[position], rate_chart, type);
        }

        return rowView;
    }
}
