package com.net.point.ui.homepage;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.RecyclerView;

import com.net.point.R;
import com.net.point.adapter.OrderInsertAdapter;
import com.net.point.base.BaseActivity;
import com.net.point.model.HomeModel;
import com.net.point.response.OrderStrackBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//查询订单
public class InsertOrderActivity extends BaseActivity {

    @BindView(R.id.mRecycleView)
    RecyclerView mRecycleView;
    @BindView(R.id.ed_insert_order)
    EditText ed_insert_order;
    @BindView(R.id.ll_no_data)
    LinearLayout ll_no_data;
    @BindView(R.id.ll_content)
    LinearLayout ll_content;

    private HomeModel model = new HomeModel();

    public static void start(Context context) {
        context.startActivity(new Intent(context, InsertOrderActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentTitle(R.string.insert_order);
        addContentView(R.layout.activity_insert_order);
        ButterKnife.bind(this);
        ed_insert_order.addTextChangedListener(new TextContentTextWatcher());
        initRecycleView();
    }

    private OrderInsertAdapter mAdapter = new OrderInsertAdapter();

    @Override
    public void onSearch(View v) {
        super.onSearch(v);
    }

    private String orderNumber;

    class TextContentTextWatcher implements TextWatcher {

        private String keyWord;

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int j, int k) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int j, int k) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            keyWord = editable.toString();
            if (TextUtils.isEmpty(keyWord)) return;
            insertOrder(keyWord);
        }
    }

    private void insertOrder(String orderNumber) {
        showProgressDialog();
        model.insertOrderFromNumber(orderNumber, e -> {
            e.printStackTrace();
            hideProgressDialog();
            Log.e("", e.getMessage());
        }, result -> {
            hideProgressDialog();
            if (result.isSuccess()) {
                if (result.getData() != null && result.getData().size() > 0) {
                    hasData();
                    List<OrderStrackBean> orderBeans = result.getData();
                    mAdapter.setItems(orderBeans);
                } else noData();
            } else noData();
        });
    }

    private void noData() {
        ll_content.setVisibility(View.GONE);
        ll_no_data.setVisibility(View.VISIBLE);
    }

    private void hasData() {
        ll_content.setVisibility(View.VISIBLE);
        ll_no_data.setVisibility(View.GONE);
    }

    private void initRecycleView() {
        mRecycleView.setAdapter(mAdapter);
    }
}
