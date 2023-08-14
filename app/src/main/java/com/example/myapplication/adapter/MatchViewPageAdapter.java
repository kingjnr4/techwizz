package com.example.myapplication.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myapplication.MatchAwayTab;
import com.example.myapplication.MatchFormationTab;
import com.example.myapplication.MatchHomeTab;
import com.example.myapplication.MatchStatsTab;
import com.example.myapplication.SavedPlayerTab;
import com.example.myapplication.SavedTeamTab;

public class MatchViewPageAdapter extends FragmentStateAdapter {


    public MatchViewPageAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 1: return new MatchFormationTab();
            case 2: return new MatchHomeTab();
            case 3: return new MatchAwayTab();
            default: return new MatchStatsTab();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
