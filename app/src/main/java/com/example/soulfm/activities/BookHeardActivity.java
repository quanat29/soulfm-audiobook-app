package com.example.soulfm.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.soulfm.R;
import com.example.soulfm.book_heard.BookHeard;
import com.example.soulfm.book_heard.BookHeardAdapter;

import java.util.ArrayList;
import java.util.List;

public class BookHeardActivity extends AppCompatActivity {

    private RecyclerView rcv_book_heard;
    private BookHeardAdapter bookHeardAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_heard);

        rcv_book_heard = findViewById(R.id.rcv_list_booksHeard);
        bookHeardAdapter = new BookHeardAdapter();

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rcv_book_heard.setLayoutManager(gridLayoutManager);
        bookHeardAdapter.setData(getBookHeardList());
        rcv_book_heard.setAdapter(bookHeardAdapter);

        Intent intent = getIntent();
        int idBook = intent.getIntExtra("idBook", -1);
        int chapterIndex = intent.getIntExtra("chapterIndex", -1);
        int currentPosition = intent.getIntExtra("currentPosition", -1);
    }

    private List<BookHeard> getBookHeardList() {
        List<BookHeard> bookHeardList = new ArrayList<>();


        return bookHeardList;
    }
}