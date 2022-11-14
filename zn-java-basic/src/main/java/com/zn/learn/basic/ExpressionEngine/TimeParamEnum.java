package com.zn.learn.basic.ExpressionEngine;

import lombok.Getter;

/**
 * 时间参数枚举
 *
 * @author zhou.xy
 * @since 1.0.0
 */
public enum TimeParamEnum {
    CURRENT("*", "当前时间"),
    YEAR("year", "当年第一天的 00:00:00（午夜）"),
    MONTH("month", "当月第一天的 00:00:00（午夜）"),
    WEEK("week", "当周周一的 00:00:00（午夜）"),
    DAY("day", "当天的 00:00:00（午夜）");
    
    @Getter
    String code;

    @Getter
    String desc;

    TimeParamEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 获取枚举
     *
     * @param code
     * @return 枚举
     */
    public static TimeParamEnum getByCode(String code) {
        for (TimeParamEnum e : TimeParamEnum.values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }
}
