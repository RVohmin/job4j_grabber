package ru.job4j.quartz;

public class AlertRabbit {

    public static void main(String[] args) {
        Store store = new Store();
        Sheduler sheduler = new Sheduler(store);
        sheduler.schedule(store.init());
    }
}