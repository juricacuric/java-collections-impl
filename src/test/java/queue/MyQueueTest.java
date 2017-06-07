package queue;

import org.junit.Before;
import org.junit.Test;

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

        executorService.submit(() -> {
            try {
                while (true) {
                    Integer value = queue.dequeue();
                    System.out.println("Value - "  + value);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        try {
            executorService.shutdown();
            executorService.awaitTermination(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
