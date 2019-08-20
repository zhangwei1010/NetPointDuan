package com.net.point.fragment;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.net.point.R;
import com.net.point.adapter.OrderManCompletedAdapter;
import com.net.point.base.BaseFragment;
import com.net.point.model.HomeModel;
import com.net.point.response.OrderDetailsBean;
import com.net.point.utils.SpUtils;
import com.net.point.view.HeadRecycleView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UnCompletedFragment extends BaseFragment {

    @BindView(R.id.mRecycleView)
    HeadRecycleView mRecycleView;
    @BindView(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private HomeModel model = new HomeModel();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_completed, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        initRecycleView();
        loadData();
    }

    private int pageNum = 1;

    private void loadData() {
        if (TextUtils.isEmpty(SpUtils.getIncNumber())) {
            hasMore = false;
            hideRefresh();
            return;
        }
        showProgressDialog();
        model.getUnCompleteOrder(SpUtils.getIncNumber(), pageSize, pageNum + "", e -> {
                    e.printStackTrace();
                    hideRefresh();
                    hasMore = false;
                    hideProgressDialog();
                    Log.e("", e.getMessage());
                },
                result -> {
                    hideProgressDialog();
                    hideRefresh();
                    if (result.isSuccess() && result.getData() != null) {
                        List<OrderDetailsBean> orderBeans = result.getData();
                        if (orderBeans == null || orderBeans.isEmpty() || orderBeans.size() < 10) {
                            hasMore = false;
                        } else hasMore = true;
                        mAdapter.setItems(orderBeans);
                    } else hasMore = false;
                });
    }

    private OrderManCompletedAdapter mAdapter;

    private void initRecycleView() {
        mAdapter = new OrderManCompletedAdapter("2", number -> {
            dispatchOrder(number);
        });
        mRecycleView.setOnLoadListener(o -> loadMore());
        mSwipeRefreshLayout.setRefreshing(false);
        mSwipeRefreshLayout.setOnRefreshListener(() -> refresh());
        mRecycleView.setAdapter(mAdapter);
    }

    private void dispatchOrder(String number) {
        if (TextUtils.isEmpty(number)) return;
        model.dispatchOrder(number, SpUtils.getUserId(), e -> {
            e.printStackTrace();
            toast(e.getMessage());
        }, result -> {
            int code = result.getCode();
            String msg = result.getMsg();
            if (result.isSuccess()) {
                if (code == 1) {//发送成功
                    mAdapter.notifyDataSetChanged();
                    toast(msg);
                }
            }
        });
    }

    private void refresh() {
        Log.e("", "UnCompletedFragment - refresh");
        loadData();
    }

    private void loadMore() {
        Log.e("", "UnCompletedFragment - loadMore");
        if (hasMore) {
            ++pageNum;
            loadData();
        }
    }

    private void hideRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        //派单点击刷新数据
        refresh();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
