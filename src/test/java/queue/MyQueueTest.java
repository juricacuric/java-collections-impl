package queue;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MyQueueTest {

    private MyBlockingQueue<Integer> queue;

    @Before
    public void setUp() {
        queue = new MyBlockingQueue<>(10);
    }

    @Test
    public void test1() {
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        executorService.submit(() -> {
            for (int i = 0; i < 20; i++) {
                try {
                    queue.add(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        List<Integer> list = Collections.synchronizedList(new ArrayList<>());
        executorService.submit(() -> {
            try {
                while (true) {
                    Integer value = queue.dequeue();
                    list.add(value);
                    System.out.println("Value - "  + value);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        try {
            executorService.shutdown();
            executorService.awaitTermination(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Assert.assertEquals(20, list.size());
    }


}
