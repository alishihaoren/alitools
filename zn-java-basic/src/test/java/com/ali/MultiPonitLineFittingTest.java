package com.ali;

import org.apache.commons.math3.fitting.PolynomialCurveFitter;
import org.apache.commons.math3.fitting.WeightedObservedPoints;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MultiPonitLineFittingTest {


    public static void main(String[] args) throws PolynomialFittingException {
        WeightedObservedPoints weightedObservedPoints = new WeightedObservedPoints();
        File file=new File("D:\\shuiwei.txt");
        try {
            BufferedReader br=new BufferedReader(new FileReader(file));
            String line=null;
            while ((line=br.readLine())!=null){
             String[]  conArr=   line.split("\t");
                weightedObservedPoints.add(Double.valueOf(conArr[0]), Double.valueOf(conArr[1]));
//                System.out.println(conArr[0]+"  "+conArr[1]);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


//        weightedObservedPoints.add(1.0, 1.1);
//        weightedObservedPoints.add(2.0, 1.5);
        PolynomialCurveFitter polynomialCurveFitter = PolynomialCurveFitter.create(8);
        final double[] polynomialCoefficient = polynomialCurveFitter.fit(weightedObservedPoints.toList());
        // 打印一元二次函数
        String polynomial = "f(x) = ";
        for (int i = polynomialCoefficient.length - 1; i >= 0; i--) {
            // 多项式系数 数字
            String itemNum = polynomialCoefficient[i] >=0.0 ? "+" : "";
            // 多项式 中的 x 次幂
            String itemXPower = i > 0 ? "x^" + i : "";
            if (i == polynomialCoefficient.length - 1) {
                // 最后一项常数
                polynomial += (polynomialCoefficient[i] + itemXPower);
            } else {
                // 带 x 的系数的项
                polynomial += (itemNum + polynomialCoefficient[i] + itemXPower);
            }
        }
        System.out.println("多项式函数 : " + polynomial);

        System.out.println(calcTotalDAta(111*1.0));



    }



    private static   Double  calcTotalDAta(Double  x ){
        return    Double.valueOf(" 3.472656796484441E-7")* Math.pow(x,4)+Double.valueOf("6.717513641750156E-4")*Math.pow(x,3)+Double.valueOf("0.05726739974531303")*Math.pow(x,2)-6.559295285337058*x+124.40277268685224;

    }

}
