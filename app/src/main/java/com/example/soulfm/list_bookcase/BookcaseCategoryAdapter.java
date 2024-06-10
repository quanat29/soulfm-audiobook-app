package com.example.soulfm.list_bookcase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soulfm.R;

import java.util.List;

public class BookcaseCategoryAdapter extends RecyclerView.Adapter<BookcaseCategoryAdapter.BookcaseCategoryViewHolder>{

    private List<BookcaseCategory> mlists;

    public void setData(List<BookcaseCategory> mlists){
        this.mlists = mlists;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookcaseCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_bookcase, parent, false);
        return new BookcaseCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookcaseCategoryViewHolder holder, int position) {
        BookcaseCategory bookcaseCategory = mlists.get(position);
        if(bookcaseCategory == null){
            return;
        }
        holder.iv_list_bookcase.setImageResource(bookcaseCategory.getId());
        holder.tv_title_list_bookcase.setText(bookcaseCategory.getTitle());
    }

    @Override
    public int getItemCount() {
        if(mlists != null){
            return mlists.size();
        }
        return 0;
    }

    public class BookcaseCategoryViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_list_bookcase;
        private TextView tv_title_list_bookcase;
        public BookcaseCategoryViewHolder(@NonNull View itemView) {
            super(itemView);

            iv_list_bookcase = itemView.findViewById(R.id.iv_icon_list_bookcase);
            tv_title_list_bookcase = itemView.findViewById(R.id.tv_title_list_bookcase);
        }
    }
}
