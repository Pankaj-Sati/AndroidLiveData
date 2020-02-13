package com.example.myapplication.Database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapplication.Database.Contracts.EventDBContract;
import com.example.myapplication.Database.Schema.EventDBSchema;

public class EventsContentProvider extends ContentProvider
{
    EventDatabaseHelper mEventDatabaseHelper;
    @Override
    public boolean onCreate()
    {
        Log.i("ContentProvider","Inside content provider onCreate()");
        mEventDatabaseHelper=new EventDatabaseHelper(getContext(), EventDBSchema.DB_NAME,null,EventDatabaseHelper.CURRENT_VERSION);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder)
    {
        SQLiteDatabase sqLiteDatabase=mEventDatabaseHelper.getReadableDatabase();
        Cursor cursor=null;
        if(uri.compareTo(EventDBContract.EventTableUri.TABLE_URI)==0)
        {
             cursor= sqLiteDatabase.query(EventDBSchema.EventTable.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
             return cursor;
        }

        return cursor; //Return null if URIs don't match
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri)
    {
        return EventDBContract.CONTENT_TYPE;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values)
    {
        Uri newInsertedRowUri=null;
        SQLiteDatabase sqLiteDatabase=mEventDatabaseHelper.getWritableDatabase();
        if(uri.compareTo(EventDBContract.EventTableUri.TABLE_URI)==0)
        {
            long newRowId=sqLiteDatabase.insert(EventDBSchema.EventTable.TABLE_NAME,null,values);

            newInsertedRowUri=Uri.withAppendedPath(uri,newRowId+"");
            return newInsertedRowUri;
        }

        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs)
    {
        int totalRowsDeleted=0;
        SQLiteDatabase sqLiteDatabase=mEventDatabaseHelper.getWritableDatabase();
        if(uri.compareTo(EventDBContract.EventTableUri.TABLE_URI)==0)
        {
            totalRowsDeleted=sqLiteDatabase.delete(EventDBSchema.EventTable.TABLE_NAME,selection,selectionArgs);
            return totalRowsDeleted;
        }
        return totalRowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        int totalRowsUpdated=0;
        SQLiteDatabase sqLiteDatabase=mEventDatabaseHelper.getWritableDatabase();
        if(uri.compareTo(EventDBContract.EventTableUri.TABLE_URI)==0)
        {
            totalRowsUpdated=sqLiteDatabase.update(EventDBSchema.EventTable.TABLE_NAME,values,selection,selectionArgs);
            return totalRowsUpdated;
        }
        return totalRowsUpdated;
    }
}
