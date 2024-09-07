package ru.job4j.concurrent.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EmailNotification {
    private final ExecutorService pool = Executors.newFixedThreadPool(
            Runtime.getRuntime().availableProcessors());
    
    public void send(String subject, String body, String email) {
        System.out.printf("subject = Notification %s to email %s", subject, email);
        System.out.printf("Add a new event to %s", subject);
    }

    public void close() {
        pool.shutdown();
        while (!pool.isTerminated()) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void emailTo(User user) {
        pool.submit(() -> send(user.name(), null, user.email()));
    }

}
