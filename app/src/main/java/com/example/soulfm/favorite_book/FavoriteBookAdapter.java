package com.example.soulfm.favorite_book;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.soulfm.R;
import com.example.soulfm.book.Book;

import java.util.List;

public class FavoriteBookAdapter extends RecyclerView.Adapter<FavoriteBookAdapter.FavoriteBookViewHolder>{

    private List<FavoriteBook> favoriteBooks;

    public void setData(List<FavoriteBook> favoriteBooks){
        this.favoriteBooks = favoriteBooks;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteBookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book, parent, false);
        return new FavoriteBookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteBookViewHolder holder, int position) {
        FavoriteBook favoriteBook = favoriteBooks.get(position);
        if(favoriteBook == null){
            return;
        }

        holder.tv_title.setText(favoriteBook.getTitle());
        Glide.with(holder.iv_book.getContext())
                .load(favoriteBook.getImage_url())
                .into(holder.iv_book);
    }

    @Override
    public int getItemCount() {
        if(favoriteBooks != null){
            return favoriteBooks.size();
        }
        return 0;
    }

    public static class FavoriteBookViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_title;
        private ImageView iv_book;
        public FavoriteBookViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_title = itemView.findViewById(R.id.tv_title);
            iv_book = itemView.findViewById(R.id.iv_book);
        }
    }
}
