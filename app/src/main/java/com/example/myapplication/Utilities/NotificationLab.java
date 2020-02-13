package com.example.myapplication.Utilities;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.myapplication.Models.Event;
import com.example.myapplication.R;

public class NotificationLab
{
    Context mContext;
    private static final String CHANNEL_ID="myEventNotification";
    private static final int ADD_EVENT_NOTIFICATION_ID=1; //ID for showing add event notification

    public NotificationLab(Context context)
    {
        mContext=context;
        initNotificationChannel(); //Notification Channel must be created before showing the notification
    }

    public void initNotificationChannel()
    {
        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.O)
        {
            String notificationName=mContext.getString(R.string.event_add_notiification_title);
            String notificationDescription=mContext.getString(R.string.event_notification_channel_description);
            NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_ID,notificationName, NotificationManager.IMPORTANCE_DEFAULT);
            notificationChannel.setDescription(notificationDescription);

            NotificationManager notificationManager= (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel); //Channel is added only once. SO do it at start
        }

    }

    public void createEventAddedNotification(Event event)
    {
        NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(mContext,CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_input_add)
                .setContentTitle(mContext.getString(R.string.event_add_notiification_title))
                .setContentText(mContext.getString(R.string.event_add_notiification_text))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Notification notification= notificationBuilder.build();

        NotificationManagerCompat notificationManagerCompat=NotificationManagerCompat.from(mContext);
        notificationManagerCompat.notify(ADD_EVENT_NOTIFICATION_ID,notification);


    }

}
