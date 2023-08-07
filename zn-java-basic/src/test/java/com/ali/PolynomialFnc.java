package com.ali;

@FunctionalInterface
public interface PolynomialFnc {
    /**
     * 根据横坐标x，计算纵坐标y的值
     * @param x 横坐标
     * @return y，纵坐标
     */
    double getY(double x);
}
