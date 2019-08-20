package com.net.point.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.net.point.R;
import com.net.point.event.ReSettingIconEvent;
import com.net.point.glide.GlideUtils;
import com.net.point.model.HomeModel;
import com.net.point.request.HttpResult;
import com.net.point.response.AddSignMsgBean;
import com.net.point.ui.homepage.MyPenaltyActivity;
import com.net.point.ui.mine.CustomerPhoneActivity;
import com.net.point.ui.mine.DysfunctionActivity;
import com.net.point.ui.mine.HelpCenterActivity;
import com.net.point.ui.mine.MyIntegralActivity;
import com.net.point.ui.mine.PersonInforActivity;
import com.net.point.utils.AppUtils;
import com.net.point.utils.SpUtils;
import com.net.point.widget.SignInPopupWindow;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MineFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private HomeModel model = new HomeModel();
    private AddSignMsgBean signMsgBean;
    private HttpResult<AddSignMsgBean> httpResult;

    public static MineFragment newInstance(String param1, String param2) {
        MineFragment fragment = new MineFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.iv_portrait)
    ImageView iv_portrait;
    @BindView(R.id.tv_current_version)
    TextView tv_current_version;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        showAvatar();
        AppUtils.setTexts(tv_current_version, "当前版本V" + AppUtils.getPackageName(getActivity()));
        return view;
    }

    @Subscribe
    public void onReSettingIconEvent(ReSettingIconEvent event) {
        showAvatar();
    }

    private void showAvatar() {
        GlideUtils.showImageViewToCircle(getActivity(), R.drawable.dog, SpUtils.getAvatarUrl(), iv_portrait);
    }

    @OnClick({R.id.tv_user_name, R.id.tv_my_integral, R.id.rl_help_center, R.id.iv_mine_sign
            , R.id.rl_complaint_record, R.id.ll_my_feedback, R.id.rl_customer_phone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_user_name:
                PersonInforActivity.start(getActivity());
                break;
            case R.id.tv_my_integral:
                MyIntegralActivity.start(getActivity());
                break;
            case R.id.rl_help_center:
                HelpCenterActivity.start(getActivity());
                break;
            case R.id.ll_my_feedback:
                DysfunctionActivity.start(getActivity());
                break;
            case R.id.iv_mine_sign:
                loadSignData();
                break;
            case R.id.rl_complaint_record:
                MyPenaltyActivity.start(getActivity(), 1);
                break;
            case R.id.rl_customer_phone:
                CustomerPhoneActivity.start(getActivity());
                break;
        }
    }

    private void loadSignData() {
        String userId = SpUtils.getUserId();
        String incNumber = SpUtils.getIncNumber();
        model.loadSignMsg(userId, incNumber, 0.0f, 0.0f, e -> {
            e.printStackTrace();
            toa(e.getMessage());
        }, result -> {
            if (result.isSuccess() && result.getData() != null) {
                httpResult = result;
            }
            showPopupWindow();
        });
    }

    private SignInPopupWindow mSignInWindow;

    private void showPopupWindow() {
        if (mSignInWindow != null) {
            mSignInWindow = null;
        }
        mSignInWindow = new SignInPopupWindow(getActivity(), httpResult);
        mSignInWindow.show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        if (mSignInWindow != null) {
            mSignInWindow = null;
        }
    }

    private void toa(String msg) {
        Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
    }
}
