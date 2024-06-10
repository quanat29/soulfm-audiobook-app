package com.example.soulfm.viewpager_adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.soulfm.fragment.AccountFragment;
import com.example.soulfm.fragment.BookcaseFragment;
import com.example.soulfm.fragment.HomeFragment;
import com.example.soulfm.fragment.TopicFragment;

public class MyViewpagerAdapter  extends FragmentStateAdapter {

    private int Id_user;

    public MyViewpagerAdapter(@NonNull FragmentActivity fragmentActivity, int Id_user) {
        super(fragmentActivity);
        this.Id_user = Id_user;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return HomeFragment.newInstance(Id_user);

            case 1:
                return TopicFragment.newInstance(Id_user);

            case 2:
                return new BookcaseFragment();

            case 3:
                return new AccountFragment();

            default: return new HomeFragment();
        }

    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
