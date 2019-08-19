package com.net.point.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.net.point.R;
import com.net.point.adapter.IntegralAdapter;
import com.net.point.base.BaseActivity;
import com.net.point.model.HomeModel;
import com.net.point.response.IntegralBean;
import com.net.point.utils.AppUtils;
import com.net.point.utils.SpUtils;
import com.net.point.view.HeadRecycleView;
import com.net.point.widget.IntegralPopupWindow;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyIntegralActivity extends BaseActivity {

    @BindView(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.mRecyclerView)
    HeadRecycleView mRecyclerView;
    @BindView(R.id.tv_total_count)
    TextView tv_total_count;
    @BindView(R.id.iv_question_mark)
    ImageView iv_question_mark;

    private HomeModel model = new HomeModel();
    private int pageNum = 1;

    public static void start(Context context) {
        context.startActivity(new Intent(context, MyIntegralActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContentView(R.layout.activity_my_integral);
        setContentTitle("我的积分");
        ButterKnife.bind(this);
        initRecycleView();
        loadTotalIntegral();
        loadIntegralData();
    }

    private void loadTotalIntegral() {
        model.loadTotalIntegral(SpUtils.getUserId(), e -> {
            e.printStackTrace();
            toast(e.getMessage());
        }, result -> {
            if (result.isSuccess() && result.getData() != null) {
                Integer data = result.getData();
                AppUtils.setTexts(tv_total_count, data + "");
            }
        });
    }

    private void loadIntegralData() {
        if (TextUtils.isEmpty(SpUtils.getUserId())) {
            hasMore = false;
            hideRefresh();
            return;
        }
        showProgressDialog();
        model.loadMyIntegral(SpUtils.getUserId(), pageSize, pageNum + "", e -> {
                    e.printStackTrace();
                    hideProgressDialog();
                    hideRefresh();
                    hasMore = false;
                    Log.e("", e.getMessage());
                },
                result -> {
                    hideProgressDialog();
                    hideRefresh();
                    if (result.isSuccess() && result.getData() != null) {
                        List<IntegralBean> orderBeans = result.getData();
                        if (orderBeans == null || orderBeans.isEmpty() || orderBeans.size() < 10) {
                            hasMore = false;
                        } else hasMore = true;
                        mAdapter.setItems(orderBeans);
                    } else hasMore = false;
                });
    }

    @OnClick({R.id.iv_question_mark})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_question_mark:
                showIntegralPop();
                break;
        }
    }

    private IntegralPopupWindow popupWindow;

    private void showIntegralPop() {
        if (popupWindow != null) {
            popupWindow = null;
        }
        popupWindow = new IntegralPopupWindow(this);
        popupWindow.show();
    }

    private IntegralAdapter mAdapter = new IntegralAdapter();

    private void initRecycleView() {
        mRecyclerView.setOnLoadListener(o -> loadMore());
        mSwipeRefreshLayout.setRefreshing(false);
        mSwipeRefreshLayout.setOnRefreshListener(() -> refresh());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void refresh() {
        Log.e("", "UnDispatchedFragment - refresh");
        loadIntegralData();
    }

    private void loadMore() {
        Log.e("", "UnDispatchedFragment - loadMore");
        if (hasMore) {
            ++pageNum;
            loadIntegralData();
        }
    }

    private void hideRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}