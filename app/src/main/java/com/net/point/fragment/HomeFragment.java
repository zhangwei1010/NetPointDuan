package com.net.point.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.Nullable;
import com.net.point.R;
import com.net.point.glide.GlideUtils;
import com.net.point.model.HomeModel;
import com.net.point.response.HomepageBackgroundBean;
import com.net.point.response.OrderBean;
import com.net.point.ui.homepage.InsertOrderActivity;
import com.net.point.ui.homepage.MyBillActivity;
import com.net.point.ui.homepage.MyContractActivity;
import com.net.point.ui.homepage.MyDispatchActivity;
import com.net.point.ui.homepage.MyPostStationActivity;
import com.net.point.ui.homepage.MyReceiptActivity;
import com.net.point.ui.homepage.NetworkSupervisionActivity;
import com.net.point.ui.homepage.OpenTrumpetActivity;
import com.net.point.ui.homepage.OrderManageActivity;
import com.net.point.ui.homepage.ShareActivity;
import com.net.point.utils.AppUtils;
import com.net.point.utils.SpUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private HomeModel homeModel = new HomeModel();
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @BindView(R.id.ll_insert_order)
    LinearLayout ll_insert_order;
    @BindView(R.id.ll_order_management)
    LinearLayout ll_order_management;
    @BindView(R.id.tv_sendCount)
    TextView tv_sendCount;
    @BindView(R.id.tv_recvCount)
    TextView tv_recvCount;
    @BindView(R.id.ivGallery)
    ImageView ivGallery;

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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.ll_insert_order, R.id.ll_order_management, R.id.ll_my_bill,
            R.id.ll_my_post_station, R.id.ll_open_trumpet, R.id.ll_share_friends,
            R.id.ll_my_dispatch, R.id.ll_my_receipt, R.id.ll_network_supervision,
    R.id.ll_my_contract})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_insert_order:
                InsertOrderActivity.start(getActivity());
                break;
            case R.id.ll_order_management:
                OrderManageActivity.start(getActivity());
                break;
            case R.id.ll_my_bill:
                MyBillActivity.start(getActivity());
                break;
            case R.id.ll_my_post_station:
                MyPostStationActivity.start(getActivity());
                break;
            case R.id.ll_open_trumpet:
                OpenTrumpetActivity.start(getActivity());
                break;
            case R.id.ll_share_friends:
                ShareActivity.start(getActivity());
                break;
            case R.id.ll_my_dispatch:
                MyDispatchActivity.start(getActivity());
                break;
            case R.id.ll_my_receipt:
                MyReceiptActivity.start(getActivity());
                break;
            case R.id.ll_network_supervision:
                NetworkSupervisionActivity.start(getActivity());
                break;
            case R.id.ll_my_contract:
                MyContractActivity.start(getActivity());
                break;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        insertOrder();
        getBackgroundIcon();
    }

    private void getBackgroundIcon() {
        homeModel.getBackgroundIcon("1", e -> e.printStackTrace(), result -> {
            if (result.isSuccess() && result.getData() != null) {
                List<HomepageBackgroundBean> beanList = result.getData();
                GlideUtils.showImageView(getActivity(), R.drawable.homepage_banner, beanList.get(0).
                        getPicpath(), ivGallery);
            }
        });
    }

    private void insertOrder() {
        if (TextUtils.isEmpty(SpUtils.getIncNumber())) return;
        homeModel.insertOrder(SpUtils.getIncNumber(), e -> e.printStackTrace(), result -> {
            if (result.isSuccess() && result.getData() != null) {
                OrderBean orderBean = result.getData();
                AppUtils.setTexts(tv_sendCount, orderBean.sendCount + "件");
                AppUtils.setTexts(tv_recvCount, orderBean.recvCount + "件");
            }
        });
    }
}
