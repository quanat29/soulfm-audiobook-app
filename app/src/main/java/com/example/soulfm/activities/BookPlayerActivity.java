package com.example.soulfm.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.example.soulfm.R;
import com.example.soulfm.api.ApiBookChapter;
import com.example.soulfm.chapter.Chapter;
import com.example.soulfm.services.AudiobookService;
import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BookPlayerActivity extends AppCompatActivity {

    private ImageView iv_image_book_player, iv_btn_down;
    private TextView tv_name_chapter_player, tv_seekbar_time;
    private SeekBar sb_play;
    private ImageView iv_btn_back_15s, iv_btn_previous, iv_btn_play, iv_btn_next, iv_btn_next_15s;
    private int idBook;
    private Handler handler = new Handler();
    private boolean isUserSeeking = false;
    private String audioUrl;
    private ProgressBar progressBar;
    private List<Chapter> chapters = new ArrayList<>();
    private int currentChapterIndex = 0;
    private AudiobookService audiobookService;
    private boolean isServiceBound = false;
    private boolean playFirstEpisode = false;
    private int currentChapter;

    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            AudiobookService.LocalBinder binder = (AudiobookService.LocalBinder) service;
            audiobookService = binder.getService();
            isServiceBound = true;
            if (playFirstEpisode) {
                getFirstEpisode(idBook);
            } else {
                if (audioUrl != null && !audioUrl.isEmpty()) {
                    getChapter(idBook);
                } else {
                    Toast.makeText(BookPlayerActivity.this, "Không có dữ liệu audio", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            // Check and use saved time position
            int savedTimePosition = getCurrentTimePosition();
            if (savedTimePosition > 0 && audiobookService != null) {
                audiobookService.seekTo(savedTimePosition);
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isServiceBound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_player);

        iv_image_book_player = findViewById(R.id.iv_image_book_player);
        iv_btn_down = findViewById(R.id.iv_btn_down);
        tv_name_chapter_player = findViewById(R.id.tv_name_chapter_player);
        sb_play = findViewById(R.id.sb_play);
        iv_btn_back_15s = findViewById(R.id.iv_btn_back_15s);
        iv_btn_previous = findViewById(R.id.iv_btn_previous);
        iv_btn_play = findViewById(R.id.iv_btn_play);
        iv_btn_next = findViewById(R.id.iv_btn_next);
        iv_btn_next_15s = findViewById(R.id.iv_btn_next_15s);
        tv_seekbar_time = findViewById(R.id.tv_seekbar_time);
        progressBar = findViewById(R.id.progressBar);


        Intent intent = getIntent();
        idBook = intent.getIntExtra("id_book", -1);
        playFirstEpisode = intent.getBooleanExtra("play_first_episode", false);
        audioUrl = intent.getStringExtra("audioUrl");
        currentChapter = intent.getIntExtra("chapterIndex", 0);

        Intent serviceIntent = new Intent(this, AudiobookService.class);
        bindService(serviceIntent, serviceConnection, BIND_AUTO_CREATE);

        iv_btn_play.setOnClickListener(view -> {
            if (audiobookService != null && audiobookService.isPlaying()) {
                pauseAudio();
            } else {
                playAudio();
            }
        });

        sb_play.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b && audiobookService != null) {
                    audiobookService.seekTo(i * 1000);
                }
                tv_seekbar_time.setText(formatTime(i));
                updateSeekBarTimePosition(seekBar);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isUserSeeking = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                isUserSeeking = false;
                if (audiobookService != null) {
                    audiobookService.seekTo(seekBar.getProgress() * 1000);
                }
            }
        });

        iv_btn_next.setOnClickListener(view -> {
            if (currentChapterIndex < chapters.size() - 1) {
                currentChapterIndex++;
                loadChapter(currentChapterIndex);
            } else {
                Toast.makeText(BookPlayerActivity.this, "Đây là chương cuối cùng", Toast.LENGTH_SHORT).show();
            }
        });

        iv_btn_previous.setOnClickListener(view -> {
            if (currentChapterIndex > 0) {
                currentChapterIndex--;
                loadChapter(currentChapterIndex);
            } else {
                Toast.makeText(BookPlayerActivity.this, "Đây là chương đầu tiên", Toast.LENGTH_SHORT).show();
            }
        });

        iv_btn_next_15s.setOnClickListener(view -> {
            if (audiobookService != null) {
                int currentPosition = audiobookService.getCurrentPosition();
                int duration = audiobookService.getDuration();
                int seekPosition = Math.min(currentPosition + 15000, duration);
                audiobookService.seekTo(seekPosition);
                sb_play.setProgress(seekPosition / 1000);
                tv_seekbar_time.setText(formatTime(seekPosition / 1000));
            }
        });

        iv_btn_back_15s.setOnClickListener(view -> {
            if (audiobookService != null) {
                int currentPosition = audiobookService.getCurrentPosition();
                int seekPosition = Math.max(currentPosition - 15000, 0);
                audiobookService.seekTo(seekPosition);
                sb_play.setProgress(seekPosition / 1000);
                tv_seekbar_time.setText(formatTime(seekPosition / 1000));
            }
        });

        iv_btn_down.setOnClickListener(view -> {
            saveBookInfo(idBook, currentChapterIndex);
            saveCurrentState();
            transitionToMiniPlayer();
            pauseAudio();
            finish();
        });

        handler.postDelayed(updateSeekBar, 1000);
    }

    private void saveBookInfo(int idBook, int currentChapterIndex) {
        SharedPreferences preferences = getSharedPreferences("BookPlayerPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("idBook", idBook);
        editor.putInt("currentIndex", currentChapter);
        editor.apply();
    }

    private void saveCurrentState() {
        if (audiobookService != null) {
            int currentPosition = audiobookService.getCurrentPosition();
            SharedPreferences preferences = getSharedPreferences("BookPlayerState", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt("idBook", idBook);
            editor.putInt("currentChapterIndex", currentChapterIndex);
            editor.putInt("currentPosition", currentPosition);
            editor.apply();
        }
    }

    private void transitionToMiniPlayer() {
        Intent intent = new Intent("com.example.soulfm.fragment.MiniPlayerFragment");
        sendBroadcast(intent);
    }

    private void getChapter(int idBook) {
        ApiBookChapter.apiService.getlistBookChapter(idBook).enqueue(new Callback<List<Chapter>>() {
            @Override
            public void onResponse(Call<List<Chapter>> call, Response<List<Chapter>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    chapters = response.body();
                    if (!chapters.isEmpty()) {
                        currentChapterIndex = currentChapter;
                        loadChapter(currentChapterIndex);
                    } else {
                        Toast.makeText(BookPlayerActivity.this, "Không có chương nào cho sách này", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(BookPlayerActivity.this, "Lỗi khi lấy dữ liệu từ API", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Chapter>> call, Throwable t) {
                Toast.makeText(BookPlayerActivity.this, "Lỗi khi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void pauseAudio() {
        if (audiobookService != null && audiobookService.isPlaying()) {
            audiobookService.pauseAudio();
            iv_btn_play.setImageResource(R.drawable.play_button);
            stopChapterNameAnimation();
        }
    }

    private void saveCurrentChapterInfo(int currentChapterIndex, int currentPosition) {
        Intent intent = new Intent(BookPlayerActivity.this, BookHeardActivity.class);
        intent.putExtra("idBook", idBook); // Pass idBook to BookHeardActivity
        intent.putExtra("chapterIndex", currentChapterIndex);
        intent.putExtra("currentPosition", currentPosition);
        startActivity(intent);
    }

    private void stopChapterNameAnimation() {
        tv_name_chapter_player.clearAnimation();
    }

    private void playAudio() {
        if (audiobookService != null) {
            audiobookService.playAudio();
            iv_btn_play.setImageResource(R.drawable.pause);
            startUpdatingSeekBar();
            startChapterNameAnimation();
        }
    }

    private void startChapterNameAnimation() {
        if (tv_name_chapter_player.getVisibility() != View.VISIBLE) {
            return;
        }
        stopChapterNameAnimation();
        int width = tv_name_chapter_player.getWidth();
        TranslateAnimation animation = new TranslateAnimation(width, -width, 0, 0);
        animation.setDuration(15000);
        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {}

            @Override
            public void onAnimationEnd(Animation animation) {
                startChapterNameAnimation();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {}
        });
        tv_name_chapter_player.startAnimation(animation);
    }

    private void prepareMediaPlayer(String audioUrl) {
        progressBar.setVisibility(View.VISIBLE);
        audiobookService.prepareMediaPlayer(audioUrl, new AudiobookService.OnMediaPreparedListener() {
            @Override
            public void onMediaPrepared() {
                progressBar.setVisibility(View.GONE);
                enableButtons();
                playAudio();
                startChapterNameAnimation();
            }

            @Override
            public void onMediaCompleted() {
                if (currentChapterIndex < chapters.size() - 1) {
                    currentChapterIndex++;
                    loadChapter(currentChapterIndex);
                } else {
                    Toast.makeText(BookPlayerActivity.this, "Đây là chương cuối cùng", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void enableButtons() {
        iv_btn_play.setEnabled(true);
        iv_btn_next.setEnabled(true);
        iv_btn_previous.setEnabled(true);
        iv_btn_next_15s.setEnabled(true);
        iv_btn_back_15s.setEnabled(true);
        sb_play.setEnabled(true);
    }

    private void startUpdatingSeekBar() {
        handler.post(updateSeekBar);
    }

    private void stopUpdatingSeekBar() {
        handler.removeCallbacks(updateSeekBar);
    }

    private void updateSeekBarTimePosition(SeekBar seekBar) {
        int thumbOffset = seekBar.getThumbOffset();
        float translateX = (seekBar.getWidth() - thumbOffset) * seekBar.getProgress() / (float) seekBar.getMax();
        translateX = Math.max(0, translateX - tv_seekbar_time.getWidth() / 2);
        translateX = Math.min(seekBar.getWidth() - tv_seekbar_time.getWidth(), translateX);
        tv_seekbar_time.setTranslationX(translateX);
    }

    private String formatTime(int i) {
        int minutes = i / 60;
        int secs = i % 60;
        return String.format("%02d:%02d", minutes, secs);
    }

    private void getFirstEpisode(int idBook) {
        ApiBookChapter.apiService.getlistBookChapter(idBook).enqueue(new Callback<List<Chapter>>() {
            @Override
            public void onResponse(Call<List<Chapter>> call, Response<List<Chapter>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    chapters = response.body();
                    if (!chapters.isEmpty()) {
                        currentChapterIndex = 0;
                        loadChapter(currentChapterIndex);
                    } else {
                        Toast.makeText(BookPlayerActivity.this, "Không có chương nào cho sách này", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(BookPlayerActivity.this, "Lỗi khi lấy dữ liệu từ API", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Chapter>> call, Throwable t) {
                Toast.makeText(BookPlayerActivity.this, "Lỗi kết nối: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadChapter(int currentChapterIndex) {
        if (currentChapterIndex < 0 || currentChapterIndex >= chapters.size()) {
            Toast.makeText(this, "Không có chương nào để phát", Toast.LENGTH_SHORT).show();
            return;
        }
        Chapter chapter = chapters.get(currentChapterIndex);
        Glide.with(iv_image_book_player.getContext())
                .load(chapter.getAnhbia())
                .into(iv_image_book_player);
        tv_name_chapter_player.setText("Chương " + chapter.getEpisode() + ": " + chapter.getTenchapter());
        int seconds = convertDurationToSeconds(chapter.getDuration());
        setSeekBarMax(seconds);
        progressBar.setVisibility(View.VISIBLE);
        audioUrl = chapter.getAudiofile();
        disableButtons();
        prepareMediaPlayer(audioUrl);
        playNewAudio(audioUrl);
    }

    private void playNewAudio(String audioUrl) {
        // Declare an Intent to start the service
        Intent intent = new Intent(this, AudiobookService.class);
        // Prepare the information for the new audio to play
        // Send request to play new audio to the service
        intent.putExtra("new_audio_url", audioUrl);
        startService(intent);
    }

    private void stopAudioService() {
        if (isServiceBound) {
            // Stop the service and unbind if it is currently bound
            stopService(new Intent(this, AudiobookService.class));
            unbindService(serviceConnection);
            isServiceBound = false;
        }
    }

    private void disableButtons() {
        iv_btn_play.setEnabled(false);
        iv_btn_next.setEnabled(false);
        iv_btn_previous.setEnabled(false);
        iv_btn_next_15s.setEnabled(false);
        iv_btn_back_15s.setEnabled(false);
        sb_play.setEnabled(false);
    }

    private void setSeekBarMax(int seconds) {
        sb_play.setMax(seconds);
    }

    private int convertDurationToSeconds(String duration) {
        try {
            String[] parts = duration.split(":");
            int hours = Integer.parseInt(parts[0]);
            int minutes = Integer.parseInt(parts[1]);
            int seconds = Integer.parseInt(parts[2]);
            return hours * 3600 + minutes * 60 + seconds;
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            Toast.makeText(BookPlayerActivity.this, "Không thể chuyển đổi duration thành số nguyên", Toast.LENGTH_SHORT).show();
            return 0;
        }
    }

    private Runnable updateSeekBar = new Runnable() {
        @Override
        public void run() {
            if (!isUserSeeking && audiobookService != null && audiobookService.isPlaying()) {
                sb_play.setProgress(audiobookService.getCurrentPosition() / 1000);
            }
            handler.postDelayed(this, 1000);
        }
    };

    private void saveCurrentTimePosition(int currentPosition) {
        SharedPreferences preferences = getSharedPreferences("AudioPosition", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("currentPosition", currentPosition);
        editor.apply();
    }

    private int getCurrentTimePosition() {
        SharedPreferences preferences = getSharedPreferences("AudioPosition", MODE_PRIVATE);
        return preferences.getInt("currentPosition", 0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isServiceBound) {
            unbindService(serviceConnection);
            isServiceBound = false;
            if (audiobookService != null) {
                saveCurrentTimePosition(audiobookService.getCurrentPosition());
            }
        }
        handler.removeCallbacks(updateSeekBar);
    }
}
