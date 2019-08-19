package com.net.point.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.net.point.R;

public class IntegralPopupWindow extends PopupWindow implements View.OnClickListener {

    private View mPopView;
    private LinearLayout llTop;
    private Button btn_know;
    private OnItemClickListener mListener;

    public IntegralPopupWindow(Activity context) {
        super(context);        // TODO Auto-generated constructor stub
        init(context);
        setPopupWindow(context);
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {        // TODO Auto-generated method stub
        LayoutInflater inflater = LayoutInflater.from(context);        //绑定布局
        mPopView = inflater.inflate(R.layout.integral_popup_window, null);
        llTop = mPopView.findViewById(R.id.llTop);
    }

    /**
     * 设置窗口的相关属性
     */
    @SuppressLint("InlinedApi")
    private void setPopupWindow(Activity context) {
        this.setContentView(mPopView);// 设置View
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);// 设置弹出窗口的宽
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);// 设置弹出窗口的高
        this.setFocusable(true);// 设置弹出窗口可
//        this.setAnimationStyle(R.style.mypopwindow_anim_style);// 设置动画
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));// 设置背景透明
        this.setOutsideTouchable(true);

        llTop.setOnClickListener(view -> dismiss());
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