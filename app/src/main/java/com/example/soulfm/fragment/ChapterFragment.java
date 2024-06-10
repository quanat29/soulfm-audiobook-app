package com.example.soulfm.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.soulfm.R;
import com.example.soulfm.activities.BookPlayerActivity;
import com.example.soulfm.api.ApiBookChapter;
import com.example.soulfm.book.Book;
import com.example.soulfm.category.Category;
import com.example.soulfm.chapter.Chapter;
import com.example.soulfm.chapter.ChapterAdapter;
import com.example.soulfm.my_interface.IClickItemChapterListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChapterFragment extends Fragment {

    private static final String ARG_BOOK_ID = "Id_book";
    private int bookId;
    private RecyclerView rcv_chapter_book_detail;
    private ChapterAdapter chapterAdapter;
    private View view;
    private TextView num_sections;

    public ChapterFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ChapterFragment newInstance(int bookId) {
        ChapterFragment fragment = new ChapterFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_BOOK_ID, bookId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            bookId = getArguments().getInt(ARG_BOOK_ID);
            Log.d("ChapterFragment", "bookId: " + bookId);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_chapter, container, false);
        initUI();
        callApiBookChapter(bookId);
        return view;
    }

    private void initUI() {
        rcv_chapter_book_detail = view.findViewById(R.id.rcv_chapter_book_detail);
        num_sections = view.findViewById(R.id.num_sections);
        chapterAdapter = new ChapterAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false);
        rcv_chapter_book_detail.setLayoutManager(linearLayoutManager);
        rcv_chapter_book_detail.setAdapter(chapterAdapter);

    }

    private void loadChapters() {
    }

    private void callApiBookChapter(int bookId) {
        ApiBookChapter.apiService.getlistBookChapter(bookId).enqueue(new Callback<List<Chapter>>() {
            @Override
            public void onResponse(Call<List<Chapter>> call, Response<List<Chapter>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Chapter> chapters = response.body();
                    if (!chapters.isEmpty()) {
                        Chapter chapter = chapters.get(0);
                        num_sections.setText(String.valueOf(chapter.getNum_sections())+ " chương");
                        chapterAdapter.setData(chapters, new IClickItemChapterListener() {
                            @Override
                            public void clickItemChapter(Chapter chapter, int position) {
                                Log.d("ChapterFragment", "Click on chapter: " + chapter.getTenchapter());
                                Log.d("ChapterFragment", "Position: " + position);
                                Intent intent = new Intent(getActivity(), BookPlayerActivity.class);
                                intent.putExtra("audioUrl", chapter.getAudiofile());
                                intent.putExtra("chapterIndex", position);
                                intent.putExtra("id_book", chapter.getId_book());
                                intent.putExtra("play_first_episode", false);
                                startActivity(intent);
                            }
                        });
                    } else {
                        Toast.makeText(getContext(), "Không có chương nào cho sách này", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Lỗi khi lấy dữ liệu từ API", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Chapter>> call, Throwable t) {
                Toast.makeText(getContext(),"failed",Toast.LENGTH_SHORT).show();
                Log.e("ChapterFragment", "onFailure: ", t);
            }
        });
    }

}