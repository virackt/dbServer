package com.nineLin.game.teaseZombies.dbServer.masterWorker;

import java.util.Map;
import java.util.Queue;

/**
 * Created by vic on 15-4-23.
 */
public class Worker extends Thread {

    private Queue<Object> taskQueue;
    private Map<String, Object> resultMap;
    protected int workerId;

    public void setTaskQueue(Queue<Object> taskQueue) {
        this.taskQueue = taskQueue;
    }

    public void setResultMap(Map<String, Object> resultMap) {
        this.resultMap = resultMap;
    }


    public void setWorkerId(int workerId) {
        this.workerId = workerId;
    }

    public int getWorkerId() {
        return workerId;
    }

    @Override
    public void run() {
        while (true) {
            Object obj = taskQueue.poll();
            handle(obj);
        }
    }

    protected Object handle(Object obj) {
        return obj;
    }

    public boolean shutdown() {
        return true;
    }

}
