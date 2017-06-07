package queue;

import java.util.LinkedList;
import java.util.Queue;

public class MyBlockingQueue<T> {

    private Queue<T> queue = new LinkedList<>();
    private final int limit;


    public MyBlockingQueue(int limit) {
        this.limit = limit;
    }

    public synchronized void add(T elemenet) throws InterruptedException {
        while (queue.size() >= limit) {
            wait();
            System.out.println("add - wait");
        }

        if (queue.size() == 0) {
            notify();
            System.out.println("add - notify");
        }

        queue.add(elemenet);
    }

    public synchronized T dequeue() throws InterruptedException {
        while (queue.size() == 0) {
            wait();
            System.out.println("dequeue - wait");
        }

        if (queue.size() == limit) {
            notifyAll();
            System.out.println("dequeue - notifyAll");
        }

        return queue.poll();
    }


}
