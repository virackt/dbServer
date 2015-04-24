package com.nineLin.game.teaseZombies.dbServer.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by vic on 15-4-16.
 */
public class TestNet {
    @Test
    public void testConnection() {
        Bootstrap bootrap = new Bootstrap();
        EventLoopGroup workerGroup = new NioEventLoopGroup(1);
        try {
            bootrap.channel(NioSocketChannel.class).handler(new LoggingHandler(LogLevel.DEBUG)).group(workerGroup).handler(new ChannelInitializer<NioSocketChannel>() {
                @Override
                protected void initChannel(NioSocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new MessageEncoder());
                    ch.pipeline().addLast(new MessageDecoder());
                    ch.pipeline().addLast(new ClientHandler());
                }
            });
            ChannelFuture cf = bootrap.connect("127.0.0.1", 9901).sync();
            Channel channel = cf.channel();
//            System.out.println(channel.isActive() + ", " + channel.isOpen());
//            channel.write("test");
//            channel.flush();
            channel.closeFuture().sync();

        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }

//        Runnable thread1 = new Runnable(){
//            @Override
//            public void run() {
//                try {
//                    System.out.println("start");
//                    for (int i = 0; i < 10; i++) {
//                        Channel channel = ClientUtil.getInstance().getClientPoolFactory().getChannel();
//                        System.out.println(channel.isActive() + ", " + channel.isOpen() + ", " + channel.hashCode());
//                        if(channel.isWritable()){
//                            System.out.println("ok");
//                        }
//                        channel.writeAndFlush("test" + i);
//                        ClientUtil.getInstance().getClientPoolFactory().releaseChannel(channel);
//                    }
//                } catch (Exception e){
//                    e.printStackTrace();
//                }

//            }
//        };z
//        new Thread(thread1).start();


    }
}
