package com.net.point.ui.mine;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.net.point.R;
import com.net.point.base.BaseActivity;
import com.net.point.event.ReSettingIconEvent;
import com.net.point.glide.GlideUtils;
import com.net.point.ui.login.FoundPasswordActivity;
import com.net.point.utils.BitMapUtils;
import com.net.point.utils.FileUtil;
import com.net.point.utils.SpUtils;
import com.net.point.widget.PhotoPopupWindow;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonInforActivity extends BaseActivity {
    //拍照和写入权限
    private String[] permisions = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};
    @BindView(R.id.iv_user_icon)
    ImageView iv_user_icon;
    @BindView(R.id.iv_user_name)
    TextView iv_user_name;


    public static void start(Context context) {
        context.startActivity(new Intent(context, PersonInforActivity.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContentView(R.layout.activity_person_infor);
        setContentTitle("个人信息");
        ButterKnife.bind(this);
        GlideUtils.showImageViewToCircle(this, R.drawable.dog, SpUtils.getAvatarUrl(), iv_user_icon);
    }

    @OnClick({R.id.iv_user_icon, R.id.iv_user_name})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_user_icon:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//大于Android 6.0
                    if (hasPermission(permisions))
                        showPopupWindow();
                    else requestPermission(HAVE_CAMERA_PERMISSION, permisions);
                } else showPopupWindow();
                break;
            case R.id.iv_user_name:
                break;
        }
    }

    public static final int TAKE_PHOTO_CODE = 1;
    public static final int CHOOSE_PHOTO_CODE = 2;
    public static final int HAVE_CAMERA_PERMISSION = 4;

    private Bitmap takePhotobm = null;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {//获取系统照片上传
            switch (requestCode) {
                case TAKE_PHOTO_CODE:
                    try {
                        takePhotobm = BitMapUtils.getBitmapFormUri(mUri, this);
                        if (takePhotobm != null) savePicture();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case CHOOSE_PHOTO_CODE:
                    Uri uri = data.getData();
                    String choosePath = FileUtil.getFilePathByUri(this, uri);
                    if (!TextUtils.isEmpty(choosePath)) {
                        SpUtils.saveAvatarUrl(choosePath);
                        GlideUtils.showImageViewToCircle(this, R.drawable.dog, choosePath, iv_user_icon);
                    }
                    break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean allowAllPermission = false;
        for (int i = 0; i < grantResults.length; i++) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {//被拒绝授权
                allowAllPermission = false;
                break;
            }
            allowAllPermission = true;
        }
        if (allowAllPermission) {
            switch (requestCode) {
                case HAVE_CAMERA_PERMISSION:
                    showPopupWindow();
                    break;
            }
        } else {
            Toast.makeText(this, "该功能需要授权方可使用", Toast.LENGTH_SHORT).show();
        }
    }

    private void showPopupWindow() {
        new PhotoPopupWindow(this, aBoolean1 -> {
            //拍照
            if (!aBoolean1) return;
            takePhoto();
        }, aBoolean2 -> {
            //从相册中选择图片
            if (!aBoolean2) return;
            chooseFromAlbum();
        }, true, popupWindow -> {
            if (TextUtils.isEmpty(SpUtils.getAvatarUrl())) {
                toast("照片保存失败！");
            } else toast("照片保存成功，照片保存在" + SpUtils.getAvatarUrl() + "文件之中！");
        }).show();
    }

    private void savePicture() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String format = dateFormat.format(new Date());
//        String fileName = getFilesDir() + File.separator + "images" + File.separator + "modeify.jpg";
        String fileName = getFilesDir() + File.separator + "images" + File.separator + format + ".jpg";
        File file = new File(fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdir();//创建文件夹
        }
        try {
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
            takePhotobm.compress(Bitmap.CompressFormat.JPEG, 80, bos);//向缓冲区压缩图片
            bos.flush();
            bos.close();
            //Toast.makeText(PersonInforActivity.this, "照片保存成功，照片保存在" + fileName + "文件之中！", Toast.LENGTH_LONG).show();
            GlideUtils.showImageViewToCircle(this, R.drawable.dog, fileName, iv_user_icon);
            SpUtils.saveAvatarUrl(fileName);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            //Toast.makeText(PersonInforActivity.this, "照片保存失败！" + e.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void chooseFromAlbum() {
        Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
        intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intentToPickPic, CHOOSE_PHOTO_CODE);
    }

    private Uri mUri;

    private void takePhoto() {
        // 步骤一：创建存储照片的文件
        String fileName = getFilesDir() + File.separator + "images" + File.separator + "modeify.jpg";
        try {///data/user/0/com.net.point/files/images/test.jpg
            File file = new File(fileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdir();
            }
            Intent intent = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //这一句非常重要
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                //步骤二：Android 7.0及以上获取文件 Uri
                mUri = FileProvider.getUriForFile(PersonInforActivity.this, "com.net.point.fileprovider", file);
            } else {
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //步骤三：获取文件Uri
                mUri = Uri.fromFile(file);
            }
            //步骤四：调取系统拍照
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
            startActivityForResult(intent, TAKE_PHOTO_CODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBack(View v) {
        EventBus.getDefault().post(new ReSettingIconEvent());
        finish();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK) {
            EventBus.getDefault().post(new ReSettingIconEvent());
        }
        return super.onKeyDown(keyCode, event);
    }
}
