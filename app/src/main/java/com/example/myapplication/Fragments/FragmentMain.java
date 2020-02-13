package com.example.myapplication.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.Models.Event;
import com.example.myapplication.R;
import com.example.myapplication.Utilities.EventLab;
import com.example.myapplication.Utilities.EventsLiveData;
import com.example.myapplication.Utilities.NotificationLab;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Date;
import java.util.List;
import java.util.Random;

public class FragmentMain extends Fragment
{
    RecyclerView mRecyclerView;
    ProgressBar mProgressBar; //To show a spinning progressbar
    FloatingActionButton mFABAddEvent;

    List<Event> mEventList;
    EventLab mEventLab;
    NotificationLab mNotificationLab;

    EventsLiveData mEventsLiveData;
    public static Fragment createNewInstance(Context context)
    {
        return new FragmentMain();
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mEventLab=EventLab.getInstance(getActivity());
        mEventList=mEventLab.getAllEvents();
        mNotificationLab=new NotificationLab(getActivity());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        View view=inflater.inflate(R.layout.fragment_main,container,false);

        mRecyclerView=view.findViewById(R.id.RV_Event_List);
        mProgressBar=view.findViewById(R.id.PB_Event_List);
        mFABAddEvent=view.findViewById(R.id.FAB_Add_Event);

        mProgressBar.setVisibility(View.VISIBLE);

        mFABAddEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                mProgressBar.setVisibility(View.VISIBLE);
                Event event=new Event();
                int randInt= new Random().nextInt(5000);
                event.setmEventName("Event "+randInt);
                event.setmEventDate(new Date());
                event.setmEventDescription("Description "+randInt);

                try
                {
                   mEventsLiveData.addEvent(event);
                    mNotificationLab.createEventAddedNotification(event);

                } catch (Exception e)
                {
                    e.printStackTrace();
                    Toast.makeText(getActivity(),"Falied to add event",Toast.LENGTH_SHORT).show();
                }
            }
        });

        initializeRecyclerView();


        mRecyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                Log.d("Test","RecyclerView onLayoutChange event fired");
                mProgressBar.setVisibility(View.GONE);
            }
        });


        mEventsLiveData=new EventsLiveData(this.getActivity().getApplication());

        Observer<List<Event>> eventListObserver=new Observer<List<Event>>() {
            @Override
            public void onChanged(List<Event> events)
            {

                Log.d("Test","onChanged="+events.size());
                mEventList=events;
                initializeRecyclerView();
            }
        };

        mEventsLiveData.getEvents().observe(this,eventListObserver);

        return view;
    }

    private void initializeRecyclerView()
    {
        if(mRecyclerView.getAdapter()==null)
        {
            mRecyclerView.setAdapter(new AdapterEvents(getActivity()));
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL,false));
        }
        else
        {
            Log.d("Test","notifyDataSetChanged() fired");
            mRecyclerView.getAdapter().notifyDataSetChanged(); //Cause the recycler view to reload the data and display all again
        }
    }

    public class ViewHolderEvents extends RecyclerView.ViewHolder
    {

        private static final String LOG_TAG="ViewHolderEvents";

        TextView vTVEventName;
        ImageButton vIBDelete;
        Event vEvent; //Event object that we are currently dealing with
        EventLab vEventLab;

        public ViewHolderEvents(@NonNull View itemView, final Context context)
        {
            super(itemView);
            vEventLab=EventLab.getInstance(context);
            vTVEventName= itemView.findViewById(R.id.TV_Event_Name);
            vIBDelete=itemView.findViewById(R.id.IB_Delete);

            vIBDelete.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Log.d(LOG_TAG,"Delete Clicked");
                    mEventsLiveData.deleteEvent(vEvent);
                }
            });
        }

        public void bindDataToView(Event event)
        {
            vEvent=event;
            vTVEventName.setText(event.getmEventName());

        }
    }


    public class AdapterEvents extends RecyclerView.Adapter<ViewHolderEvents>
    {

        Context aContext;
        public AdapterEvents(Context context)
        {
            aContext=context;

            Log.d("Test","AdapterEvents() fired");
        }



        @NonNull
        @Override
        public ViewHolderEvents onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            View view= LayoutInflater.from(aContext).inflate(R.layout.rv_event_item,parent,false);

            return new ViewHolderEvents(view,aContext);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolderEvents holder, int position)
        {
            holder.bindDataToView(mEventList.get(position));

        }

        @Override
        public int getItemCount()
        {
            if(mEventList==null)
            {
                return 0;
            }
            return mEventList.size();
        }
    }

}
