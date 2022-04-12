package com.ali.basic.process;

import com.ali.basic.beans.BandStudentRule;
import com.ali.basic.beans.Student;
import com.ali.basic.source.RandomBeanSource;
import com.ali.basic.source.RuleParallSource;
import com.alibaba.fastjson.JSON;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.api.common.functions.RichFunction;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.datastream.KeyedStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.ProcessFunction;

import org.apache.flink.streaming.api.functions.co.ProcessJoinFunction;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.table.api.bridge.java.StreamTableEnvironment;
import org.apache.flink.util.Collector;

import java.time.Duration;

public class StreamJoinProcess {

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
        ));

// interval join  操作
        DataStream<BandStudentRule> keyRuleStream = ruleStream.assignTimestampsAndWatermarks(WatermarkStrategy.<BandStudentRule>forBoundedOutOfOrderness(Duration.ofSeconds(5)).withTimestampAssigner((event, timestamp) -> event.getTmp()))
                .keyBy(event -> event.getName());




//        stStream.print();
//        ruleStream.print();


        try {
            senv.execute(" data process  data");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
