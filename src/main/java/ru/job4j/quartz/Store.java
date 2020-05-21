package ru.job4j.quartz;

import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class Store {

    private Connection init() {
        try (FileInputStream in = new FileInputStream("rabbit.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            return DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    private List<Long> shedule(Connection cn) {
        List<Long> store = new ArrayList<>();
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
            data.put("store", store);
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
            System.out.println(store);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return store;
    }

    public void insertDB() {
        Connection cn = init();
        List<Long> store = shedule(cn);
        try (PreparedStatement ps = cn.prepareStatement(
                "INSERT INTO rabbit (created_date) VALUES (to_timestamp(?, 'YYYY/MM/dd HH:MI:SS'))")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            for (Long item : store) {
                String startTime = sdf.format(new Date(item));
                ps.setString(1, startTime);
                ps.executeUpdate();
            }
            cn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
