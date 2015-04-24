package com.nineLin.game.teaseZombies.dbServer.net;

import com.nineLin.game.teaseZombies.dbServer.db.DBUtil;
import com.nineLin.game.teaseZombies.dbServer.db.SqlRepository;
import com.nineLin.game.teaseZombies.dbServer.masterWorker.Master;
import com.nineLin.game.teaseZombies.dbServer.masterWorker.TlogSqlWorker;
import com.nineLin.game.teaseZombies.dbServer.masterWorker.Worker;
import com.nineLin.game.teaseZombies.dbServer.net.message.LogMessage;
import com.nineLin.game.teaseZombies.dbServer.util.Constants;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by vic on 15-4-16.
 */
@ChannelHandler.Sharable
public class NettyHandler extends ChannelInboundHandlerAdapter {
    static boolean init = false;
    static Master master;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ctx.writeAndFlush(msg);
//        SqlRepository.add(log.getSql());
        if (!init) {
            master = new Master();
            Map<Integer, Worker> threadMap = new HashMap<Integer, Worker>();
            for (int i = 0; i < Constants.WORKER_NUM; i++) {
                TlogSqlWorker worker = new TlogSqlWorker();
                worker.setConnection(DBUtil.getDataSource().getConnection());
                worker.setTaskQueue(master.getWorkQueue());
                worker.setResultMap(master.getResultMap());
                worker.setWorkerId(i);
                threadMap.put(i, worker);
            }
            master.setThreadMap(threadMap);
            master.execute();
            init = !init;
        }
        master.submit(msg);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }


}
