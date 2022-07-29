package com.zn.learn.basic.network;


import com.alibaba.fastjson.JSON;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.config.SocketConfig;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

public class HttpUtils {

    public static String BASE_URL = "http://127.0.0.1:8080/api/v1/data/tsdb";


    private static CloseableHttpClient httpClient;
    private static CloseableHttpClient httpsClient;
    public static final String CHARSET = "UTF-8";
    /**
     * 客户端从服务端读取数据的超时时间
     */
    private static final int HTTP_TIMEOUT = 30000;
    /**
     * 客户端与服务器建立连接的超时时间
     */
    private static final int HTTP_CON_TIMEOUT = 10000;
    /**
     * 客户端从连接池中获取连接的超时时间
     */
    private static final int HTTP_CON_REQ_TIMEOUT = 1000;
    /**
     * 路由的默认最大连接
     */
    private static final int HTTP_MAX_PER_ROUTE = 50;
    /**
     * 整个连接池连接的最大值
     */
    private static final int HTTP_MAX_TOTAL = 100;

    static PoolingHttpClientConnectionManager connManager;
    static RequestConfig defaultRequestConfig;

    static {
        //  创建连接池管理器
        connManager = new PoolingHttpClientConnectionManager();
        //  设置socket配置
        connManager.setDefaultSocketConfig(SocketConfig.custom().setTcpNoDelay(true).build());
        connManager.setMaxTotal(HTTP_MAX_TOTAL);
        connManager.setDefaultMaxPerRoute(HTTP_MAX_PER_ROUTE);
        //  设置获取连接超时时间、建立连接超时时间、从服务端读取数据的超时时间
        defaultRequestConfig = RequestConfig.custom().setSocketTimeout(HTTP_TIMEOUT)
                .setConnectTimeout(HTTP_CON_TIMEOUT)
                .setConnectionRequestTimeout(HTTP_CON_REQ_TIMEOUT)
                .build();
        //  创建httpclient实例
        httpClient = HttpClients.custom().setConnectionManager(connManager).setDefaultRequestConfig(defaultRequestConfig).build();
    }

    private static final String AppKey = "e7f17ee4c0534e60a4af5ac15508a4c0";
    private static final String AppSecret = "f327c160bb8c4d228698ed7a4c12017b";

    public static String sendPostData(String apiUrl,Map<String, String> head, Map<String, Object> body) throws IOException {

        String result = "";
        String body1 = JSON.toJSONString(body);
        StringEntity stringEntity = new StringEntity(body1, CHARSET);
        stringEntity.setContentEncoding(CHARSET);
        stringEntity.setContentType(ContentType.APPLICATION_JSON.toString());

        // 加密
        String nonce = UUID.randomUUID().toString();
        String timestamp = System.currentTimeMillis() + "";


        //参数按字母顺序排序，规则body&按顺序排序的参数&APP_SECRET
        String signValue = body1 + "&" + CommonConstants.PARAM_APP_NONCE + "=" + nonce + "&" + CommonConstants.PARAM_APP_TIMESTAMP + "=" + timestamp + "&" + AppSecret;
        Map<String, String> header = new HashMap<>();

        header.put(CommonConstants.PARAM_APP_KEY, AppKey);
//        header.put(CommonConstants.COMPANY_CODE, commonParams.getAppKey());
        header.put(CommonConstants.PARAM_SIGN, DigestUtils.md5Hex(signValue.getBytes()));
        header.put(CommonConstants.PARAM_APP_NONCE, nonce);
        header.put(CommonConstants.PARAM_APP_TIMESTAMP, timestamp);


        HttpPost httpPost = new HttpPost(BASE_URL + "/upSertTagData2");
        httpPost.setEntity(stringEntity);
        header.forEach(httpPost::addHeader);
        httpClient.execute(httpPost);

        Long startLong = System.currentTimeMillis();
        CloseableHttpResponse response = httpClient.execute(httpPost);
        System.out.println("---- 调用耗时" + (System.currentTimeMillis() - startLong));
        result = getResultFromResponse(response, httpPost);
        response.close();

//        System.out.println(response.code()+"   "+new String(response.body().bytes()));
        return result;
    }

    /**
     * @param args
     * @throws IOException
     */


    public static void main(String[] args) throws IOException {
        Map<String, Object> body = new HashMap<>();
        body.put("businessCode", "ali_test");
        body.put("companyCode","company_ali");
        List<TagData> tagDataList=new ArrayList<TagData>();
        TagData tagData1=new TagData("A1",1.45,"2022-07-29T00:01:02.000+08:00");
        TagData tagData2=new TagData("A2",1.45,"2022-07-29T01:01:03.000+08:00");
        tagDataList.add(tagData1);
        tagDataList.add(tagData2);
        body.put("tagDataList",tagDataList);

//        Map<String, String> header = new HashMap<>();


        System.out.println(sendPostData(null,null, body))
        ;
    }


    private static String getResultFromResponse(CloseableHttpResponse response, HttpRequestBase httpRequest) throws IOException {
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != 200) {
            httpRequest.abort();
            throw new RuntimeException("HttpClient,error status code :" + statusCode);
        }

        HttpEntity entity = response.getEntity();
        String result = null;
        if (entity != null) {
            result = EntityUtils.toString(entity, "utf-8");
        }
        EntityUtils.consume(entity);
        return result;
    }

}
