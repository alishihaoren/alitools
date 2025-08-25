package com.ali.zn.data.dataprocess.tsadata;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LinearInterpolationAlignment {



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

        // 创建目标时间点（每15分钟一个点）
        Instant start = Instant.parse("2023-01-01T00:00:00Z");
        Instant end = Instant.parse("2023-01-01T03:00:00Z");
        List<Instant> targetTimestamps = generateTimestamps(start, end, Duration.ofMinutes(15));

        // 对齐序列1
        List<DataPoint> alignedSeries1 = interpolateSeries(series1, targetTimestamps);
        List<DataPoint> alignedSeries2 = interpolateSeries(series2, targetTimestamps);

        // 打印对齐结果
        System.out.println("Timestamp\tSeries1\tSeries2");
        for (int i = 0; i < targetTimestamps.size(); i++) {
            System.out.printf("%s\t%.1f\t%.1f%n",
                    targetTimestamps.get(i),
                    alignedSeries1.get(i).value,
                    alignedSeries2.get(i).value);
        }
    }

    // 生成时间戳序列
    private static List<Instant> generateTimestamps(Instant start, Instant end, Duration interval) {
        List<Instant> timestamps = new ArrayList<>();
        Instant current = start;
        while (!current.isAfter(end)) {
            timestamps.add(current);
            current = current.plus(interval);
        }
        return timestamps;
    }

    // 线性插值
    private static List<DataPoint> interpolateSeries(List<DataPoint> series, List<Instant> targetTimestamps) {
        List<DataPoint> result = new ArrayList<>();
        int index = 0;

        for (Instant ts : targetTimestamps) {
            // 找到目标时间点前后的数据点
            while (index < series.size() - 1 && series.get(index + 1).timestamp.isBefore(ts)) {
                index++;
            }

            if (index >= series.size() - 1) {
                // 如果目标时间点在序列末尾之后，使用最后一个值
                result.add(new DataPoint(ts, series.get(series.size() - 1).value));
            } else {
                DataPoint left = series.get(index);
                DataPoint right = series.get(index + 1);

                if (ts.isBefore(left.timestamp)) {
                    // 如果目标时间点在第一个点之前，使用第一个值
                    result.add(new DataPoint(ts, left.value));
                } else {
                    // 线性插值
                    double fraction = (double) Duration.between(left.timestamp, ts).toMillis() /
                            Duration.between(left.timestamp, right.timestamp).toMillis();
                    double value = left.value + fraction * (right.value - left.value);
                    result.add(new DataPoint(ts, value));
                }
            }
        }

        return result;
    }

}
