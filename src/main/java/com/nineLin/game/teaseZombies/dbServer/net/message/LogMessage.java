package com.nineLin.game.teaseZombies.dbServer.net.message;

import com.nineLin.game.teaseZombies.dbServer.util.ByteBufUtil;
import io.netty.buffer.ByteBuf;

/**
 * Created by vic on 15-4-23.
 */
public class LogMessage {

    private int uid;

    private String sql;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public void encode(ByteBuf buf) {
        buf.writeInt(uid);
        ByteBufUtil.writeString(buf, sql);
    }

    public void decode(ByteBuf buf) {
        this.uid = buf.readInt();
        this.sql = ByteBufUtil.readString(buf);
    }
}
