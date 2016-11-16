package com.cmos.adpu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class RateChartDBAdapter {

    static final String DATABASE_NAME = "adpu.db";
    static final int DATABASE_VERSION = 1;
    static String DATABASE_CREATE = "create table RATE_CHART (ID integer primary key autoincrement, FAT text, SNF text, RATE text, RATE_CHART text);";
    private final Context context;
    public SQLiteDatabase db;
    Integer i = 0;
    private DataBaseHelper dbHelper;

    public RateChartDBAdapter(Context context) {
        this.context = context;
        this.dbHelper = new DataBaseHelper(this.context, "adpu.db", null, 1);
    }

    public void close() {
        this.db.close();
    }

    public void count() {
        try {
            Cursor cursor = this.db.query("RATE_CHART", null, null, null, null, null, null);
            cursor.getCount();
            cursor.moveToFirst();
            cursor.close();
        } catch (SQLException localSQLException) {
            //Toast.makeText(this.context, "Error!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteMembers(String fat, String snf, String rate) {
        int i = this.db.delete("RATE_CHART", " FAT=? AND SNF=? AND RATE_CHART=?", new String[]{fat, snf, rate});
        //Toast.makeText(this.context, "Records deleted: " + i, Toast.LENGTH_SHORT).show();
    }

    public void delete_table() {
        this.db.execSQL("DROP TABLE IF EXISTS RATE_CHART");
        //Toast.makeText(this.context, "Droped Successfully", Toast.LENGTH_SHORT).show();
    }

    public SQLiteDatabase getDatabaseInstance() {
        return this.db;
    }

    public String getSingleEntry(String fat, String snf, String rate) {
        try {
            Cursor cursor = this.db.query("RATE_CHART", null, " FAT=? AND SNF=? AND RATE_CHART=?", new String[]{fat, snf, rate}, null, null, null);
            if (cursor.getCount() < 1) {
                cursor.close();
                return "RECORD DOES NOT EXIST.";
            }
            cursor.moveToFirst();
            snf = cursor.getString(cursor.getColumnIndex("RATE"));
            cursor.close();
            return snf;
        } catch (SQLException ex) {
            if (ex.toString().toLowerCase().contains("no such table")) {
                this.db.execSQL(DATABASE_CREATE);
                return "RECORD DOES NOT EXIST.";
            }
        }
        return "ERROR!!";
    }

    public String updateEntry(String fat, String snf, String rate, String rateChart) {
        String result = getSingleEntry(fat, snf, rateChart);
        String countRows = null;
        if (result.equals("RECORD DOES NOT EXIST.") || result.equals("ERROR!!")) {
            insertEntry(fat, snf, rate, rateChart);
            return "error";
        } else {
            ContentValues localContentValues = new ContentValues();
            localContentValues.put("FAT", fat);
            localContentValues.put("SNF", snf);
            localContentValues.put("RATE", rate);
            localContentValues.put("RATE_CHART", rateChart);
            countRows = String.valueOf(this.db.update("RATE_CHART", localContentValues, " FAT=? AND SNF=? AND RATE_CHART=?", new String[]{fat, snf, rateChart}));
            return countRows;
        }

    }

    public String insertEntry(String fat, String snf, String rate, String rateChart) {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("FAT", fat);
        localContentValues.put("SNF", snf);
        localContentValues.put("RATE", rate);
        localContentValues.put("RATE_CHART", rateChart);
        try {
            this.db.insertOrThrow("RATE_CHART", null, localContentValues);
            //Toast.makeText(this.context, "Collection Is Successfully Saved", Toast.LENGTH_SHORT).show();
            return "Success";
        } catch (SQLException localSQLException) {
            if (localSQLException.toString().toLowerCase().contains("no such table")) {
                this.db.execSQL(DATABASE_CREATE);
                insertEntry(fat, snf, rate, rateChart);
            }
        }
        return "Failed";
    }

    public RateChartDBAdapter open()
            throws SQLException {
        this.db = this.dbHelper.getWritableDatabase();
        return this;
    }
}