package ru.job4j.concurrent.cache;

public class OptimisticException extends RuntimeException {
    public OptimisticException(String massage) {
        super(massage);
    }
}
