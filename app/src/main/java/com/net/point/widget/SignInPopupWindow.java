package com.net.point.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.net.point.R;
import com.net.point.request.HttpResult;
import com.net.point.response.AddSignMsgBean;
import com.net.point.utils.AppUtils;

import static com.net.point.R.id;

//签到弹窗
public class SignInPopupWindow extends PopupWindow implements View.OnClickListener {

    private View mPopView;
    private ImageView ivClose;
    private TextView tv_sign_status, tv_signDays, tv_receivePoint, tv_points, tv_days,tv_sign_how;
    private Context context;
    private HttpResult<AddSignMsgBean> httpResult;

    public SignInPopupWindow(Context context, HttpResult<AddSignMsgBean> httpResult) {
        super(context);        // TODO Auto-generated constructor stub
        this.context = context;
        this.httpResult = httpResult;
        init(context);
        setPopupWindow(context);
        initData();
    }

    private void initData() {
        AddSignMsgBean signMsgBean = httpResult.getData();
        AppUtils.setTexts(tv_sign_status, httpResult.getMsg());
        AppUtils.setTexts(tv_signDays, signMsgBean.signDays + "");
        AppUtils.setTexts(tv_sign_how, signMsgBean.signDays + "");
        AppUtils.setTexts(tv_receivePoint, "+" + signMsgBean.receivePoint + "");
        AppUtils.setTexts(tv_days, signMsgBean.days + "");
        AppUtils.setTexts(tv_points, signMsgBean.points + "积分");
    }

    /**
     * 初始化
     *
     * @param context
     */
    private void init(Context context) {        // TODO Auto-generated method stub
        LayoutInflater inflater = LayoutInflater.from(context);        //绑定布局
        mPopView = inflater.inflate(R.layout.sign_in_popup_window, null);
        findView();
    }

    private void findView() {
        ivClose = mPopView.findViewById(id.ivClose);
        tv_sign_status = mPopView.findViewById(id.tv_sign_status);
        tv_signDays = mPopView.findViewById(id.tv_signDays);
        tv_receivePoint = mPopView.findViewById(id.tv_receivePoint);
        tv_points = mPopView.findViewById(id.tv_points);
        tv_days = mPopView.findViewById(id.tv_days);
        tv_sign_how = mPopView.findViewById(id.tv_sign_how);
    }

    /**
     * 设置窗口的相关属性
     *
     * @param context
     */
    @SuppressLint("InlinedApi")
    private void setPopupWindow(Context context) {
        this.setContentView(mPopView);// 设置View
        this.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);// 设置弹出窗口的宽
        this.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);// 设置弹出窗口的高
        this.setFocusable(true);// 设置弹出窗口可
        this.setBackgroundDrawable(new ColorDrawable(0x00000000));// 设置背景透明
        this.setOutsideTouchable(true);
        ivClose.setOnClickListener(view -> dismiss());
    }

    public void show() {
        showAsDropDown(mPopView);
    }


    @Override
    public void onClick(View v) {        // TODO Auto-generated method stub
    }
}