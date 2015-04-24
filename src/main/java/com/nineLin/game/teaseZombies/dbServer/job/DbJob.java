package com.nineLin.game.teaseZombies.dbServer.job;

import com.nineLin.game.teaseZombies.dbServer.db.SqlRepository;
import com.nineLin.game.teaseZombies.dbServer.db.TlogInsertor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

/**
 * Created by vic on 15-4-22.
 */
public class DbJob implements Job {
    private static long startTime = 0l;

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        List<String> sqlList = SqlRepository.getSqlList(2000);
        if (sqlList.size() != 0 && startTime == 0) {
            startTime = System.currentTimeMillis();
        }
        if (sqlList.size() == 0 && startTime != 0) {
            System.out.println("cost time: " + (System.currentTimeMillis() - startTime));
            startTime = 0;
        }
        TlogInsertor.execute(sqlList);
    }
}
