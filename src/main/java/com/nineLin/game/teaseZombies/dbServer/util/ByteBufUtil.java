package com.nineLin.game.teaseZombies.dbServer.util;

import io.netty.buffer.ByteBuf;

import java.nio.charset.Charset;

/**
 * Created by vic on 15-4-23.
 */
public class ByteBufUtil {

    public static String readString(ByteBuf buf) {
        int len = buf.readInt();
        if (len == 0) {
            return null;
        }
        byte[] data = new byte[len];
        buf.readBytes(data);
        return new String(data, Charset.forName("UTF-8"));
    }

    public static void writeString(ByteBuf buf, String value) {
        if (value == null) {
            buf.writeInt(0);
            return;
        }
        byte[] data = value.getBytes(Charset.forName("UTF-8"));
        buf.writeInt(data.length);
        buf.writeBytes(data);
    }
}
