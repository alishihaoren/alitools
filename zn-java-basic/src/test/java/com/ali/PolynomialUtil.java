package com.ali;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据多项式拟合工具类
 *
 * @author pupengfei
 * @version 1.0
 * @date 2020/8/27 13:56
 */
public class PolynomialUtil {

    /**
     * 多项式拟合
     * @param data 坐标点集合
     * @return 多项式计算公式
     */
    public static PolynomialFnc fitting(List<Point> data) throws PolynomialFittingException {
        // 多项式每一项的计算表达式字符串
        List<String> returnResult = new ArrayList<>();

        int n = data.size();

        List<List<Double>> inputMatrix = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            List<Double> tempArr = new ArrayList<>();
            for (int j = 0; j < n; j++) {
                tempArr.add(Math.pow(data.get(i).getX(), n - j - 1));
            }

            tempArr.add(data.get(i).getY());
            inputMatrix.add(tempArr);
        }

        for (int i = 0; i < n; i++) {
            double base = inputMatrix.get(i).get(i);
            for (int j = 0; j < n + 1; j++) {
                if (base == 0) {
                    //存在相同x不同y的点，无法使用多项式进行拟合
                    throw new PolynomialFittingException("存在相同x不同y的点，无法使用多项式进行拟合");
                }
                inputMatrix.get(i).set(j, inputMatrix.get(i).get(j) / base);
            }
            for (int j = 0; j < n; j++) {
                if (i != j) {
                    double baseInner = inputMatrix.get(j).get(i);
                    for (int k = 0; k < n + 1; k++) {
                        inputMatrix.get(j).set(k, inputMatrix.get(j).get(k) - baseInner * inputMatrix.get(i).get(k));
                    }
                }
            }
        }
        for (int i = 0; i < n; i++) {
            if (inputMatrix.get(i).get(n) > 0) {
                returnResult.add("+");
            }

            if (inputMatrix.get(i).get(n) != 0) {
                String tmp_x = "";
                for (int j = 0; j < n - 1 - i; j++) {
                    tmp_x = tmp_x + "*x";
                }
                returnResult.add((inputMatrix.get(i).get(n) + tmp_x));
            }
        }

        // 将多项式表达式，转换为计算公式
        return x -> {
            double y = 0;
            for (String s : returnResult) {
                if ("+".equals(s)) {
                    y += 0;
                } else if (s.contains("*")) {
                    String[] split = s.split("\\*");
                    double temp = 1;
                    for (String s1 : split) {
                        if ("x".equals(s1)) {
                            temp *= x;
                        } else {
                            temp *= Double.parseDouble(s1);
                        }
                    }
                    y += temp;
                } else {
                    y += Double.parseDouble(s);
                }
            }

            return y;
        };
    }

}