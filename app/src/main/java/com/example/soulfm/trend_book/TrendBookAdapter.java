package com.example.soulfm.trend_book;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.soulfm.R;
import com.example.soulfm.my_interface.IClickItemTrendBookListener;

import java.util.List;

public class TrendBookAdapter extends RecyclerView.Adapter<TrendBookAdapter.TrendBookAdapterViewHolder>{

    private List<TrendBook> trendBooks;
    private IClickItemTrendBookListener iClickItemTrendBookListener;

    public void setData(List<TrendBook> trendBooks, IClickItemTrendBookListener iClickItemTrendBookListener){
        this.trendBooks = trendBooks;
        this.iClickItemTrendBookListener = iClickItemTrendBookListener;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TrendBookAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_trend_book, parent, false);
        return new TrendBookAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrendBookAdapterViewHolder holder, int position) {
        TrendBook trendBook = trendBooks.get(position);
        if(trendBook == null){
            return;
        }

        Glide.with(holder.iv_image_trend_book.getContext())
                .load(trendBook.getImage_url())
                .into(holder.iv_image_trend_book);
        holder.tv_author_name.setText(trendBook.getAuthors());
        holder.tv_number.setText(String.valueOf(trendBook.getId()));
        holder.tv_title_book.setText(trendBook.getTitle());
        holder.layout_item_trend_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickItemTrendBookListener.clickItemTrendBook(trendBook);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(trendBooks != null){
            return trendBooks.size();
        }
        return 0;
    }

    public class TrendBookAdapterViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_author_name,tv_title_book,tv_number;
        private ImageView iv_image_trend_book;

        private  CardView layout_item_trend_book;

        public TrendBookAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_number = itemView.findViewById(R.id.tv_number);
            tv_title_book = itemView.findViewById(R.id.tv_title_book);
            tv_author_name = itemView.findViewById(R.id.tv_author_name);
            iv_image_trend_book = itemView.findViewById(R.id.iv_image_trend_book);
            layout_item_trend_book = itemView.findViewById(R.id.layout_item_trend_book);

        }
    }
}
