package com.nineLin.game.teaseZombies.dbServer.masterWorker;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by vic on 15-4-23.
 */
public class Master {

    private Map<Integer, Worker> threadMap = new HashMap<Integer, Worker>();
    private Queue<Object> workQueue = new ConcurrentLinkedQueue<Object>();
    private Map<String, Object> resultMap = new ConcurrentHashMap<String, Object>();

    public void setThreadMap(Map<Integer, Worker> threadMap) {
        this.threadMap = threadMap;
    }

    public Queue<Object> getWorkQueue() {
        return workQueue;
    }

    /**
     * 是否所有子任务都结束了
     *
     * @return
     */
    public boolean isComplete() {
        for (Thread thread : threadMap.values()) {
            if (thread.getState() != Thread.State.TERMINATED) {
                return false;
            }
        }
        return true;
    }


    public void submit(Object obj) {
        workQueue.add(obj);
    }

    public void execute() {
        for (Worker worker : threadMap.values()) {
            new Thread(worker).start();
        }
    }

    public Map<String, Object> getResultMap() {
        return resultMap;
    }

    public void shutdown() {
        if (isComplete()) {
            for (Worker worker : threadMap.values()) {
                worker.shutdown();
            }
        }
    }
}
