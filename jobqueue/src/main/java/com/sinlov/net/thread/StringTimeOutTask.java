package com.sinlov.net.thread;

import android.support.annotation.NonNull;

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
public class StringTimeOutTask extends AbsTimeOutTask {


    public StringTimeOutTask(@NonNull Runnable runnable, @NonNull NetAsyncCall netAsyncCall) {
        super(runnable, "", netAsyncCall);
    }

    @Override
    protected String result() {
        return (String) openTimeOut();
    }
}
