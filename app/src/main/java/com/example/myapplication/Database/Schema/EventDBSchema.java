package com.example.myapplication.Database.Schema;

public class EventDBSchema
{
    public static final String DB_NAME="event_db";

    public static final class EventTable
    {
        public static final String TABLE_NAME="event_table";
        public static final class Columns
        {
            public static final String EVENT_NAME="e_name";
            public static final String EVENT_TIMESTAMP="e_timestamp"; //Will contain the timestamp i.e. seconds from Unix epoch to date of event
            public static final String EVENT_DESCRIPTION="e_description";
            public static final String EVENT_ID="e_id";  //Unique Event ID (UUID)
        }
    }
}
