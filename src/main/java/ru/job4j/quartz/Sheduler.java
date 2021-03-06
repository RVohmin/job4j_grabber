package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class Sheduler {
    Store store;

    public Sheduler(Store store) {
        this.store = store;
    }

    public void schedule(Connection cn) {
        List<Long> list = new ArrayList<>();
        try {
            int time = 0;
            BufferedReader reader = new BufferedReader(new FileReader(new File("rabbit.properties")));
            String line = reader.readLine();
            if (line.contains("rabbit.interval")) {
                time = Integer.parseInt(line.substring(line.indexOf("=") + 1));
            }
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            scheduler.start();
            JobDataMap data = new JobDataMap();
            data.put("list", list);
            data.put("connection", cn);
            JobDetail job = newJob(Rabbit.class)
                    .usingJobData(data)
                    .build();
            SimpleScheduleBuilder times = simpleSchedule()
                    .withIntervalInSeconds(time)
                    .repeatForever();
            Trigger trigger = newTrigger()
                    .startNow()
                    .withSchedule(times)
                    .build();
            scheduler.scheduleJob(job, trigger);
            Thread.sleep(10000);
            scheduler.shutdown();
            System.out.println(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        store.insertDB(list);
    }

}
