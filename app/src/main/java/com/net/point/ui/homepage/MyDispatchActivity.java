package com.net.point.ui.homepage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.net.point.R;
import com.net.point.adapter.TabDetectionAdapter;
import com.net.point.base.BaseActivity;
import com.net.point.fragment.DamagedPartsFragment;
import com.net.point.fragment.DispatchedFragment;
import com.net.point.fragment.QuestionPieceFragment;
import com.net.point.fragment.UnDispatchedFragment;
import com.net.point.fragment.WarehouseRetentionFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

//我的派件
public class MyDispatchActivity extends BaseActivity {

    @BindView(R.id.mTabLayout)
    TabLayout mTabLayout;
    @BindView(R.id.mViewPager)
    ViewPager mViewPager;

    public static void start(Context context) {
        context.startActivity(new Intent(context, MyDispatchActivity.class));
    }

    private DispatchedFragment dispatchedFragment;
    private UnDispatchedFragment unDispatchedFragment;
    private WarehouseRetentionFragment retentionFragment;
    private DamagedPartsFragment damagedPartsFragment;
    private QuestionPieceFragment pieceFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentTitle(R.string.my_dispatch);
        addContentView(R.layout.activity_order_manage);
        ButterKnife.bind(this);

        TabDetectionAdapter adapter = new TabDetectionAdapter(getSupportFragmentManager());
        dispatchedFragment = new DispatchedFragment();
        unDispatchedFragment = new UnDispatchedFragment();
        retentionFragment = new WarehouseRetentionFragment();
        damagedPartsFragment = new DamagedPartsFragment();
        pieceFragment = new QuestionPieceFragment();

        adapter.addFragment(getString(R.string.dispatched), dispatchedFragment);
        adapter.addFragment(getString(R.string.undispatched), unDispatchedFragment);
        adapter.addFragment(getString(R.string.warehouse_retention), retentionFragment);
        adapter.addFragment(getString(R.string.damaged_parts), damagedPartsFragment);
        adapter.addFragment(getString(R.string.question_piece), pieceFragment);

        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}
