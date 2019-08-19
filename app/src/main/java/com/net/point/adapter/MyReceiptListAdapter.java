package com.net.point.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

public class MyReceiptListAdapter extends CommonItemAdapter<OrderDetailsBean,

        MyReceiptListAdapter.MyReceiptHolderView> {

    @Override
    protected void onBindViewHolder(@NonNull MyReceiptHolderView holder, @Nullable OrderDetailsBean item) {
        AppUtils.setTexts(holder.tv_number, item.number);
        AppUtils.setTexts(holder.tv_receipt_type, item.isgoods);
        AppUtils.setTexts(holder.tv_from_person, item.fromname);
        AppUtils.setTexts(holder.tv_to_person, item.fromname);
        AppUtils.setTexts(holder.tv_data, item.createtime);
        if (item.paymentflag == 0) {//已付过了（现付）
            holder.iv_pay.setImageResource(R.drawable.now_pay);
        } else if (item.paymentflag == 1) {//未付过（到付）
            holder.iv_pay.setImageResource(R.drawable.arrive_pay);
        }
    }

    @NonNull
    @Override
    public MyReceiptHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(NetPointApplication.getInstance())
                .inflate(R.layout.item_my_receipt
                        , parent, false);
        return new MyReceiptHolderView(view);
    }

    public class MyReceiptHolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_number)
        TextView tv_number;
        @BindView(R.id.tv_receipt_type)
        TextView tv_receipt_type;
        @BindView(R.id.tv_from_person)
        TextView tv_from_person;
        @BindView(R.id.tv_to_person)
        TextView tv_to_person;
        @BindView(R.id.tv_data)
        TextView tv_data;
        @BindView(R.id.iv_pay)
        ImageView iv_pay;

        public MyReceiptHolderView(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
