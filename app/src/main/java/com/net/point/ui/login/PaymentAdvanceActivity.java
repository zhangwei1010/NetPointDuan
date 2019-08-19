package com.net.point.ui.login;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.net.point.R;
import com.net.point.base.BaseActivity;
import com.net.point.model.LoginModel;
import com.net.point.response.BankInfoBean;
import com.net.point.utils.AppUtils;
import com.net.point.utils.BitMapUtils;
import com.net.point.utils.FileUtil;
import com.net.point.utils.SpUtils;
import com.net.point.widget.PhotoPopupWindow;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//银行卡线下支付方式
public class PaymentAdvanceActivity extends BaseActivity {
    public static void start(Context context) {
        context.startActivity(new Intent(context, PaymentAdvanceActivity.class));
    }

    @BindView(R.id.ivUpload)
    ImageView ivUpload;
    @BindView(R.id.et_customer_name)
    EditText et_customer_name;
    @BindView(R.id.ed_bank_num)
    EditText ed_bank_num;
    @BindView(R.id.ed_bank_type)
    EditText ed_bank_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle(R.string.pay_way);
        addContentView(R.layout.activity_pay_way);
        ButterKnife.bind(this);
        getBankInfo();
    }

    private void getBankInfo() {
        model.getBankInfo(e -> e.printStackTrace(), result -> {
            if (result.isSuccess() && result.getData() != null) {
                BankInfoBean infoBean = result.getData();
                AppUtils.setTexts(et_customer_name, infoBean.bankcustomer);
                AppUtils.setTexts(ed_bank_num, infoBean.banknumber);
                AppUtils.setTexts(ed_bank_type, infoBean.banktype);
            }
        });
    }

    @OnClick({R.id.tvUpload, R.id.tvCommit})
    public void OnViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tvUpload:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//大于Android 6.0
                    if (hasPermission(permisions))
                        showPopupWindow();
                    else requestPermission(HAVE_CAMERA_PERMISSION, permisions);
                } else showPopupWindow();
                break;
            case R.id.tvCommit:
                uploadInfor();
                break;
        }
    }

    private LoginModel model = new LoginModel();
    private String imgValue = "";

    private void uploadInfor() {
        String signNetId = SpUtils.getSignNetId();
        String customer_name = et_customer_name.getText().toString();
        String bank_num = ed_bank_num.getText().toString();
        String bank_type = ed_bank_type.getText().toString();
        if (isNull()) return;
        showProgressDialog();
        model.upLoadUnlineInfor(signNetId, imgValue, customer_name, bank_num, bank_type, e -> {
            e.printStackTrace();
            hideProgressDialog();
        }, result -> {
            //审核中
            hideProgressDialog();
            InReViewActivity.start(this, "");
        });
    }

    private boolean isNull() {
        boolean isnull = false;
        if (TextUtils.isEmpty(imgValue)) {
            isnull = true;
            toast("转账凭证不能为空");
        }
        return isnull;
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
        }).show();
    }

    public static final int TAKE_PHOTO_CODE = 1;
    public static final int CHOOSE_PHOTO_CODE = 2;
    public static final int HAVE_CAMERA_PERMISSION = 4;

    private void chooseFromAlbum() {
        Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
        intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intentToPickPic, CHOOSE_PHOTO_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {//获取系统照片上传
            switch (requestCode) {
                case TAKE_PHOTO_CODE:
                    Bitmap bm = null;
                    bm = BitMapUtils.getBitmapFormUri(mUri, this);
                    if (ivUpload != null) ivUpload.setImageBitmap(bm);
                    imgValue = BitMapUtils.bitmaptoString(bm, 100);
//                    imgValue = BitMapUtils.imgToBase64(imgValue, null);
                    break;
                case CHOOSE_PHOTO_CODE:
                    Uri uri = data.getData();
                    String filePath = FileUtil.getFilePathByUri(this, uri);
                    if (!TextUtils.isEmpty(filePath)) {
                        Glide.with(this).load(filePath).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).into(ivUpload);
                    }
                    Bitmap bitmapFormUri = BitMapUtils.getBitmapFormUri(uri, this);
                    imgValue = BitMapUtils.imgToBase64("", bitmapFormUri);
                    break;
            }
        }
    }

    private Uri mUri;

    private void takePhoto() {
        // 步骤一：创建存储照片的文件
        String path = getFilesDir() + File.separator + "images" + File.separator;
        try {///data/user/0/com.net.point/files/images/test.jpg
            File file = new File(path, "test.jpg");
            if (!file.getParentFile().exists())
                file.getParentFile().mkdirs();
            Intent intent = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //这一句非常重要
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                //步骤二：Android 7.0及以上获取文件 Uri
                mUri = FileProvider.getUriForFile(PaymentAdvanceActivity.this, "com.net.point.fileprovider", file);
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
    public void doRequestPermissionsResult(int requestCode, @NonNull int[] grantResults) {
        super.doRequestPermissionsResult(requestCode, grantResults);
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
}
