package com.ali.basic.process;

import com.ali.basic.beans.BandStudentRule;
import com.ali.basic.beans.Student;
import com.ali.basic.source.RandomBeanSource;
import com.ali.basic.source.RuleParallSource;
import com.alibaba.fastjson.JSON;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.state.MapStateDescriptor;
import org.apache.flink.api.common.typeinfo.BasicTypeInfo;
import org.apache.flink.api.common.typeinfo.TypeHint;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.streaming.api.datastream.BroadcastStream;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.co.KeyedBroadcastProcessFunction;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.util.Collector;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class BroadCastJoinProcess {

    public static void main(String[] args) {
        StreamExecutionEnvironment senv = StreamExecutionEnvironment.getExecutionEnvironment();
        StreamTableEnvironment stEnv = StreamTableEnvironment.create(senv);
        DataStreamSource<Student> stStream = senv.setParallelism(1).addSource(new RandomBeanSource());
        DataStreamSource<BandStudentRule> ruleStream = senv.setParallelism(1).addSource(new RuleParallSource());

// interval join  操作
//        KeyedStream<Student, String> stKeyStream = stStream.assignTimestampsAndWatermarks(WatermarkStrategy.<Student>forBoundedOutOfOrderness(Duration.ofSeconds(10)).withTimestampAssigner(
//                (event, timestamp) -> event.getTmp()
//        )).keyBy(k -> k.getName().toString());
//
//
//        KeyedStream<BandStudentRule, String> keyRuleStream = ruleStream.assignTimestampsAndWatermarks(WatermarkStrategy.<BandStudentRule>forBoundedOutOfOrderness(Duration.ofSeconds(5)).withTimestampAssigner((event, timestamp) -> event.getTmp()))
//                .keyBy(event -> event.getName());
//        stKeyStream.intervalJoin(keyRuleStream).between(Time.seconds(0),Time.seconds(5)).process(new ProcessJoinFunction<Student, BandStudentRule, Object>() {
//            @Override
//            public void processElement(Student student, BandStudentRule bandStudentRule, Context context, Collector<Object> collector) throws Exception {
////                System.out.println(JSON.toJSONString(student));
////                System.out.println(JSON.toJSONString(bandStudentRule));
////
//                collector.collect(JSON.toJSONString(student)+ JSON.toJSONString(bandStudentRule));
//            }
//        }).print();


        DataStream<Student> stKeyStream = stStream.assignTimestampsAndWatermarks(WatermarkStrategy.<Student>forBoundedOutOfOrderness(Duration.ofSeconds(10)).withTimestampAssigner(
                (event, timestamp) -> event.getTmp()
        )).keyBy(event -> event.getName());

        MapStateDescriptor<String, BandStudentRule> ruleStateDescriptor = new MapStateDescriptor<>(
                "RulesBroadcastState",
                BasicTypeInfo.STRING_TYPE_INFO,
                TypeInformation.of(new TypeHint<BandStudentRule>() {
                }));

// 广播流，广播规则并且创建 broadcast state
        BroadcastStream<BandStudentRule> ruleBroadcastStream = ruleStream
                .broadcast(ruleStateDescriptor);
        stKeyStream.connect(ruleBroadcastStream).process(new KeyedBroadcastProcessFunction<Object, Student, BandStudentRule, Object>() {
         private Map<String ,BandStudentRule>   hashMap=new HashMap<>();

            @Override
            public void processElement(Student student, ReadOnlyContext readOnlyContext, Collector<Object> collector) throws Exception {
                System.out.println(JSON.toJSONString(student));
                System.out.println(JSON.toJSONString(hashMap));
                collector.collect("++++++student");
            }

            @Override
            public void processBroadcastElement(BandStudentRule bandStudentRule, Context context, Collector<Object> collector) throws Exception {
//                System.out.println(JSON.toJSONString(bandStudentRule));
                hashMap.put(bandStudentRule.getName(),bandStudentRule);
                collector.collect("------rule");
            }
        }).print();

//// interval join  操作
//        DataStream<BandStudentRule> keyRuleStream = ruleStream.assignTimestampsAndWatermarks(WatermarkStrategy.<BandStudentRule>forBoundedOutOfOrderness(Duration.ofSeconds(5)).withTimestampAssigner((event, timestamp) -> event.getTmp()))
//                .keyBy(event -> event.getName());


        // boradCast 操作


//        stStream.print();
//        ruleStream.print();


        try {
            senv.execute(" data process  data");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
