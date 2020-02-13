package com.example.myapplication.Models;

/*
    * This is the model class of Event Object
 */

import java.util.Date;
import java.util.UUID;

public class Event
{
    private String mEventName;
    private String mEventDescription;
    private Date mEventDate;
    private UUID mEventId;


    public Event()
    {

        mEventId=UUID.randomUUID();

    }

    public String getmEventName()
    {
        return mEventName;
    }

    public void setmEventName(String mEventName) {
        this.mEventName = mEventName;
    }

    public String getmEventDescription() {
        return mEventDescription;
    }

    public void setmEventDescription(String mEventDescription) {
        this.mEventDescription = mEventDescription;
    }

    public Date getmEventDate() {
        return mEventDate;
    }

    public void setmEventDate(Date mEventDate) {
        this.mEventDate = mEventDate;
    }

    public UUID getmEventId() {
        return mEventId;
    }

    public void setmEventId(UUID mEventId) {
        this.mEventId = mEventId;
    }
}
