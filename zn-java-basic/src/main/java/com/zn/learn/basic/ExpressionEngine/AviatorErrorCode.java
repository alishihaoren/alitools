package com.zn.learn.basic.ExpressionEngine;

/**
 * <pre>
 * Aviator 相关错误码（5位） -->配置模块标识为 12
 * 1-2位--->配置模块标识，00开始，00为公共模块，其它为自定义模块01,02,03...
 * 3-5位--->模块对应的具体错误码，001开始，001,002,003...
 * </pre>
 *
 * @author zhou.xy
 * @since 1.0.0
 **/
public class AviatorErrorCode {

    /**
     * 函数【{0}】的【{1}】参数格式错误
     */
    public static final String FUNCTION_PARAM_ERROR = "12001";
    /**
     * 函数【{0}】的【{1}】参数不能为空
     */
    public static final String FUNCTION_PARAM_NULL_ERROR = "12002";
    /**
     * 计算失败，原因：{0}
     */
    public static final String CALCULATION_FAILED_ERROR = "12003";
    /**
     * 执行失败，SQL：{0}, error：{1}
     */
    public static final String EXECUTE_ERROR = "12004";
    /**
     * 函数【{0}】的参数个数错误
     */
    public static final String FUNCTION_PARAM_NUMBER_ERROR = "12005";
    /**
     * 函数【{0}】暂不支持
     */
    public static final String FUNCTION_NOT_SUPPORT_ERROR = "12006";
    /**
     * 计算测点公式错误，参数只允许使用单引号，请检查
     */
    public static final String EQUATION_ERROR = "12007";
    /**
     * 时间参数在【{0}】附近错误，请检查
     */
    public static final String TIME_PARAM_ERROR = "12008";
}
