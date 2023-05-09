//package com.java.basic.test;
//
//import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
//import redis.clients.jedis.DefaultJedisClientConfig;
//import redis.clients.jedis.HostAndPort;
//import redis.clients.jedis.JedisClientConfig;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.providers.ClusterConnectionProvider;
//
//import java.io.Serializable;
//import java.util.Set;
//
//public class JedisSlotConnectionHandlerImp extends ClusterConnectionProvider implements Serializable {
//
//
//    public JedisSlotConnectionHandlerImp(Set<HostAndPort> nodes,JedisClientConfig jedisClientConfig, GenericObjectPoolConfig poolConfig, int connectionTimeout, int soTimeout, String password) {
////       JedisClientConfig  jedisClientConfig=new DefaultJedisClientConfig();
//        super(nodes,jedisClientConfig, poolConfig);
//    }
//
//    // 自定义通过slot获取JedisPool的方法
//    // 为了保证后面一个JedisPool只取一个Jedis
//    public JedisPool getJedisPoolFromSlot(int slot) {
//        JedisPool jedisPool = cache.getSlotPool(slot);
//        if (jedisPool != null) {
//            return jedisPool;
//        } else {
//            renewSlotCache();
//            jedisPool = cache.getSlotPool(slot);
//            if (jedisPool != null) {
//                return jedisPool;
//            } else {
//                throw new JedisNoReachableClusterNodeException("No reachable node in cluster for slot " + slot);
//            }
//        }
//    }
//}
