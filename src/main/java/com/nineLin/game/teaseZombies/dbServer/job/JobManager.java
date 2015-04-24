package com.nineLin.game.teaseZombies.dbServer.job;

import org.quartz.*;
import org.quartz.impl.JobDetailImpl;
import org.quartz.impl.StdSchedulerFactory;

/**
 * Created by vic on 15-4-22.
 */
public class JobManager {

    public static void startJobs() {
        SchedulerFactory factory = new StdSchedulerFactory();
        try {
            Scheduler scheduler = factory.getScheduler();
            scheduler.start();
            JobDetailImpl jobDetail = new JobDetailImpl();
            jobDetail.setJobClass(DbJob.class);
            Trigger trigger = TriggerBuilder.newTrigger().withIdentity("test", "DEFAULT").startNow().withSchedule(SimpleScheduleBuilder.repeatSecondlyForever(3)).build();
            scheduler.scheduleJob(jobDetail.getJobBuilder().build(), trigger);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
