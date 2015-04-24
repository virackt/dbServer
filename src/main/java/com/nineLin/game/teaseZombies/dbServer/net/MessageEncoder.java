package com.nineLin.game.teaseZombies.dbServer.net;

import com.nineLin.game.teaseZombies.dbServer.net.message.LogMessage;
import com.nineLin.game.teaseZombies.dbServer.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.socks.SocksMessageEncoder;

/**
 * Created by vic on 15-4-17.
 */
public class MessageEncoder extends MessageToByteEncoder {

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        if (msg instanceof String) {
            ByteBufUtil.writeString(out, msg.toString());
        }
        if (msg instanceof LogMessage) {
            ((LogMessage) msg).encode(out);
        }

    }


}
