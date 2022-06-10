package com.web.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class SelectorClient {
    private Selector selector;
    private SocketChannel channel = null;
    private ByteBuffer outBuff =  ByteBuffer.allocate(1024);
    private ByteBuffer inBuff =  ByteBuffer.allocate(1024);
    private int keys = 0;


    public static void main(String[] args) throws IOException {
        new SelectorClient().start();
    }

    public void start() throws IOException {
        initClient();
        listen();
    }

    public void initClient() throws IOException {
        channel = SocketChannel.open();
        selector = Selector.open();

        channel.configureBlocking(false);

        channel.connect(new InetSocketAddress("127.0.0.1", 8888));
        channel.register(selector, SelectionKey.OP_CONNECT);
    }

    public void listen () throws IOException {
        while(true) {
            //
            keys = this.selector.select();
            if (keys > 0)  {
                Iterator it = this.selector.selectedKeys().iterator();
                while (it.hasNext()) {
                    SelectionKey key = (SelectionKey) it.next();
                    it.remove();

                    if (key.isConnectable()) {

                        SocketChannel channel = (SocketChannel) key.channel();
                        if (channel.isConnectionPending()) {
                            channel.finishConnect();
                            System.out.println("Complete connect.");
                        }

                        channel.register(selector, SelectionKey.OP_WRITE);
                    } else if (key.isWritable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        outBuff.clear();
                        System.out.println("client is write data start.");

                        channel.write(outBuff.wrap("I'm client.".getBytes()));
                        channel.register(selector, SelectionKey.OP_READ);
                        System.out.println("client is write data end.");

                    } else if (key.isReadable()) {
                        // read
                        SocketChannel channel = (SocketChannel) key.channel();
                        inBuff.clear();

                        int length = channel.read(inBuff);
                        String msg = "server receive msg:" + new String(inBuff.array(), 0, length);
                        System.out.println(msg);
                    }
                }

            }
        }
    }

}