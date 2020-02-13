package com.example.myapplication.Utilities;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import com.example.myapplication.Database.Contracts.EventDBContract;
import com.example.myapplication.Database.EventsCursorWrapper;
import com.example.myapplication.Database.Schema.EventDBSchema;
import com.example.myapplication.Models.Event;

import java.util.ArrayList;
import java.util.List;

public class EventLab
{

    Context mContext;
    EventManupulationCallbacks mEventManupulationCallbacks; //To listen to changes in Event related data passed by different components

    public static EventLab getInstance(Context context)
    {
       return new EventLab(context);
    }
    private EventLab(Context context)
    {
        mContext=context;
    }

    public void setEventCallbacks(EventManupulationCallbacks eventManupulationCallbacks)
    {
        mEventManupulationCallbacks=eventManupulationCallbacks;
    }

    public List<Event> getAllEvents()
    {
        List<Event> eventList=new ArrayList<>();
        Cursor cursor= mContext.getContentResolver().query(EventDBContract.EventTableUri.TABLE_URI,null,null,null,null);
        if(cursor==null)
        {
            return null;
        }
        EventsCursorWrapper eventsCursorWrapper=new EventsCursorWrapper(cursor);

        while (eventsCursorWrapper.moveToNext())
        {
            eventList.add(eventsCursorWrapper.getEventFromCursor());
        }
        return eventList;
    }

    public void addEvent(Event event) throws Exception {
        if(event==null)
        {
            return;
        }
        Uri insertRecordUri=mContext.getContentResolver().insert(EventDBContract.EventTableUri.TABLE_URI,makeContentValues(event));

        if(insertRecordUri==null)
        {
            throw new Exception("Insert Event Failed");
        }
    }

    public int deleteEvent(String eventId)
    {
        //Returns number of deleted rows
        if(eventId==null)
        {
            return 0;
        }

        return mContext.getContentResolver().delete(EventDBContract.EventTableUri.TABLE_URI, EventDBSchema.EventTable.Columns.EVENT_ID+"=?",new String[]{eventId});
    }

    public EventManupulationCallbacks getEventCallbacks()
    {
        return mEventManupulationCallbacks;
    }

    private ContentValues makeContentValues(Event event)
    {
        ContentValues contentValues=new ContentValues();
        contentValues.put(EventDBSchema.EventTable.Columns.EVENT_ID,event.getmEventId().toString());
        contentValues.put(EventDBSchema.EventTable.Columns.EVENT_NAME,event.getmEventName());
        contentValues.put(EventDBSchema.EventTable.Columns.EVENT_DESCRIPTION,event.getmEventDescription());
        contentValues.put(EventDBSchema.EventTable.Columns.EVENT_TIMESTAMP,event.getmEventDate().getTime());
        return contentValues;
    }


}
