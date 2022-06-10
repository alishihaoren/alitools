package com.web.bio;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class BioClientV2 {

    public static void main(String[] args) throws IOException {
        //创建socket对象请求服务器的连接
        Socket socket = new Socket("127.0.0.1",9999);
        //从socket对象中获取一个字节输出流
        OutputStream os = socket.getOutputStream();
        //把字节输出流包装成一个打印流
        PrintStream ps = new PrintStream(os);
        Scanner sc = new Scanner(System.in);
        while(true){
            System.out.println("请说");
            String msg = sc.nextLine();
            ps.println(msg);
            ps.flush();
        }
    }


}
