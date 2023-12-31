package com.example.myapplication.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication.SavedPlayerTab;
import com.example.myapplication.SavedTeamTab;

public class SaveViewPageAdapter extends FragmentStateAdapter {


    public SaveViewPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new SavedPlayerTab();
            case 1: return new SavedTeamTab();
            default: return new SavedPlayerTab();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
