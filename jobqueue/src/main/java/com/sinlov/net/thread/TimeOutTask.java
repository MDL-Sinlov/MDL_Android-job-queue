package com.sinlov.net.thread;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import java.util.Timer;
import java.util.TimerTask;

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
public abstract class TimeOutTask {


    private final int TIMER_EXECUTE = 1;
    private final int ERROR_MESSAGE = 1;
    private final int CHECK_TIME = 5000;

    private Timer timer;
    private TimerTask timerTask;
    private EThread eThread;


    public void action() {
        timerTask = new TimerTask() {
            @Override
            public void run() {
                checkThread();
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, CHECK_TIME);
        eThread = new EThread();
        eThread.start();
    }

    private void checkThread() {
        Message msg = new Message();
        msg.what = TIMER_EXECUTE;
        mHandler.sendMessage(msg);
    }

    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {

                case TIMER_EXECUTE:
                    if (eThread.getState().toString().equals("TERMINATED") ||
                            eThread.getState().toString().equals("TIMED_WAITING")) {
                        eThread.stopThread(true);
                        onTimeOut();
                        timer.cancel();// 关闭计时器
                    }
                    break;
            }
            super.handleMessage(msg);
        }
    };

    protected abstract void onTimeOut();

    class EThread extends Thread {

        private boolean flag = true;

        public void stopThread(boolean flag) {
            this.flag = !flag;
        }

        @Override
        public void run() {
            Looper.prepare();

            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            timer.cancel();// 关闭计时器
            if (!flag) {
                return;
            }
            doCallBack();
        }
    }

    protected abstract void doCallBack();
}
