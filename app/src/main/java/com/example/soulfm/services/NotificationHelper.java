package com.example.soulfm.services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.soulfm.R;
import com.example.soulfm.activities.BookPlayerActivity; // Update this import with the correct path

public class NotificationHelper {

    private static final String CHANNEL_ID = "AudiobookServiceChannel";
    private static final int NOTIFICATION_ID = 1;

    public static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Audiobook Service Channel",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel.setDescription("Channel for Audiobook service");
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
        }
    }

    public static NotificationCompat.Builder createNotificationBuilder(Context context, PendingIntent playIntent, PendingIntent pauseIntent, PendingIntent nextIntent, PendingIntent previousIntent, PendingIntent stopIntent) {
        Intent notificationIntent = new Intent(context, BookPlayerActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        return new NotificationCompat.Builder(context, CHANNEL_ID)
                .setContentTitle("Audiobook Player")
                .setContentText("Playing audiobook")
                .setSmallIcon(R.drawable.bookmark)
                .setContentIntent(contentIntent) // This makes the notification open the BookPlayerActivity
//                .addAction(R.drawable.previous, "Previous", previousIntent)
                .addAction(R.drawable.play_button, "Play", playIntent)
                .addAction(R.drawable.pause, "Pause", pauseIntent)
//                .addAction(R.drawable.next, "Next", nextIntent)
//                .addAction(R.drawable.close, "Stop", stopIntent)
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .setAutoCancel(true); // Allow notification to be dismissed with swipe
    }
}
