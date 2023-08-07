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
        String  dataLine="line/";
        if((dataLine.lastIndexOf('/')==dataLine.length()-1)||dataLine.lastIndexOf('\\')==dataLine.length()-1){
            System.out.println(" last date ");
        };




    }




}
