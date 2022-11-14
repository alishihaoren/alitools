package com.zn.learn.basic.ExpressionEngine;

import cn.hutool.core.util.StrUtil;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.AviatorEvaluatorInstance;

import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.Objects;

public class AviatorUtils {

    public static AviatorEvaluatorInstance INSTANCE = AviatorEvaluator.getInstance();
    // 注册函数本身


    public static void main(String[] args) {

        AviatorEvaluator.addFunction(new SumValFunction());
        AviatorEvaluator.execute("SumVal('tagName1','2022-01-01T00:00:00.000+08:00','day')");


    }

    /**
     * 获取时间
     *
     * @param time       时间参数value
     * @param funcName   函数名
     * @param paramLabel 时间参数描述
     * @return
     */
    public static Long getTime(String time, String funcName, String paramLabel) {
        if (StrUtil.containsAny(time, Constants.Symbol.COLON)) {
            try {
                return DateUtils.parseDateToLong(DateUtils.SIMPLEDATEFORMAT, time);
            } catch (Exception e) {
//                log.warn("======> time 格式不是：{}", DateUtils.STANDARD_TIME_PATTEN);
            }
        }
        // 去除空格
        time = time.trim();
        if (StrUtil.startWith(time, TimeParamEnum.CURRENT.getCode(), true)) {
            time = StrUtil.replaceIgnoreCase(time, TimeParamEnum.CURRENT.getCode(), "");
            LocalDateTime now = LocalDateTime.now();
            now = cal(time, now);
            return now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        }
        if (StrUtil.startWith(time, TimeParamEnum.YEAR.getCode(), true)) {
            time = StrUtil.replaceIgnoreCase(time, TimeParamEnum.YEAR.getCode(), "");
            LocalDateTime now = LocalDateTime.of(LocalDate.now().withDayOfYear(1), LocalTime.MIN);
            now = cal(time, now);
            return now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        }
        if (StrUtil.startWith(time, TimeParamEnum.MONTH.getCode(), true)) {
            time = StrUtil.replaceIgnoreCase(time, TimeParamEnum.MONTH.getCode(), "");
            LocalDateTime now = LocalDateTime.of(LocalDate.now().withDayOfMonth(1), LocalTime.MIN);
            now = cal(time, now);
            return now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        }
        if (StrUtil.startWith(time, TimeParamEnum.WEEK.getCode(), true)) {
            time = StrUtil.replaceIgnoreCase(time, TimeParamEnum.WEEK.getCode(), "");
            LocalDateTime now = LocalDateTime.of(LocalDate.now().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)), LocalTime.MIN);
            now = cal(time, now);
            return now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        }
        if (StrUtil.startWith(time, TimeParamEnum.DAY.getCode(), true)) {
            time = StrUtil.replaceIgnoreCase(time, TimeParamEnum.DAY.getCode(), "");
            LocalDateTime now = LocalDateTime.of(LocalDate.now(), LocalTime.MIN);
            now = cal(time, now);
            return now.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        }
//        throw new BusinessException(AviatorErrorCode.FUNCTION_PARAM_ERROR, new String[]{funcName, paramLabel});
        return 0L;
    }

    /**
     * 时间计算
     *
     * @param time
     * @param now
     * @return
     */
    public static LocalDateTime cal(String time, LocalDateTime now) {
        StringBuilder calUnit = new StringBuilder();
        Integer operatorCount = 0;
        for (int i = 0, len = time.length(); i < len; i++) {
            char c = time.charAt(i);
            calUnit.append(c);
            if (c == '-' || c == '+') {
                operatorCount++;
            }
            if (operatorCount > 1 || (i == len - 1 && !StrUtil.endWithAny(calUnit, TimeUnitEnum.YEAR.getCode(),
                    TimeUnitEnum.MONTH.getCode(), TimeUnitEnum.WEEK.getCode(), TimeUnitEnum.DAY.getCode(),
                    TimeUnitEnum.HOUR.getCode(), TimeUnitEnum.MINUTE.getCode(), TimeUnitEnum.SECOND.getCode()))) {
//                throw new BusinessException(AviatorErrorCode.TIME_PARAM_ERROR, new Object[]{calUnit});
            }
            if (Objects.equals(TimeUnitEnum.YEAR.getCode().charAt(0), c)) {
                String t = calUnit.toString().replace(TimeUnitEnum.YEAR.getCode(), "");
                now = now.plusYears(Integer.valueOf(t));
                calUnit.setLength(0);
                operatorCount = 0;
            } else if (Objects.equals(TimeUnitEnum.MONTH.getCode().charAt(0), c)) {
                String t = calUnit.toString().replace(TimeUnitEnum.MONTH.getCode(), "");
                now = now.plusMonths(Integer.valueOf(t));
                calUnit.setLength(0);
                operatorCount = 0;
            } else if (Objects.equals(TimeUnitEnum.WEEK.getCode().charAt(0), c)) {
                String t = calUnit.toString().replace(TimeUnitEnum.WEEK.getCode(), "");
                now = now.plusWeeks(Integer.valueOf(t));
                calUnit.setLength(0);
                operatorCount = 0;
            } else if (Objects.equals(TimeUnitEnum.DAY.getCode().charAt(0), c)) {
                String t = calUnit.toString().replace(TimeUnitEnum.DAY.getCode(), "");
                now = now.plusDays(Integer.valueOf(t));
                calUnit.setLength(0);
                operatorCount = 0;
            } else if (Objects.equals(TimeUnitEnum.HOUR.getCode().charAt(0), c)) {
                String t = calUnit.toString().replace(TimeUnitEnum.HOUR.getCode(), "");
                now = now.plusHours(Integer.valueOf(t));
                calUnit.setLength(0);
                operatorCount = 0;
            } else if (Objects.equals(TimeUnitEnum.MINUTE.getCode().charAt(0), c)) {
                String t = calUnit.toString().replace(TimeUnitEnum.MINUTE.getCode(), "");
                now = now.plusMinutes(Integer.valueOf(t));
                calUnit.setLength(0);
                operatorCount = 0;
            } else if (Objects.equals(TimeUnitEnum.SECOND.getCode().charAt(0), c)) {
                String t = calUnit.toString().replace(TimeUnitEnum.SECOND.getCode(), "");
                now = now.plusSeconds(Integer.valueOf(t));
                calUnit.setLength(0);
                operatorCount = 0;
            }
        }
        return now;
    }


}
