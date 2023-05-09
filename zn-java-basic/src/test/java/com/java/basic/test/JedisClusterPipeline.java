//package com.java.basic.test;
//
//
//import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
//import redis.clients.jedis.JedisCluster;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.util.JedisClusterCRC16;
//
//import java.io.Serializable;
//import java.util.HashSet;
//
//public class JedisClusterPipeline extends JedisCluster implements Serializable {
//    // 覆盖父类中的connectionHandler
//    protected JedisSlotConnectionHandlerImp connectionHandler;
//    public JedisClusterPipeline(HashSet node, int connectionTimeout, int soTimeout, int maxAttempts, String password, GenericObjectPoolConfig poolConfig) {
//        super(node, connectionTimeout, soTimeout, maxAttempts, password, poolConfig);
//        connectionHandler = new JedisSlotConnectionHandlerImp(node, poolConfig, connectionTimeout, soTimeout, password);
//    }
//    // 通过key转换成slot，再获取JedisPool
//    public JedisPool getJedisPoolFromSlot(String key) {
//        return connectionHandler.getJedisPoolFromSlot(JedisClusterCRC16.getSlot(key));
//    }
//}
