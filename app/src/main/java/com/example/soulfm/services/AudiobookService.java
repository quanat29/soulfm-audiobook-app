package com.example.soulfm.services;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

import com.example.soulfm.R;

import java.io.IOException;

public class AudiobookService extends Service {

    private final IBinder binder = new LocalBinder();
    private MediaPlayer mediaPlayer;
    private static final String CHANNEL_ID = "AudiobookServiceChannel";
    private static final int NOTIFICATION_ID = 1;

    public class LocalBinder extends Binder {
        public AudiobookService getService() {
            return AudiobookService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        NotificationHelper.createNotificationChannel(this);
        setupNotification();
    }

    private void setupNotification() {
        Intent playIntent = new Intent(this, AudiobookService.class);
        playIntent.setAction("PLAY");
        PendingIntent playPendingIntent = PendingIntent.getService(this, 0, playIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent pauseIntent = new Intent(this, AudiobookService.class);
        pauseIntent.setAction("PAUSE");
        PendingIntent pausePendingIntent = PendingIntent.getService(this, 0, pauseIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent nextIntent = new Intent(this, AudiobookService.class);
        nextIntent.setAction("NEXT");
        PendingIntent nextPendingIntent = PendingIntent.getService(this, 0, nextIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent previousIntent = new Intent(this, AudiobookService.class);
        previousIntent.setAction("PREVIOUS");
        PendingIntent previousPendingIntent = PendingIntent.getService(this, 0, previousIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Intent stopIntent = new Intent(this, AudiobookService.class);
        stopIntent.setAction("STOP");
        PendingIntent stopPendingIntent = PendingIntent.getService(this, 0, stopIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = NotificationHelper.createNotificationBuilder(
                this, playPendingIntent, pausePendingIntent, nextPendingIntent, previousPendingIntent, stopPendingIntent
        );

        startForeground(NOTIFICATION_ID, notificationBuilder.build());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        if (action != null) {
            switch (action) {
                case "PLAY":
                    playAudio();
                    break;
                case "PAUSE":
                    pauseAudio();
                    break;
                case "STOP":
                    stopForeground(true);
                    stopSelf();
                    break;
            }
        }
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void prepareMediaPlayer(String audioUrl, OnMediaPreparedListener listener) {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }

        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(audioUrl);
            mediaPlayer.prepareAsync();
            mediaPlayer.setOnPreparedListener(mp -> listener.onMediaPrepared());
            mediaPlayer.setOnCompletionListener(mp -> listener.onMediaCompleted());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void playAudio() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    public void pauseAudio() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    public void resumeAudio() {
        if (mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    public int getCurrentPosition() {
        if (mediaPlayer != null) {
            return mediaPlayer.getCurrentPosition();
        }
        return 0;
    }

    public boolean isPlaying() {
        return mediaPlayer != null && mediaPlayer.isPlaying();
    }

    public interface OnMediaPreparedListener {
        void onMediaPrepared();
        void onMediaCompleted();
    }

    public void seekTo(int positionInMillis) {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(positionInMillis);
        }
    }

    public int getDuration() {
        if (mediaPlayer != null) {
            return mediaPlayer.getDuration();
        }
        return 0;
    }

    public void stopAndResetPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    public void playNewAudio(String newAudioUrl, OnMediaPreparedListener listener) {
        stopAndResetPlayer();
        prepareMediaPlayer(newAudioUrl, listener);
    }

    public void stopService() {
        stopAndResetPlayer();
        stopForeground(true);
        stopSelf();
    }
}
