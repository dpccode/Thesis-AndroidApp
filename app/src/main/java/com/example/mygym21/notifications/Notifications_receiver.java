package com.example.mygym21.notifications;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import androidx.core.app.NotificationCompat;

import com.example.mygym21.R;

public class Notifications_receiver extends BroadcastReceiver {

    private String notificationsTitle;
    private String notificationContent;

    @Override
    public void onReceive(Context context, Intent intent) {

        notificationsTitle = "Title";
        notificationContent = " ";

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        NotificationCompat.Builder Builder= new NotificationCompat.Builder(context,"my not");
        notificationsTitle = intent.getStringExtra("Notification title");
        notificationContent = intent.getStringExtra("Notification Content");
        String text = intent.getStringExtra("text");
        Builder.setColor(Color.YELLOW);
        Builder.setSmallIcon(R.drawable.abs);

        Builder.setAutoCancel(true);

        if(notificationContent.equals("WorkOutTime")){
            Builder.setContentTitle(notificationsTitle);
            Builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.gains));
            notificationManager.notify(100,Builder.build());
        }

        if(notificationContent.equals("PreWorkOut")){
            Builder.setContentTitle(notificationsTitle);
            Builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.shaker));

            notificationManager.notify(101,Builder.build());
        }

        if(notificationContent.equals("LastPreWorkOutMeal")){
            Builder.setContentTitle(notificationsTitle);
            Builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.lastmeal1));

            notificationManager.notify(102,Builder.build());
        }

        if(notificationContent.equals("PostWorkOut")){
            Builder.setContentTitle(notificationsTitle);
            Builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(),
                    R.drawable.shaker));

            notificationManager.notify(103,Builder.build());
        }

    }
}
