package com.cmos.adpu;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class SettingsDBAdapter
{
    static String DATABASE_CREATE = "create table SETTINGS (ID integer primary key autoincrement, NAME text, VALUE text);";
    static final String DATABASE_NAME = "adpu.db";
    static final int DATABASE_VERSION = 1;
    private final Context context;
    public SQLiteDatabase db;
    private DataBaseHelper dbHelper;
    Integer i = 0;

    public SettingsDBAdapter(Context context)
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
            Cursor cursor = this.db.query("SETTINGS", null, null, null, null, null, null);
            cursor.getCount();
            cursor.moveToFirst();
            cursor.close();
        }
        catch (SQLException localSQLException)
        {
            //Toast.makeText(this.context, "Error!!", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteMembers(String paramString)
    {
        int i = this.db.delete("SETTINGS", "NAME=?", new String[] { paramString });
        Toast.makeText(this.context, "Records deleted: " + i, Toast.LENGTH_SHORT).show();
    }

    public void delete_table()
    {
        this.db.execSQL("DROP TABLE IF EXISTS SETTINGS");
        Toast.makeText(this.context, "Droped Successfully", Toast.LENGTH_SHORT).show();
    }

    public SQLiteDatabase getDatabaseInstance()
    {
        return this.db;
    }

    public String getSingleEntry(String paramString1)
    {
        String paramString2;
        try
        {
            Cursor cursor = this.db.query("SETTINGS", null, "NAME=?", new String[] { paramString1 }, null, null, null);
            if (cursor.getCount() < 1)
            {
                cursor.close();
                return "RECORD DOES NOT EXIST.";
            }
            cursor.moveToFirst();
            paramString2 = cursor.getString(cursor.getColumnIndex("VALUE"));
            cursor.close();
            return paramString2;
        }
        catch (SQLException ex)
        {
            if (ex.toString().toLowerCase().contains("no such table"))
            {
                this.db.execSQL(DATABASE_CREATE);
                return "no such table";
            }
        }
        return "ERROR!!";
    }

    public String updateEntry(String paramString1, String paramString2)
    {
        String result = getSingleEntry(paramString1);
        if(result.equals("RECORD DOES NOT EXIST.") || result.equals("ERROR!!"))
                insertEntry(paramString1, paramString2);
        else {
            ContentValues localContentValues = new ContentValues();
            localContentValues.put("NAME", paramString1);
            localContentValues.put("VALUE", paramString2);
            this.db.update("SETTINGS", localContentValues, "NAME=?", new String[] { paramString1 });
        }
        return "";
    }

    public String insertEntry(String name, String value)
    {
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("NAME", name);
        localContentValues.put("VALUE", value);
        try
        {
            this.db.insertOrThrow("SETTINGS", null, localContentValues);
            //Toast.makeText(this.context, "Collection Is Successfully Saved", Toast.LENGTH_SHORT).show();
            return "Success";
        }
        catch (SQLException localSQLException)
        {
            if (localSQLException.toString().toLowerCase().contains("no such table"))
            {
                this.db.execSQL(DATABASE_CREATE);
                insertEntry(name, value);
            }
        }
        return "Failed";
    }

    public SettingsDBAdapter open()
            throws SQLException
    {
        this.db = this.dbHelper.getWritableDatabase();
        return this;
    }
}