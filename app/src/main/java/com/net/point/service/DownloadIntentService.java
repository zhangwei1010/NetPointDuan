package com.net.point.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;

import com.net.point.BuildConfig;
import com.net.point.R;
import com.net.point.ui.MainActivity;
import com.net.point.utils.FileManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.UUID;

public class DownloadIntentService extends IntentService {
    /**
     * 默认超时时间
     */
    private static final int DEFAULT_TIME_OUT = 10 * 1000;
    /**
     * 缓存大小
     */
    private static final int BUFFER_SIZE = 10 * 1024;

    public static final String INTENT_VERSION_NAME = "service.intent.version_name";
    public static final String INTENT_DOWNLOAD_URL = "service.intent.download_url";
    public static final String SAVE_FILE_NAME = "CMPP.apk";

    private static final int NOTIFICATION_ID = UUID.randomUUID().hashCode();

    private String mDownloadUrl;
    private String mVersionName;

    public DownloadIntentService() {
        super("DownloadService");
    }

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public DownloadIntentService(String name) {
        super(name);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 移除通知
        mNotifyManager.cancel(NOTIFICATION_ID);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            mVersionName = intent.getStringExtra(INTENT_VERSION_NAME);
            mDownloadUrl = intent.getStringExtra(INTENT_DOWNLOAD_URL);
            if (!TextUtils.isEmpty(mVersionName) && !TextUtils.isEmpty(mDownloadUrl)) {
                Log.i("TEST", "INTENT_VERSION_NAME---" + mVersionName);
                Log.i("TEST", "INTENT_DOWNLOAD_URL---" + mDownloadUrl);
                initNotify();
                startDownload();
            }
        }
    }

    private static final String CHANNEL_ID = "channel_id";   //通道渠道id
    public static final String CHANEL_NAME = "chanel_name"; //通道渠道名称

    /**
     * 初始化通知栏
     */

    private Notification.Builder builder1;
    private NotificationManager mNotifyManager;
    private NotificationCompat.Builder builder2;

    private void initNotify() {
        String title = String.format("%s V%s", getString(getApplicationInfo().labelRes), mVersionName);
        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //创建 通知通道  channelid和channelname是必须的（自己命名就好）
            channel = new NotificationChannel(CHANNEL_ID, CHANEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(true);//是否在桌面icon右上角展示小红点
            channel.setLightColor(Color.GREEN);//小红点颜色
            channel.setShowBadge(false); //是否在久按桌面图标时显示此渠道的通知
        }
        //获取Notification实例   获取Notification实例有很多方法处理    在此我只展示通用的方法（虽然这种方式是属于api16以上，但是已经可以了，毕竟16以下的Android机很少了，如果非要全面兼容可以用）
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //向上兼容 用Notification.Builder构造notification对象
            builder1 = new Notification.Builder(this, CHANNEL_ID)
                    .setContentTitle(title)
                    //通知首次出现在通知栏，带上升动画效果的
                    .setTicker("捷账宝新版本诚邀体验")
                    //通常是用来表示一个后台任务
                    .setOngoing(true).setSmallIcon(R.mipmap.logo)
                    .setWhen(System.currentTimeMillis())
                    .setColor(Color.parseColor("#FEDA26"));
        } else {
            //向下兼容 用NotificationCompat.Builder构造notification对象
            builder2 = new NotificationCompat.Builder(this)
                    .setContentTitle(title)
                    //通知首次出现在通知栏，带上升动画效果的
                    .setTicker("捷账宝新版本诚邀体验")
                    //通常是用来表示一个后台任务
                    .setOngoing(true).setSmallIcon(R.mipmap.logo)
                    .setWhen(System.currentTimeMillis())
                    .setColor(Color.parseColor("#FEDA26"));
        }
        //发送通知
        //创建一个通知管理器
        mNotifyManager = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotifyManager.createNotificationChannel(channel);
        }
    }

    /**
     * 开始下载
     */
    private void startDownload() {
        InputStream in = null;
        FileOutputStream out = null;
        try {
            URL url = new URL(mDownloadUrl);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.setRequestMethod("GET");
            urlConnection.setDoOutput(false);
            urlConnection.setConnectTimeout(DEFAULT_TIME_OUT);
            urlConnection.setReadTimeout(DEFAULT_TIME_OUT);
            urlConnection.setRequestProperty("Connection", "Keep-Alive");
            urlConnection.setRequestProperty("Charset", "UTF-8");
            urlConnection.setRequestProperty("Accept-Encoding", "gzip, deflate");

            urlConnection.connect();
            long bytetotal = urlConnection.getContentLength();
            long bytesum = 0;
            int byteread = 0;
            in = urlConnection.getInputStream();
            String apkDownLoadDir = FileManager.getApkDownLoadDir(getApplication());
            File apkFile = new File(apkDownLoadDir, SAVE_FILE_NAME);
            out = new FileOutputStream(apkFile);
            byte[] buffer = new byte[BUFFER_SIZE];
            int oldProgress = 0;
            while ((byteread = in.read(buffer)) != -1) {
                bytesum += byteread;
                out.write(buffer, 0, byteread);
                int progress = (int) (bytesum * 100L / bytetotal);
                // 如果进度与之前进度相等，则不更新，如果更新太频繁，否则会造成界面卡顿
                if (progress != oldProgress) {
                    updateProgress(progress);
                }
                oldProgress = progress;
            }
            // 下载完成
            installAPk();
        } catch (Exception e) {
            if (e != null) {
                Log.e("TEST", "download apk file error:" + e.getMessage());
            } else {
                Log.e("TEST", "download apk file error:下载失败");
            }
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException ignored) {
                }
            }
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    /**
     * 更新通知栏的进度(下载中)
     *
     * @param progress
     */
    private void updateProgress(int progress) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder1.setContentText(String.format("正在下载:%1$d%%", progress)).setProgress(100, progress, false);
            PendingIntent pendingintent = PendingIntent.getActivity(this, 0, new Intent(), PendingIntent
                    .FLAG_UPDATE_CURRENT);
            builder1.setContentIntent(pendingintent);
            mNotifyManager.notify(NOTIFICATION_ID, builder1.build());
        } else {
            builder2.setContentText(String.format("正在下载:%1$d%%", progress)).setProgress(100, progress, false);
            PendingIntent pendingintent = PendingIntent.getActivity(this, 0, new Intent(), PendingIntent
                    .FLAG_UPDATE_CURRENT);
            builder2.setContentIntent(pendingintent);
            mNotifyManager.notify(NOTIFICATION_ID, builder2.build());
        }
    }

    /**
     * 开始安装
     */
    private void installAPk() {
        MainActivity.getInstance().startActivity(getInstallIntent());
    }

    /**
     * 启动安装界面
     *
     * @return
     */
    private Intent getInstallIntent() {
        File apkInstallDir = FileManager.getApkInstallDir(getApplication());
        Log.i("TEST", "路径---" + apkInstallDir.getAbsolutePath());
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            intent.setDataAndType(Uri.fromFile(apkInstallDir), "application/vnd.android.package-archive");
        } else {
            // 声明需要的零时权限
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            // 第二个参数，即第一步中配置的authorities
            Uri contentUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileprovider",
                    apkInstallDir);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        }
        return intent;
    }

    /**
     * 合成更新的Icon
     *
     * @param drawable
     * @return
     */
    public Bitmap drawableToBitmap(Drawable drawable) {
        Bitmap bitmap = Bitmap.createBitmap(
                drawable.getIntrinsicWidth(),
                drawable.getIntrinsicHeight(),
                drawable.getOpacity() != PixelFormat.OPAQUE ? Bitmap.Config.ARGB_8888 : Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    /**
     * 获取App的Icon
     *
     * @param context
     * @return
     */
    public Drawable getAppIcon(Context context) {
        try {
            return context.getPackageManager().getApplicationIcon(context.getPackageName());
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}