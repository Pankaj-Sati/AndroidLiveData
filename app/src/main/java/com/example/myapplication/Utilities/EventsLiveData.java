package com.example.myapplication.Utilities;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.Models.Event;

import java.util.List;

public class EventsLiveData extends AndroidViewModel
{
    EventLab mEventLab;

    MutableLiveData<List<Event>> mEventsData;
    public EventsLiveData(@NonNull Application application)
    {

        super(application);
        mEventLab=EventLab.getInstance(application);
        Log.i("Test","Inside Oncreate EventsLIveData");
    }

    public  MutableLiveData<List<Event>>  getEvents()
    {
        if(mEventsData==null)
        {
            mEventsData=new MutableLiveData<>();
            mEventsData.setValue(mEventLab.getAllEvents());
        }

        return mEventsData;
    }

    public void addEvent(Event event) throws Exception {
        try
        {
            mEventLab.addEvent(event);
            mEventsData.setValue(mEventLab.getAllEvents());
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new Exception("ffa");

        }
    }

    public void deleteEvent(Event event)
    {
        mEventLab.deleteEvent(event.getmEventId().toString());
        mEventsData.setValue(mEventLab.getAllEvents());
    }
}
