package ru.job4j.concurrent;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class Wget2 implements Runnable {
    private final String url;
    private final int speed;
    private final File file;

    public Wget2(String url, int speed, File file) {;
        validator(url);
        this.url = url;
        this.speed = speed;
        this.file = file;
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length < 3 ) {
          throw new IllegalStateException();
        }
        String url = args[0];
        int speed = Integer.parseInt(args[1]);
        File file1 = new File(args[2]);
        Thread wget = new Thread(new Wget2(url, speed, file1));
        wget.start();
        wget.join();
    }

    @Override
    public void run() {
        long startAt = System.currentTimeMillis();
        try (InputStream input = new URL(url).openStream();
             FileOutputStream output = new FileOutputStream(file)) {
            System.out.printf(
                    "Open connection: %d ms%n", System.currentTimeMillis() - startAt);
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            int countByteRead = 0;
            long startDownload = System.currentTimeMillis();
            while ((bytesRead = input.read(dataBuffer, 0, dataBuffer.length)) != -1) {
                output.write(dataBuffer, 0, bytesRead);
                countByteRead += bytesRead;
                if (countByteRead >= speed) {
                    long endDownload = System.currentTimeMillis() - startDownload;
                    if (endDownload < 1000) {
                        try {
                            long sleep = 1000 - endDownload;
                            Thread.sleep(sleep);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                    startDownload = System.currentTimeMillis();
                    countByteRead = 0;
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
