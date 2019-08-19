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
import com.net.point.response.MyMessageBean;
import com.net.point.utils.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyPenaltyListAdapter extends CommonItemAdapter<MyMessageBean,

        MyPenaltyListAdapter.MyPenaltyHolderView> {

    @Override
    protected void onBindViewHolder(@NonNull MyPenaltyHolderView holder, @Nullable MyMessageBean item) {
        AppUtils.setTexts(holder.tv_date, item.createtime);
        AppUtils.setTexts(holder.tv_name, "姓名:" + item.name);
        AppUtils.setTexts(holder.tv_phone_number, "电话：" + item.phone);
        AppUtils.setTexts(holder.tv_reason, item.describe);
        AppUtils.setTexts(holder.tv_handle_status, item.status);
        holder.tv_comfirm.setOnClickListener(v -> {

        });
    }

    @NonNull
    @Override
    public MyPenaltyHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(NetPointApplication.getInstance())
                .inflate(R.layout.item_penalty, parent, false);
        return new MyPenaltyHolderView(view);
    }


    public class MyPenaltyHolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_date)
        TextView tv_date;
        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_phone_number)
        TextView tv_phone_number;
        @BindView(R.id.tv_reason)
        TextView tv_reason;
        @BindView(R.id.tv_handle_status)
        TextView tv_handle_status;
        @BindView(R.id.tv_comfirm)
        TextView tv_comfirm;

        public MyPenaltyHolderView(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
