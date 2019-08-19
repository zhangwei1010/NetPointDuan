package com.net.point.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.net.point.R;
import com.net.point.utils.AppUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NetPointAdapter extends RecyclerView.Adapter<NetPointAdapter.ViewHolder> {

    private Context context;
    private List<String> provinceList;

    public NetPointAdapter(Context context, List<String> provinceList) {
        this.context = context;
        this.provinceList = provinceList;
    }

    @NonNull
    @Override
    public NetPointAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate( R.layout.item_net_point_province, null,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NetPointAdapter.ViewHolder holder, int position) {
        AppUtils.setTexts(holder.tv_province, provinceList.get(position));
        holder.itemView.setOnClickListener(view -> {

        });
    }

    @Override
    public int getItemCount() {
        return provinceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_province)
        TextView tv_province;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
