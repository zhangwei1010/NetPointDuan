package com.net.point.ui.mine;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.net.point.R;
import com.net.point.adapter.DysFunctionAdapter;
import com.net.point.base.BaseActivity;
import com.net.point.model.HomeModel;
import com.net.point.utils.AppUtils;
import com.net.point.utils.BitMapUtils;
import com.net.point.utils.FileUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

//功能异常提交页面
public class FunctionCommitActivity extends BaseActivity {

    private String questions;
    private String type;
    @BindView(R.id.tv_picture_count)
    TextView tv_picture_count;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_word_count)
    TextView tv_word_count;
    @BindView(R.id.ed_input_word)
    EditText ed_input_word;
    @BindView(R.id.ed_phone_number)
    EditText ed_phone_number;
    @BindView(R.id.iv_see_problem)
    ImageView iv_see_problem;
    @BindView(R.id.iv_new_function)
    ImageView iv_new_function;
    @BindView(R.id.iv_add_picture)
    ImageView iv_add_picture;
    @BindView(R.id.ll_toolbar)
    LinearLayout ll_toolbar;
    @BindView(R.id.mRecycleView)
    RecyclerView mRecycleView;

    private DysFunctionAdapter mAdapter;
    private String imgValue = "";
    private List<String> filePathList = new ArrayList<>();
    private List<String> base64List = new ArrayList<>();
    private HomeModel model = new HomeModel();

    public static void start(Context context, String questions, String type) {
        Intent intent = new Intent(context, FunctionCommitActivity.class);
        intent.putExtra("questions", questions);
        intent.putExtra("type", type);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addContentView(R.layout.activity_function_commit);
        setContentTitle(R.string.dys_function);
        ButterKnife.bind(this);
        questions = getIntent().getStringExtra("questions");
        type = getIntent().getStringExtra("type");
        showView();
        ed_input_word.addTextChangedListener(new TextContentTextWatcher());
        initRecycleView();
    }

    private void initRecycleView() {
        mAdapter = new DysFunctionAdapter(o -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//大于Android 6.0
                if (hasPermission(permisions))
                    chooseFromAlbum();
                else requestPermission(HAVE_CAMERA_PERMISSION, permisions);
            } else chooseFromAlbum();
        });
        mRecycleView.setAdapter(mAdapter);
        filePathList.add("0");
        mAdapter.setItems(filePathList);
    }

    private void showView() {
        if (!TextUtils.isEmpty(questions)) {
            if (TextUtils.equals(questions, getString(R.string.opinions_and_suggestions))) {
                ll_toolbar.setVisibility(View.VISIBLE);
                tv_title.setVisibility(View.GONE);
            } else {
                ll_toolbar.setVisibility(View.GONE);
                tv_title.setVisibility(View.VISIBLE);
                AppUtils.setTexts(tv_title, questions);
            }
        }
    }

    private boolean showProblemButton = true;

    @OnClick({R.id.iv_see_problem, R.id.tv_see_problem, R.id.iv_new_function, R.id.tv_new_function,
            R.id.iv_add_picture, R.id.btn_commit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_see_problem:
            case R.id.tv_see_problem:
                showProblemButton = true;
                type = "8";
                iv_see_problem.setImageResource(showProblemButton ? R.drawable.check_mark : R.drawable.uncheck_mark);
                iv_new_function.setImageResource(showProblemButton ? R.drawable.uncheck_mark : R.drawable.check_mark);
                break;
            case R.id.iv_new_function:
            case R.id.tv_new_function:
                showProblemButton = false;
                type = "9";
                iv_new_function.setImageResource(showProblemButton ? R.drawable.uncheck_mark : R.drawable.check_mark);
                iv_see_problem.setImageResource(showProblemButton ? R.drawable.check_mark : R.drawable.uncheck_mark);
                break;
            case R.id.iv_add_picture:
                break;
            case R.id.btn_commit:
                commitData();
                break;
        }
    }

    private void commitData() {
        String question = ed_input_word.getText().toString().trim();
        if (TextUtils.isEmpty(question)) {
            toast("请描述问题");
            return;
        }
        String phone = ed_phone_number.getText().toString().trim();
        if (TextUtils.isEmpty(phone)) {
            toast("邮箱/手机号不能为空");
            return;
        }
        String imgValue1 = "";
        String imgValue2 = "";
        String imgValue3 = "";
        String imgValue4 = "";
        for (int i = 0; i < filePathList.size(); i++) {
            switch (i) {
                case 0:
                    imgValue1 = filePathList.get(i);
                    break;
                case 1:
                    imgValue2 = filePathList.get(i);
                    break;
                case 2:
                    imgValue3 = filePathList.get(i);
                    break;
                case 3:
                    imgValue4 = filePathList.get(i);
                    break;
            }
        }
        model.addFeedBack(type, question, phone, imgValue1, imgValue2, imgValue3, imgValue4, e -> e.printStackTrace(), result -> {
            if (result.isSuccess()) {
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {//获取系统照片上传
            switch (requestCode) {
                case CHOOSE_PHOTO_CODE:
                    Uri uri = data.getData();
                    String filePath = FileUtil.getFilePathByUri(this, uri);
                    if (!TextUtils.isEmpty(filePath)) {
                        filePathList.add(filePathList.size() - 1, filePath);
                        mAdapter.setItems(filePathList);
                        Glide.with(this).load(filePath).diskCacheStrategy(DiskCacheStrategy.NONE).
                                skipMemoryCache(true).into(iv_add_picture);
                        tv_picture_count.setText((filePathList.size() - 1) + "/4");
                    }
                    Bitmap bitmapFormUri = BitMapUtils.getBitmapFormUri(uri, this);
                    imgValue = BitMapUtils.imgToBase64("", bitmapFormUri);
                    base64List.add(imgValue);
                    break;
            }
        }
    }

    public static final int CHOOSE_PHOTO_CODE = 1;
    public static final int HAVE_CAMERA_PERMISSION = 2;

    private void chooseFromAlbum() {
        Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
        intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intentToPickPic, CHOOSE_PHOTO_CODE);
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
                    chooseFromAlbum();
                    break;
            }
        } else {
            Toast.makeText(this, "该功能需要授权方可使用", Toast.LENGTH_SHORT).show();
        }
    }

    class TextContentTextWatcher implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int j, int k) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int j, int k) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            if (editable.length() > 200) {
                toast("输入超过200字");
                ed_input_word.setFocusable(false);
            } else {
                tv_word_count.setText(editable.length() + "/200");
                ed_input_word.setFocusable(true);
            }
        }
    }
}
