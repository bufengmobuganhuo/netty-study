package chapter4;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.Timer;
import io.netty.util.TimerTask;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author yuzhang
 * @date 2020/12/30 下午8:43
 * 时间轮延迟测试
 */
public class HashedWheelTimerTest {
    public static void main(String[] args) {
        Timer timer = new HashedWheelTimer();
        // 提交新任务，延迟10s
        Timeout timeout1 = timer.newTimeout(new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {
                System.out.println("timeout1:" + new Date());
            }
        }, 10, TimeUnit.SECONDS);
        // 提前取消任务
        if (!timeout1.isExpired()) {
            timeout1.cancel();
        }
        // 延迟1s执行的新任务
        timer.newTimeout(new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {
                System.out.println("timeout2:" + new Date());
                Thread.sleep(5000);
            }
        }, 1, TimeUnit.SECONDS);
    }
}
