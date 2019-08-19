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
import com.net.point.response.PenaltyBean;
import com.net.point.utils.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyBenefitsListAdapter extends CommonItemAdapter<PenaltyBean,

        MyBenefitsListAdapter.MyBenefitsHolderView> {

    @Override
    protected void onBindViewHolder(@NonNull MyBenefitsHolderView holder, @Nullable PenaltyBean item) {
        AppUtils.setTexts(holder.tv_date, item.date);
        AppUtils.setTexts(holder.tv_benefits_place, item.title);
        AppUtils.setTexts(holder.tv_benefits, item.isHandle);
    }

    @NonNull
    @Override
    public MyBenefitsHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(NetPointApplication.getInstance())
                .inflate(R.layout.item_my_benefits
                        , parent, false);
        return new MyBenefitsHolderView(view);
    }


    public class MyBenefitsHolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_date)
        TextView tv_date;
        @BindView(R.id.tv_benefits_place)
        TextView tv_benefits_place;
        @BindView(R.id.tv_benefits)
        TextView tv_benefits;

        public MyBenefitsHolderView(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
