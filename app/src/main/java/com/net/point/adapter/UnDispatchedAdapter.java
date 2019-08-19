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
import rx.functions.Action1;

public class UnDispatchedAdapter extends CommonItemAdapter<OrderDetailsBean,

        UnDispatchedAdapter.UnDispatchedHolderView> {
    private int type = -1;
    private Action1<String> dispath;

    public UnDispatchedAdapter(int s, Action1<String> dispath) {
        this.dispath = dispath;
        type = s;
    }

    @Override
    protected void onBindViewHolder(@NonNull UnDispatchedHolderView holder, @Nullable OrderDetailsBean item) {
        AppUtils.setTexts(holder.tv_time, item.begintime);
        if (type == 1) {
            holder.tv_dispatched.setOnClickListener(v -> {
                dispath.call(item.number);
            });
            AppUtils.setTexts(holder.tv_order_status, "未派送");
            AppUtils.setTexts(holder.tv_dispatched, "派件");
        } else if (type == 2) {
            AppUtils.setTexts(holder.tv_order_status, "滞留件");
            AppUtils.setTexts(holder.tv_dispatched, "派件");
        } else if (type == 3) {
            AppUtils.setTexts(holder.tv_order_status, "快递损坏");
            AppUtils.setTexts(holder.tv_dispatched, "查看详情");
        }
        AppUtils.setTexts(holder.tv_order_number, item.number);
        AppUtils.setTexts(holder.tv_time, item.begintime);
        AppUtils.setTexts(holder.tv_from_address, item.fromcityname);
        AppUtils.setTexts(holder.tv_to_address, item.tocityname);
        AppUtils.setTexts(holder.tv_from_person, item.fromname);
        AppUtils.setTexts(holder.tv_to_person, item.toname);
    }

    @NonNull
    @Override
    public UnDispatchedHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(NetPointApplication.getInstance())
                .inflate(R.layout.item_undispatched
                        , parent, false);
        return new UnDispatchedHolderView(view);
    }


    public class UnDispatchedHolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_time)
        TextView tv_time;
        @BindView(R.id.tv_order_status)
        TextView tv_order_status;
        @BindView(R.id.tv_dispatched)
        TextView tv_dispatched;
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

        public UnDispatchedHolderView(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
