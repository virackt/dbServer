package com.nineLin.game.teaseZombies.dbServer.db;

import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by vic on 15-4-21.
 */
public class TestSqlRepository {

    @Test
    public void addAndGet() {

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.execute(new Runnable() {


            @Override
            public void run() {
                int i = 0;
                while (i < 2510) {
                    SqlRepository.add("test");
                    i++;
                }
            }

        });


        executorService.execute(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 1000; i++) {
                    SqlRepository.getSqlList(5);
                }
            }
        });


        try {
            Thread.sleep(10000);
        } catch (Exception e) {

        }
        System.out.println("over");
    }
}
