package com.ali.zn.data.dataprocess.tsadata;

import java.time.Instant;
import java.util.*;

public class TimeSeriesAlignment {

    static class DataPoint {
        Instant timestamp;
        double value;

        DataPoint(Instant timestamp, double value) {
            this.timestamp = timestamp;
            this.value = value;
        }
    }

    public static void main(String[] args) {
        // 创建两个时间序列
        List<DataPoint> series1 = Arrays.asList(
                new DataPoint(Instant.parse("2023-01-01T00:00:00Z"), 10.0),
                new DataPoint(Instant.parse("2023-01-01T01:00:00Z"), 15.0),
                new DataPoint(Instant.parse("2023-01-01T02:00:00Z"), 20.0)
        );

        List<DataPoint> series2 = Arrays.asList(
                new DataPoint(Instant.parse("2023-01-01T00:30:00Z"), 100.0),
                new DataPoint(Instant.parse("2023-01-01T01:30:00Z"), 150.0),
                new DataPoint(Instant.parse("2023-01-01T02:30:00Z"), 200.0)
        );

        // 对齐时间序列
        Map<Instant, Double> alignedSeries1 = new TreeMap<>();
        Map<Instant, Double> alignedSeries2 = new TreeMap<>();

        // 填充第一个序列
        for (DataPoint dp : series1) {
            alignedSeries1.put(dp.timestamp, dp.value);
        }

        // 填充第二个序列
        for (DataPoint dp : series2) {
            alignedSeries2.put(dp.timestamp, dp.value);
        }

        // 创建包含所有时间戳的集合
        Set<Instant> allTimestamps = new TreeSet<>();
        allTimestamps.addAll(alignedSeries1.keySet());
        allTimestamps.addAll(alignedSeries2.keySet());

        // 打印对齐结果
        System.out.println("Timestamp\tSeries1\tSeries2");
        for (Instant ts : allTimestamps) {
            Double val1 = alignedSeries1.get(ts);
            Double val2 = alignedSeries2.get(ts);

            System.out.printf("%s\t%.1f\t%.1f%n",
                    ts,
                    val1 != null ? val1 : Double.NaN,
                    val2 != null ? val2 : Double.NaN);
        }
    }




}
