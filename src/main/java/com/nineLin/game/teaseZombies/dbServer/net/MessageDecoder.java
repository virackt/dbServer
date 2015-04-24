package com.nineLin.game.teaseZombies.dbServer.net;

import com.nineLin.game.teaseZombies.dbServer.net.message.LogMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by vic on 15-4-17.
 */
public class MessageDecoder extends ByteToMessageDecoder {
    private static final Logger logger = LoggerFactory.getLogger("msgDecoder");
    private static final int MIN_SIZE = 4;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (!in.isReadable()) {
            return;
        }
        LogMessage msg = new LogMessage();
        msg.decode(in);
        out.add(msg);
    }
}
