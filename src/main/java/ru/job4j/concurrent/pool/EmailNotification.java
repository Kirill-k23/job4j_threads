package ru.job4j.concurrent.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool;

    public EmailNotification(ExecutorService pool) {
        this.pool = Executors.newFixedThreadPool(
                Runtime.getRuntime().availableProcessors());
    }

    public void send(String subject, String body, String email) {
        System.out.printf("subject = Notification %s to email %s", subject, email);
        System.out.printf("Add a new event to %s", subject);
    }

    public void close() {
        pool.shutdown();
    }

    public void emailTo(User user) {
        pool.submit(new Runnable() {
            @Override
            public void run() {
                send(user.name(), null, user.email());
            }
        });
    }

}
