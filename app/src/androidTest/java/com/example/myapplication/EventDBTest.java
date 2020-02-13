package com.example.myapplication;

import android.app.Instrumentation;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.example.myapplication.Database.Contracts.EventDBContract;
import com.example.myapplication.Database.EventDatabaseHelper;
import com.example.myapplication.Database.EventsContentProvider;
import com.example.myapplication.Database.EventsCursorWrapper;
import com.example.myapplication.Database.Schema.EventDBSchema;
import com.example.myapplication.Models.Event;
import com.example.myapplication.Utilities.EventLab;

import org.json.JSONObject;
import org.json.JSONStringer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Date;

@RunWith(AndroidJUnit4.class)
public class EventDBTest
{
    private static final String LOG_TAG="Events Test";
    Context context;
    EventLab mEventLab;

    @Before
    public void init()
    {

        context=InstrumentationRegistry.getInstrumentation().getTargetContext();
        mEventLab=EventLab.getInstance(context);
    }

    @Test
    public void getAllRecords()
    {
       Cursor cursor= context.getContentResolver().query(EventDBContract.EventTableUri.TABLE_URI,null,null,null,null);
        EventsCursorWrapper eventsCursorWrapper=new EventsCursorWrapper(cursor);
        while (eventsCursorWrapper.moveToNext())
        {
            Log.i(LOG_TAG, eventsCursorWrapper.getEventFromCursor().toString());
        }
    }

    @Test
    public void getAllEventDirectly()
    {
        SQLiteDatabase sqLiteDatabase=new EventDatabaseHelper(context, EventDBSchema.DB_NAME,null,EventDatabaseHelper.CURRENT_VERSION).getReadableDatabase();
        Cursor cursor= sqLiteDatabase.query(EventDBSchema.EventTable.TABLE_NAME,null,null,null,null,null,null);
        Log.e(LOG_TAG,cursor+"");
        EventsCursorWrapper eventsCursorWrapper=new EventsCursorWrapper(cursor);
        while (eventsCursorWrapper.moveToNext())
        {
            Log.i(LOG_TAG, eventsCursorWrapper.getEventFromCursor().getmEventName()+"");
        }
    }

    @Test
    public void insertEvent()
    {
        Event event=new Event();
        event.setmEventName("Event1");
        event.setmEventDate(new Date());
        event.setmEventDescription("Description of Event 1");

        try
        {
            mEventLab.addEvent(event);
        } catch (Exception e)
        {
            e.printStackTrace();

        }


    }

}
