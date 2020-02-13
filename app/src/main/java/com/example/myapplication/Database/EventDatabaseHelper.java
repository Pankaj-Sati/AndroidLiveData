package com.example.myapplication.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.myapplication.Database.Schema.EventDBSchema;

public class EventDatabaseHelper extends SQLiteOpenHelper
{
    public static final int CURRENT_VERSION=1; //Holding latest DB version number
    public EventDatabaseHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {

        db.execSQL("CREATE TABLE IF NOT EXISTS "+ EventDBSchema.EventTable.TABLE_NAME+"(id INTEGER PRIMARY KEY AUTOINCREMENT,"
                +EventDBSchema.EventTable.Columns.EVENT_ID + " TEXT NOT NULL,"
                + EventDBSchema.EventTable.Columns.EVENT_NAME + " TEXT NOT NULL,"
                + EventDBSchema.EventTable.Columns.EVENT_DESCRIPTION + " TEXT,"
                + EventDBSchema.EventTable.Columns.EVENT_TIMESTAMP + " REAL NOT NULL"
                +")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {

    }
}
