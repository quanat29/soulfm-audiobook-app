package com.example.soulfm.fragment;

import static android.content.Intent.getIntent;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.soulfm.R;
import com.example.soulfm.activities.BookPlayerActivity;
import com.example.soulfm.api.ApiBookChapter;
import com.example.soulfm.chapter.Chapter;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MiniPlayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MiniPlayerFragment extends Fragment {


    private static final String ARG_ID_BOOK = "idBook";
    private static final String ARG_CURRENT_INDEX = "currentChapterIndex";
    private View mView;
    private CircleImageView iv_image_book;
    private TextView tv_name_mini, tv_chapter_mini;
    private ImageView iv_play_mini, iv_close_mini;
    private List<Chapter> chapters;
    private int currentChapterIndex, idBook;

    public MiniPlayerFragment() {
        // Required empty public constructor
    }

    public static MiniPlayerFragment newInstance(int idBook, int currentChapterIndex) {
        MiniPlayerFragment fragment = new MiniPlayerFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_ID_BOOK, idBook);
        args.putInt(ARG_CURRENT_INDEX, currentChapterIndex);
        fragment.setArguments(args);
        return fragment;
    }

    private BroadcastReceiver miniPlayerReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if ("com.example.soulfm.fragment.MiniPlayerFragment".equals(intent.getAction())) {
                updateMiniPlayerUI();
            }
        }
    };

    private void updateMiniPlayerUI() {
        if (chapters != null && !chapters.isEmpty()) {
            Chapter chapter = chapters.get(currentChapterIndex);
            Glide.with(iv_image_book.getContext()).load(chapter.getAnhbia()).into(iv_image_book);
            tv_chapter_mini.setText("Chương " + chapter.getEpisode() + ": " + chapter.getTenchapter());
            tv_name_mini.setText(chapter.getTensach());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            idBook = getArguments().getInt(ARG_ID_BOOK);
            currentChapterIndex = getArguments().getInt(ARG_CURRENT_INDEX);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_mini_player, container, false);
        initUI();
        callApiBookChapter(idBook);
        return mView;
    }

    private void callApiBookChapter(int idBook) {
        ApiBookChapter.apiService.getlistBookChapter(idBook).enqueue(new Callback<List<Chapter>>() {
            @Override
            public void onResponse(Call<List<Chapter>> call, Response<List<Chapter>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    chapters = response.body();
                    if (!chapters.isEmpty()) {
                        Chapter chapter = chapters.get(currentChapterIndex);
                        Glide.with(iv_image_book.getContext())
                                .load(chapter.getAnhbia())
                                .into(iv_image_book);
                        tv_chapter_mini.setText("Chương " + chapter.getEpisode() + ": " + chapter.getTenchapter());
                        tv_name_mini.setText(chapter.getTensach());
                    } else {
                        Context context = requireContext(); // or getActivity()
                        if (context != null) {
                            Toast.makeText(context, "Không có chương nào cho sách này", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Context context = requireContext(); // or getActivity()
                    if (context != null) {
                        Toast.makeText(context, "Lỗi khi lấy dữ liệu từ API", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Chapter>> call, Throwable t) {

            }
        });
    }

    private void initUI() {
        iv_close_mini  = mView.findViewById(R.id.iv_close_mini);
        iv_image_book  = mView.findViewById(R.id.iv_image_book);
        tv_name_mini  = mView.findViewById(R.id.tv_name_mini);
        tv_chapter_mini  = mView.findViewById(R.id.tv_chapter_mini);
        iv_play_mini  = mView.findViewById(R.id.iv_play_mini);

    }
}