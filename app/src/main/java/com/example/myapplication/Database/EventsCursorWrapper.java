package com.example.myapplication.Database;

import android.database.Cursor;
import android.database.CursorWrapper;
import android.util.Log;

import com.example.myapplication.Database.Schema.EventDBSchema;
import com.example.myapplication.Models.Event;

import java.util.Date;
import java.util.UUID;

public class EventsCursorWrapper extends CursorWrapper
{


    /**
     * Creates a cursor wrapper.
     *
     * @param cursor The underlying cursor to wrap.
     */
    public EventsCursorWrapper(Cursor cursor)
    {
        super(cursor);
    }

    public Event getEventFromCursor()
    {

        String eName=getString(getColumnIndex(EventDBSchema.EventTable.Columns.EVENT_NAME));
        String eId=getString(getColumnIndex(EventDBSchema.EventTable.Columns.EVENT_ID));
        long eLocation=getLong(getColumnIndex(EventDBSchema.EventTable.Columns.EVENT_TIMESTAMP));
        String eDescription=getString(getColumnIndex(EventDBSchema.EventTable.Columns.EVENT_DESCRIPTION));
        Event event=new Event();
        event.setmEventId(UUID.fromString(eId));
        event.setmEventName(eName);
        event.setmEventDate(new Date(eLocation));
        event.setmEventDescription(eDescription);
        return event;
    }
}
