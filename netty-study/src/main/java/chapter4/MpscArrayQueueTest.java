package chapter4;

import io.netty.util.internal.shaded.org.jctools.queues.MpscArrayQueue;

/**
 * @author yuzhang
 * @date 2021/1/4 下午9:11
 * MpscArrayQueue使用
 */
public class MpscArrayQueueTest {
    public static final MpscArrayQueue<String> MPSC_ARRAY_QUEUE = new MpscArrayQueue<>(2);

    public static void main(String[] args) {
        for (int i = 1; i <= 2; i++) {
            int index = i;
            new Thread(()->MPSC_ARRAY_QUEUE.offer("data"+index),"thread-"+i).start();
        }
        try {
            Thread.sleep(1000L);
            // 此时队列满，抛出异常
            MPSC_ARRAY_QUEUE.add("dta3");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("队列大小："+MPSC_ARRAY_QUEUE.size()+", 队列容量："+MPSC_ARRAY_QUEUE.capacity());
        // 如果为空，抛出异常
        System.out.println("出队："+MPSC_ARRAY_QUEUE.remove());
        // 如果为空，返回null
        System.out.println("出队："+MPSC_ARRAY_QUEUE.poll());
    }
}
