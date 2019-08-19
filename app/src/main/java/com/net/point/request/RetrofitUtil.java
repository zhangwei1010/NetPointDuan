package com.net.point.request;

import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.net.point.factory.GosnEmptyConverterFactory;
import com.net.point.utils.Base64Utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.functions.Action0;

/**
 * Created by cxm
 * on 2019/6/20
 */
public class RetrofitUtil {

    public interface Code {
        int SUCCESS = 0;
        int ERROR = 400;
        int ERROR_UNAVAILABLE = 90000;
    }

    /*
     * 项目正式服务器地址
     */
//    public final static String SERVER_ADDRESS = "http://122.114.82.200:80/";//ip
    public final static String SERVER_ADDRESS = "http://www.k8yz.com/";//域名

    //    //下面是测试服务器
    public final static String TEST_SERVER_ADDRESS = "http://192.168.124.40:8081/";

    public static String getServerAddress() {
        return TEST_SERVER_ADDRESS;
    }

    private final static File CACHE_DIR = Environment.getDataDirectory();

    @NonNull
    private static String userAgent = "okhttp";

    public static void init(@NonNull String userAgent) {
        RetrofitUtil.userAgent = userAgent;
    }

    @NonNull
    private static OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new LoggingInterceptor())
            .connectTimeout(10, TimeUnit.MINUTES)
            .readTimeout(10, TimeUnit.MINUTES)
            .writeTimeout(10, TimeUnit.MINUTES)
            .cache(new Cache(CACHE_DIR, 1024 * 1024 * 128)) //128M 缓存
            .build();

    @NonNull
    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(getServerAddress())
            .client(client)
            .addConverterFactory(GosnEmptyConverterFactory.create())
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .build();


    @NonNull
    public static OkHttpClient getClient() {
        return client;
    }

    @NonNull
    public static Retrofit getInstance() {
        return retrofit;
    }

    @NonNull
    public static String buildParams(@NonNull Map<String, Object> params) {
        Gson gson = new Gson();
        String str = gson.toJson(params);// Json编码
//        logger.d("base64加密前的数据为" + str);
        Log.d("", "base64加密前的数据为" + str);
        str = Base64Utils.getBase64(str);// Base64加密
        str = str.replaceAll("/", "-");
        str = str.replaceAll("\\s", "");
        return str;
    }

    @NonNull
    public static String buildParams(@NonNull List<?> params) {
        Gson gson = new Gson();
        String str = gson.toJson(params);// Json编码
//        logger.d("base64加密前的数据为" + str);
        Log.d("", "base64加密前的数据为" + str);
        str = Base64Utils.getBase64(str);// Base64加密
        str = str.replaceAll("/", "-");
        str = str.replaceAll("\\s", "");
        return str;
    }

    /**
     * 加密一个HashMap:
     * HashMap转json
     * 先用aes加密json
     * 前面拼接随机生成16位字符串
     * 再用base64加密
     *
     * @return 加密后的字符串
     */
//    @NonNull
//    public static String encryptByAesAndBase64(@NonNull HashMap<String, String> map) {
//        String iv = StringUtil.getStringRandom(16);//16位随机数
//        String aesResult = Aes256Utils.encryptMap(map, Aes256Utils.KEY, iv);//aes加密
//        return Base64Utils.getBase64(iv + aesResult);//再用base64加密
//    }
    @NonNull
    public static Map<String, Object> getParamsMap(@NonNull Object params) {
        Field[] fields = params.getClass().getDeclaredFields();
        HashMap<String, Object> hashMap = new HashMap<>();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                hashMap.put(field.getName(), field.get(params));
            }
        } catch (Exception e) {
//            logger.d(e);
            Log.d("", e.getMessage());
        }
        return hashMap;
    }

    @NonNull
    public static String buildParams(@NonNull Object params) {
        return buildParams(getParamsMap(params));
    }

    @NonNull
    public static <T> Subscriber<T> getDefault() {
        return new Subscriber<T>() {
            @Override
            public void onCompleted() {
                Log.d("", "数据接收完毕");
//                logger.d("数据接收完毕");
            }

            @Override
            public void onError(Throwable e) {
                if (e != null) {
//                    logger.e(e);
                    Log.d("", "数据接收完毕");
                }
            }

            @Override
            public void onNext(T t) {
//                logger.d(t);
                Log.d("", "数据接收完毕");
            }
        };
    }

    @NonNull
    public static final Action0 DEFAULT_COMPLETE = () -> Log.e("", "网络请求完成");

    public static class LoggingInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            //这个chain里面包含了request和response，所以你要什么都可以从这里拿
            Request request = chain.request();
            long t1 = System.nanoTime();//请求发起的时间
            String method = request.method();
            if ("POST".equals(method)) {
                StringBuilder sb = new StringBuilder();
                if (request.body() instanceof FormBody) {
                    FormBody body = (FormBody) request.body();
                    for (int i = 0; i < body.size(); i++) {
                        sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",");
                    }
                    sb.delete(sb.length() - 1, sb.length());
                    Log.d("http_log", String.format("发送请求 %s on %s %n%s %nRequestParams:{%s}",
                            request.url(), chain.connection(), request.headers(), sb.toString()));
                }
            } else {
                Log.d("http_log", String.format("发送请求 %s on %s%n%s",
                        request.url(), chain.connection(), request.headers()));
            }
            Response response = chain.proceed(request);
            long t2 = System.nanoTime();//收到响应的时间
            //这里不能直接使用response.body().string()的方式输出日志
            //因为response.body().string()之后，response中的流会被关闭，程序会报错，我们需要创建出一
            //个新的response给应用层处理
            ResponseBody responseBody = response.peekBody(1024 * 1024);
            Log.d("http_log",
                    String.format("接收响应: [%s] %n返回json:【%s】 %.1fms %n%s",
                            response.request().url(),
                            responseBody.string(),
                            (t2 - t1) / 1e6d,
                            response.headers()
                    ));
            return response;
        }
    }

//    private void getData() {//okhttp3 异步post请求
//        OkHttpClient client = new OkHttpClient();
//        FormBody.Builder builder = new FormBody.Builder();
//        /* 添加两个参数 */
//        builder.add("totalAmount", totalAmount);
//        builder.add("body", body);
//        builder.add("subject", subject);
//        builder.add("netSignId", netSignId);
//        FormBody body = builder.build();
//        String url = "http://122.114.82.200/aliPay/AppPay";
//        url = "http://www.k8yz.com/aliPay/AppPay";
//        Request request = new Request.Builder().url(url).post(body).build();
//
//        /* 下边和get一样了 */
//        client.newCall(request).enqueue(new Callback() {
//            public void onResponse(Call call, Response response) {
//            }
//
//            public void onFailure(Call call, final IOException e) {
//            }
//        });
//    }
}
