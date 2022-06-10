package com.web.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class SelectorServer {
    private Selector selector;
    private ServerSocketChannel serverChannel = null;
    private int keys = 0;

    public static void main(String[] args) throws IOException {
        // TODO Auto-generated method stub
        new SelectorServer().start();
    }

    public void start() throws IOException {
        initServer();
        listen();
    }

    public void initServer() throws IOException {
        // 获取一个sokcet通道，
        serverChannel = ServerSocketChannel.open();
        // 获取一个通道管理器
        selector = Selector.open();
        // 非阻塞
        serverChannel.configureBlocking(false);
        // 连接服务器
        serverChannel.socket().bind(new InetSocketAddress("127.0.0.1", 8888));
        // 吧serverChannel这个通道注册到管理器对象selector中去，当有客户端连接时触发
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void listen() throws IOException {
        System.out.println("server start.");

        while(true) {
            //
            keys = this.selector.select();
            if (keys > 0)  {
                Iterator it = this.selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey key = (SelectionKey) it.next();
                    it.remove();
                    // 是否完成套接字连接
                    if (key.isAcceptable()) {
                        serverChannel = (ServerSocketChannel) key.channel();

                        SocketChannel channel = serverChannel.accept();
                        channel.configureBlocking(false);

                        channel.write(ByteBuffer.wrap(new String("Hello Client.").getBytes()));
                        channel.register(selector, SelectionKey.OP_READ);
                    } else if (key.isReadable()) {
                        // read
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        int length = channel.read(buffer);
                        String msg = "server receive msg:" + new String(buffer.array(), 0, length);
                        System.out.println(msg);
                    }
                }

            } else {

            }
        }
    }

}