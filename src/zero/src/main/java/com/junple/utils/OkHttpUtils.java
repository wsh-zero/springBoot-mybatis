package com.junple.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;

import java.io.IOException;

/**
 * OkHttpUtils
 * @author chentao
 * @version 2019-04-10
 */
public class OkHttpUtils {

    /**
     * 懒汉单例
     */
    private static class Holder {
        static final OkHttpClient CLIENT_INSTANCE = new OkHttpClient();
    }

    public static String postJson(String url, Object object) {
        return postJson(url, JSON.toJSONString(object));
    }

    /**
     * 提交json数据
     */
    public static String postJson(String url, String json) {
        RequestBody body = FormBody.create(MediaType.parse("application/json; charset=utf-8"), json);
        return sendPost(url, body, String.class);
    }

    public static <T> T sendPost(String url, RequestBody body, Class<T> resultType) {

        Request request = new Request.Builder().url(url).post(body).build();
        Response response = null;
        try {
            response = Holder.CLIENT_INSTANCE.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String result = null;
        if (response != null) {
            try {
                result = response.body().string();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (result == null || resultType == String.class) {
            return (T) result;
        }
        else {
            return JSON.parseObject(result, resultType);
        }
    }
}
