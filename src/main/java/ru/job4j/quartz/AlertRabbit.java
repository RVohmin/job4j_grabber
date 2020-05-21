package ru.job4j.quartz;

public class AlertRabbit {

    public static void main(String[] args) {
        Connect connect = new Connect();
        Schedule schedule = new Schedule();
        Store store = new Store(connect, schedule);
        store.insertDB();
    }
}