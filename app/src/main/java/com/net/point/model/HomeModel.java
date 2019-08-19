package com.net.point.model;

import androidx.annotation.NonNull;

import com.net.point.base.BaseModel;
import com.net.point.request.HttpResult;
import com.net.point.request.RetrofitUtil;
import com.net.point.response.AddSignMsgBean;
import com.net.point.response.HomepageBackgroundBean;
import com.net.point.response.IntegralBean;
import com.net.point.response.LoginBean;
import com.net.point.response.MyMessageBean;
import com.net.point.response.OrderBean;
import com.net.point.response.OrderDetailsBean;
import com.net.point.response.OrderStrackBean;
import com.net.point.response.PostStationBean;
import com.net.point.response.QuestionPieceBean;
import com.net.point.response.UserInforBean;
import com.net.point.response.VersionBean;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by ${yyy} on 18-5-17.
 * Model of SignIn SignUp BindMobileNumber
 */

public class HomeModel extends BaseModel {

    @NonNull
    private Service service = RetrofitUtil.getInstance().create(Service.class);

    interface Service {
        @POST("app/order/MySendAndReceiveOrder")
        @FormUrlEncoded
        Observable<HttpResult<OrderBean>> insertOrder(@Field("incid") @NonNull String incid);

        @POST("login/openTrumpet")
        @FormUrlEncoded
        Observable<HttpResult<UserInforBean>> openTrumpet(@Field("userId") @NonNull String userId
                , @Field("mobile") @NonNull String mobile, @Field("password") @NonNull String password
                , @Field("smsCode") @NonNull String smsCode);

        @POST("Homepage/checkHome")
        @FormUrlEncoded
        Observable<HttpResult<List<HomepageBackgroundBean>>> getBackgroundIcon(@Field("id") @NonNull String id);

        @POST("feedBack/insertBack")
        @FormUrlEncoded
        Observable<HttpResult<Object>> addFeedBack(@Field("type") @NonNull String type, @Field("remark") @NonNull String remark,
                                                   @Field("phone") @NonNull String phone, @Field("imgValue1") @NonNull String imgValue1,
                                                   @Field("imgValue2") @NonNull String imgValue2, @Field("imgValue3") @NonNull String imgValue3,
                                                   @Field("imgValue4") @NonNull String imgValue4);

        @POST("Version/getlist")
        @FormUrlEncoded
        Observable<HttpResult<List<VersionBean>>> getVersion(@Field("type") @NonNull String type);

        @POST("Netsign/getPath")
        @FormUrlEncoded
        Observable<HttpResult<LoginBean>> getContractUrl(@Field("IncNumber") @NonNull String IncNumber);

        @POST("app/order/hadFinishPackage")
        @FormUrlEncoded
        Observable<HttpResult<List<OrderDetailsBean>>> getCompleteOrder(@Field("incNum") @NonNull String incNum,
                                                                        @Field("pageSize") @NonNull String pageSize,
                                                                        @Field("pageNum") @NonNull String pageNum);

        @POST("app/order/NoHadFinishPackage")
        @FormUrlEncoded
        Observable<HttpResult<List<OrderDetailsBean>>> getUnCompleteOrder(@Field("incNum") @NonNull String incNum,
                                                                          @Field("pageSize") @NonNull String pageSize,
                                                                          @Field("pageNum") @NonNull String pageNum);

        @POST("app/order/hadPostPackage")
        @FormUrlEncoded
        Observable<HttpResult<List<OrderDetailsBean>>> getDispatchedOrder(@Field("incNum") @NonNull String incNum,
                                                                          @Field("pageSize") @NonNull String pageSize,
                                                                          @Field("pageNum") @NonNull String pageNum);

        @POST("app/Leave/getListLeaveMsg")
        @FormUrlEncoded
        Observable<HttpResult<List<MyMessageBean>>> loadMyMessage(@Field("incNumber") @NonNull String incNumber,
                                                                  @Field("pageNumber") @NonNull String pageNumber,
                                                                  @Field("pageSize") @NonNull String pageSize);

        @POST("app/order/listAddressee")
        @FormUrlEncoded
        Observable<HttpResult<List<OrderDetailsBean>>> getReceiptListOrder(@Field("incid") @NonNull String incid,
                                                                           @Field("pageSize") @NonNull String pageSize,
                                                                           @Field("pageNum") @NonNull String pageNum);

        @POST("app/Stayedandtrouble/getDestoryedPackage")
        @FormUrlEncoded
        Observable<HttpResult<List<OrderDetailsBean>>> getLiuCangOrder(@Field("incid") @NonNull String incid,
                                                                       @Field("type") @NonNull String type,
                                                                       @Field("pageSize") @NonNull String pageSize,
                                                                       @Field("pageNum") @NonNull String pageNum);

        @POST("app/Stayedandtrouble/getProblemPackage")
        @FormUrlEncoded
        Observable<HttpResult<List<QuestionPieceBean>>> getQuestiondOrder(@Field("incid") @NonNull String incNum,
                                                                          @Field("pageSize") @NonNull String pageSize,
                                                                          @Field("pageNum") @NonNull String pageNum);

        @POST("app/order/NohadPostPackage")
        @FormUrlEncoded
        Observable<HttpResult<List<OrderDetailsBean>>> getUnDispatchedOrder(@Field("incNum") @NonNull String incNum,
                                                                            @Field("pageSize") @NonNull String pageSize,
                                                                            @Field("pageNum") @NonNull String pageNum);

        @POST("app/postStation/getPostStationByincNumbers")
        @FormUrlEncoded
        Observable<HttpResult<List<PostStationBean>>> insertNearPosition(@Field("incNumber") @NonNull String incNumber,
                                                                         @Field("longtitude") @NonNull double longtitude,
                                                                         @Field("latitude") @NonNull double pageNum);

        @POST("app/order/getOrderDetailByNum")
        @FormUrlEncoded
        Observable<HttpResult<OrderDetailsBean>> insertOrderDetails(@Field("number") @NonNull String number);


        @POST("app/order/getStrackingList")
        @FormUrlEncoded
        Observable<HttpResult<List<OrderStrackBean>>> insertOrderFromNumber(@Field("billsNumber") @NonNull String billsNumber);


        @POST("app/sign/addSginMsg")
        @FormUrlEncoded
        Observable<HttpResult<AddSignMsgBean>> loadSignMsg(@Field("userid") @NonNull String userid, @Field("incnumber") @NonNull String incnumber
                , @Field("longitude") @NonNull double longitude, @Field("latitude") @NonNull double latitude);


        @POST("app/sign/CountDate")
        @FormUrlEncoded
        Observable<HttpResult<List<IntegralBean>>> loadMyIntegral(@Field("userid") @NonNull String userid,
                                                                  @Field("pageNum") @NonNull String pageNum, @Field("pageSize") @NonNull String pageSize);

        @POST("app/sign/getTotalPoints")
        @FormUrlEncoded
        Observable<HttpResult<Integer>> loadTotalIntegral(@Field("userid") @NonNull String userid);

        @POST("app/handlingOrders/incSentPackageToNext")
        @FormUrlEncoded
        Observable<HttpResult<Object>> dispatchOrder(@Field("billNumber") @NonNull String billNumber, @Field("userid") @NonNull String userid);
    }

    //派单
    public void dispatchOrder(@NonNull String billNumber, @NonNull String userid, @NonNull Action1<Throwable> onError, @NonNull Action1<HttpResult<Object>> onResult) {
        register(service.dispatchOrder(billNumber, userid), onError, onResult);
    }

    //查询省市县区
    public void insertOrder(@NonNull String incid, @NonNull Action1<Throwable> onError, @NonNull Action1<HttpResult<OrderBean>> onResult) {
        register(service.insertOrder(incid), onError, onResult);
    }

    //获取首页背景图
    public void getBackgroundIcon(
            @NonNull String id, @NonNull Action1<Throwable> onError, @NonNull Action1<HttpResult<List<HomepageBackgroundBean>>> onResult) {
        register(service.getBackgroundIcon(id), onError, onResult);
    }

    //获取服务器上的版本号
    public void getVersion(
            @NonNull String type, @NonNull Action1<Throwable> onError, @NonNull Action1<HttpResult<List<VersionBean>>> onResult) {
        register(service.getVersion(type), onError, onResult);
    }

    //获取合同下载地址（返回很多信息）
    public void getContractUrl(
            @NonNull String IncNumber, @NonNull Action1<Throwable> onError, @NonNull Action1<HttpResult<LoginBean>> onResult) {
        register(service.getContractUrl(IncNumber), onError, onResult);
    }

    //添加反馈
    public void addFeedBack(
            @NonNull String type,
            @NonNull String remark,
            @NonNull String phone,
            @NonNull String imgValue1,
            @NonNull String imgValue2,
            @NonNull String imgValue3,
            @NonNull String imgValue4, @NonNull Action1<Throwable> onError, @NonNull Action1<HttpResult<Object>> onResult) {
        register(service.addFeedBack(type, remark, phone, imgValue1, imgValue2, imgValue3, imgValue4), onError, onResult);
    }

    //获取已完成的订单，pageSize一页条多少数据，pageNum多少页
    public void getCompleteOrder(
            @NonNull String incNum,
            @NonNull String pageSize,
            @NonNull String pageNum, @NonNull Action1<Throwable> onError, @NonNull Action1<HttpResult<List<OrderDetailsBean>>> onResult) {
        register(service.getCompleteOrder(incNum, pageSize, pageNum), onError, onResult);
    }

    //获取未完成的订单，pageSize一页条多少数据，pageNum多少页
    public void getUnCompleteOrder(
            @NonNull String incNum,
            @NonNull String pageSize,
            @NonNull String pageNum, @NonNull Action1<Throwable> onError, @NonNull Action1<HttpResult<List<OrderDetailsBean>>> onResult) {
        register(service.getUnCompleteOrder(incNum, pageSize, pageNum), onError, onResult);
    }

    //获取积分列表
    public void loadMyIntegral(
            @NonNull String userid,
            @NonNull String pageSize,
            @NonNull String pageNum, @NonNull Action1<Throwable> onError, @NonNull Action1<HttpResult<List<IntegralBean>>> onResult) {
        register(service.loadMyIntegral(userid, pageSize, pageNum), onError, onResult);
    }

    //获取积分总数
    public void loadTotalIntegral(
            @NonNull String userid, @NonNull Action1<Throwable> onError, @NonNull Action1<HttpResult<Integer>> onResult) {
        register(service.loadTotalIntegral(userid), onError, onResult);
    }

    //查询订单详情
    public void insertOrderDetails(
            @NonNull String number, @NonNull Action1<Throwable> onError, @NonNull Action1<HttpResult<OrderDetailsBean>> onResult) {
        register(service.insertOrderDetails(number), onError, onResult);
    }

    //开通小号
    public void openTrumpet(
            @NonNull String userId, @NonNull String mobile, @NonNull String password, @NonNull String NonNull, @NonNull Action1<Throwable> onError, @NonNull Action1<HttpResult<UserInforBean>> onResult) {
        register(service.openTrumpet(userId, mobile, password, NonNull), onError, onResult);
    }

    //根据订单号查询物流信息
    public void insertOrderFromNumber(
            @NonNull String billsNumber, @NonNull Action1<Throwable> onError, @NonNull Action1<HttpResult<List<OrderStrackBean>>> onResult) {
        register(service.insertOrderFromNumber(billsNumber), onError, onResult);
    }

    //获取已派件的页面数据
    public void getDispatchedOrder(
            @NonNull String incNum,
            @NonNull String pageSize,
            @NonNull String pageNum, @NonNull Action1<Throwable> onError, @NonNull Action1<HttpResult<List<OrderDetailsBean>>> onResult) {
        register(service.getDispatchedOrder(incNum, pageSize, pageNum), onError, onResult);
    }

    //获取我的留言页面数据
    public void loadMyMessage(
            @NonNull String incNumber,
            @NonNull String pageNumber,
            @NonNull String pageSize, @NonNull Action1<Throwable> onError, @NonNull Action1<HttpResult<List<MyMessageBean>>> onResult) {
        register(service.loadMyMessage(incNumber, pageNumber, pageSize), onError, onResult);
    }

    //获取我的收件页面数据
    public void getReceiptListOrder(
            @NonNull String incid,
            @NonNull String pageSize,
            @NonNull String pageNum, @NonNull Action1<Throwable> onError, @NonNull Action1<HttpResult<List<OrderDetailsBean>>> onResult) {
        register(service.getReceiptListOrder(incid, pageSize, pageNum), onError, onResult);
    }

    //获取留仓件页面数据 1：留仓件， 2 ： 已损件
    public void getLiuCangOrder(
            @NonNull String incid,
            @NonNull String type,
            @NonNull String pageSize,
            @NonNull String pageNum, @NonNull Action1<Throwable> onError, @NonNull Action1<HttpResult<List<OrderDetailsBean>>> onResult) {
        register(service.getLiuCangOrder(incid, type, pageSize, pageNum), onError, onResult);
    }

    //获取问题件页面数据
    public void getQuestiondOrder(
            @NonNull String incid,
            @NonNull String pageSize,
            @NonNull String pageNum, @NonNull Action1<Throwable> onError, @NonNull Action1<HttpResult<List<QuestionPieceBean>>> onResult) {
        register(service.getQuestiondOrder(incid, pageSize, pageNum), onError, onResult);
    }

    //获取未派件的页面数据
    public void insertNearPosition(
            @NonNull String incNumber,
            @NonNull double longtitude,
            @NonNull double latitude, @NonNull Action1<Throwable> onError, @NonNull Action1<HttpResult<List<PostStationBean>>> onResult) {
        register(service.insertNearPosition(incNumber, longtitude, latitude), onError, onResult);
    }

    //获取附近驿站页面数据
    public void getUnDispatchedOrder(
            @NonNull String incNum,
            @NonNull String pageSize,
            @NonNull String pageNum, @NonNull Action1<Throwable> onError, @NonNull Action1<HttpResult<List<OrderDetailsBean>>> onResult) {
        register(service.getUnDispatchedOrder(incNum, pageSize, pageNum), onError, onResult);
    }

    //用户签到
    public void loadSignMsg(
            @NonNull String userid,
            @NonNull String incnumber,
            @NonNull double longitude,
            @NonNull double latitude, @NonNull Action1<Throwable> onError, @NonNull Action1<HttpResult<AddSignMsgBean>> onResult) {
        register(service.loadSignMsg(userid, incnumber, longitude, latitude), onError, onResult);
    }

    public class HasSignBean {
        public boolean hasSigned;
    }
}
