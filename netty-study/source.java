public HashedWheelTimer(
		// 线程池，但是只创建了一个线程
        ThreadFactory threadFactory,
		// 时针每次 tick 的时间，相当于时针间隔多久走到下一个 slot
        long tickDuration, 
		// 表示 tickDuration 的时间单位
        TimeUnit unit, 
		// 时间轮上一共有多少个 slot，默认 512 个。分配的 slot 越多，占用的内存空间就越大
        int ticksPerWheel, 
		// 
        boolean leakDetection,

        long maxPendingTimeouts) {

    // 省略其他代码

    wheel = createWheel(ticksPerWheel); // 创建时间轮的环形数组结构

    mask = wheel.length - 1; // 用于快速取模的掩码

    long duration = unit.toNanos(tickDuration); // 转换成纳秒处理

    // 省略其他代码

    workerThread = threadFactory.newThread(worker); // 创建工作线程

    leak = leakDetection || !workerThread.isDaemon() ? leakDetector.track(this) : null; // 是否开启内存泄漏检测

    this.maxPendingTimeouts = maxPendingTimeouts; // 最大允许等待任务数，HashedWheelTimer 中任务超出该阈值时会抛出异常

    // 如果 HashedWheelTimer 的实例数超过 64，会打印错误日志

    if (INSTANCE_COUNTER.incrementAndGet() > INSTANCE_COUNT_LIMIT &&

        WARNED_TOO_MANY_INSTANCES.compareAndSet(false, true)) {

        reportTooManyInstances();

    }

}
