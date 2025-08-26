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

            Connection conn = DriverManager.getConnection("jdbc:TAOS-RS://10.162.200.58:6041/cms_arithmetic_db?user=root&password=taosdata&httpConnectTimeout=600000&httpSocketTimeout=600000&timezone=UTC-8&charset=UTF-8&locale=en_US.UTF-8&httpPoolSize=350");
            Statement statement = conn.createStatement();
            StringBuilder sqlbuilder = new StringBuilder("insert into   ");

            String stableName = "cms_arithmetic_stable";
            Integer rownum = 1000;
//            File file = new File("E:\\data\\cms\\上电6WM\\arith");
//            File file = new File("E:\\data\\cms\\明阳更新0623-2\\明阳更新0623-2");
            File file = new File("F:\\data\\cms\\json字段0822");


//            FileWriter fw = new FileWriter("E:\\data\\cms\\明阳更新0623-2/ssmy_arithmetics0623-2.sql");
            if (file.isDirectory()) {

                JedisSentinelPool sentinelPool = new JedisSentinelPool(masterName, sentinels, poolConfig,10000,"Zjhc@1234!",14);


                JedisPoolConfig config = new JedisPoolConfig();
                config.setMaxTotal(20);    // 最大连接数
                config.setMaxIdle(5);      // 最大空闲连接
                JedisPool pool = new JedisPool(config, "10.162.200.26", 8000, 10000, null, 14);


                File[] fileNames = file.listFiles();

                for (File f : fileNames) {
                    Jedis jedis = pool.getResource();
//                    Jedis jedis = sentinelPool.getResource();
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

                            String event_operational_status = "健康";
                            String event_alert_location = "";

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

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    jedis.close();
                }
            }
//            fw.close();

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
