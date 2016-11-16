package com.cmos.adpu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class MembersDBAdapter
{
    static String DATABASE_CREATE = "create table MEMBERS (ID integer primary key autoincrement,CODE text, NAME text, MOBILE text, EMAIL text, IMAGE text, SYNC_FLAG text);";
    static final String DATABASE_NAME = "adpu.db";
    static final int DATABASE_VERSION = 1;
    private final Context context;
    public SQLiteDatabase db;
    private DataBaseHelper dbHelper;

    public MembersDBAdapter(Context context)
    {
        this.context = context;
        this.dbHelper = new DataBaseHelper(this.context, "adpu.db", null, 1);
    }

    public void close()
    {
        this.db.close();
    }

    public void count()
    {
        try
        {
            Cursor cursor = this.db.query("MEMBERS", null, null, null, null, null, null);
            cursor.getCount();
            cursor.moveToFirst();
            cursor.close();
        }
        catch (SQLException localSQLException)
        {
            Toast.makeText(this.context, "Error!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteMembers(String paramString)
    {
        int i = this.db.delete("MEMBERS", "ID=?", new String[] { paramString });
        Toast.makeText(this.context, "Records deleted: " + i, Toast.LENGTH_SHORT).show();
    }

    public void delete_table()
    {
        this.db.execSQL("DROP TABLE IF EXISTS MEMBERS");
    }

    public SQLiteDatabase getDatabaseInstance()
    {
        return this.db;
    }

    public String getCode()
    {
        try
        {
            Cursor cursor = this.db.query("MEMBERS", null, null, null, null, null, null);
            if (cursor.getCount() < 1)
            {
                cursor.close();
                return "1";
            }
            cursor.moveToLast();
            String str = cursor.getString(cursor.getColumnIndex("CODE"));
            cursor.close();
            return Integer.valueOf(Integer.parseInt(str) + 1).toString();
        }
        catch (SQLException localSQLException)
        {
            if (localSQLException.toString().toLowerCase().contains("no such table"))
            {
                this.db.execSQL(DATABASE_CREATE);
                return "1";
            }
        }
        return "0";
    }

    public String getSingleEntry(String paramString1)
    {
        try
        {
            Cursor cursor = this.db.query("MEMBERS", null, "CODE=?", new String[] { paramString1 }, null, null, null);
            if (cursor.getCount() < 1)
            {
                cursor.close();
                return "RECORD DOES NOT EXIST.";
            }
            cursor.moveToFirst();
            paramString1 = "";
            paramString1 = paramString1 + cursor.getString(cursor.getColumnIndex("CODE")) + ",";
            paramString1 = paramString1 + cursor.getString(cursor.getColumnIndex("NAME")) + ",";
            paramString1 = paramString1 + cursor.getString(cursor.getColumnIndex("MOBILE")) + ",";
            paramString1 = paramString1 + cursor.getString(cursor.getColumnIndex("EMAIL")) + ",";
            paramString1 = paramString1 + cursor.getString(cursor.getColumnIndex("IMAGE"));
            cursor.close();
            return paramString1;
        }
        catch (SQLException ex)
        {
            if (ex.toString().toLowerCase().contains("no such table"))
            {
                this.db.execSQL(DATABASE_CREATE);
                return "RECORD DOES NOT EXIST.";
            }
        }
        return "ERROR!!";
    }

    public String updateEntry(String code, String name, String mobile, String email, String image)
    {
        String result = getSingleEntry(code);
        if(result.equals("RECORD DOES NOT EXIST.") || result.equals("ERROR!!"))
            return insertEntry(code, name, mobile, email, image);
        else {
            ContentValues localContentValues = new ContentValues();
            localContentValues.put("CODE", code);
            localContentValues.put("NAME", name);
            localContentValues.put("MOBILE", mobile);
            localContentValues.put("EMAIL", email);
            localContentValues.put("IMAGE", image);
            this.db.update("MEMBERS", localContentValues, "CODE=?", new String[] { code });
            return "Member updated Successfully.";
        }
    }

    public String insertEntry(String code, String name, String mobile, String email, String image)
    {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("CODE", code);
        localContentValues.put("NAME", name);
        localContentValues.put("MOBILE", mobile);
        localContentValues.put("EMAIL", email);
        localContentValues.put("IMAGE", image);
        localContentValues.put("SYNC_FLAG", "N");
        try
        {
            this.db.insertOrThrow("MEMBERS", null, localContentValues);
            return "Member added Successfully.";
        }
        catch (SQLException localSQLException)
        {
            while (localSQLException.toString().toLowerCase().contains("no such table"))
            {
                this.db.execSQL(DATABASE_CREATE);
                insertEntry(code, name, mobile, email, image);
            }
        }
        return "Failed";
    }

    public String updateSyncFlag(String paramString1)
    {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("SYNC_FLAG", "Y");
        this.db.update("MEMBERS", localContentValues, "CODE=?", new String[]{paramString1});
        return "SyncFlag updated Successfully.";
    }

    public MembersDBAdapter open()
            throws SQLException
    {
        this.db = this.dbHelper.getWritableDatabase();
        return this;
    }
}