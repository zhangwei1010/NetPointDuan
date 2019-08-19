package com.net.point.ui.homepage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.net.point.R;
import com.net.point.adapter.MyPenaltyListAdapter;
import com.net.point.base.BaseActivity;
import com.net.point.model.HomeModel;
import com.net.point.response.MyMessageBean;
import com.net.point.response.OrderDetailsBean;
import com.net.point.utils.SpUtils;
import com.net.point.view.HeadRecycleView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//我的罚款
public class MyPenaltyActivity extends BaseActivity {

    @BindView(R.id.mRecycleView)
    HeadRecycleView mRecycleView;
    @BindView(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.ll_content)
    LinearLayout ll_content;
    private int type = -1;
    private int pageNum = 1;

    public static void start(Context context, int type) {
        Intent intent = new Intent(context, MyPenaltyActivity.class);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContentView(R.layout.activity_my_penalty);
        ButterKnife.bind(this);
        initRecycleView();
        initData();
        loadData();
    }

    private HomeModel model = new HomeModel();

    private void loadData() {
        model.loadMyMessage(SpUtils.getIncNumber(), pageNum + "", pageSize, e -> {
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
                        List<MyMessageBean> orderBeans = result.getData();
                        if (orderBeans == null || orderBeans.isEmpty() || orderBeans.size() < 10) {
                            hasMore = false;
                        } else hasMore = true;
                        mAdapter.setItems(orderBeans);
                    } else hasMore = false;
                });
    }

    private MyPenaltyListAdapter mAdapter = new MyPenaltyListAdapter();

    private void initData() {
        type = getIntent().getIntExtra("type", -1);
        if (type == 1) {
            ll_content.setVisibility(View.GONE);
            setContentTitle(R.string.my_message);
        } else if (type == 2) {
            ll_content.setVisibility(View.VISIBLE);
            setContentTitle(R.string.my_penalty);
        }
    }

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


//    @OnClick({R.id.ll_my_benefits, R.id.ll_my_penalty, R.id.ll_mine})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.ll_my_benefits:
//                break;
//            case R.id.ll_my_penalty:
//                break;
//        }
//    }
}
