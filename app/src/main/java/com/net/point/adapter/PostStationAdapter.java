package com.net.point.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.baidu.mapapi.model.LatLng;
import com.net.point.NetPointApplication;
import com.net.point.R;
import com.net.point.response.PostStationBean;
import com.net.point.utils.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

public class PostStationAdapter extends CommonItemAdapter<PostStationBean,

        PostStationAdapter.UnDispatchedHolderView> {
    private Action1<Integer> LatLng;

    public PostStationAdapter(Action1<Integer> LatLng) {
        this.LatLng = LatLng;
    }

    @Override
    protected void onBindViewHolder(@NonNull UnDispatchedHolderView holder, @Nullable PostStationBean item) {
        AppUtils.setTexts(holder.tv_current_position, item.address);
        AppUtils.setTexts(holder.tv_distance, item.distance);
        holder.tv_now_go.setOnClickListener(v -> {
            if (LatLng != null) {
                LatLng.call(getPosition());
            }
        });
        if (getPosition() % 2 == 0) {
            holder.iv_icon.setImageResource(R.drawable.express_point_red);
        } else {
            holder.iv_icon.setImageResource(R.drawable.express_point_blue);
        }
    }

    @NonNull
    @Override
    public UnDispatchedHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(NetPointApplication.getInstance())
                .inflate(R.layout.item_post_station
                        , parent, false);
        return new UnDispatchedHolderView(view);
    }

    public class UnDispatchedHolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_icon)
        ImageView iv_icon;
        @BindView(R.id.tv_current_position)
        TextView tv_current_position;
        @BindView(R.id.tv_distance)
        TextView tv_distance;
        @BindView(R.id.tv_now_go)
        TextView tv_now_go;

        public UnDispatchedHolderView(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
