package com.example.soulfm.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.soulfm.R;
import com.example.soulfm.api.ApiBookChapter;
import com.example.soulfm.chapter.Chapter;
import com.example.soulfm.fragment.MiniPlayerFragment;
import com.example.soulfm.image_slide.Image;
import com.example.soulfm.image_slide.ImageViewPagerAdapter;
import com.example.soulfm.services.AudiobookService;
import com.example.soulfm.viewpager_adapter.MyViewpagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import me.relex.circleindicator.CircleIndicator;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_BOOK_PLAYER = 1;

    private ViewPager2 viewPager2;
    private BottomNavigationView bottomNavigationView;

    private ViewPager viewPager;
    private CircleIndicator circleIndicator;
    private RelativeLayout layout_mini_player;
    private CircleImageView iv_image_book;
    private TextView tv_name_mini, tv_chapter_mini;
    private ImageView iv_play_mini, iv_close_mini;
    private boolean isServiceBound = false;
    private int savedBookId, currentChapterIndex;

    private Handler mHander = new Handler();

    private Runnable mRunable = new Runnable() {
        @Override
        public void run() {
            if(viewPager.getCurrentItem() == getImageList().size() - 1){
                viewPager.setCurrentItem(0);
            }else {
                viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
            }
        }
    };

    private TextView tv_search;
    private ImageView iv_icon_member;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager2 = findViewById(R.id.view_pager_2);
        bottomNavigationView = findViewById(R.id.bottom_navigation);
        tv_search = findViewById(R.id.tv_search);
//        iv_icon_member = findViewById(R.id.iv_icon_member);
        layout_mini_player = findViewById(R.id.layout_mini_player);
        iv_image_book = findViewById(R.id.iv_image_book);
        tv_name_mini = findViewById(R.id.tv_name_mini);
        tv_chapter_mini = findViewById(R.id.tv_chapter_mini);
        iv_play_mini = findViewById(R.id.iv_play_mini);
        iv_close_mini = findViewById(R.id.iv_close_mini);

        iv_close_mini.setOnClickListener(view -> layout_mini_player.setVisibility(View.GONE));

        loadBookIdAndSetupMiniPlayer();

        //nhận intent
        Intent intent = getIntent();
        int Id_user = intent.getIntExtra("Id_user", -1);

        //khai báo thành phần image_slide
        viewPager = findViewById(R.id.view_pager_slide);
        circleIndicator = findViewById(R.id.circle_indicator);

        ImageViewPagerAdapter imageViewPagerAdapter = new ImageViewPagerAdapter(getImageList());
        viewPager.setAdapter(imageViewPagerAdapter);
        circleIndicator.setViewPager(viewPager);

        //auto image slide
        mHander.postDelayed(mRunable, 3000);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mHander.removeCallbacks(mRunable);
                mHander.postDelayed(mRunable, 3000);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        //khai báo view pager adapter
        MyViewpagerAdapter myViewpagerAdapter = new MyViewpagerAdapter(this, Id_user);
        viewPager2.setAdapter(myViewpagerAdapter);

        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                switch (position){
                    case 0:
                        bottomNavigationView.getMenu().findItem(R.id.menu_home).setChecked(true);
                        break;

                    case 1:
                        bottomNavigationView.getMenu().findItem(R.id.menu_topic).setChecked(true);
                        break;

                    case 2:
                        bottomNavigationView.getMenu().findItem(R.id.menu_bookcase).setChecked(true);
                        break;

                    case 3:
                        bottomNavigationView.getMenu().findItem(R.id.menu_account).setChecked(true);
                        break;
                }
            }
        });

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_home:
                        viewPager2.setCurrentItem(0);
                        break;

                    case R.id.menu_topic:
                        viewPager2.setCurrentItem(1);
                        break;

                    case R.id.menu_bookcase:
                        viewPager2.setCurrentItem(2);
                        break;

                    case R.id.menu_account:
                        viewPager2.setCurrentItem(3);
                        break;
                }
                return true;
            }
        });


        //click tv_search
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                // Truyền Id_user sang SearchActivity
                intent.putExtra("Id_user", Id_user);
                startActivity(intent);
            }
        });
    }

    private void loadBookIdAndSetupMiniPlayer() {
        SharedPreferences preferences = getSharedPreferences("BookPlayerPrefs", MODE_PRIVATE);
        savedBookId = preferences.getInt("bookId", -1);
        currentChapterIndex = preferences.getInt("currentIndex", 0);
        if (savedBookId != -1) {
            // Gọi API để lấy thông tin sách và cập nhật MiniPlayer
            fetchBookDetails(savedBookId);
        }
    }

    private void fetchBookDetails(int savedBookId) {
        ApiBookChapter.apiService.getlistBookChapter(savedBookId).enqueue(new Callback<List<Chapter>>() {
            @Override
            public void onResponse(Call<List<Chapter>> call, Response<List<Chapter>> response) {
                if (response.isSuccessful() && response.body() != null) {
                   List<Chapter> chapters = response.body();
                    if (!chapters.isEmpty()) {
                        Chapter chapter = chapters.get(currentChapterIndex);
                        Glide.with(iv_image_book.getContext())
                                .load(chapter.getAnhbia())
                                .into(iv_image_book);
                        tv_chapter_mini.setText("Chương " + chapter.getEpisode() + ": " + chapter.getTenchapter());
                        tv_name_mini.setText(chapter.getTensach());
                    } else {

                            Toast.makeText(MainActivity.this, "Không có chương nào cho sách này", Toast.LENGTH_SHORT).show();
                    }
                } else {

                        Toast.makeText(MainActivity.this, "Lỗi khi lấy dữ liệu từ API", Toast.LENGTH_SHORT).show();
                    }
            }

            @Override
            public void onFailure(Call<List<Chapter>> call, Throwable t) {

            }
        });
    }


    private List<Image> getImageList() {
        List<Image> list = new ArrayList<>();

        list.add(new Image(R.drawable.khong_gia_dinh));
        list.add(new Image(R.drawable.so_do));
        list.add(new Image(R.drawable.hai_so_phan));
        list.add(new Image(R.drawable.nha_gia_kim));

        return list;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mHander.removeCallbacks(mRunable);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHander.postDelayed(mRunable, 3000);
    }
}