package ru.job4j.concurrent;

import org.apache.commons.validator.routines.UrlValidator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final int speed;

    public Wget(String url, int speed) {
        this.url = url;
        this.speed = speed;
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        if (!validator(url)) {
            System.out.println("url is no valid");
        } else {
            int speed = Integer.parseInt(args[1]);
            Thread wget = new Thread(new Wget(url, speed));
            wget.start();
            wget.join();
        }
    }

    @Override
    public void run() {
        File file = new File("tmp.xml");
        byte[] bytes = new byte[1024];
        try (InputStream inputStream = new URL(url).openStream();
             FileOutputStream outputStream = new FileOutputStream(file)
        ) {
            int download;
            while ((download = inputStream.read(bytes,0, bytes.length)) != -1) {
                long start = System.nanoTime();
                outputStream.write(bytes,0, download);
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
            throw new RuntimeException(e);
        }
    }

    public static boolean validator(String url) {
        UrlValidator validator = new UrlValidator();
        return validator.isValid(url);
    }
}
