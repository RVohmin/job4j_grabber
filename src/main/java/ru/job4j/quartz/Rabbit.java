package ru.job4j.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

import java.util.List;

public class Rabbit implements Job {
    public Rabbit() {
        System.out.println(hashCode());
    }

    @Override
    public void execute(JobExecutionContext context) {
        System.out.println("Rabbit runs here . . .");
        List<Long> store = (List<Long>) context.getJobDetail().getJobDataMap().get("list");
        store.add(System.currentTimeMillis());
    }
}
