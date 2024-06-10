package com.example.soulfm.services;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import java.io.IOException;

public class AudiobookService extends Service {

    private final IBinder binder = new LocalBinder();
    private MediaPlayer mediaPlayer;



    public class LocalBinder extends Binder {
        public AudiobookService getService() {
            return AudiobookService.this;
        }
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
            mediaPlayer.setOnPreparedListener(mp -> {
                listener.onMediaPrepared();
            });
            mediaPlayer.setOnCompletionListener(mp -> {
                listener.onMediaCompleted();
            });
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
    // Trong class AudiobookService
    public void seekTo(int positionInMillis) {
        if (mediaPlayer != null) {
            mediaPlayer.seekTo(positionInMillis);
        }
    }

    // Trong class AudiobookService
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
        stopAndResetPlayer(); // Dừng phát audio hiện tại và reset player
        prepareMediaPlayer(newAudioUrl, listener); // Chuẩn bị phát audio mới
    }
}
