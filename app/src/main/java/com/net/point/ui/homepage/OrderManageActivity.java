package com.net.point.ui.homepage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.net.point.R;
import com.net.point.adapter.TabDetectionAdapter;
import com.net.point.base.BaseActivity;
import com.net.point.fragment.CompletedFragment;
import com.net.point.fragment.UnCompletedFragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderManageActivity extends BaseActivity {

    @BindView(R.id.mTabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;

    public static void start(Context context) {
        context.startActivity(new Intent(context, OrderManageActivity.class));
    }

    private CompletedFragment completedFragment;
    private UnCompletedFragment unCompletedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentTitle(R.string.order_manage);
        addContentView(R.layout.activity_order_manage);
        ButterKnife.bind(this);

        TabDetectionAdapter adapter = new TabDetectionAdapter(getSupportFragmentManager());
        completedFragment  = new CompletedFragment();
        unCompletedFragment = new UnCompletedFragment();

        adapter.addFragment(getString(R.string.completed), completedFragment);
        adapter.addFragment(getString(R.string.uncompleted), unCompletedFragment);

        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
