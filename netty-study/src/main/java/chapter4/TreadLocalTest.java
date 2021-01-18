package chapter4;

/**
 * @author yuzhang
 * @date 2020/12/24 下午6:40
 * TODO
 */
public class TreadLocalTest {
    private static final ThreadLocal<String> THREAD_NAME_LOCAL = ThreadLocal.withInitial(() -> Thread.currentThread().getName());
    private static final ThreadLocal<TradeOrder> TRADE_THREAD_LOCAL = new ThreadLocal<>();

    public static void main(String[] args) {
        for (int i = 0; i < 2; i++) {
            int finalI = i;
            new Thread(() -> {
                TradeOrder order = new TradeOrder(finalI, finalI % 2 == 0 ? "已支付" : "未支付");
                TRADE_THREAD_LOCAL.set(order);
                System.err.println("threadName:"+THREAD_NAME_LOCAL.get());
                System.err.println("tradeOrder info:"+TRADE_THREAD_LOCAL.get());
            },"thread-"+i).start();
        }
    }

    static class TradeOrder {
        long id;
        String status;

        public TradeOrder(long id, String status) {
            this.id = id;
            this.status = status;
        }

        @Override
        public String toString() {
            return "TradeOrder{" +
                    "id=" + id +
                    ", status='" + status + '\'' +
                    '}';
        }
    }
}
