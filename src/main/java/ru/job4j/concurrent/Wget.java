package ru.job4j.concurrent;

public class Wget {
    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {
                    int index = 0;
                    while (index <= 100) {
                        System.out.println("\rLoading : " + index++ + "%");
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    System.out.println(System.lineSeparator() + "Loaded.");
                }
        );
        thread.start();
    }
}
