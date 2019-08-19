package com.net.point.adapter;

import android.util.Pair;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;

public class CommonFragmentPagerAdapter extends FragmentPagerAdapter {

    private List<Pair<String, Fragment>> mFragments = new LinkedList<>();

    public CommonFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setFragments(List<Pair<String, Fragment>> fragments) {
        mFragments.clear();
        if (fragments != null) {
            mFragments.addAll(fragments);
        }
        notifyDataSetChanged();
    }

    @NotNull
    public CommonFragmentPagerAdapter addFragment(@NonNull String title, @NonNull Fragment fragment) {
        mFragments.add(new Pair<>(title, fragment));
        return this;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position).second;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragments.get(position).first;
    }

    public void setTitle(int position, String title) {
        mFragments.set(position, Pair.create(title, mFragments.get(position).second));
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}