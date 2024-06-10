package com.example.soulfm.chapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.soulfm.R;
import com.example.soulfm.my_interface.IClickItemChapterListener;

import java.util.List;

public class ChapterAdapter extends RecyclerView.Adapter<ChapterAdapter.ChapterAdapterViewHolder>{

    private List<Chapter> chapters;
    private IClickItemChapterListener iClickItemChapterListener;


    public void setData(List<Chapter> chapters, IClickItemChapterListener iClickItemChapterListener){
        this.chapters = chapters;
        this.iClickItemChapterListener = iClickItemChapterListener;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public ChapterAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chapter, parent, false);
        return new ChapterAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterAdapterViewHolder holder, int position) {
        Chapter chapter = chapters.get(position);
        if(chapter == null){
            return;
        }
        holder.tv_chapter_number.setText(chapter.getEpisode());
        holder.tv_title_chapter.setText(chapter.getTenchapter());
        holder.bind(chapter, position);
    }

    @Override
    public int getItemCount() {
        if(chapters != null){
            return chapters.size();
        }
        return 0;
    }

    public class ChapterAdapterViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_chapter_number, tv_title_chapter;
        private CardView layout_item_chapter;
        public ChapterAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_chapter_number = itemView.findViewById(R.id.tv_chapter_number);
            tv_title_chapter = itemView.findViewById(R.id.tv_title_chapter);
            layout_item_chapter = itemView.findViewById(R.id.layout_item_chapter);
        }

        public void bind(Chapter chapter, int position) {
            layout_item_chapter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iClickItemChapterListener.clickItemChapter(chapter, position);
                }
            });
        }
    }
}
