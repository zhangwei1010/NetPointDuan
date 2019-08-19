package com.net.point.adapter;


import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;


public final class TabDetectionAdapter extends CommonFragmentPagerAdapter {

    public TabDetectionAdapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }
}
