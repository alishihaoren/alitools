package com.ali.zn.data.dataprocess.redis;

import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisSentinelPool;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.Set;

public class RedisTest {


    @Test
    public void QueryRedisData() {

        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(20);    // 最大连接数
        config.setMaxIdle(5);      // 最大空闲连接

        try (JedisPool pool = new JedisPool(config, "10.162.200.26", 8000, 1000, null, 14);
             Jedis jedis = pool.getResource()) {

//            jedis.set("pool_test", "connected ali11111");
            System.out.println(jedis.get("SNAPSHOT:2191.C1T22.Y"));
        }


    }


    @Test
    public void QuerySendRedisData() {

        String masterName = "mymaster"; // 主节点名称
        Set<String> sentinels = new HashSet<>();
        sentinels.add("10.162.201.26:7001");
        sentinels.add("10.162.201.27:7001");
        sentinels.add("10.162.201.28:7001");

        // 连接池配置
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(20);
        poolConfig.setMaxIdle(5);

        // 创建Sentinel连接池
        try (JedisSentinelPool sentinelPool = new JedisSentinelPool(masterName, sentinels, poolConfig)) {
            // 写入数据
            try (Jedis jedis = sentinelPool.getResource()) {
                jedis.set("key1", "value1");
                System.out.println("写入 key1 成功");
            }

            // 读取数据
            try (Jedis jedis = sentinelPool.getResource()) {
                String value = jedis.get("key1");
                System.out.println("读取 key1: " + value);
            }

            // 批量插入
            try (Jedis jedis = sentinelPool.getResource()) {
                for (int i = 0; i < 10; i++) {
                    jedis.set("key" + i, "value" + i);
                }
                System.out.println("批量插入完成");
            }

            // 批量查询
            try (Jedis jedis = sentinelPool.getResource()) {
                for (int i = 0; i < 10; i++) {
                    String value = jedis.get("key" + i);
                    System.out.println("key" + i + "=" + value);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    @Test
    public void data2TDTest() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        SimpleDateFormat formatNormal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        String masterName = "mymaster"; // 主节点名称
        Set<String> sentinels = new HashSet<>();
        sentinels.add("10.162.201.26:7001");
        sentinels.add("10.162.201.27:7001");
        sentinels.add("10.162.201.28:7001");

        // 连接池配置
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(20);
        poolConfig.setMaxIdle(5);

        String redisPrefix = "CMS::";

        try {
            Class.forName("com.taosdata.jdbc.rs.RestfulDriver");

            Connection conn = DriverManager.getConnection("jdbc:TAOS-RS://10.162.201.119:6041/cms_arithmetic_db?user=root&password=taosdata&httpConnectTimeout=600000&httpSocketTimeout=600000&timezone=UTC-8&charset=UTF-8&locale=en_US.UTF-8&httpPoolSize=350");
            Statement statement = conn.createStatement();
            StringBuilder sqlbuilder = new StringBuilder("insert into   ");

            String stableName = "cms_arithmetic_stable";
            Integer rownum = 1000;
            File file = new File("E:\\data\\cms\\上电6WM\\arith");
//            File file = new File("E:\\data\\cms\\明阳更新0623-2\\明阳更新0623-2");


            FileWriter fw = new FileWriter("E:\\data\\cms\\明阳更新0623-2/ssmy_arithmetics0623-2.sql");
            if (file.isDirectory()) {

//                JedisSentinelPool sentinelPool = new JedisSentinelPool(masterName, sentinels, poolConfig,10000,"Zjhc@1234!",14);
//
//                Jedis jedis = sentinelPool.getResource();
                JedisPoolConfig config = new JedisPoolConfig();
                config.setMaxTotal(20);    // 最大连接数
                config.setMaxIdle(5);      // 最大空闲连接
                JedisPool pool = new JedisPool(config, "10.162.200.26", 8000, 10000, null, 14);
                Jedis jedis = pool.getResource();

                File[] fileNames = file.listFiles();

                for (File f : fileNames) {
                    System.out.println(f.getName());

                    FileReader fr = new FileReader(f.getAbsoluteFile());
                    BufferedReader br = new BufferedReader(fr);
                    String line = "";
                    while ((line = br.readLine()) != null) {
                        try {
                            JSONObject jo = JSONObject.parseObject(line);
                            JSONObject redisJo = new JSONObject();
                            String time = jo.getString("recorded_time");
                            if (time.contains("/")) {
                                time = formatNormal.format(format.parse(time));
                            }
                            redisJo.put("recorded_time", time);
//                            if (time.length() == 19) {
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

                            String event_operational_status = jo.getString("operational_status");
                            String event_alert_location = jo.getString("alert_location");

                            redisJo.put("operational_status", operational_status);
                            redisJo.put("alert_location", alert_location);

                            Double gearbox_mechanical_index = jo.getDouble("gearbox_mechanical_index");
                            Double generator_shaft_mechanical_index = jo.getDouble("generator_shaft_mechanical_index");
                            Double main_bearing_mechanical_index = jo.getDouble("main_bearing_mechanical_index");
                            Double gearbox_health_index = jo.getDouble("gearbox_health_index");
                            Double generator_bearing_health_index = jo.getDouble("generator_bearing_health_index");
                            Double main_bearing_health_index = jo.getDouble("main_bearing_health_index");
                            //新增三个属性

                            Double mechanical_index1 = jo.getDouble("mechanical_index1");
                            Double mechanical_index2 = jo.getDouble("mechanical_index2");
                            Double mechanical_index3 = jo.getDouble("mechanical_index3");


                            String device_id = jo.getString("device_id");
                            String tagName = "5211.CMS." + device_id;
                            String redisKey = redisPrefix + tagName;
                            // 遇到不健康的则更新 状态
                            if (!operational_status.equals("健康")) {
                                jedis.set(redisKey, redisJo.toString());
                            }


                            jo.put("tag_name", tagName);
                            String redisContent = jedis.get(redisKey);
                            if (redisContent != null) {
                                JSONObject redisINfo = JSONObject.parseObject(redisContent);
                                if (!redisINfo.getString("operational_status").equals("健康")) {
                                    // 更新后续值状态
                                    event_operational_status = redisINfo.getString("operational_status");
                                    event_alert_location = redisINfo.getString("alert_location");
                                }
                            }

                            String tbName = "cms_arithmetic_stable_" + tagName.replace(".", "_");
                            sqlbuilderback.append(tbName).append("(time,rotation_speed,gearbox_rms_1, gearbox_rms_2, generator_bearing_rms_1,generator_bearing_rms_2, generator_bearing_rms_3, generator_bearing_rms_4, main_bearing_rms_1, main_bearing_rms_2, main_bearing_rms_3, main_bearing_rms_4, operational_status, alert_location, gearbox_mechanical_index, generator_shaft_mechanical_index, main_bearing_mechanical_index, gearbox_health_index,  generator_bearing_health_index, main_bearing_health_index,mechanical_index1,mechanical_index2,mechanical_index3,event_operational_status,event_alert_location) using cms_arithmetic_stable( tag_name,device_id,standard_code) TAGS('" + tagName + "','" + device_id + "','" + tagName + "')" +
                                    "values('" + time + "'," + rotation_speed + "," + gearbox_rms_1 + ", " + gearbox_rms_2 + ", " + generator_bearing_rms_1 + "," + generator_bearing_rms_2 + ", " + generator_bearing_rms_3 + ", " + generator_bearing_rms_4 + ", " + main_bearing_rms_1 + "," + main_bearing_rms_2 + ", " + main_bearing_rms_3 + ", " + main_bearing_rms_4 + ", '" + operational_status + "', '" + alert_location + "', " + gearbox_mechanical_index + ", " + generator_shaft_mechanical_index + ", " + main_bearing_mechanical_index + "," + gearbox_health_index + "," + generator_bearing_health_index + ", " + main_bearing_health_index + ", " + mechanical_index1 + ", " + mechanical_index2 + ", " + mechanical_index3 + ", '" + operational_status + "', '" + alert_location + "')");
//                                System.out.println(statement.execute(sqlbuilderback.toString()));
//                            fw.write(sqlbuilderback.append(";").append("\r\n").toString());
//                            }
                            statement.execute(sqlbuilderback.toString());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
//                    fw.flush();
                }
//                fw.flush();
            }
            fw.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

}
