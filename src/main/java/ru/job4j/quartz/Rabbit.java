package ru.job4j.quartz;

import org.quartz.Job;
import org.quartz.JobExecutionContext;

public class Rabbit implements Job {
    @Override
    public void execute(JobExecutionContext context) {
        System.out.println("ru.job4j.quartz.Rabbit runs here ...");
    }
}
