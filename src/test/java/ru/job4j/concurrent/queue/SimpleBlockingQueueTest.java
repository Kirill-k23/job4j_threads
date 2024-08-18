package ru.job4j.concurrent.queue;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

class SimpleBlockingQueueTest {
    @Test
    public void queueTest() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<Integer>(5);
        List<Integer> list = new ArrayList<>();
        Thread producer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                queue.offer(i);
            }
        });
        Thread consumer = new Thread(() -> {
            for (int i = 0; i < 5; i++) {
                list.add(queue.poll());
            }
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertThat(list).containsExactly(0, 1, 2, 3, 4);
    }

}