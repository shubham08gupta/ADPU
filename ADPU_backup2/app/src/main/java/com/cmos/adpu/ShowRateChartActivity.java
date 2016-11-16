package com.cmos.adpu;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class ShowRateChartActivity extends Activity {
    SettingsDBAdapter settingsDBAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_rate_chart);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        BaseActivity.dataInput = new DataInput(this);
        settingsDBAdapter = new SettingsDBAdapter(this);
        settingsDBAdapter.open();
        getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        getActionBar().setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
        getActionBar().setDisplayShowTitleEnabled(true);
        getActionBar().setTitle("Show Rate Chart " + getIntent().getStringExtra("RATE_CHART"));

        for (double i = Math.floor(Float.parseFloat(settingsDBAdapter.getSingleEntry("cow_fat_range_from"))); i <= Float.parseFloat(settingsDBAdapter.getSingleEntry("buffalo_fat_range_to")); i = i + 1.0f) {
            TabContentFragment tabContentFragment = new TabContentFragment();
            Bundle args = new Bundle();
            args.putInt("FAT_RANGE", (int) i);
            tabContentFragment.setArguments(args);
            getActionBar().addTab(getActionBar().newTab().setText(String.valueOf((int) i)).setTabListener(new TabListener(tabContentFragment)));
        }
        try {
            Number number = NumberFormat.getNumberInstance(Locale.ENGLISH).parse(settingsDBAdapter.getSingleEntry("buffalo_fat_range_to"));
            DataInput.last_tab = number.intValue();
        } catch (ParseException pe) {
            Toast.makeText(this, "Parsing Error", Toast.LENGTH_SHORT).show();
        }
        DataInput.tab_selected = 0;
        settingsDBAdapter.close();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        DataInput.item_selected = 1;
    }

    public static class TabContentFragment extends Fragment {
        SettingsDBAdapter settingsDBAdapter;
        String[] fat, snf;
        CustomSRCList customSRCList;

        public TabContentFragment() {
        }

        public String getText() {
            return String.valueOf(getArguments().getInt("FAT_RANGE"));
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View fragView = inflater.inflate(R.layout.show_rate_chart_tab_content, container, false);
            String fat_range = String.valueOf(getArguments().getInt("FAT_RANGE"));
            fragView.findViewById(R.id.progress_src).setVisibility(View.GONE);
            settingsDBAdapter = new SettingsDBAdapter(getActivity());
            settingsDBAdapter.open();
            float cow_fat_from = Float.parseFloat(settingsDBAdapter.getSingleEntry("cow_fat_range_from"));
            float buf_fat_to = Float.parseFloat(settingsDBAdapter.getSingleEntry("buffalo_fat_range_to"));
            float cow_snf_from = Float.parseFloat(settingsDBAdapter.getSingleEntry("cow_snf_range_from"));
            float buf_snf_to = Float.parseFloat(settingsDBAdapter.getSingleEntry("buffalo_snf_range_to"));
            Integer snfCount = (int) (buf_snf_to - cow_snf_from) * 10 + 1;
            float fat_start_range = Float.parseFloat(fat_range);
            float fat_end_range = fat_start_range + 0.9f;
            fat_start_range = cow_fat_from > fat_start_range ? cow_fat_from : fat_start_range;
            fat_end_range = buf_fat_to < fat_end_range ? buf_fat_to : fat_end_range;
            Integer fatCount = (int) ((float) (fat_end_range - fat_start_range) * 10) + 1;
            fat = new String[snfCount * fatCount + 1];
            snf = new String[snfCount * fatCount + 1];
            int k = 1;
            fat[0] = "Fat";
            snf[0] = "SNF";
            for (float i = fat_start_range; i <= fat_end_range; i = i + 0.1f) {
                for (float j = cow_snf_from; j < buf_snf_to + 0.1f; j = j + 0.1f) {
                    fat[k] = String.format(Locale.ENGLISH, "%.1f", i);
                    snf[k] = String.format(Locale.ENGLISH, "%.1f", j);
                    k++;
                }
            }
            String type = settingsDBAdapter.getSingleEntry("ratechart" + getActivity().getIntent().getStringExtra("RATE_CHART"));
            settingsDBAdapter.close();
            customSRCList = new CustomSRCList(getActivity(), fat, snf, getActivity().getIntent().getStringExtra("RATE_CHART"), type);
            ListView listView = (ListView) fragView.findViewById(R.id.list_src);
            listView.setAdapter(customSRCList);
            BaseActivity.dataInput = new DataInput(getActivity(), listView);
            DataInput.object_selected = "SHOW_RATE_CHART";
            DataInput.first_time_selected = true;
            return fragView;
        }
    }

    private class TabListener implements ActionBar.TabListener {
        private TabContentFragment mFragment;

        public TabListener(TabContentFragment fragment) {
            mFragment = fragment;
        }

        public void onTabSelected(Tab tab, FragmentTransaction ft) {
            ft.add(R.id.fragment_content, mFragment, mFragment.getText());
        }

        public void onTabUnselected(Tab tab, FragmentTransaction ft) {
            ft.remove(mFragment);
        }

        public void onTabReselected(Tab tab, FragmentTransaction ft) {
            Toast.makeText(ShowRateChartActivity.this, "Reselected!", Toast.LENGTH_SHORT).show();
        }
    }
}
