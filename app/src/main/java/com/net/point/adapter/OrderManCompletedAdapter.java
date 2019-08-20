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
import com.net.point.ui.homepage.OrderManageDetailsActivity;
import com.net.point.utils.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

public class OrderManCompletedAdapter extends CommonItemAdapter<OrderDetailsBean,

        OrderManCompletedAdapter.MyBenefitsHolderView> {

    private String type = "";
    private Action1<String> action1;

    public OrderManCompletedAdapter(String type, Action1<String> action1) {
        this.type = type;
        this.action1 = action1;
    }

    @Override
    protected void onBindViewHolder(@NonNull MyBenefitsHolderView holder, @Nullable OrderDetailsBean item) {
        AppUtils.setTexts(holder.tv_number, "订单号： " + item.number);
        AppUtils.setTexts(holder.tv_contracts, "联系人： " + item.toname);
        AppUtils.setTexts(holder.tv_contracts_way, "联系方式： " + item.totel);
        AppUtils.setTexts(holder.tv_time, item.begintime);
//        AppUtils.setTexts(holder.tv_benefits, item.isHandle);
        if (type.equals("1")) {
            holder.tv_see_details.setText("查看详情");
            holder.tv_see_details.setOnClickListener(view -> OrderManageDetailsActivity.
                    start(NetPointApplication.getInstance(), item.number));
        } else {
            holder.tv_see_details.setOnClickListener(view -> {
                if (action1 != null) {
                    action1.call(item.number);
                }
            });
            holder.tv_see_details.setText("派单");
        }
        holder.itemView.setOnClickListener(view -> OrderManageDetailsActivity.
                start(NetPointApplication.getInstance(), item.number));
    }

    @NonNull
    @Override
    public MyBenefitsHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(NetPointApplication.getInstance())
                .inflate(R.layout.item_order_manage_completed
                        , parent, false);
        return new MyBenefitsHolderView(view);
    }


    public class MyBenefitsHolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.tv_number)
        TextView tv_number;
        @BindView(R.id.tv_see_details)
        TextView tv_see_details;
        @BindView(R.id.tv_contracts)
        TextView tv_contracts;
        @BindView(R.id.tv_contracts_way)
        TextView tv_contracts_way;

        public MyBenefitsHolderView(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
