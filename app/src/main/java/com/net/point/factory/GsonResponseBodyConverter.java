package com.net.point.factory;

/**
 * Created by ${yyy} on 17-12-26.
 */

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.net.point.request.HttpResult;
import com.net.point.request.RetrofitUtil;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String json = value.string();
        try {
            JSONObject jsonObject = new JSONObject(json);
            int errcode = jsonObject.getInt("errcode");
            if (errcode != RetrofitUtil.Code.SUCCESS) {
                HttpResult httpResult = new HttpResult<>();
                httpResult.setErrcode(errcode);
                httpResult.setErrdesc(jsonObject.getString("errdesc"));
                return (T) httpResult;
            }
            return adapter.fromJson(json);
        } catch (Exception e) {
            return adapter.read(new JsonReader(new BufferedReader(new StringReader(json))));
        } finally {
            value.close();
        }
    }
}
