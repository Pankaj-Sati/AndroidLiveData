package com.example.myapplication.Database.Contracts;

import android.net.Uri;

import com.example.myapplication.Database.Schema.EventDBSchema;

public class EventDBContract
{
    public static final String AUTHORITY="com.netcarrots.eventManagerAUTHORITY";
    public static final String SCHEMA="content://";
    public static final Uri BASE_URI=Uri.parse(SCHEMA+AUTHORITY);
    public static final String CONTENT_TYPE="event_records";

    public static final class EventTableUri
    {
        public static final Uri TABLE_URI=Uri.withAppendedPath(BASE_URI,EventDBSchema.EventTable.TABLE_NAME);
        public static final class ColumnUri
        {
            public static final Uri EVENT_NAME_URI=Uri.withAppendedPath(TABLE_URI,EventDBSchema.EventTable.Columns.EVENT_NAME);
            public static final Uri EVENT_ID_URI=Uri.withAppendedPath(TABLE_URI,EventDBSchema.EventTable.Columns.EVENT_ID);
            public static final Uri EVENT_DESCRIPTION_URI=Uri.withAppendedPath(TABLE_URI,EventDBSchema.EventTable.Columns.EVENT_DESCRIPTION);
            public static final Uri EVENT_DATE_URI=Uri.withAppendedPath(TABLE_URI,EventDBSchema.EventTable.Columns.EVENT_TIMESTAMP);
        }
    }
}
