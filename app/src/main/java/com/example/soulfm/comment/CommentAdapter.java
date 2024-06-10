package com.example.soulfm.comment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soulfm.R;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentAdapterViewHolder>{

    private List<Comment> commentList;

    public void setData(List<Comment> commentList){
        this.commentList = commentList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CommentAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentAdapterViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        if(comment == null){
            return;
        }
        holder.tv_username.setText(comment.getUser_name());
        holder.tv_date_comment.setText(comment.getComment_time());
        holder.tv_content_comment.setText(comment.getContent());
    }

    @Override
    public int getItemCount() {
        if(commentList != null){
            return commentList.size();
        }
        return 0;
    }

    public class CommentAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_username, tv_date_comment, tv_content_comment;
        public CommentAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_username = itemView.findViewById(R.id.tv_username);
            tv_date_comment = itemView.findViewById(R.id.tv_date_comment);
            tv_content_comment = itemView.findViewById(R.id.tv_content_comment);
        }
    }
}
