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
import com.net.point.response.QuestionPieceBean;
import com.net.point.utils.AppUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QuestionPieceAdapter extends CommonItemAdapter<QuestionPieceBean,

        QuestionPieceAdapter.QuestionHolderView> {

    @Override
    protected void onBindViewHolder(@NonNull QuestionHolderView holder, @Nullable QuestionPieceBean item) {
        AppUtils.setTexts(holder.tv_name, item.fromName);
        AppUtils.setTexts(holder.tv_mobile, item.billNum);
        AppUtils.setTexts(holder.tv_addressee, item.address);
        AppUtils.setTexts(holder.tv_reason, item.troubledtype);
    }

    @NonNull
    @Override
    public QuestionHolderView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(NetPointApplication.getInstance())
                .inflate(R.layout.item_question_piece
                        , parent, false);
        return new QuestionHolderView(view);
    }

    public class QuestionHolderView extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_name)
        TextView tv_name;
        @BindView(R.id.tv_mobile)
        TextView tv_mobile;
        @BindView(R.id.tv_addressee)
        TextView tv_addressee;
        @BindView(R.id.tv_reason)
        TextView tv_reason;

        public QuestionHolderView(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
