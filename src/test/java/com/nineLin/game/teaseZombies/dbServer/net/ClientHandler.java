package com.nineLin.game.teaseZombies.dbServer.net;

import com.nineLin.game.teaseZombies.dbServer.net.message.LogMessage;
import com.nineLin.game.teaseZombies.dbServer.util.Constants;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by vic on 15-4-16.
 */
public class ClientHandler extends ChannelInboundHandlerAdapter {
    private AtomicInteger atoInt = new AtomicInteger(1);
    private static Random random = new Random(System.currentTimeMillis());
    private static long startTime;
    String sql = "insert into test values({0}, '{1}');";

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.write(getSql());
        ctx.flush();
        startTime = System.currentTimeMillis();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        int num = atoInt.getAndIncrement();
        if (num == 100000) {
            System.out.println(System.currentTimeMillis() - startTime);
            System.exit(1);
        }
        ctx.write(getSql());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private LogMessage getSql() {
        LogMessage log = new LogMessage();
        log.setUid(random.nextInt(Constants.WORKER_NUM));
        log.setSql(sql.replace("{0}", String.valueOf(random.nextLong())).replace("{1}", UUID.randomUUID().toString()));
//        System.out.println(log.getSql());
        return log;
    }
}
