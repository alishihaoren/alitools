package com.zn.learn.basic.ExpressionEngine;

/**
 * 组件公共常量类
 *
 * @author chenpi
 * @since 2021/1/14
 **/
public class Constants {
    public static final String PARAM_APP_KEY = "appKey";
    public static final String PARAM_SIGN = "sign";
    public static final String PARAM_APP_NONCE = "nonce";
    public static final String PARAM_APP_TIMESTAMP = "timestamp";
    /**
     * 鉴权属性
     */
    public static final String AUTHORIZATION = "Authorization";
    /**
     * 包前缀
     */
    public static final String PACKAGE_NAME_PREFIX = "com.zhenergy.tsdb";
    /**
     * 空
     */
    public static final String BLANK = "";
    /**
     * 点号：.
     */
    public static final String POINT = ".";
    /**
     * 星号：*
     */
    public static final String ASTERISK = "*";
    /**
     * 星号：*
     */
    public static final String ASTERISK2 = "\\*";
    /**
     * 0
     */
    public static final Integer ZERO = 0;
    /**
     * 60
     */
    public static final Integer SIXTY = 60;

    public static final String ASC = "ASC";

    public static final String DESC = "DESC";

    public static final String CONFIG_KEY_ADMIN_ROLE = "adminRole";

    public static final String CONFIG_KEY_DEFAULT_PASSWORD = "defaultPassword";

    public static final String ERROR_MSG_KEY = "errorMsg";

    public static final String DEFAULT_TAG_VALUE_COLUMN = "tag_value";
    public static final String DEFAULT_TAG_CODE_COLUMN = "tag_name";
    public static final String DEFAULT_TIME_COLUMN = "time";

    public static final Long MAX_RETURN_COUNT = 1000L;

    /**
     * 符号
     */
    public static final class Symbol {
        /**
         * 英文半角逗号
         */
        public static final String COMMA = ",";
        /**
         * @
         */
        public static final String AT = "@";
        /**
         * 点号：.
         */
        public static final String PERIOD = ".";
        /**
         * 单引号：'
         */
        public static final String QUOTE = "'";
        /**
         * 下划线：_
         */
        public static final String UNDERSCORE = "_";
        /**
         * 减号：-
         */
        public static final String MINUS = "-";
        /**
         * 冒号：:
         */
        public static final String COLON = ":";
        /**
         * 竖线：|
         */
        public static final String BAR = "\\|";
        /**
         * 换行
         */
        public static final String LINE_FEED = "\n";
        /**
         * 换行
         */
        public static final String PERCENT = "%";
        /**
         * 等于
         */
        public static final String EQUAL = "=";
        /**
         * 问号
         */
        public static final String QUESTION_MARK = "?";
        /**
         * 问号（中文）
         */
        public static final String QUESTION_MARK_CN = "？";
        /**
         * 问号
         */
        public static final String QUESTION_MARK2 = "\\?";
    }

    /**
     * TDengine 字段常量
     */
    public static final class TD {
        /**
         * time 字段
         */
        public static final String TIME = "time";
        /**
         * send_ts 字段
         */
        public static final String SEND_TS = "send_ts";
        /**
         * tag_name 字段
         */
        public static final String TAG_NAME = "tag_name";
        /**
         * u_code 字段
         */
        public static final String U_CODE = "u_code";
    }

    public static final class Redis {
        /**
         * tag属性redis  存储的值
         */
        public static final String TAG_INFO_INDEX = "TAG_BASE";

    }

    public static final class TsdbBusQueryLimit {
        public static final Integer PAGESIZE_LIMIT = 200001;
        public static final Integer HIS_TRENDQUERY_LIMIT = 500;

        public static final Integer FIVE_DAY_INTEGRAL_LIMIT = 5 * 60 * 60 * 24 * 1000;

    }

    /**
     * 字符串常量
     */
    public static final class Strings {
        public static final String DATALIST = "dataList";
        public static final String COUNT = "count";
        public static final String SQL = "SQL";
        public static final String TOTAOL_SQL = "TOTAOL_SQL";
    }


    /**
     * 历史查询枚举
     */
    public static final class Find_history {
        public static final String FIRST = "first"; //快照查询
        public static final String SECOND = "second"; //条件查询
        public static final String THIRD = "third";  //趋势查询
        public static final String FOURTH = "fourth";  //聚合查询查询
        public static final String FIFTH = "fifth";  //选择函数查询
        public static final String SIXTH = "sixth";  //计算函数查询
        public static final String SEVENTH = "seventh";  //窗口函数查询
        public static final String EIGHT = "eight";  //插值查询
        public static final String NINE = "nine";  //自定义查询
        public static final String TEN = "ten";  // 连续查询
    }

    /**
     * 文件导出
     */
    public static final class IMPORT_FILE {
        public static final int SQL_RES_MAX_INT = 200000; //数据库最大查询条数
        public static final double SQL_RES_MAX_DOUBLE = 200000.0; //数据库最大查询条数
        public static final double IMPORT_MAX_SIZE = 2000000; //最大导出数据条数

    }


}
