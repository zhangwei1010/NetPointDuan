package com.net.point.ui.homepage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.net.point.R;
import com.net.point.adapter.DispatchedAdapter;
import com.net.point.adapter.MyReceiptListAdapter;
import com.net.point.base.BaseActivity;
import com.net.point.model.HomeModel;
import com.net.point.response.OrderDetailsBean;
import com.net.point.utils.SpUtils;
import com.net.point.view.HeadRecycleView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//我的收件
public class MyReceiptActivity extends BaseActivity {

    @BindView(R.id.mRecycleView)
    HeadRecycleView mRecycleView;
    @BindView(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private HomeModel model = new HomeModel();
    private int pageNum = 1;


    public static void start(Context context) {
        context.startActivity(new Intent(context, MyReceiptActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContentView(R.layout.activity_my_receipt);
        setContentTitle("我的收件");
        ButterKnife.bind(this);
        initRecycleView();
        loadData();
    }
    private void loadData() {
        if (TextUtils.isEmpty(SpUtils.getIncNumber())) {
            hasMore = false;
            hideRefresh();
            return;
        }
        showProgressDialog();
        model.getReceiptListOrder(SpUtils.getIncNumber(), pageSize, pageNum + "", e -> {
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
                        List<OrderDetailsBean> orderBeans = result.getData();
                        if (orderBeans == null || orderBeans.isEmpty() || orderBeans.size() < 10) {
                            hasMore = false;
                        } else hasMore = true;
                        mAdapter.setItems(orderBeans);
                    } else hasMore = false;
                });
    }

    private MyReceiptListAdapter mAdapter = new MyReceiptListAdapter();

    private void initRecycleView() {
        mRecycleView.setOnLoadListener(o -> loadMore());
        mSwipeRefreshLayout.setRefreshing(false);
        mSwipeRefreshLayout.setOnRefreshListener(() -> refresh());
        mRecycleView.setAdapter(mAdapter);
    }

    private void refresh() {
        Log.e("", "DispatchedFragment - refresh");
        loadData();
    }

    private void loadMore() {
        Log.e("", "DispatchedFragment - loadMore");
        if (hasMore) {
            ++pageNum;
            loadData();
        }
    }

    private void hideRefresh() {
        mSwipeRefreshLayout.setRefreshing(false);
    }
}
