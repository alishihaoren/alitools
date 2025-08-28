package com.ali.zn.data.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

@Log4j2
public class CMS2ZnTSDBApplication {

    public  static  String redisPrefix = "CMS::";

    public static void main(String[] args) {
        log.info(" kafka  算法结构并 实现数据推送--");
        // 消费   201 数据
//        String kafkaUrl = "10.162.200.23:9092,10.162.200.24:9092,10.162.200.25:9092";
        String kafkaUrl = "10.166.15.216:9094,10.166.15.217:9094,10.166.15.218:9094";
        String groupId = "cms2zntsdb";
        Properties properties = new Properties();
        properties.put("bootstrap.servers", kafkaUrl);
        properties.put("group.id", groupId);
        properties.put("enable.auto.commit", "false");
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("auto.offset.reset", "earliest");
        properties.put("session.timeout.ms", "30000");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        properties.setProperty("security.protocol", "SASL_PLAINTEXT");
        properties.setProperty("sasl.mechanism", "PLAIN");
        properties.setProperty("security.protocol", "SASL_PLAINTEXT");
        properties.setProperty("sasl.mechanism", "PLAIN");
        String username = "admin";
        String passwd = "Z24qjmw1Yd495AilDxC4";
        String jassc = "org.apache.kafka.common.security.plain.PlainLoginModule required\n"
                + "username = \"" + username + "\"\n"
                + "password =\"" + passwd + "\";";
        properties.setProperty("sasl.jaas.config", jassc);
        KafkaConsumer<String, String> consumer = new KafkaConsumer(properties);
        consumer.subscribe(Arrays.asList("wind_5211_cms_arithmetic"));
        // 初始化  rdd partitons
        //      Class.forName("com.taosdata.jdbc.rs.RestfulDriver")
        Connection conn = null;
        Statement statement = null;
        String stableName = "cms_arithmetic_stable";
        try {
            Class.forName("com.taosdata.jdbc.rs.RestfulDriver");

            conn = DriverManager.getConnection("jdbc:TAOS-RS://10.166.15.57:16041/cms_arithmetic_db?user=root&password=taosdata&httpConnectTimeout=600000&httpSocketTimeout=600000&timezone=UTC-8&charset=UTF-8&locale=en_US.UTF-8&httpPoolSize=350");
            statement = conn.createStatement();
            StringBuilder sqlbuilder = new StringBuilder("insert into   ");


        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String masterName = "mymaster"; // 主节点名称
        Set<String> sentinels = new HashSet<>();
        sentinels.add("10.166.15.216:26379");
        sentinels.add("10.166.15.217:26379");
        sentinels.add("10.166.15.237:26379");
        // 连接池配置
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(20);
        poolConfig.setMaxIdle(5);

        JedisSentinelPool sentinelPool = new JedisSentinelPool(masterName, sentinels, poolConfig,10000,"VPIoHw1S6yfDm9jjBJ6u",1);

//        Jedis jedis = sentinelPool.getResource();

        while (true) {
            try {
                ConsumerRecords<String, String> recordList = consumer.poll(1000);
                Boolean needSleep = true;
                Jedis jedis = sentinelPool.getResource();
                for (ConsumerRecord<String, String> onerecord : recordList) {
                    needSleep = false;
//                    System.out.println(onerecord.partition() + "   " + onerecord.offset() + "   " + );

//                    JSONObject jo = JSONObject.parseObject(onerecord.value().toString());
                    try {
                        JSONObject jo = JSONObject.parseObject(onerecord.value().toString());
                        String time = jo.getString("recorded_time");

                        JSONObject redisJo = new JSONObject();

                        redisJo.put("recorded_time", time);
                        if (time.length() == 19) {
                            StringBuilder sqlbuilderback = new StringBuilder("insert into   ");

                            Double rotation_speed = jo.getDouble("rotation_speed");
                            Double gearbox_rms_1 = jo.getDouble("gearbox_rms_1");
                            Double gearbox_rms_2 = jo.getDouble("gearbox_rms_2");
                            Double generator_bearing_rms_1 = jo.getDouble("generator_bearing_rms_1");
                            Double generator_bearing_rms_2 = jo.getDouble("generator_bearing_rms_2");
                            Double generator_bearing_rms_3 = jo.getDouble("generator_bearing_rms_3");
                            Double generator_bearing_rms_4 = jo.getDouble("generator_bearing_rms_4");
                            Double main_bearing_rms_1 = jo.getDouble("main_bearing_rms_1");
                            Double main_bearing_rms_2 = jo.getDouble("main_bearing_rms_2");
                            Double main_bearing_rms_3 = jo.getDouble("main_bearing_rms_3");
                            Double main_bearing_rms_4 = jo.getDouble("main_bearing_rms_4");
                            String operational_status = jo.getString("operational_status");
                            String alert_location = jo.getString("alert_location");
                            Double gearbox_mechanical_index = jo.getDouble("gearbox_mechanical_index");
                            Double generator_shaft_mechanical_index = jo.getDouble("generator_shaft_mechanical_index");
                            Double main_bearing_mechanical_index = jo.getDouble("main_bearing_mechanical_index");
                            Double gearbox_health_index = jo.getDouble("gearbox_health_index");
                            Double generator_bearing_health_index = jo.getDouble("generator_bearing_health_index");
                            Double main_bearing_health_index = jo.getDouble("main_bearing_health_index");
                            Double mechanical_index1 = jo.getDouble("mechanical_index1");
                            Double mechanical_index2 = jo.getDouble("mechanical_index2");
                            Double mechanical_index3 = jo.getDouble("mechanical_index3");
                            redisJo.put("operational_status", operational_status);
                            redisJo.put("alert_location", alert_location);
                            String event_operational_status = jo.getString("operational_status");
                            String event_alert_location = jo.getString("alert_location");

                            //2025年8月25日新增三个字段
                            Double main_bearing_health_index1 = jo.getDouble("main_bearing_health_index1");
                            Double main_bearing_health_index2 = jo.getDouble("main_bearing_health_index2");
                            Double main_bearing_health_index3 = jo.getDouble("main_bearing_health_index3");

                            redisJo.put("alarmtimes",0);
                            String device_id = jo.getString("device_id");
                            String tagName = "5211.CMS." + device_id;
                            String redisKey = redisPrefix + tagName;
                            // 遇到不健康的则更新 状态
                            String  alarmStatus= jedis.get(redisKey);
                            if (!operational_status.equals("健康")) {
                                if(alarmStatus==null) {
                                    redisJo.put("alarmtimes",1);
                                    jedis.set(redisKey,  redisJo.toJSONString());
                                }else{
                                    // 更新redis 数字
                                    JSONObject jo1 = JSONObject.parseObject(alarmStatus);
                                    Integer alarmNums=jo1.getInteger("alarmtimes")+1;
                                    redisJo.put("alarmtimes",alarmNums);
                                    jedis.set(redisKey, redisJo.toString());
                                    if(alarmNums>=3){
                                        // 更新后续值状态
                                        event_operational_status =operational_status;
                                        event_alert_location = alert_location;
                                    }
                                }
                            }else{
                                JSONObject jo1 = JSONObject.parseObject(alarmStatus);
                                // 当不满足 连续三次式需要清空redis 里面的key 值
                                if(alarmStatus!=null) {
                                    Integer alarmNums=jo1.getInteger("alarmtimes");
                                    if (alarmNums < 3) {
                                        jedis.del(redisKey);
                                        event_operational_status = operational_status;
                                        event_alert_location = alert_location;
                                    }else {
                                        // 记录告警部位 不在时间
                                        event_operational_status = jo1.getString("operational_status");
                                        event_alert_location = jo1.getString("alert_location");
                                    }
                                }
                            }

                            String tbName = "cms_arithmetic_stable_" + tagName.replace(".", "_");
                            sqlbuilderback.append(tbName).append("(time,rotation_speed,gearbox_rms_1, gearbox_rms_2, generator_bearing_rms_1,generator_bearing_rms_2, generator_bearing_rms_3, generator_bearing_rms_4, main_bearing_rms_1, main_bearing_rms_2, main_bearing_rms_3, main_bearing_rms_4, operational_status, alert_location, gearbox_mechanical_index, generator_shaft_mechanical_index, main_bearing_mechanical_index, gearbox_health_index,  generator_bearing_health_index, main_bearing_health_index,mechanical_index1,mechanical_index2,mechanical_index3,event_operational_status,event_alert_location,main_bearing_health_index1,main_bearing_health_index2,main_bearing_health_index3) using cms_arithmetic_stable( tag_name,device_id,standard_code) TAGS('" + tagName + "','" + device_id + "','" + tagName + "')" +
                                    "values('" + time + "'," + rotation_speed + "," + gearbox_rms_1 + ", " + gearbox_rms_2 + ", " + generator_bearing_rms_1 + "," + generator_bearing_rms_2 + ", " + generator_bearing_rms_3 + ", " + generator_bearing_rms_4 + ", " + main_bearing_rms_1 + "," + main_bearing_rms_2 + ", " + main_bearing_rms_3 + ", " + main_bearing_rms_4 + ", '" + operational_status + "', '" + alert_location + "', " + gearbox_mechanical_index + ", " + generator_shaft_mechanical_index + ", " + main_bearing_mechanical_index + "," + gearbox_health_index + "," + generator_bearing_health_index + ", " + main_bearing_health_index + ", " + mechanical_index1 + ", " + mechanical_index2 + ", " + mechanical_index3 +",'"+event_operational_status+"','"+event_alert_location+"',"+main_bearing_health_index1+ ","+main_bearing_health_index2+ ","+main_bearing_health_index3+ ")");

                            System.out.println(statement.execute(sqlbuilderback.toString()));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                consumer.commitAsync();

                if (needSleep) {
                    Thread.sleep(5000);
                }
                jedis.close();
            } catch (Exception e) {

                e.printStackTrace();
            }

        }
//


    }

}
