package com.ali.zn.data.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.log4j.Log4j2;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Properties;

@Log4j2
public class CMS2DorisApplication {
    public static void main(String[] args) {
        // 消费   201 数据
//        String kafkaUrl = "10.162.200.23:9092,10.162.200.24:9092,10.162.200.25:9092";
        String kafkaUrl = "10.166.15.216:9094,10.166.15.217:9094,10.166.15.218:9094";
        String groupId = "cms2doris";
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
        consumer.subscribe(Arrays.asList("zn_cms_freq_domain"));
        // 初始化  rdd partitons
        //      Class.forName("com.taosdata.jdbc.rs.RestfulDriver")
        Connection conn=null;
        Statement statement=null;
        String sqlFormat="insert  into zn_cms_freq_domain(time,device_code,device_id,gearbox_frequency_domain,generator_bearing_frequency_domain,main_bearing_frequency_domain)  values('%s','%s',%s,'%s','%s','%s')";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
             conn = DriverManager.getConnection("jdbc:mysql://10.166.15.55:9030/cms_arithmetic?useUnicode=true&characterEncoding=utf8&useTimezone=true&serverTimezone=Asia/Shanghai&useSSL=false&allowPublicKeyRetrieval=true", "root", "Znsk@123456");
             statement = conn.createStatement();

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        while (true) {
            try {
                ConsumerRecords<String, String> recordList = consumer.poll(1000);
                Boolean  needSleep=true;
                for (ConsumerRecord<String, String> onerecord : recordList) {
                    needSleep=false;
//                    System.out.println(onerecord.partition() + "   " + onerecord.offset() + "   " + );
                    JSONObject jo = JSONObject.parseObject(onerecord.value().toString());
                    String tagName="5211.CMS."+jo.getString("device_id");
                    Integer deviceId=jo.getInteger("device_id");
                    String time=jo.getString("recorded_time");
                    String  gFDStr=null;
                    String  gBAStr=null;
                    String  mBAStr=null;
                    if(jo.size()>2) {
                        JSONArray gearboxArr = jo.getJSONArray("gearbox_frequency_domain");

                        if(gearboxArr!=null) {
//                        byte[] compArr= compressString(gearboxArr.toString());
                            gFDStr=gearboxArr.toString();
//                        System.out.println(gearboxArr.toString().length() + "   " +"      comp "+compArr.length+ gearboxArr.toString().substring(0, 1000));
                        }
                        JSONArray gBArr = jo.getJSONArray("generator_bearing_frequency_domain");

                        if(gBArr!=null) {
//                        byte[] compArr= compressString(gearboxArr.toString());
                            gBAStr=gBArr.toString();
//                        System.out.println(gearboxArr.toString().length() + "   " +"      comp "+compArr.length+ gearboxArr.toString().substring(0, 1000));
                        }
                        JSONArray mBArr = jo.getJSONArray("main_bearing_frequency_domain");
                        if(mBArr!=null&&mBArr.size()>1000) {
//                        byte[] compArr= compressString(gearboxArr.toString());
                            mBAStr=mBArr.toString();
//                        System.out.println(gearboxArr.toString().length() + "   " +"      comp "+compArr.length+ gearboxArr.toString().substring(0, 1000));
                        }
                    }
                    String execSql=String.format(sqlFormat,time,tagName,deviceId,gFDStr, gBAStr,mBAStr);
                    System.out.println(  statement.execute(execSql));

                }
                consumer.commitAsync();
                if(needSleep){
                    log.info(" 未拉取到数据 休眠5秒钟");
                    Thread.sleep(5000);
                }
            } catch (Exception e) {

                e.printStackTrace();
            }


        }


    }

}
