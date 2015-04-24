package com.nineLin.game.teaseZombies.dbServer.job;

import org.junit.Test;

/**
 * Created by vic on 15-4-22.
 */
public class TestQuartz {

    @Test
    public void testJob() {
        JobManager.startJobs();
    }

    /**
     * 测试异或运算符
     */
    @Test
    public void testOrElse() {
        int i = 0;
        i ^= 1;
        System.out.println(i);
        i ^= 1;
        System.out.println(i);
    }
}
