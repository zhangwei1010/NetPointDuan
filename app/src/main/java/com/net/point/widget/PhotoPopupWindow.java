package com.net.point.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.net.point.R;

import rx.functions.Action1;

public class PhotoPopupWindow extends PopupWindow implements View.OnClickListener {

    private View mPopView;
    private TextView tvPhoto, tvChooseAlbum, tvCancel, tvSave;
    private LinearLayout llTop;
    private OnItemClickListener mListener;

    public PhotoPopupWindow(Activity context, Action1<Boolean> action1, Action1<Boolean> action2) {
        super(context);        // TODO Auto-generated constructor stub
        init(context);
        Action1<PhotoPopupWindow> windowAction1 = null;
        setPopupWindow(context, action1, action2, false, windowAction1);
    }

    public PhotoPopupWindow(Activity context, Action1<Boolean> action1, Action1<Boolean> action2, boolean saveView, Action1<PhotoPopupWindow> windowAction1) {
        super(context);        // TODO Auto-generated constructor stub
        init(context);
        setPopupWindow(context, action1, action2, saveView, windowAction1);
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {        // TODO Auto-generated method stub
        LayoutInflater inflater = LayoutInflater.from(context);        //绑定布局
        mPopView = inflater.inflate(R.layout.photo_popup_window, null);
        llTop = mPopView.findViewById(R.id.llTop);
        tvPhoto = mPopView.findViewById(R.id.tvPhoto);
        tvChooseAlbum = mPopView.findViewById(R.id.tvChooseAlbum);
        tvCancel = mPopView.findViewById(R.id.tvCancel);
        tvSave = mPopView.findViewById(R.id.tvSave);
    }

    /**
     * 设置窗口的相关属性
     *
     * @param context
     * @param action1
     * @param saveView
     */
    @SuppressLint("InlinedApi")
    private void setPopupWindow(Activity context, Action1<Boolean> action1, Action1<Boolean> action2, boolean saveView, Action1<PhotoPopupWindow> action3) {
        this.setContentView(mPopView);// 设置View
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);// 设置弹出窗口的宽
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);// 设置弹出窗口的高
        this.setFocusable(true);// 设置弹出窗口可
//        this.setAnimationStyle(R.style.mypopwindow_anim_style);// 设置动画
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));// 设置背景透明
        this.setOutsideTouchable(true);

        llTop.setOnClickListener(view -> dismiss());
        if (saveView) {
            tvSave.setVisibility(View.VISIBLE);
            tvSave.setOnClickListener(view -> action3.call(this));
        } else tvSave.setVisibility(View.GONE);

        tvPhoto.setOnClickListener(view -> {
            dismiss();
            action1.call(true);
        });
        tvChooseAlbum.setOnClickListener(view -> {
            dismiss();
            action2.call(true);
        });
        tvCancel.setOnClickListener(view -> dismiss());
    }

    public void show() {
        showAsDropDown(mPopView);
    }

    /**
     * 定义一个接口，公布出去 在Activity中操作按钮的单击事件
     */
    public interface OnItemClickListener {
        void setOnItemClick(View v);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onClick(View v) {        // TODO Auto-generated method stub
        if (mListener != null) {
            mListener.setOnItemClick(v);
        }
    }
}