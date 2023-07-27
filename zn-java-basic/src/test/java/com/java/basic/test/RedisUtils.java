package com.java.basic.test;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class RedisUtils {

    private static JedisPool jedisPool;
    private static String defaultHost = "127.0.0.1";
    private static int defaultPort = 6379;
    private static int defaultMaxTotal = 10;
    private static int defaultMaxIdle = 5;
    private static int defaultMinIdle = 2;
    private static String defaultPassword = null;
    private static int defaultTimeout = 20000;
    private static int defaultDatabase = 0;

//    public RedisUtils(JedisPool jedisPool, String host, int port, int maxTotal, int maxIdle, int minIdle, String password, int timeout) {
//        this.jedisPool = jedisPool;
//        this.host = host;
//        this.port = port;
//        this.maxTotal = maxTotal;
//        this.maxIdle = maxIdle;
//        this.minIdle = minIdle;
//        this.password = password;
//        this.timeout = timeout;
//    }

    public static void initJedis(String host, Integer port, Integer maxTotal, Integer maxIdle, Integer minIdle, String password, Integer timeout, Integer database) {
        if (host != null) defaultHost = host;
        if (port != null) defaultPort = port;
        if (maxTotal != 0) defaultMaxTotal = maxTotal;
        if (minIdle != null) defaultMinIdle = minIdle;
        if (maxIdle != null) defaultMaxIdle = maxIdle;
        if (password != null) defaultPassword = password;
        if (timeout != null) defaultTimeout = timeout;
        if (database != null) defaultDatabase = database;
        // 初始化 jedisPool
        GenericObjectPoolConfig cfg = new JedisPoolConfig();
        cfg.setMaxTotal(defaultMaxTotal);
        cfg.setMaxIdle(defaultMaxIdle);
        cfg.setMaxWaitMillis(defaultTimeout);
        cfg.setTestOnBorrow(true);
        cfg.setTestWhileIdle(true);
        jedisPool = new JedisPool(cfg, defaultHost, defaultPort, timeout, password, defaultDatabase);
    }

    public static Jedis getJedisClient() {
        return jedisPool.getResource();
    }

    public void  closeJedis(Jedis jedis){
        jedis.close();
    }

}
