package com.zn.learn.basic.network;

/**
 * 公共参数
 *
 * @author chenpi
 * @since 2020/12/10
 **/
public class CommonParams {
    private String appKey;
    private String appSecret;
    private String gatewayUrlPrefix;

    public CommonParams() {

    }

    public CommonParams(String appKey, String appSecret, String gatewayUrlPrefix) {
        this.appKey = appKey;
        this.appSecret = appSecret;
        this.gatewayUrlPrefix = gatewayUrlPrefix;
    }

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getGatewayUrlPrefix() {
        return gatewayUrlPrefix;
    }

    public void setGatewayUrlPrefix(String gatewayUrlPrefix) {
        this.gatewayUrlPrefix = gatewayUrlPrefix;
    }
}
