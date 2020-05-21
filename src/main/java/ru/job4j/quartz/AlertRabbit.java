package ru.job4j.quartz;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AlertRabbit {
    Connect connect;
    Schedule schedule;

    public AlertRabbit(Connect connect, Schedule schedule) {
        this.connect = connect;
        this.schedule = schedule;
    }

    public void insertDB() {
        Connection cn = connect.init();
        List<Long> store = schedule.shedule(cn);
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

    public static void main(String[] args) {
        Connect connect = new Connect();
        Schedule schedule = new Schedule();
        AlertRabbit rabbit = new AlertRabbit(connect, schedule);
        rabbit.insertDB();
    }
}