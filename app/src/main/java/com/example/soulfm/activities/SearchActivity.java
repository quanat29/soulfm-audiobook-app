package com.example.soulfm.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.soulfm.R;
import com.example.soulfm.my_interface.IClickItemTrendBookListener;
import com.example.soulfm.trend_book.TrendBook;
import com.example.soulfm.trend_book.TrendBookAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView rcv_list_search;
    private TrendBookAdapter trendBookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        rcv_list_search = findViewById(R.id.rcv_list_search);
        trendBookAdapter = new TrendBookAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcv_list_search.setLayoutManager(linearLayoutManager);
        trendBookAdapter.setData(getTrendBookList(), new IClickItemTrendBookListener() {
            @Override
            public void clickItemTrendBook(TrendBook trendBook) {

            }
        });
        rcv_list_search.setAdapter(trendBookAdapter);
    }

    private List<TrendBook> getTrendBookList() {

        List<TrendBook> trendBooks = new ArrayList<>();



        return trendBooks;
    }
}