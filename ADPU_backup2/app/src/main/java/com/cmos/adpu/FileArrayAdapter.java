package com.cmos.adpu;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Pankaj on 13/02/2016.
 */
public class FileArrayAdapter extends ArrayAdapter<Option> {
    private Context c;
    private int id;
    private List<Option> items;

    public FileArrayAdapter(Context context, int textViewResourceId,
                            List<Option> objects) {
        super(context, textViewResourceId, objects);
        c = context;
        id = textViewResourceId;
        items = objects;
    }

    public Option getItem(int i) {
        return items.get(i);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(id, null);
        }
        final Option o = items.get(position);
        if (o != null) {
            TextView t1 = (TextView) v.findViewById(R.id.frc_name_tv);
            TextView t2 = (TextView) v.findViewById(R.id.frc_data_tv);
            if (t1 != null)
                t1.setText(o.getName());
            if (t2 != null)
                t2.setText(o.getData());
        }
        TextView t1 = (TextView) v.findViewById(R.id.frc_name_tv);
        LinearLayout l1 = (LinearLayout) v.findViewById(R.id.frc_listitem);
        if (position == DataInput.item_selected) {
            l1.setBackgroundColor(0xFF99EEFF);
            t1.requestFocus();
        } else {
            l1.setBackgroundColor(0xFFFFFFFF);
        }
        return v;
    }
}
