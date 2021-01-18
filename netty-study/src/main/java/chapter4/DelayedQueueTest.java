package chapter4;

import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * @author yuzhang
 * @date 2020/12/30 下午8:19
 * DelayedQueue 使用示例
 */
public class DelayedQueueTest {
    public static void main(String[] args) throws InterruptedException {
        BlockingQueue<SampleTask> delayQueue = new DelayQueue<>();
        long now = System.currentTimeMillis();
        delayQueue.put(new SampleTask(now+1000));
        delayQueue.put(new SampleTask(now+2000));
        delayQueue.put(new SampleTask(now+3000));
        for (int i = 0; i < 3; i++) {
            System.out.println(new Date(delayQueue.take().getTime()));
        }
    }

    static class SampleTask implements Delayed {
        private long time;

        public SampleTask(long time) {
            this.time = time;
        }

        public long getTime() {
            return time;
        }

        /**
         * 获取当前剩余的延时时间
         * @param unit
         * @return
         */
        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(time-System.currentTimeMillis(),TimeUnit.MILLISECONDS);
        }

        /**
         * 比较两个延时任务当前的延时时间
         * @param o
         * @return
         */
        @Override
        public int compareTo(Delayed o) {
            return Long.compare(this.getDelay(TimeUnit.MILLISECONDS),o.getDelay(TimeUnit.MILLISECONDS));
        }
    }
}
