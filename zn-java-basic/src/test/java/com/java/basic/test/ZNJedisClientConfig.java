package com.java.basic.test;

import redis.clients.jedis.JedisClientConfig;

public class ZNJedisClientConfig  implements JedisClientConfig {


    private String password;

    public ZNJedisClientConfig(String password) {
        this.password = password;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
