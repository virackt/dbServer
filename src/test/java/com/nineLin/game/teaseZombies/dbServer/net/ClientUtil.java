package com.nineLin.game.teaseZombies.dbServer.net;

import org.apache.commons.pool.impl.GenericObjectPool;

/**
 * Created by vic on 15-4-20.
 */
public class ClientUtil {

    private static ClientUtil instance;
    private ClientPoolFactory poolFactory;

    private ClientUtil() {
        GenericObjectPool.Config config = new GenericObjectPool.Config();
        config.maxActive = 100;
        config.maxIdle = 10;
        poolFactory = new ClientPoolFactory(config, "127.0.0.1", 9901);
    }

    public static ClientUtil getInstance() {
        if (instance == null) {
            instance = new ClientUtil();
        }
        return instance;
    }

    public ClientPoolFactory getClientPoolFactory() {
        return poolFactory;
    }
}
