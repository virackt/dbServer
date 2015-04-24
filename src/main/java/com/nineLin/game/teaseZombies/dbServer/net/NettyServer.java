package com.nineLin.game.teaseZombies.dbServer.net;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

/**
 * Created by vic on 15-4-15.
 */
public class NettyServer {

    private volatile ChannelHandler channelHandler;
    private volatile EventLoopGroup superGroup;
    private volatile EventLoopGroup childGroup;
    private volatile SocketAddress address;


    public NettyServer(EventLoopGroup superGroup, EventLoopGroup childGroup, ChannelHandler channelHandler, InetSocketAddress address) {
        this.superGroup = superGroup;
        this.channelHandler = channelHandler;
        this.childGroup = childGroup;
        this.address = address;
    }


    public void startServer() {
        ServerBootstrap bootstrap = new ServerBootstrap();
        try {
            bootstrap.group(superGroup, childGroup).channel(NioServerSocketChannel.class).handler(new LoggingHandler(LogLevel.DEBUG)).childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new MessageEncoder());
                    ch.pipeline().addLast(new MessageDecoder());
                    ch.pipeline().addLast(channelHandler);
                }
            }).childOption(ChannelOption.SO_KEEPALIVE, true);
            ChannelFuture channelFuture = bootstrap.bind(address).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            superGroup.shutdownGracefully();
            childGroup.shutdownGracefully();
        }
    }

}
