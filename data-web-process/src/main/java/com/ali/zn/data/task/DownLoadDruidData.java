package com.ali.zn.data.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;

import java.io.IOException;


public class DownLoadDruidData {
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private final OkHttpClient client = new OkHttpClient();

    public String postJson(String url, String json) throws IOException {
        RequestBody body = RequestBody.create(json, JSON);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code: " + response);
            return response.body().string();
        }
    }

    public static void main(String[] args) throws IOException {
        DownLoadDruidData example = new DownLoadDruidData();
        Long startTime = System.currentTimeMillis();
        String json="{ \"query\": \"SELECT  *  FROM ZHOUSHAN_PROD WHERE __time >= TIMESTAMP '2024-01-26 10:00:00' and __time <= TIMESTAMP '2024-01-26 10:05:00'  \"}";

        String response = example.postJson("http://10.154.102.4:8082/druid/v2/sql", json);
        System.out.println("请求耗时"+(System.currentTimeMillis() - startTime));
        JSONArray  jo= JSONObject.parseArray(response);
        System.out.println("totalsize :"+jo.size()+"    "+jo.get(1).toString());
        System.out.println("解析耗时"+(System.currentTimeMillis() - startTime));
    }
}