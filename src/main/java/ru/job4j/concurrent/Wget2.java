package ru.job4j.concurrent;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Wget2 implements Runnable {
    private final String url;
    private final int speed;

    public Wget2(String url, int speed) {
        validator(url);
        this.url = url;
        this.speed = speed;
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        Thread wget = new Thread(new Wget2(url, speed));
        wget.start();
        wget.join();
    }

    @Override
    public void run() {
        File file = new File("tmp.xml");
        byte[] bytes = new byte[1024];
        try (InputStream input = new URL(url).openStream();
             FileOutputStream out = new FileOutputStream(file)) {
            int download;
            while ((download = input.read(bytes, 0, bytes.length)) != -1) {
                long start = System.nanoTime();
                out.write(bytes, 0, download);
                double time = System.nanoTime() - start;
                double realSpeed = bytes.length / time * 1000000;
                if (speed < realSpeed) {
                    try {
                        Thread.sleep((long) realSpeed / speed);
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void validator(String url) {
        try {
            new URL(url).toURI();
        } catch (Exception e) {
            throw new IllegalStateException("Url invalid");
        }
    }
}
