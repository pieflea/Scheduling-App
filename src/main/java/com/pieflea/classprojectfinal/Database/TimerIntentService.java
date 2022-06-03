package com.pieflea.classprojectfinal.Database;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.pieflea.classprojectfinal.Models.TimerModel;
import com.pieflea.classprojectfinal.R;

public class TimerIntentService extends IntentService {

    private String channelTimer = "Channel Timer";
    private String notificationTitle;
    private String notificationDescription;


    public TimerIntentService() {
        super("TimerIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        // Get millis from the activity and start a new TimerModel
        long millisLeft = intent.getLongExtra("timeLeft", 0);
        notificationTitle = intent.getStringExtra("notificationTitle");
        notificationDescription = intent.getStringExtra("notificationText");
        TimerModel timerModel = new TimerModel();
        timerModel.start(millisLeft);
        Log.i("Working?", "onHandleIntent: called.");

        // Create notification channel
        createTimerNotificationChannel("Course/Assessment Channel Notification", "Channel for notifying the user about upcoming courses or assessments");


        while (timerModel.isRunning()) {
            try {
                createTimerNotification(notificationDescription, notificationTitle);
                Thread.sleep(1000);

                if (timerModel.getRemainingMilliseconds() == 0) {
                    timerModel.stop();
                    createTimerNotification(notificationDescription, notificationTitle);
                }
            } catch (InterruptedException e) {
            }
        }
    }

    private void createTimerNotificationChannel(String channelName, String channelDescription) {
        if (Build.VERSION.SDK_INT < 26){
            return;
        }
        CharSequence name = channelName;
        String description = channelDescription;
        int importance = NotificationManager.IMPORTANCE_MIN;
        NotificationChannel channel = new NotificationChannel(channelTimer, name, importance);
        channel.setDescription(description);

        // Register channel with system
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    private final int NOTIFICATION_ID = 0;
    private void createTimerNotification(String text, String Title) {
        Notification notification = new NotificationCompat.Builder(this, channelTimer)
                .setSmallIcon(android.R.drawable.ic_dialog_info)
                .setContentTitle(Title)
                .setContentText(text)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        notificationManager.notify(NOTIFICATION_ID, notification);
    }
}
