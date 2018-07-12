package com.sinlov.net.thread;

import android.text.TextUtils;
import android.util.Log;

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
class NetSyncLog {

    private static boolean isDebug = Boolean.TRUE;

    private static final String TAG = "NetSync";

    static void i(String message) {
        if (isDebug) {
            if (TextUtils.isEmpty(message)) {
                message = "empty message";
            }
            Log.i(TAG, message);
        }
    }

    static void d(String message) {
        if (isDebug) {
            if (TextUtils.isEmpty(message)) {
                message = "empty message";
            }
            Log.d(TAG, message);
        }
    }

    static void w(String message, Throwable ex) {
        if (isDebug) {
            if (TextUtils.isEmpty(message)) {
                message = "empty message";
            }
            Log.w(TAG, message, ex);
        }
    }

    static void e(String message, Throwable ex) {
        if (TextUtils.isEmpty(message)) {
            message = "empty message";
        }
        Log.e(TAG, message, ex);
    }
}
