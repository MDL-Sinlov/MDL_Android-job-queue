package com.sinlov.net.thread;

import android.support.annotation.NonNull;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

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
public abstract class AbsTimeOutTask<T> extends FutureTask {

    /**
     * 每个任务线程默认超时
     */
    private static final long DEFAULT_EACH_TIME_OUT = 5L;

    /**
     * 每个任务线程超时单位
     */
    private static final TimeUnit DEFAULT_EACH_TIME_OUT_UNIT = TimeUnit.SECONDS;

    private final NetAsyncCall netAsyncCall;

    protected abstract T result();

    public AbsTimeOutTask(@NonNull Runnable runnable, T result, @NonNull NetAsyncCall netAsyncCall) {
        super(runnable, result);
        this.netAsyncCall = netAsyncCall;
    }

    public T openTimeOut() {
        return openTimeOut(DEFAULT_EACH_TIME_OUT, DEFAULT_EACH_TIME_OUT_UNIT);
    }

    public T openTimeOut(long timeOut, TimeUnit timeOutUnit) {
        try {
            return (T) this.get(timeOut, timeOutUnit);
        } catch (InterruptedException e) {
            netAsyncCall.onException(e);
            this.cancel(true);
        } catch (ExecutionException e) {
            this.cancel(true);
            netAsyncCall.onException(e);
        } catch (TimeoutException e) {
            this.cancel(true);
            netAsyncCall.onCancel(timeOut, timeOutUnit, e);
        } finally {
            this.done();
        }
        return null;
    }
}
