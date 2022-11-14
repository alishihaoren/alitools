package com.zn.learn.basic.ExpressionEngine;

import lombok.Getter;

/**
 * 时间运算单位枚举
 *
 * @author zhou.xy
 * @since 1.0.0
 */

public enum TimeUnitEnum {
    YEAR("y", "年"),
    MONTH("M", "月"),
    WEEK("w", "周"),
    DAY("d", "天"),
    HOUR("h", "时"),
    MINUTE("m", "分"),
    SECOND("s", "秒");

    @Getter
    String code;

    @Getter
    String desc;

    TimeUnitEnum(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 获取枚举
     *
     * @param code
     * @return 枚举
     */
    public static TimeUnitEnum getByCode(String code) {
        for (TimeUnitEnum e : TimeUnitEnum.values()) {
            if (e.getCode().equals(code)) {
                return e;
            }
        }
        return null;
    }




}
