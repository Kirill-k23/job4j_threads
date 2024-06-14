package ru.job4j.concurrent;

public class Wget {
    public static void main(String[] args) {
        Thread thread = new Thread(
                () -> {
                    try {
                        for (int index = 0; index <= 100; index++)
                            System.out.println("\rLoading : " + index++ + "%");
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(System.lineSeparator() + "Loaded.");
                }
        );
        thread.start();
    }
}
