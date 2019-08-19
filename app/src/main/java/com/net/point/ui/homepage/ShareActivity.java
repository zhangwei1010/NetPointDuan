package com.net.point.ui.homepage;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;

import com.net.point.R;
import com.net.point.base.BaseActivity;
import com.net.point.utils.BitMapUtils;
import com.net.point.utils.SpUtils;
import com.net.point.widget.SharePopupWindow;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;

//分享页面
public class ShareActivity extends BaseActivity {

    private ImageView iv_share_background;

    public static void start(Context context) {
        context.startActivity(new Intent(context, ShareActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContentView(R.layout.activity_share);
        setContentTitle(R.string.share_friends);
        showShareView(true);
        iv_share_background = findViewById(R.id.iv_share_background);
        Observable.timer(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(aLong -> showShareView());
    }

    @Override
    public void onShare(View v) {
        showShareView();
    }

    private SharePopupWindow sharePopupWindow;

    private void showShareView() {
        if (sharePopupWindow != null) {
            sharePopupWindow = null;
        }
        sharePopupWindow = new SharePopupWindow(this, i -> {
            SHARE_MEDIA share_media = null;
            if (i == 1) {
                share_media = SHARE_MEDIA.QQ;
                SharePic(this, "qqfenxiang", "分享内容", share_media, SpUtils.getAvatarUrl(), umShareListener);
            } else if (i == 5) {
                share_media = SHARE_MEDIA.WEIXIN;
                SharePic(this, "weixinfenxiang", "分享内容", share_media, SpUtils.getAvatarUrl(), umShareListener);
            }
        });
        sharePopupWindow.showPopupWindow(iv_share_background);
    }

    /**
     * 分享监听
     */
    public UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA share_media) {
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);
            Toast.makeText(ShareActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(ShareActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(ShareActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    /**
     * 分享
     *
     * @param mActivity
     * @param title     标题
     * @param context   内容
     * @param Sharetype 分享方式
     *                  SHARE_MEDIA.WEIXIN_FAVORITE
     *                  SHARE_MEDIA.WEIXIN_CIRCLE
     *                  SHARE_MEDIA.WEIXIN
     *                  SHARE_MEDIA.SINA
     *                  SHARE_MEDIA.QZONE
     *                  SHARE_MEDIA.QQ
     * @param picurl    图片地址
     */
    public void SharePic(Activity mActivity, String title, String context, SHARE_MEDIA Sharetype, String picurl, UMShareListener umShareListener) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(mActivity, mPermissionList, 123);
        }
        ShareAction shareAction = new ShareAction(mActivity);
        if (!TextUtils.isEmpty(picurl)) {
            final UMWeb web = new UMWeb(picurl); //切记切记 这里分享的链接必须是http开头
            web.setTitle(title);//标题
            web.setDescription(context);//描述
            shareAction.setPlatform(Sharetype)
                    .withMedia(web)
                    .setCallback(umShareListener)
                    .share();
        } else {
            Bitmap bt = BitmapFactory.decodeResource(this.getResources(), R.mipmap.logo);
            Bitmap bitmap = BitMapUtils.compressSize(bt, 2);
            UMImage image = new UMImage(this, bitmap);//分享图标
            final UMWeb web = new UMWeb(picurl); //切记切记 这里分享的链接必须是http开头
            web.setThumb(image);  //缩略图
            shareAction.setPlatform(Sharetype)
                    .withText(context)
                    .withMedia(image)
                    .setCallback(umShareListener)
                    .share();
        }
    }

    /**
     * 友盟QQ登录需要的回调 在有些低端手机上登录之后不走回调，需要这个方法
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (sharePopupWindow != null) {
            sharePopupWindow = null;
        }
    }
}
