package com.ali;

import org.junit.Test;

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

}
