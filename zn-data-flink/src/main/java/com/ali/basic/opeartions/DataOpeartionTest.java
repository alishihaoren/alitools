package com.ali.basic.opeartions;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.typeinfo.Types;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.KeyedProcessFunction;
import org.apache.flink.streaming.api.functions.ProcessFunction;
import org.apache.flink.streaming.runtime.streamrecord.StreamElement;
import org.apache.flink.table.api.DataTypes;
import org.apache.flink.table.api.Schema;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.table.types.DataType;
import org.apache.flink.util.Collector;

import javax.sound.midi.Soundbank;
import java.util.HashMap;
import java.util.Map;

public class DataOpeartionTest {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment sen1 = StreamExecutionEnvironment.createLocalEnvironment();
      StreamTableEnvironment sTable=  StreamTableEnvironment.create(sen1);
        sen1.setParallelism(1);
        DataStream<String> fileLine = sen1.readTextFile("file:///D://data.txt");
       DataStream<Tuple2<String,Integer>> dataLine= fileLine.map(new MapFunction<String, Tuple2<String, Integer>>() {
            @Override
            public Tuple2<String, Integer> map(String s) throws Exception {
                JSONObject jsonObject = JSONObject.parseObject(s);
                return new Tuple2(jsonObject.getString("name"), jsonObject.getInteger("age"));
            }
        });

        sTable.createTemporaryView("ali_tb",dataLine, Schema.newBuilder().columnByExpression("name","f0").columnByExpression("age", "cast(f1 as bigint)").build());


        sTable.executeSql("select name,age from ali_tb").print();

        sen1.execute("exec data process");

    }


}


