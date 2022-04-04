package com.ali.basic.opeartions;

import com.alibaba.fastjson.JSON;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.runtime.streamrecord.StreamElement;

import javax.sound.midi.Soundbank;
import java.util.HashMap;
import java.util.Map;

public class DataOpeartion {

    public static void main(String[] args) {
        StreamExecutionEnvironment senv = StreamExecutionEnvironment.getExecutionEnvironment();
    DataStream  fileLine=    senv.readTextFile("file:///D://data.txt");


    }


}


