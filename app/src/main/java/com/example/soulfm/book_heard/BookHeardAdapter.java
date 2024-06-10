package com.example.soulfm.book_heard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soulfm.R;
import com.example.soulfm.list_bookcase.BookcaseCategoryAdapter;

import java.util.List;

public class BookHeardAdapter extends RecyclerView.Adapter<BookHeardAdapter.BookHeardAdapterViewHolder>{

    private Context mContext;
    private List<BookHeard> bookHeardList;

    public void setData(List<BookHeard> bookHeardList){
        this.bookHeardList = bookHeardList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookHeardAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_book_heard, parent, false);
        return new BookHeardAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookHeardAdapterViewHolder holder, int position) {
        BookHeard bookHeard = bookHeardList.get(position);
        if(bookHeard == null){
            return;
        }
        holder.iv_image_book_heard.setImageResource(bookHeard.getImageSource());
        holder.sb_book_heard.setProgress(bookHeard.getProcess());
        holder.tv_book_heard_name.setText(bookHeard.getTitle());
    }

    @Override
    public int getItemCount() {
        if(bookHeardList != null){
            return bookHeardList.size();
        }
        return 0;
    }

    public class BookHeardAdapterViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_image_book_heard;
        private TextView tv_book_heard_name;
        private SeekBar sb_book_heard;
        public BookHeardAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_image_book_heard = itemView.findViewById(R.id.iv_image_book_heard);
            tv_book_heard_name = itemView.findViewById(R.id.tv_book_heard_name);
            sb_book_heard = itemView.findViewById(R.id.sb_book_heard);

        }
    }
}
