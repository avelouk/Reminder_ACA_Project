package com.reminder.mobile_activities.services;

import android.Manifest;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;

import com.reminder.R;
import com.reminder.mobile_activities.CallActivity;

/**
 * Created by Armen on 20.03.2016.
 */
public class CallService extends IntentService {

    String number;

    public CallService() {
        super(CallService.class.getName());
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        number = intent.getExtras().getString("n");
        createNotification(number);
    }

    public void createNotification(String s) {

        Intent intent = new Intent(CallService.this, NotifyActivityHandler.class);
        intent.putExtra("do_action", "c");
        intent.putExtra("n", number);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, (int) System.currentTimeMillis(), intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification noti = new Notification.Builder(this)
                .setContentTitle("Your scheduled call is ready")
                .setContentText("You scheduled call to " + s)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .addAction(R.mipmap.ic_launcher, "Call", pendingIntent)
                .build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        noti.flags |= Notification.FLAG_AUTO_CANCEL;
        noti.defaults |= Notification.DEFAULT_SOUND;
        noti.defaults |= Notification.DEFAULT_VIBRATE;

        notificationManager.notify(0, noti);

    }
}