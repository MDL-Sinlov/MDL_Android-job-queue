package com.sinlov.net.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * <pre>
 *     sinlov
 *
 *     /\__/\
 *    /`    '\
 *  ≈≈≈ 0  0 ≈≈≈ Hello world!
 *    \  --  /
 *   /        \
 *  /          \
 * |            |
 *  \  ||  ||  /
 *   \_oo__oo_/≡≡≡≡≡≡≡≡o
 *
 * </pre>
 * Created by sinlov on 2018/7/10.
 */
public class NetASyncPoolExecutor extends ThreadPoolExecutor {

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int INIT_THREAD_COUNT = CPU_COUNT + 1;
    private static final int MAX_THREAD_COUNT = INIT_THREAD_COUNT;
    /**
     * 多余线程空闲最大时间 线程存活的时间
     */
    private static final long SURPLUS_THREAD_LIFE = 30L;

    private static NetASyncPoolExecutor instance;

    public static NetASyncPoolExecutor getInstance() {
        if (null == instance) {
            synchronized (NetASyncPoolExecutor.class) {
                if (null == instance) {
                    instance = new NetASyncPoolExecutor(
                            INIT_THREAD_COUNT,
                            MAX_THREAD_COUNT,
                            SURPLUS_THREAD_LIFE,
                            TimeUnit.SECONDS,
                            new ArrayBlockingQueue<Runnable>(64),
                            new NetASyncThreadFactory());
                }
            }
        }
        return instance;
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        if (t == null && r instanceof Future<?>) {
            try {
                ((Future<?>) r).get();
            } catch (CancellationException ce) {
                // TODO: sinlov 2018/7/10 cancelAll
                t = ce;
                NetSyncLog.w("cancelAll", t);
            } catch (ExecutionException ee) {
                // TODO: sinlov 2018/7/10 ExecutionException
                t = ee.getCause();
                NetSyncLog.w("Execution", t);
            } catch (InterruptedException ie) {
                // TODO: sinlov 2018/7/10 interruptedException
                Thread.currentThread().interrupt(); // ignore/reset
            }
        }
    }

    public NetASyncPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue, threadFactory, new RejectedExecutionHandler() {
            @Override
            public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                NetSyncLog.e("Task rejected, too many task!", new IllegalArgumentException("too many task"));
            }
        });
    }

    public void executeAsync(AbsTimeOutTask absTimeOutTask) {
        this.execute(absTimeOutTask);
        absTimeOutTask.openTimeOut();
    }

    public void executeAsync(AbsTimeOutTask absTimeOutTask, long timeOut, TimeUnit timeOutUnit) {
        this.execute(absTimeOutTask);
        absTimeOutTask.openTimeOut(timeOut, timeOutUnit);
    }
}
