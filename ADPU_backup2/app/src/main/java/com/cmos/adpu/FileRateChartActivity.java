package com.cmos.adpu;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FileRateChartActivity extends ListActivity {
    private File currentDir;
    private FileArrayAdapter adapter;
    RateChartDBAdapter rateChartDBAdapter;
    String rate_chart, result, farmerCode;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        //requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        //setContentView(R.layout.activity_file_rate_chart);

        //check whether uploading for Add Farmer Activity
        result = getIntent().getStringExtra("AddFarmer");
        // get the code to match with code in csv file
        farmerCode = getIntent().getStringExtra("AddFarmerCode");
        rate_chart = getIntent().getStringExtra("RATE_CHART");
        currentDir = new File("/sdcard/");
        context = this;
        fill(currentDir);
    }

    private void fill(File f) {
        File[] dirs = f.listFiles();
        this.setTitle("Current Dir: " + f.getName());
        List<Option> dir = new ArrayList<>();
        List<Option> fls = new ArrayList<>();
        try {
            for (File ff : dirs) {
                if (ff.isDirectory())
                    dir.add(new Option(ff.getName(), "Folder", ff.getAbsolutePath()));
                else if (ff.getName().substring(ff.getName().lastIndexOf(".") + 1).equals("csv")) {
                    fls.add(new Option(ff.getName(), "File Size: " + ff.length(), ff.getAbsolutePath()));
                }
            }
        } catch (Exception e) {
        }
        Collections.sort(dir);
        Collections.sort(fls);
        dir.addAll(fls);
        if (!f.getName().equalsIgnoreCase("sdcard"))
            dir.add(0, new Option("..", "Parent Directory", f.getParent()));

        adapter = new FileArrayAdapter(FileRateChartActivity.this, R.layout.activity_file_rate_chart, dir);

        this.setListAdapter(adapter);

        BaseActivity.dataInput = new DataInput(this, this.getListView(), adapter);
        DataInput.object_selected = "FILE_RATE_CHART";
        DataInput.item_selected = 0;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        // TODO Auto-generated method stub
        super.onListItemClick(l, v, position, id);
        Option o = adapter.getItem(position);
        if (o.getData().equalsIgnoreCase("folder") || o.getData().equalsIgnoreCase("parent directory")) {
            currentDir = new File(o.getPath());
            DataInput.item_selected = 0;
            fill(currentDir);
        } else {
            onFileClick(o, result);
        }
    }

    private void onFileClick(final Option o, final String result) {
        //setProgressBarIndeterminateVisibility(true);
        //FileRateChartUpdate fileRateChartUpdate = new FileRateChartUpdate(getApplicationContext());
        //fileRateChartUpdate.execute(o.getPath(), "2");
        final ProgressDialog progressDialog = ProgressDialog.show(this, "Please Wait...", "Uploading rate chart...", true);
        //progressDialog.setCancelable(true);
        DataInput.data_input = false;
        new Thread(new Runnable() {
            @Override
            public void run() {


                if (result.equals("true")) {
                    //add new farmer from csv file
                    MembersDBAdapter membersDBAdapter = new MembersDBAdapter(getApplicationContext());
                    membersDBAdapter.open();
                    try {

                        File file = new File(o.getPath());
                        BufferedReader br = new BufferedReader(new FileReader(file));
                        String line;
                        while ((line = br.readLine()) != null) {

                            String[] cell = line.split(",");

                            // match with farmer code and then only update
                            if (cell[0].equals(farmerCode)) {
                                //cell[0]= code, cell[1]=Name, cell[2]= mobile, cell[3]=email
                                membersDBAdapter.updateEntry(cell[0], cell[1], cell[2], cell[3], "");
                                // Log.v("TAG","Successfully updated " +cell[0]+ cell[1]+ cell[2] + cell[3]);
                            }
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        membersDBAdapter.close();

                    }


                } else {

                    try {
                        rateChartDBAdapter = new RateChartDBAdapter(getApplicationContext());
                        rateChartDBAdapter.open();
                        File file = new File(o.getPath());
                        BufferedReader br = new BufferedReader(new FileReader(file));
                        String line;
                        int i = 0;
                        while ((line = br.readLine()) != null) {
                            String[] cell = line.split(",");
                            if (i != 0)
                                if (Float.parseFloat(cell[0]) > 0.0f && Float.parseFloat(cell[0]) < 15.1f &&
                                        Float.parseFloat(cell[1]) > 0.0f && Float.parseFloat(cell[1]) < 15.1f &&
                                        Float.parseFloat(cell[2]) > 0.0f && Float.parseFloat(cell[2]) < 500.1f) {
                                    rateChartDBAdapter.updateEntry(String.valueOf(Float.parseFloat(cell[0])), String.valueOf(Float.parseFloat(cell[1])),
                                            String.valueOf(Float.parseFloat(cell[2])), rate_chart);
                                } else {
                                    Toast.makeText(context, "Wrong File Format!!", Toast.LENGTH_SHORT).show();
                                }
                            else
                                i++;
                        }
                        rateChartDBAdapter.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                        rateChartDBAdapter.close();
                    }
                }

                DataInput.data_input = true;
                progressDialog.dismiss();
                finish();
            }
        }).start();
    }

    /*public class FileArrayAdapter extends ArrayAdapter<Option>{
        private Context c;
        private int id;
        private List<Option>items;
        public FileArrayAdapter(Context context, int textViewResourceId,
                                List<Option> objects) {
            super(context, textViewResourceId, objects);
            c = context;
            id = textViewResourceId;
            items = objects;
        }
        public Option getItem(int i)
        {
            return items.get(i);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(id, null);
            }
            final Option o = items.get(position);
            if (o != null) {
                TextView t1 = (TextView) v.findViewById(R.id.frc_name_tv);
                TextView t2 = (TextView) v.findViewById(R.id.frc_data_tv);
                if(t1!=null)
                t1.setText(o.getName());
                if(t2!=null)
                t2.setText(o.getData());
            }
            return v;
        }
    }

    public class Option implements Comparable<Option>{
        private String name;
        private String data;
        private String path;

        public Option(String n,String d,String p)
        {
            name = n;
            data = d;
            path = p;
        }
        public String getName()
        {
            return name;
        }
        public String getData()
        {
            return data;
        }
        public String getPath()
        {
            return path;
        }

        @Override
        public int compareTo(Option o) {
            if(this.name != null)
            return this.name.toLowerCase().compareTo(o.getName().toLowerCase());
            else
            throw new IllegalArgumentException();
        }
    }*/
}
