package com.example.soulfm.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.soulfm.R;
import com.example.soulfm.my_interface.IClickItemTrendBookListener;
import com.example.soulfm.trend_book.TrendBook;
import com.example.soulfm.trend_book.TrendBookAdapter;

import java.util.ArrayList;
import java.util.List;

public class TrendBookActivity extends AppCompatActivity {

    private RecyclerView rcv_trend_book;
    private TrendBookAdapter trendBookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trend_book);

        rcv_trend_book = findViewById(R.id.rcv_trend_book);

        trendBookAdapter = new TrendBookAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcv_trend_book.setLayoutManager(linearLayoutManager);
        List<TrendBook> trendBooks = (List<TrendBook>) getIntent().getSerializableExtra("listbook");
        if (trendBooks != null) {
            trendBookAdapter.setData(trendBooks, new IClickItemTrendBookListener() {
                @Override
                public void clickItemTrendBook(TrendBook trendBook) {
                    onClickGoToDetai(trendBook);
                }
            });
            rcv_trend_book.setAdapter(trendBookAdapter);
        } else {
            Toast.makeText(this, "No data available", Toast.LENGTH_SHORT).show();
        }
    }

    private void onClickGoToDetai(TrendBook trendBook) {
        Intent intent = new Intent(TrendBookActivity.this, BookDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("trend_book", trendBook);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}