package com.example.soulfm.viewpager_adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.soulfm.fragment.ChapterFragment;
import com.example.soulfm.fragment.CommentFragment;

public class ViewpagerChapterAdapter extends FragmentStateAdapter {

    private int Id_book;
    private int Id_user;

    public ViewpagerChapterAdapter(@NonNull FragmentActivity fragmentActivity, int Id_book, int Id_user) {
        super(fragmentActivity);
        this.Id_book = Id_book;
        this.Id_user = Id_user;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return ChapterFragment.newInstance(Id_book);

            case 1:
                return CommentFragment.newInstance(Id_book, Id_user);

            default: return new ChapterFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
