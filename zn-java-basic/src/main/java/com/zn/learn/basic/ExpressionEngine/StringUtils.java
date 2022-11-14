//package com.zn.learn.basic.ExpressionEngine;
//
//import cn.hutool.core.util.StrUtil;
//import com.google.common.collect.Lists;
//import com.zhenergy.fire.base.constant.CommonConstants;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
///**
// * <pre>
// * 字符串工具类
// * </pre>
// *
// * @author zhou.xy
// * @since 1.0.0
// */
//public class StringUtils {
//    /**
//     * 字符转 Long
//     *
//     * @param str          待转换字符串
//     * @param defaultValue 默认值
//     * @return
//     */
//    public static Long toLong(final String str, final long defaultValue) {
//        if (StrUtil.isBlank(str)) {
//            return defaultValue;
//        }
//        try {
//            return Long.valueOf(str);
//        } catch (NumberFormatException e) {
//            return defaultValue;
//        }
//    }
//
//    /**
//     * 分割字符串并转换成 Long 数组
//     *
//     * @param str   待分割字符串
//     * @param split 分隔符
//     * @return
//     */
//    public static Long[] toLongArray(String str, String split) {
//        if (StrUtil.isBlank(str) || StrUtil.isBlank(split)) {
//            return new Long[0];
//        }
//        String[] array = str.split(split);
//        Long[] longArray = new Long[array.length];
//        for (int i = 0; i < array.length; i++) {
//            longArray[i] = toLong(array[i], 0L);
//        }
//        return longArray;
//    }
//
//    /**
//     * 分割字符串并转换成 Long 集合
//     *
//     * @param str   待分割字符串
//     * @param split 分隔符
//     * @return
//     */
//    public static List<Long> toLongList(String str, String split) {
//        return Arrays.asList(toLongArray(str, split));
//    }
//
//    /**
//     * 分割字符串并转换成 Long 集合
//     *
//     * @param str 待分割字符串
//     * @return
//     */
//    public static List<Long> toLongList(String str) {
//        return toLongList(str, CommonConstants.COMMA);
//    }
//
//    /**
//     * 获取表后缀，将 . - 替换成 _
//     *
//     * @param value 后缀
//     * @return
//     */
//    public static String getTagTableNameSuffix(String value) {
//        return replaceStr(value, Lists.newArrayList(Constants.Symbol.PERIOD, Constants.Symbol.MINUS), Constants.Symbol.UNDERSCORE);
//    }
//
//    /**
//     * 字符替换
//     *
//     * @param str         原文本
//     * @param target      需要替换的字符
//     * @param replacement 替换成的字符
//     * @return
//     */
//    public static String replaceStr(String str, ArrayList<String> target, String replacement) {
//        if (StrUtil.isBlank(str)) {
//            return "";
//        }
//        if (org.apache.commons.collections.CollectionUtils.isEmpty(target) || replacement == null) {
//            return str;
//        }
//        for (String t : target) {
//            str = str.replace(t, replacement);
//        }
//        return str;
//    }
//
//    /**
//     * 替换去除字符串中的空格/回车/换行符/制表符
//     *
//     * @param str
//     * @return
//     */
//    public static String replaceBlank(String str) {
//        String dest = "";
//        if (str != null) {
//            Pattern p = Pattern.compile("\\s*|t|r|n");
//            Matcher m = p.matcher(str);
//            dest = m.replaceAll("");
//        }
//        return dest;
//    }
//
//    /**
//     * 模糊查询参数处理，不包含*和?的在前后拼上%，包含*的将*替换为%，包含?（？）的将?（？）替换为_
//     *
//     * @param param
//     * @return
//     */
//    public static String fuzzyQueryParamHandler(String param) {
//        if (StrUtil.isBlank(param)) {
//            return param;
//        }
//        if (!param.contains(Constants.ASTERISK) && !param.contains(Constants.Symbol.QUESTION_MARK)
//                && !param.contains(Constants.Symbol.QUESTION_MARK_CN)) {
//            return Constants.Symbol.PERCENT + param + Constants.Symbol.PERCENT;
//        }
//        if (param.contains(Constants.ASTERISK)) {
//            param = param.replaceAll(Constants.ASTERISK2, Constants.Symbol.PERCENT);
//        }
//        if (param.contains(Constants.Symbol.QUESTION_MARK)) {
//            param = param.replaceAll(Constants.Symbol.QUESTION_MARK2, Constants.Symbol.UNDERSCORE);
//        }
//        if (param.contains(Constants.Symbol.QUESTION_MARK_CN)) {
//            param = param.replaceAll(Constants.Symbol.QUESTION_MARK_CN, Constants.Symbol.UNDERSCORE);
//        }
//        return param;
//    }
//}
