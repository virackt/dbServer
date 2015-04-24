package com.nineLin.game.teaseZombies.dbServer.net;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.impl.GenericObjectPool;

import java.net.InetSocketAddress;
import java.net.SocketAddress;


/**
 * Created by vic on 15-4-20.
 */
public class ClientPoolFactory {

    private GenericObjectPool<Channel> pool;

    public ClientPoolFactory(GenericObjectPool.Config config, String ip, int port) {
        ClientFactory factory = new ClientFactory(ip, port);
        pool = new GenericObjectPool<Channel>(factory, config);
    }

    public Channel getChannel() throws Exception {
        Channel channel = pool.borrowObject();
//        if(!channel.isOpen() && !channel.isActive()){
//            Thread.sleep(1000);
//            channel = pool.borrowObject();
//        }
//        if(channel.isWritable()){
//            System.out.println("ok");
//        }
        System.out.println("getChannel ->" + channel.isOpen() + "--" + channel.isActive() + "==" + pool.getNumIdle() + "--" + pool.getNumActive());
        return channel;
    }

    public void releaseChannel(Channel channel) {
        try {
            System.out.println("releaseChannel ->" + channel.isOpen() + "--" + channel.isActive() + "==" + pool.getNumIdle() + "--" + pool.getNumActive());
            pool.returnObject(channel);
        } catch (Exception e) {
            if (channel != null) {
                channel.close();
            }
        }
    }

    public void reset() {
        pool.clear();
        try {
            pool.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    class ClientFactory extends BasePoolableObjectFactory<Channel> {
        private SocketAddress address;

        public ClientFactory(String ip, int port) {
            this(new InetSocketAddress(ip, port));
        }

        public ClientFactory(SocketAddress address) {
            this.address = address;
        }

        @Override
        public Channel makeObject() throws Exception {
            Channel channel = null;
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
                }).option(ChannelOption.SO_KEEPALIVE, true).option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000);
                ChannelFuture cf = bootrap.connect(address);
                cf.syncUninterruptibly();
                channel = cf.channel();
            } finally {
                workerGroup.shutdownGracefully();
            }
            return channel;
        }

        @Override
        public void destroyObject(Channel obj) throws Exception {
            obj.close();
        }

        @Override
        public boolean validateObject(Channel obj) {
            return obj.isOpen() && obj.isActive();
        }
    }
}
