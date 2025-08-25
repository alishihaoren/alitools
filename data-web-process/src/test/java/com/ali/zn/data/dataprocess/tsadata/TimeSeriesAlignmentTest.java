package com.ali.zn.data.dataprocess.tsadata;

import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

public class TimeSeriesAlignmentTest {

    // 时间序列数据点
    static class DataPoint {
        Instant timestamp;
        double value;

        DataPoint(Instant timestamp, double value) {
            this.timestamp = timestamp;
            this.value = value;
        }

        @Override
        public String toString() {
            return "[" + timestamp + " : " + value + "]";
        }
    }

    // 对齐结果
    static class AlignedPoint {
        Instant timestamp;
        Double value1;
        Double value2;

        AlignedPoint(Instant timestamp, Double value1, Double value2) {
            this.timestamp = timestamp;
            this.value1 = value1;
            this.value2 = value2;
        }

        @Override
        public String toString() {
            return String.format("%s\t%.2f\t%.2f", timestamp,
                    value1 != null ? value1 : Double.NaN,
                    value2 != null ? value2 : Double.NaN);
        }
    }

    public static void main(String[] args) {
        // 创建两个时间序列（原始时间戳）
        List<DataPoint> series1 = Arrays.asList(
                new DataPoint(Instant.parse("2023-01-01T08:00:00Z"), 10.0),
                new DataPoint(Instant.parse("2023-01-01T08:15:00Z"), 15.0),
                new DataPoint(Instant.parse("2023-01-01T08:45:00Z"), 20.0),
                new DataPoint(Instant.parse("2023-01-01T09:30:00Z"), 25.0)
        );

        List<DataPoint> series2 = Arrays.asList(
                new DataPoint(Instant.parse("2023-01-01T08:05:00Z"), 100.0),
                new DataPoint(Instant.parse("2023-01-01T08:35:00Z"), 150.0),
                new DataPoint(Instant.parse("2023-01-01T09:15:00Z"), 200.0),
                new DataPoint(Instant.parse("2023-01-01T09:45:00Z"), 250.0)
        );

        System.out.println("原始序列1:");
        series1.forEach(System.out::println);

        System.out.println("\n原始序列2:");
        series2.forEach(System.out::println);

        // 1. 简单合并对齐（保留所有时间点）
        System.out.println("\n方法1: 简单合并对齐");
        List<AlignedPoint> mergedAlignment = mergeAlignment(series1, series2);
        mergedAlignment.forEach(System.out::println);

        // 2. 最近邻对齐
        System.out.println("\n方法2: 最近邻对齐");

        List<AlignedPoint> nearestAlignment = nearestNeighborAlignment(series1, series2, Duration.ofMinutes(15));
        nearestAlignment.forEach(System.out::println);


        // 3. 线性插值对齐
        System.out.println("\n方法3: 线性插值对齐");
        List<AlignedPoint> interpolatedAlignment = linearInterpolationAlignment(series1, series2);
        interpolatedAlignment.forEach(System.out::println);

        // 4. 时间窗口平均对齐
        System.out.println("\n方法4: 时间窗口平均对齐");
        List<AlignedPoint> windowedAlignment = windowedAverageAlignment(series1, series2, Duration.ofMinutes(30));
        windowedAlignment.forEach(System.out::println);
    }

    /*​**​
            * 方法1: 简单合并对齐
     * 将所有时间点合并，保留所有原始数据点
     */
    public static List<AlignedPoint> mergeAlignment(List<DataPoint> series1, List<DataPoint> series2) {
        // 合并所有时间戳并排序
        Set<Instant> allTimestamps = new TreeSet<>();
        series1.forEach(p -> allTimestamps.add(p.timestamp));
        series2.forEach(p -> allTimestamps.add(p.timestamp));

        // 创建映射以便快速查找
        Map<Instant, Double> map1 = series1.stream()
                .collect(Collectors.toMap(p -> p.timestamp, p -> p.value));

        Map<Instant, Double> map2 = series2.stream()
                .collect(Collectors.toMap(p -> p.timestamp, p -> p.value));

        // 创建对齐结果
        List<AlignedPoint> result = new ArrayList<>();
        for (Instant ts : allTimestamps) {
            result.add(new AlignedPoint(ts, map1.get(ts), map2.get(ts)));
        }

        return result;
    }

    /*​**​
            * 方法2: 最近邻对齐
     * 在指定时间容差内找到最近的点
     */
    public static List<AlignedPoint> nearestNeighborAlignment(
            List<DataPoint> series1, List<DataPoint> series2, Duration tolerance) {
        // 合并所有时间戳并排序
        Set<Instant> allTimestamps = new TreeSet<>();
        series1.forEach(p -> allTimestamps.add(p.timestamp));
        series2.forEach(p -> allTimestamps.add(p.timestamp));

        // 创建对齐结果
        List<AlignedPoint> result = new ArrayList<>();

        for (Instant ts : allTimestamps) {
            // 在序列1中查找最近点
            Double value1 = findNearestValue(series1, ts, tolerance);

            // 在序列2中查找最近点
            Double value2 = findNearestValue(series2, ts, tolerance);

            result.add(new AlignedPoint(ts, value1, value2));
        }

        return result;
    }

    private static Double findNearestValue(List<DataPoint> series, Instant target, Duration tolerance) {
        return series.stream()
                .filter(p -> Math.abs(ChronoUnit.MILLIS.between(p.timestamp, target)) <= tolerance.toMillis())
                .min(Comparator.comparingLong(p -> Math.abs(ChronoUnit.MILLIS.between(p.timestamp, target))))
                .map(p -> p.value)
                .orElse(null);
    }

    /*​**​
            * 方法3: 线性插值对齐
     * 将两个序列插值到对方的时间点上
     */
    public static List<AlignedPoint> linearInterpolationAlignment(
            List<DataPoint> series1, List<DataPoint> series2) {
        // 合并所有时间戳并排序
        Set<Instant> allTimestamps = new TreeSet<>();
        series1.forEach(p -> allTimestamps.add(p.timestamp));
        series2.forEach(p -> allTimestamps.add(p.timestamp));

        // 创建对齐结果
        List<AlignedPoint> result = new ArrayList<>();

        for (Instant ts : allTimestamps) {
            // 对序列1插值
            Double value1 = interpolateValue(series1, ts);

            // 对序列2插值
            Double value2 = interpolateValue(series2, ts);

            result.add(new AlignedPoint(ts, value1, value2));
        }

        return result;
    }

    private static Double interpolateValue(List<DataPoint> series, Instant target) {
        // 如果时间点正好存在，直接返回值
        for (DataPoint p : series) {
            if (p.timestamp.equals(target)) {
                return p.value;
            }
        }
        // 找到目标时间点前后的数据点
        DataPoint before = null;
        DataPoint after = null;
        for (DataPoint p : series) {
            if (p.timestamp.isBefore(target)) {
                before = p;
            } else if (p.timestamp.isAfter(target)) {
                after = p;
                break;
            }
        }
        // 处理边界情况
        if (before == null && after == null) {
            return null;
        } else if (before == null) {
            return after.value;
        } else if (after == null) {
            return before.value;
        }
        // 线性插值
        long totalMillis = ChronoUnit.MILLIS.between(before.timestamp, after.timestamp);
        long elapsedMillis = ChronoUnit.MILLIS.between(before.timestamp, target);
        double fraction = (double) elapsedMillis / totalMillis;

        return before.value + fraction * (after.value - before.value);
    }

    /*​**​
            * 方法4: 时间窗口平均对齐
     * 将数据聚合到固定时间窗口内
     */
    public static List<AlignedPoint> windowedAverageAlignment(
            List<DataPoint> series1, List<DataPoint> series2, Duration windowSize) {
        // 确定时间范围
        Instant minTime = series1.stream().map(p -> p.timestamp)
                .min(Comparator.naturalOrder())
                .orElse(Instant.MIN);

        Instant maxTime = series1.stream().map(p -> p.timestamp)
                .max(Comparator.naturalOrder())
                .orElse(Instant.MAX);

        // 生成时间窗口
        List<Instant> windowStarts = new ArrayList<>();
        Instant current = minTime;
        while (current.isBefore(maxTime.plus(windowSize))) {
            windowStarts.add(current);
            current = current.plus(windowSize);
        }

        // 创建对齐结果
        List<AlignedPoint> result = new ArrayList<>();

        for (Instant windowStart : windowStarts) {
            Instant windowEnd = windowStart.plus(windowSize);

            // 计算序列1在窗口内的平均值
            Double avg1 = calculateWindowAverage(series1, windowStart, windowEnd);

            // 计算序列2在窗口内的平均值
            Double avg2 = calculateWindowAverage(series2, windowStart, windowEnd);

            result.add(new AlignedPoint(windowStart, avg1, avg2));
        }

        return result;
    }

    private static Double calculateWindowAverage(
            List<DataPoint> series, Instant windowStart, Instant windowEnd) {
        List<Double> values = new ArrayList<>();

        for (DataPoint p : series) {
            if (!p.timestamp.isBefore(windowStart) && p.timestamp.isBefore(windowEnd)) {
                values.add(p.value);
            }
        }

        if (values.isEmpty()) {
            return null;
        }

        return values.stream().mapToDouble(Double::doubleValue).average().getAsDouble();
    }
}