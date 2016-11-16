package com.cmos.adpu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.Calendar;

public class CollectionsDBAdapter {
    static final String DATABASE_NAME = "adpu.db";
    static final int DATABASE_VERSION = 1;
    static String DATABASE_CREATE = "create table COLLECTIONS (ID integer primary key autoincrement, SERIAL text, NAME text, CODE text, WEIGHT text, FAT text, SNF text, RATE text, TYPE text, DATE_SHIFT text, SYNC_FLAG text);";
    private final Context context;
    public SQLiteDatabase db;
    private DataBaseHelper dbHelper;

    public CollectionsDBAdapter(Context context) {
        this.context = context;
        this.dbHelper = new DataBaseHelper(this.context, "adpu.db", null, 1);
    }

    public void close() {
        this.db.close();
    }

    public void count() {
        try {
            Cursor cursor = this.db.query("COLLECTIONS", null, null, null, null, null, null);
            cursor.getCount();
            cursor.moveToFirst();
            //Toast.makeText(this.context, cursor.getString(cursor.getColumnIndex("SERIAL")), Toast.LENGTH_SHORT).show();
            cursor.close();
        } catch (SQLException localSQLException) {
            //Toast.makeText(this.context, "Error!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteCollections(String paramString) {
        int i = this.db.delete("COLLECTIONS", "DATE_SHIFT=?", new String[]{paramString});
        Toast.makeText(this.context, "Records deleted: " + i, Toast.LENGTH_SHORT).show();
    }

    public void delete_table() {
        this.db.execSQL("DROP TABLE IF EXISTS COLLECTIONS");
    }

    public SQLiteDatabase getDatabaseInstance() {
        return this.db;
    }

    public String getMultipleEntry(String paramString) {
        try {
            Cursor cursor = this.db.query("COLLECTIONS", null, "DATE_SHIFT=?", new String[]{paramString}, null, null, null);
            if (cursor.getCount() < 1) {
                cursor.close();
                return "RECORD DOES NOT EXIST.";
            }
            cursor.moveToFirst();
            paramString = "" + cursor.getString(cursor.getColumnIndex("NAME")) + ",";
            paramString = paramString + cursor.getString(cursor.getColumnIndex("CODE")) + ",";
            paramString = paramString + cursor.getString(cursor.getColumnIndex("WEIGHT")) + ",";
            paramString = paramString + cursor.getString(cursor.getColumnIndex("FAT")) + ",";
            paramString = paramString + cursor.getString(cursor.getColumnIndex("SNF")) + ",";
            paramString = paramString + cursor.getString(cursor.getColumnIndex("RATE")) + ",";
            paramString = paramString + cursor.getString(cursor.getColumnIndex("TYPE")) + ",";

            for (paramString = paramString + cursor.getString(cursor.getColumnIndex("DATE_SHIFT")); !cursor.isLast(); paramString = paramString + cursor.getString(cursor.getColumnIndex("DATE_SHIFT"))) {
                cursor.moveToNext();
                paramString = paramString + ",..,";
                paramString = paramString + cursor.getString(cursor.getColumnIndex("NAME")) + ",";
                paramString = paramString + cursor.getString(cursor.getColumnIndex("CODE")) + ",";
                paramString = paramString + cursor.getString(cursor.getColumnIndex("WEIGHT")) + ",";
                paramString = paramString + cursor.getString(cursor.getColumnIndex("FAT")) + ",";
                paramString = paramString + cursor.getString(cursor.getColumnIndex("SNF")) + ",";
                paramString = paramString + cursor.getString(cursor.getColumnIndex("RATE")) + ",";
                paramString = paramString + cursor.getString(cursor.getColumnIndex("TYPE")) + ",";
            }
            cursor.close();
            return paramString;
        } catch (SQLException ex) {
            if (ex.toString().toLowerCase().contains("no such table")) {
                this.db.execSQL(DATABASE_CREATE);
                return "RECORD DOES NOT EXIST.";
            }
        }
        return "ERROR!!";
    }

    public String getMultipleEntryWithCode(String paramString1, String paramString2) {
        try {
            Cursor cursor = this.db.query("COLLECTIONS", null, " DATE_SHIFT=? AND CODE=?", new String[]{paramString1, paramString2}, null, null, null);
            if (cursor.getCount() < 1) {
                cursor.close();
                return "RECORD DOES NOT EXIST.";
            }
            cursor.moveToFirst();
            paramString1 = "" + cursor.getString(cursor.getColumnIndex("NAME")) + ",";
            paramString1 = paramString1 + cursor.getString(cursor.getColumnIndex("CODE")) + ",";
            paramString1 = paramString1 + cursor.getString(cursor.getColumnIndex("WEIGHT")) + ",";
            paramString1 = paramString1 + cursor.getString(cursor.getColumnIndex("FAT")) + ",";
            paramString1 = paramString1 + cursor.getString(cursor.getColumnIndex("SNF")) + ",";
            paramString1 = paramString1 + cursor.getString(cursor.getColumnIndex("RATE")) + ",";
            paramString1 = paramString1 + cursor.getString(cursor.getColumnIndex("TYPE")) + ",";
            for (paramString1 = paramString1 + cursor.getString(cursor.getColumnIndex("DATE_SHIFT")); !cursor.isLast(); paramString1 = paramString1 + cursor.getString(cursor.getColumnIndex("DATE_SHIFT"))) {
                cursor.moveToNext();
                paramString1 = paramString1 + ",..,";
                paramString1 = paramString1 + cursor.getString(cursor.getColumnIndex("NAME")) + ",";
                paramString1 = paramString1 + cursor.getString(cursor.getColumnIndex("CODE")) + ",";
                paramString1 = paramString1 + cursor.getString(cursor.getColumnIndex("WEIGHT")) + ",";
                paramString1 = paramString1 + cursor.getString(cursor.getColumnIndex("FAT")) + ",";
                paramString1 = paramString1 + cursor.getString(cursor.getColumnIndex("SNF")) + ",";
                paramString1 = paramString1 + cursor.getString(cursor.getColumnIndex("RATE")) + ",";
                paramString1 = paramString1 + cursor.getString(cursor.getColumnIndex("TYPE")) + ",";
            }
            cursor.close();
            return paramString1;
        } catch (SQLException ex) {
            if (ex.toString().toLowerCase().contains("no such table")) {
                this.db.execSQL(DATABASE_CREATE);
                return "RECORD DOES NOT EXIST.";
            }
        }
        return "ERROR!!";
    }

    public String getSerialNumber() {
        try {
            final Calendar c = Calendar.getInstance();
            String day = ((Integer) c.get(Calendar.DAY_OF_MONTH)).toString();
            if (c.get(Calendar.DAY_OF_MONTH) < 10)
                day = "0" + day;
            String month = ((Integer) (c.get(Calendar.MONTH) + 1)).toString();
            if ((c.get(Calendar.MONTH) + 1) < 10)
                month = "0" + month;
            String year = ((Integer) c.get(Calendar.YEAR)).toString();
            String paramString1 = day + "/" + month + "/" + year + " Morning";
            String paramString2 = day + "/" + month + "/" + year + " Evening";
            Cursor cursor = this.db.query("COLLECTIONS", null, " DATE_SHIFT=? OR DATE_SHIFT=?", new String[]{paramString1, paramString2}, null, null, null);
            if (cursor.getCount() < 1) {
                cursor.close();
                return "1";
            }
            cursor.moveToLast();
            String str = cursor.getString(cursor.getColumnIndex("SERIAL"));
            cursor.close();
            return Integer.valueOf(Integer.parseInt(str) + 1).toString();
        } catch (SQLException localSQLException) {
            if (localSQLException.toString().toLowerCase().contains("no such table")) {
                this.db.execSQL(DATABASE_CREATE);
                return "1";
            }
        }
        return "0";
    }

    public String getSingleEntry(String paramString1, String paramString2) {
        try {
            Cursor cursor = this.db.query("COLLECTIONS", null, " CODE=? AND DATE_SHIFT=?", new String[]{paramString1, paramString2}, null, null, null);
            if (cursor.getCount() < 1) {
                cursor.close();
                return "RECORD DOES NOT EXIST.";
            }
            cursor.moveToFirst();
            paramString2 = "" + cursor.getString(cursor.getColumnIndex("NAME")) + ",";
            paramString2 = paramString2 + cursor.getString(cursor.getColumnIndex("CODE")) + ",";
            paramString2 = paramString2 + cursor.getString(cursor.getColumnIndex("WEIGHT")) + ",";
            paramString2 = paramString2 + cursor.getString(cursor.getColumnIndex("FAT")) + ",";
            paramString2 = paramString2 + cursor.getString(cursor.getColumnIndex("SNF")) + ",";
            paramString2 = paramString2 + cursor.getString(cursor.getColumnIndex("RATE")) + ",";
            paramString2 = paramString2 + cursor.getString(cursor.getColumnIndex("TYPE")) + ",";
            paramString2 = paramString2 + cursor.getString(cursor.getColumnIndex("DATE_SHIFT"));
            cursor.close();
            return paramString2;
        } catch (SQLException ex) {
            if (ex.toString().toLowerCase().contains("no such table")) {
                this.db.execSQL(DATABASE_CREATE);
                return "RECORD DOES NOT EXIST.";
            }
        }
        return "ERROR!!";
    }

    public String insertEntry(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8, String paramString9) {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("SERIAL", paramString1);
        localContentValues.put("NAME", paramString2);
        localContentValues.put("CODE", paramString3);
        localContentValues.put("WEIGHT", paramString4);
        localContentValues.put("FAT", paramString5);
        localContentValues.put("SNF", paramString6);
        localContentValues.put("RATE", paramString7);
        localContentValues.put("TYPE", paramString8);
        localContentValues.put("DATE_SHIFT", paramString9);
        localContentValues.put("SYNC_FLAG", "N");
        try {
            this.db.insertOrThrow("COLLECTIONS", null, localContentValues);
            //Toast.makeText(this.context, "Collection Is Successfully Saved", Toast.LENGTH_SHORT).show();
            return "Success";
        } catch (SQLException localSQLException) {
            while (localSQLException.toString().toLowerCase().contains("no such table")) {
                this.db.execSQL(DATABASE_CREATE);
                insertEntry(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6, paramString7, paramString8, paramString9);
            }
        }
        return "Failed";
    }

    public String updateSyncFlag(String paramString1, String paramString2) {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("SYNC_FLAG", "Y");
        this.db.update("COLLECTIONS", localContentValues, " CODE=? AND DATE_SHIFT=?", new String[]{paramString1, paramString2});
        return "SyncFlag updated Successfully.";
    }

    public CollectionsDBAdapter open()
            throws SQLException {
        this.db = this.dbHelper.getWritableDatabase();
        return this;
    }
}