package com.nineLin.game.teaseZombies.dbServer.bootrap;

import com.nineLin.game.teaseZombies.dbServer.net.NettyHandler;
import com.nineLin.game.teaseZombies.dbServer.net.NettyServer;
import io.netty.channel.ChannelHandler;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

import java.net.InetSocketAddress;

/**
 * Created by vic on 15-4-13.
 */
public class Main {

    public static void main(String[] args) {
        EventLoopGroup superGroup = new NioEventLoopGroup(1);
        EventLoopGroup childGroup = new NioEventLoopGroup();
        ChannelHandler childHandler = new NettyHandler();
        InetSocketAddress address = new InetSocketAddress("127.0.0.1", 9901);
//        JobManager.startJobs();

        NettyServer server = new NettyServer(superGroup, childGroup, childHandler, address);
        server.startServer();
    }
}
