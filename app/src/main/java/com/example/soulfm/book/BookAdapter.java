package com.example.soulfm.book;

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
import com.example.soulfm.my_interface.IClickitemBookListener;

import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookViewHolder>{

    private  List<Book> mBooks;
    private int Id_user;

    private IClickitemBookListener iClickitemBookListener;

    public void setData(List<Book> mBooks,IClickitemBookListener iClickitemBookListener, int Id_user){
        this.mBooks = mBooks;
        this.iClickitemBookListener = iClickitemBookListener;
        this.Id_user = Id_user;
        notifyDataSetChanged();
    }

    public void clearData(){
        mBooks.clear();
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book,parent, false);
        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book book = mBooks.get(position);
        if(book == null){
            return;
        }

        holder.tv_title.setText(book.getTitle());
        Glide.with(holder.iv_book.getContext())
                .load(book.getImage_url())
                .into(holder.iv_book);
        holder.layout_item_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                iClickitemBookListener.clickItemBook(book);
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mBooks != null){
            return mBooks.size();
        }
        return 0;
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder{

        private final ImageView iv_book;
        private final TextView tv_title;
        private final CardView layout_item_book;
        public BookViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_book = itemView.findViewById(R.id.iv_book);
            tv_title = itemView.findViewById(R.id.tv_title);
            layout_item_book = itemView.findViewById(R.id.layout_item_book);
        }
    }
}
