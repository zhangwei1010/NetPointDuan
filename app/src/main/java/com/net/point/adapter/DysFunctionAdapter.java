package com.net.point.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.net.point.NetPointApplication;
import com.net.point.R;
import com.net.point.glide.GlideUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.functions.Action1;

public class DysFunctionAdapter extends CommonItemAdapter<String,

        DysFunctionAdapter.MyBenefitsHolderView> {

    private Action1 action1;

    public DysFunctionAdapter(Action1 action1) {
        this.action1 = action1;
    }

    @Override
    protected void onBindViewHolder(@NonNull MyBenefitsHolderView holder, @Nullable String url) {
//        AppUtils.setTexts(holder.tv_date, item.date);
//        AppUtils.setTexts(holder.tv_benefits_place, item.title);
//        AppUtils.setTexts(holder.tv_benefits, item.isHandle);
        if (getItemCount() - 1 == getPosition()) {
            holder.iv_add_picture.setImageResource(R.drawable.white_add);
            holder.iv_add_picture.setOnClickListener(view -> action1.call(true));
        } else {
            GlideUtils.showImageView(NetPointApplication.getInstance(), R.drawable.dog,
                    url, holder.iv_add_picture);
        }
    }

    @NonNull
    @Override
    public MyBenefitsHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(NetPointApplication.getInstance())
                .inflate(R.layout.item_dys_function
                        , parent, false);
        return new MyBenefitsHolderView(view);
    }


    public class MyBenefitsHolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_add_picture)
        ImageView iv_add_picture;

        public MyBenefitsHolderView(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
