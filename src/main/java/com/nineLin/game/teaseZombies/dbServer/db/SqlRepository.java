package com.nineLin.game.teaseZombies.dbServer.db;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by vic on 15-4-21.
 */
public class SqlRepository {

    private static Queue<String>[] queues = new LinkedBlockingQueue[]{new LinkedBlockingQueue<String>(), new LinkedBlockingQueue<String>()};

    private static int monitor = 0;


    public static void add(String sql) {
        queues[monitor].offer(sql);
    }

    public static List<String> getSqlList(final int len) {
        List<String> resultList = Lists.newArrayList();

        int size = queues[monitor ^ 1].size();
        if (size > len) {
            for (int i = 0; i < len; i++) {
                resultList.add(queues[monitor ^ 1].poll());
            }
            return resultList;
        }
        resultList.addAll(queues[monitor ^ 1]);
        queues[monitor ^ 1].clear();
        changeFlag();
        System.out.println("all out " + (monitor ^ 1) + " size=" + size);
        return resultList;
    }

    private static int changeFlag() {
        monitor ^= 1;
        return monitor;
    }
}
