package com.net.point.widget;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.net.point.R;
import com.net.point.utils.AppUtils;

public class SharePopupWindow extends PopupWindow implements OnClickListener {

    private View conentView;
    private Context mActivity;

    public SharePopupWindow(final Context mActivity, OnShareClickListener onShareClickListener) {
        this.mActivity = mActivity;
        this.onShareClickListener = onShareClickListener;
        LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        conentView = inflater.inflate(R.layout.umeng_share_custom_board_v5, null);
        this.setContentView(conentView);
        this.setWidth(LayoutParams.MATCH_PARENT);
        this.setHeight(LayoutParams.MATCH_PARENT);
        this.setFocusable(true);
        this.setOutsideTouchable(true);
        this.update();
        ColorDrawable dw = new ColorDrawable(0xb0000000);
        this.setBackgroundDrawable(dw);
        this.setAnimationStyle(R.style.AnimationPreview_v5);
        LinearLayout qqShare = conentView.findViewById(R.id.share_qq);
        LinearLayout wxShare = conentView.findViewById(R.id.share_wx);
        LinearLayout wbshare = conentView.findViewById(R.id.share_wb);
        LinearLayout circleshare = conentView.findViewById(R.id.share_friend);
        LinearLayout qzshare = conentView.findViewById(R.id.share_qz);
        conentView.findViewById(R.id.pop_dis).setOnTouchListener((v, event) -> {
            SharePopupWindow.this.dismiss();
            return false;
        });
        conentView.findViewById(R.id.tv_dis).setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                SharePopupWindow.this.dismiss();
            }
        });

        qqShare.setOnClickListener(this);
        wxShare.setOnClickListener(this);
        wbshare.setOnClickListener(this);
        circleshare.setOnClickListener(this);
        qzshare.setOnClickListener(this);
    }

    /**
     * showPopupWidow
     *
     * @param parent
     */
    public void showPopupWindow(View parent) {

        if (!this.isShowing()) {
            this.showAsDropDown(parent, 0, 5);
        } else {
            this.dismiss();
        }
    }

    private OnShareClickListener onShareClickListener;

    public interface OnShareClickListener {
        void onShareClick(int i);
    }

    public void removeShareClickListener() {
        onShareClickListener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.share_qq:
                if (!AppUtils.isInstalledApp(mActivity, "com.tencent.mobileqq")) {
                    Toast.makeText(mActivity, "请先安装QQ！", Toast.LENGTH_SHORT).show();
                    this.dismiss();
                    return;
                }
                if (onShareClickListener != null) {
                    onShareClickListener.onShareClick(1);
                }
                break;
            case R.id.share_qz:
                if (!AppUtils.isInstalledApp(mActivity, "com.tencent.mobileqq")) {
                    Toast.makeText(mActivity, "请先安装QQ！", Toast.LENGTH_SHORT).show();
                    this.dismiss();
                    return;
                }
                if (onShareClickListener != null) {
                    onShareClickListener.onShareClick(2);
                }
                break;
            case R.id.share_wx:
                if (!AppUtils.isInstalledApp(mActivity, "com.tencent.mm")) {
                    Toast.makeText(mActivity, "请先安装微信！", Toast.LENGTH_SHORT).show();
                    this.dismiss();
                    return;
                }
                if (onShareClickListener != null) {
                    onShareClickListener.onShareClick(3);
                }
                break;
            case R.id.share_wb:
                if (!AppUtils.isInstalledApp(mActivity, "com.sina.weibo")) {
                    Toast.makeText(mActivity, "请先安装微博！", Toast.LENGTH_SHORT).show();
                    this.dismiss();
                    return;
                }
                if (onShareClickListener != null) {
                    onShareClickListener.onShareClick(4);
                }
                break;
            case R.id.share_friend:
                if (!AppUtils.isInstalledApp(mActivity, "com.tencent.mm")) {
                    Toast.makeText(mActivity, "请先安装微信！", Toast.LENGTH_SHORT).show();
                    this.dismiss();
                    return;
                }
                if (onShareClickListener != null) {
                    onShareClickListener.onShareClick(5);
                }
                break;
            default:
                break;
        }
        this.dismiss();
    }
}
