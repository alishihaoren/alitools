package com.web.bio;


import javax.print.DocFlavor;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiServer {
    public static void main(String[] args) {
        try {
            //注册端口
            ServerSocket ss = new ServerSocket(9999);
            //定义一个死循环，负责不断地接收客户端的socket请求
            while (true) {
                Socket socket = ss.accept();
                //注册一个独立的线程来处理这个客户端的请求
                new ServerThreadReader(socket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ServerThreadReader extends Thread {
    private Socket socket;

    public ServerThreadReader(Socket socket) {
        this.socket = socket;
    }

    public void run() {
        try {
            //从socket对象中得到一个字节输入流
            InputStream is = socket.getInputStream();
            //使用缓冲字符输入流包装字节输入流
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String msg;
            while ((msg = br.readLine()) != null) {
                System.out.println(msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


