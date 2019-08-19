package com.net.point.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.net.point.NetPointApplication;
import com.net.point.R;
import com.net.point.response.OrderStrackBean;
import com.net.point.utils.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class OrderInsertAdapter extends CommonItemAdapter<OrderStrackBean,

        OrderInsertAdapter.OrderInsertHolderView> {

    @Override
    protected void onBindViewHolder(@NonNull OrderInsertHolderView holder, @Nullable OrderStrackBean item) {
        String operationtime = item.getOperationtime();
        String[] split = operationtime.split("\\ ");
        AppUtils.setTexts(holder.tv_time1, split[0]);
        AppUtils.setTexts(holder.tv_time2, split[1]);
        AppUtils.setTexts(holder.tv_description, item.getDescription());
    }

    @NonNull
    @Override
    public OrderInsertHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(NetPointApplication.getInstance())
                .inflate(R.layout.item_order_insert
                        , parent, false);
        return new OrderInsertHolderView(view);
    }


    public class OrderInsertHolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_time1)
        TextView tv_time1;
        @BindView(R.id.tv_time2)
        TextView tv_time2;
        @BindView(R.id.tv_description)
        TextView tv_description;

        public OrderInsertHolderView(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
