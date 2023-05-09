package com.java.basic.test;

import com.alibaba.fastjson.JSONObject;
import com.zn.learn.basic.proto.TSData;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.*;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;
import redis.clients.jedis.util.JedisClusterCRC16;

import java.util.*;

public class RedisTest {

    private JedisCluster jc;

    private KafkaConsumer<String, String> consumer;


    @Before
    public void initRedisCluster() {
        Set<HostAndPort> jedisclusterNode = new HashSet<HostAndPort>();
        jedisclusterNode.add(new HostAndPort("10.162.201.109", 6379));
        jedisclusterNode.add(new HostAndPort("10.162.201.110", 6379));
        jedisclusterNode.add(new HostAndPort("10.162.201.111", 6379));
//        jedisclusterNode.add(new HostAndPort("192.168.1.121",7004));
//        jedisclusterNode.add(new HostAndPort("192.168.1.121",7005));
//        jedisclusterNode.add (new HostAndPort("192.168.1.121",7006));
        GenericObjectPoolConfig cfg = new JedisPoolConfig();
        cfg.setMaxTotal(100);
        cfg.setMaxIdle(20);
        cfg.setMaxWaitMillis(-1);
        cfg.setTestOnBorrow(true);
        cfg.setTestWhileIdle(true);
        jc = new JedisCluster(jedisclusterNode, 6000, 100, 6, "Zjny@1234", cfg);
    }

    @Before
    public void initKafka() {
        String kafkaUrl = "hadoop01:6667,hadoop02:6667,hadoop03:6667";
        String groupId = "redis pool";

//        if (consumer != null) {
//            return consumer;
//        }
        Properties properties = new Properties();
        properties.put("bootstrap.servers", kafkaUrl);
        properties.put("group.id", groupId);
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("auto.offset.reset", "earliest");
        properties.put("session.timeout.ms", "30000");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

//        properties.setProperty ("security.protocol", "SASL_PLAINTEXT");
//        properties.setProperty ("sasl.mechanism", "PLAIN");
//        properties.setProperty("security.protocol", "SASL_PLAINTEXT");
//        properties.setProperty("sasl.mechanism", "PLAIN");
        String username = "xuleiming";
        String passwd = "NuQ5kg%9dyQREpk5XeoLzIN6v^6uWB4a*Rw#";
        String jassc = "org.apache.kafka.common.security.plain.PlainLoginModule required\n"
                + "username = \"" + username + "\"\n"
                + "password =\"" + passwd + "\";";
        properties.setProperty("sasl.jaas.config", jassc);
        consumer = new KafkaConsumer(properties);
    }


    @Test
    public void getRedisData() {
        Set<HostAndPort> jedisclusterNode = new HashSet<HostAndPort>();
        jedisclusterNode.add(new HostAndPort("10.162.201.109", 6379));
        jedisclusterNode.add(new HostAndPort("10.162.201.110", 6379));
        jedisclusterNode.add(new HostAndPort("10.162.201.111", 6379));
//        jedisclusterNode.add(new HostAndPort("192.168.1.121",7004));
//        jedisclusterNode.add(new HostAndPort("192.168.1.121",7005));
//        jedisclusterNode.add (new HostAndPort("192.168.1.121",7006));
        GenericObjectPoolConfig cfg = new JedisPoolConfig();
        cfg.setMaxTotal(100);
        cfg.setMaxIdle(20);
        cfg.setMaxWaitMillis(-1);
        cfg.setTestOnBorrow(true);
        cfg.setTestWhileIdle(true);
        JedisCluster jc = new JedisCluster(jedisclusterNode, 6000, 100, 6, "Zjny@1234", cfg);
//           new JedisCluster(jedisclusterNode,)

        consumer.subscribe(Arrays.asList("renewable_solar_prod"));


        JedisClientConfig jedisClientConfig = new ZNJedisClientConfig("Zjny@1234");
        ClusterPipeline clusterPipeline = new ClusterPipeline(jedisclusterNode, jedisClientConfig, cfg);


        Integer countTotal = 0;
        Long nowTime = System.currentTimeMillis();
        String[] dataArr = new String[20000];
        while (true) {
            ConsumerRecords<String, String> recordList = consumer.poll(1000);
            Map<String, String> hashMap = new HashMap<>();
            for (ConsumerRecord<String, String> onerecord : recordList) {
                TSData tsData = JSONObject.parseObject(onerecord.value(), TSData.class);
                int solitID = JedisClusterCRC16.getCRC16(tsData.getTagName());
//                Connection jedis = jc.getConnectionFromSlot(solitID);
                clusterPipeline.set(tsData.getTagName(), onerecord.value());
//                Pipeline pipeline = jedis.pipelined();
//                jc.set(tsData.getTagName(), onerecord.value());

//                System.out.println(onerecord.value());

//                tsData.getCompanyCode();

//                dataArr[countTotal]=tsData.getTagName();
//                dataArr[countTotal+10000]=onerecord.value();
//                hashMap.put(tsData.getTagName(), onerecord.value());

                countTotal++;
                if (countTotal == 10000) {
                    clusterPipeline.sync();
//                    pipeline.sync();
//                    pipeline.close();
                    System.out.println(" 1w 条耗时" + (System.currentTimeMillis() - nowTime));
                    nowTime = System.currentTimeMillis();
                    countTotal = 0;
//                    hashMap = new HashMap<>();
                }

            }

        }

    }


    @Test
    public void testOneNodeINCluster() {
//        JedisClientConfig jcc=new DefaultJedisClientConfig();
        Jedis jedis = new Jedis("10.162.201.110", 6379);
        jedis.auth("Zjny@1234");
        jedis.getDB();
        jedis.set("ali", "1111");
    }

    @Test
    public void getAllCacheLoadCluster() {

        Set<HostAndPort> jedisclusterNode = new HashSet<HostAndPort>();
        jedisclusterNode.add(new HostAndPort("10.162.201.109", 6379));
        jedisclusterNode.add(new HostAndPort("10.162.201.110", 6379));
        jedisclusterNode.add(new HostAndPort("10.162.201.111", 6379));
//        jedisclusterNode.add(new HostAndPort("192.168.1.121",7004));
//        jedisclusterNode.add(new HostAndPort("192.168.1.121",7005));
//        jedisclusterNode.add (new HostAndPort("192.168.1.121",7006));
        GenericObjectPoolConfig cfg = new JedisPoolConfig();
        cfg.setMaxTotal(100);
        cfg.setMaxIdle(20);
        cfg.setMaxWaitMillis(-1);
        cfg.setTestOnBorrow(true);
        cfg.setTestWhileIdle(true);
        JedisCluster jc = new JedisCluster(jedisclusterNode, 6000, 100, 6, "Zjny@1234", cfg);
        JedisClientConfig jedisClientConfig = new ZNJedisClientConfig("Zjny@1234");
        ClusterPipeline clusterPipeline = new ClusterPipeline(jedisclusterNode, jedisClientConfig, cfg);
//        List<String>  reslist= jc.mget("5131.P010WBA01CE002PW04","5131.P010WBA01CE002PW04");
//        while (true){
//              Long startTmp=System.currentTimeMillis();
//            Response<ScanResult<String>>  rs=  clusterPipeline.scan("*");
//
//            Long endTmp=System.currentTimeMillis();
//            System.out.println(" 集群获取数据耗时："+(endTmp-startTmp));
//            startTmp=endTmp;
//
//        }

        final CommandObjects commandObjects = new CommandObjects();
        commandObjects.keys("*");
        Set<String> result = new HashSet<>();

        Map<String, ConnectionPool> clusterNodes = jc.getClusterNodes();

        for (Map.Entry<String, ConnectionPool> entry : clusterNodes.entrySet()) {
            Connection resource = entry.getValue().getResource();

            Set<String> res = resource.executeCommand(commandObjects.keys("5131*"));
            if (!CollectionUtils.isEmpty(res)) {
                // 合并搜索结果
                result.addAll(res);
            }
            resource.close();
        }
      ;
        List  datalist=  new ArrayList<>(result);

        Long startTmp = System.currentTimeMillis();
        for (Object key :   datalist.subList(10000,15000)) {
           jc.get(key.toString());
        }
        Long endTmp = System.currentTimeMillis();
        System.out.println(result.size()+"   集群获取数据耗时：" + (endTmp - startTmp));


    }


}
