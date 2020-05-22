package ru.job4j.quartz;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class Store {

    public Connection init() {
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

    public void insertDB(List<Long> store) {
        Connection cn = init();
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
