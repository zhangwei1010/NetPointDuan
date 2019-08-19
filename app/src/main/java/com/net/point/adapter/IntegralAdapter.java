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
import com.net.point.response.IntegralBean;
import com.net.point.utils.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IntegralAdapter extends CommonItemAdapter<IntegralBean,

        IntegralAdapter.IntegralHolderView> {

    @Override
    protected void onBindViewHolder(@NonNull IntegralHolderView holder, @Nullable IntegralBean item) {
        AppUtils.setTexts(holder.tv_total_count, item.crttime);
        AppUtils.setTexts(holder.tv_data, item.typeRemark);
        AppUtils.setTexts(holder.tv_integral, "+" + item.pointnum);
    }

    @NonNull
    @Override
    public IntegralHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(NetPointApplication.getInstance())
                .inflate(R.layout.item_integral
                        , parent, false);
        return new IntegralHolderView(view);
    }


    public class IntegralHolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_total_count)
        TextView tv_total_count;
        @BindView(R.id.tv_data)
        TextView tv_data;
        @BindView(R.id.tv_integral)
        TextView tv_integral;

        public IntegralHolderView(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
