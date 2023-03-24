package com.ali;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Scanner;

public class TestPrint {

    @Test
    public void getMsgInfo() {
        Scanner scanner = new Scanner(System.in);
        System.out.println(scanner.next());
    }


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            System.out.println(scanner.next());
        }
    }
    @Test
    public void bigDecimalInfo(){
        System.out.println(  BigDecimal.valueOf(Double.parseDouble("1.234")/1024).setScale(4,BigDecimal.ROUND_HALF_DOWN).toString());
    }




}
