package org.ali.data;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import org.ali.data.handler.SimpleHttpServerHandler;


/**
 *BIO client -thread 一一对应模式
 *
 */



public class StartMessageProcess {
    /**
     * host
     */
    private final static String host = "127.0.0.1";

    /**
     * 端口号
     */
    private final static Integer port = 8085;

    /**
     * netty服务端启动方法
     */
    public void start() {
//        log.info("SimpleHttpServer start begin ");

        EventLoopGroup bossEventLoopGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerEventLoopGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    .group(bossEventLoopGroup, workerEventLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    //开启tcp nagle算法
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    //开启长连接
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel c) {
                            c.pipeline().addLast(new HttpRequestDecoder())
                                    .addLast(new HttpResponseEncoder())
                                    .addLast(new HttpObjectAggregator(512 * 1024))
                                    .addLast(new SimpleHttpServerHandler());

                        }
                    });

            ChannelFuture channelFuture = serverBootstrap.bind(host, port).sync();

//            log.info("SimpleHttpServer start at port " + port);

            channelFuture.channel().closeFuture().sync();
        } catch (Exception e) {
//            log.error("SimpleHttpServer start exception,", e);
        } finally {
//            log.info("SimpleHttpServer shutdown bossEventLoopGroup&workerEventLoopGroup gracefully");
            bossEventLoopGroup.shutdownGracefully();
            workerEventLoopGroup.shutdownGracefully();
        }
    }


}
