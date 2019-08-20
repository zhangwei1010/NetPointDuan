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
import com.net.point.response.OrderDetailsBean;
import com.net.point.utils.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DispatchedAdapter extends CommonItemAdapter<OrderDetailsBean,

        DispatchedAdapter.DispatchedHolderView> {

    @Override
    protected void onBindViewHolder(@NonNull DispatchedHolderView holder, @Nullable OrderDetailsBean item) {
        AppUtils.setTexts(holder.tv_order_number, item.number);
        AppUtils.setTexts(holder.tv_time, item.begintime);
        AppUtils.setTexts(holder.tv_from_address, item.fromcityname);
        AppUtils.setTexts(holder.tv_to_address, item.tocityname);
        AppUtils.setTexts(holder.tv_from_person, item.fromname);
        AppUtils.setTexts(holder.tv_to_person, item.toname);
    }

    @NonNull
    @Override
    public DispatchedHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(NetPointApplication.getInstance())
                .inflate(R.layout.item_dispatched
                        , parent, false);
        return new DispatchedHolderView(view);
    }

    public class DispatchedHolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.tv_order_number)
        TextView tv_order_number;
        @BindView(R.id.tv_from_address)
        TextView tv_from_address;
        @BindView(R.id.tv_to_address)
        TextView tv_to_address;
        @BindView(R.id.tv_from_person)
        TextView tv_from_person;
        @BindView(R.id.tv_to_person)
        TextView tv_to_person;

        public DispatchedHolderView(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
