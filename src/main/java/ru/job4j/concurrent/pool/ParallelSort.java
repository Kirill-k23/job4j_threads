package ru.job4j.concurrent.pool;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelSort<T> extends RecursiveTask<Integer> {
    private final T[] array;
    private final int from;
    private final int to;
    private final T key;

    public ParallelSort(T[] array, int from, int to, T key) {
        this.array = array;
        this.from = from;
        this.to = to;
        this.key = key;
    }

    @Override
    protected Integer compute() {
        if (to - from < 10) {
            return LineSearch();
        }
        int middle = (from + to) / 2;
        ParallelSort<T> parallelLeft = new ParallelSort<>(array, from, middle, key);
        ParallelSort<T> parallelRight = new ParallelSort<>(array, middle + 1, to, key);
        parallelLeft.fork();
        parallelRight.fork();
        int left = parallelLeft.join();
        int right = parallelRight.join();
        return Math.max(left, right);
    }

    private Integer LineSearch() {
        int item = 0;
        for (int i = from; i <= to; i++) {
            if (array[i].equals(key)) {
                item = i;
                break;
            }
        }
        return item;
    }

    public static <T> Integer sort(T[] array, T value) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        return forkJoinPool.invoke(new ParallelSort<>(array, 0, array.length, value));
    }
}
