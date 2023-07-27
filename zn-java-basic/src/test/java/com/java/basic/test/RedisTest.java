package com.java.basic.test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.zn.learn.basic.proto.TSData;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.Before;
import org.junit.Test;
import redis.clients.jedis.*;
import redis.clients.jedis.params.ScanParams;
import redis.clients.jedis.resps.ScanResult;
import redis.clients.jedis.util.JedisClusterCRC16;

import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class RedisTest {

    private JedisCluster jc;

    private KafkaConsumer<String, String> consumer;

    private JedisPool jedisPool;


//    @Before
//    public void initRedisCluster() {
//        Set<HostAndPort> jedisclusterNode = new HashSet<HostAndPort>();
//        jedisclusterNode.add(new HostAndPort("10.162.201.109", 6379));
//        jedisclusterNode.add(new HostAndPort("10.162.201.110", 6379));
//        jedisclusterNode.add(new HostAndPort("10.162.201.111", 6379));
////        jedisclusterNode.add(new HostAndPort("192.168.1.121",7004));
////        jedisclusterNode.add(new HostAndPort("192.168.1.121",7005));
////        jedisclusterNode.add (new HostAndPort("192.168.1.121",7006));
//        GenericObjectPoolConfig cfg = new JedisPoolConfig();
//        cfg.setMaxTotal(100);
//        cfg.setMaxIdle(20);
//        cfg.setMaxWaitMillis(-1);
//        cfg.setTestOnBorrow(true);
//        cfg.setTestWhileIdle(true);
//        jc = new JedisCluster(jedisclusterNode, 6000, 100, 6, "Zjny@1234", cfg);
//    }

    @Before
    public void initKafka() {
//        String kafkaUrl = "hadoop01:6667,hadoop02:6667,hadoop03:6667";
        String kafkaUrl = "10.162.5.6:10001,10.162.5.7:10002,10.162.5.8:10003";
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

        properties.setProperty("security.protocol", "SASL_PLAINTEXT");
        properties.setProperty("sasl.mechanism", "PLAIN");
        properties.setProperty("security.protocol", "SASL_PLAINTEXT");
        properties.setProperty("sasl.mechanism", "PLAIN");
        String username = "xuleiming";
        String passwd = "NuQ5kg%9dyQREpk5XeoLzIN6v^6uWB4a*Rw#";
        String jassc = "org.apache.kafka.common.security.plain.PlainLoginModule required\n"
                + "username = \"" + username + "\"\n"
                + "password =\"" + passwd + "\";";
        properties.setProperty("sasl.jaas.config", jassc);
        consumer = new KafkaConsumer(properties);
    }

    @Before
    public void getRedisPool() {

        GenericObjectPoolConfig cfg = new JedisPoolConfig();
        cfg.setMaxTotal(100);
        cfg.setMaxIdle(20);
        cfg.setMaxWaitMillis(-1);
        cfg.setTestOnBorrow(true);
        cfg.setTestWhileIdle(true);
//        JedisCluster jc = new JedisCluster(jedisclusterNode, 6000, 100, 6, "Zjny@1234", cfg);
//           new JedisCluster(jedisclusterNode,)
        jedisPool = new JedisPool(cfg, "10.162.201.26", 8000, 3000, "Zjhc@1234!", 14);
//        return  jedisPool;
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
//        JedisPool jedisPool=new JedisPool("10",cfg);
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
        List datalist = new ArrayList<>(result);

        Long startTmp = System.currentTimeMillis();
        for (Object key : datalist.subList(10000, 15000)) {
            jc.get(key.toString());
        }
        Long endTmp = System.currentTimeMillis();
        System.out.println(result.size() + "   集群获取数据耗时：" + (endTmp - startTmp));


    }


    @Test
    public void getRedisAloneData() throws IOException {
        JedisClientConfig jedisClientConfig = new ZNJedisClientConfig("Zjhc@1234!", null, 10);
        Jedis jedis = new Jedis("10.162.201.26", 8000, jedisClientConfig);
//        jedis.auth("Zjhc@1234!");
//            Jedis jedisclient= jedisPool.getResource();
        Pipeline pipeline = jedis.pipelined();

        consumer.subscribe(Arrays.asList("renewable_solar_prod"));
        Integer countTotal = 0;
        Long nowTime = System.currentTimeMillis();
        String[] dataArr = new String[20000];
        FileWriter fileWriter = new FileWriter("F:\\data\\renewable_solar_prod20230509.json");

        while (true) {
            ConsumerRecords<String, String> recordList = consumer.poll(1000);
            Map<String, String> hashMap = new HashMap<>();
            for (ConsumerRecord<String, String> onerecord : recordList) {
                TSData tsData = JSONObject.parseObject(onerecord.value(), TSData.class);
                fileWriter.write(onerecord.value() + "\r\n");
                pipeline.set(tsData.getTagName(), onerecord.value());
                countTotal++;
                if (countTotal == 10000) {
                    pipeline.sync();
                    fileWriter.flush();
                    System.out.println(" 1w 条耗时" + (System.currentTimeMillis() - nowTime));
                    nowTime = System.currentTimeMillis();
                    countTotal = 0;
                }
            }
        }
    }

    @Test
    public void QueryRedisAloneData() {
        JedisClientConfig jedisClientConfig = new ZNJedisClientConfig("Zjhc@1234!", null, 10);
        Jedis jedis = new Jedis("10.162.201.26", 8000, jedisClientConfig);
//        jedis.auth("Zjhc@1234!");
//            Jedis jedisclient= jedisPool.getResource();
        Pipeline pipeline = jedis.pipelined();

        String pattern = "5131*";
        String cursor = String.valueOf(0);
        List<String> total = new ArrayList<String>();
        ScanParams params = new ScanParams();
        params.match(pattern);
        params.count(20000);


        do {
            ScanResult<String> result = jedis.scan(cursor, params);
            cursor = result.getCursor();
            total.addAll(result.getResult());
        } while (Integer.valueOf(cursor) > 0);

        String[] keys = total.toArray(new String[total.size()]);
        Long nowTime = System.currentTimeMillis();
        List<String> mget1 = jedis.mget(keys);
        for (String values : mget1) {
//            values;
            if (values.contains("5131.P340WBC28GU010")) {
                System.out.println(values);
            }
        }


        System.out.println(total.size());
        System.out.println(" 1w 条耗时" + (System.currentTimeMillis() - nowTime));


    }

    /**
     * 测试 Redis update相关操作
     */

    @Test
    public void updateRedisDataTest() {
//        JedisClientConfig jedisClientConfig = new ZNJedisClientConfig("Zjhc@1234!", null, 10);

        RedisUtils.initJedis("10.162.201.26", 8000, 10, 5, 3, "Zjhc@1234!", 20000, 10);
        Jedis jedis = RedisUtils.getJedisClient();
//        Jedis jedis = new Jedis("10.162.201.26", 8000, jedisClientConfig);
        String prefix = "SNAPSHOT:";
        consumer.subscribe(Arrays.asList("renewable_wind_prod"));

        while (true) {
            Long startTime = System.currentTimeMillis();
            Pipeline pipeline = jedis.pipelined();
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            if (records.count() < 1) {
                continue;
            }
            List<TSData> batchList = new ArrayList<>();
            Set<String> tagNameSet = new HashSet<>();
            Map<String, TSData> batchMap = new HashMap<>();
            for (ConsumerRecord<String, String> crd : records) {
                TSData tsData = JSONObject.parseObject(crd.value(), TSData.class);
                batchList.add(tsData);
                tagNameSet.add(prefix + tsData.getTagName());
            }

            String[] keys = tagNameSet.toArray(new String[tagNameSet.size()]);
            List<String> list = jedis.mget(keys);
            for (String context : list) {
                TSData tsData = JSONObject.parseObject(context, TSData.class);
                if (tsData != null && tsData.getTime() != null && tsData.getTagName() != null && tsData.getTagValue() != null)
                    batchMap.put(tsData.getTagName(), tsData);
            }
            Integer updateTime = 0;
            // 时间校对 满足时间限定的直接 推送到 redis中
            for (TSData line : batchList) {
                //
                if (line == null || line.getTime() == null || line.getTagName() == null || line.getTime() == null || line.getTagValue() == null) {
                    continue;
                }
                try {
                    if (batchMap.size() < 1 || batchMap.get(line.getTagName()) == null || (batchMap.get(line.getTagName()).getTime() != null ? batchMap.get(line.getTagName()).getTime().isBefore(line.getTime()) : false)) {
                        pipeline.set(prefix + line.getTagName(), JSON.toJSONString(line));
                        updateTime++;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            pipeline.sync();
            pipeline.close();
            System.out.println("size::" + records.count() + " cost::  " + (System.currentTimeMillis() - startTime) + " ms" + " update data size:: " + updateTime);
        }
    }


    class RedisSetTask implements Runnable {
        @Override
        public void run() {

        }
    }

}
