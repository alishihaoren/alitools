package com.ali.basic.process;

import com.ali.basic.beans.Student;
import com.ali.basic.source.RandomBeanSource;
import com.alibaba.fastjson.JSON;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.streaming.api.TimeCharacteristic;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.assigners.SlidingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.time.Duration;

public class StateDataProcess {

    public static void main(String[] args) {

        StreamExecutionEnvironment senv = StreamExecutionEnvironment.getExecutionEnvironment();
        senv.setStreamTimeCharacteristic(TimeCharacteristic.EventTime);
        senv.setParallelism(1);

        DataStreamSource<Student> stSource = senv.addSource(new RandomBeanSource());
          stSource.assignTimestampsAndWatermarks(WatermarkStrategy.<Student>forBoundedOutOfOrderness(Duration.ofSeconds(3)).withTimestampAssigner
                ((event, timestamp) -> event.getTmp())).keyBy(v -> v.getName()).window(SlidingEventTimeWindows.of(Time.seconds(10), Time.seconds(5))).apply(new WindowFunction<Student, Object, String, TimeWindow>() {
            @Override
            public void apply(String s, TimeWindow timeWindow, Iterable<Student> iterable, Collector<Object> collector) throws Exception {
                iterable.forEach(line -> {
                            System.out.println("------" + JSON.toJSONString(line));
                        }
                );
                collector.collect(JSON.toJSONString("AAAAAA"));
            }
        }).print();

        try {
            senv.execute(" windows  join  process");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
