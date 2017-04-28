package com.sxdtdx.aitou.utils;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by X on 15/9/6.
 */
public final class ThreadUtils {

    private static final Handler HANDLER = new Handler(Looper.getMainLooper());

    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(0, 2, 2000, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(), new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setName("kyokux-lib-network-thread-" + t.getId());
            return t;
        }
    });

    public static void runOnUIThread(Runnable runnable) {
        HANDLER.post(runnable);
    }

    public static void runOnUIThreadDelayed(Runnable runnable, long delayMillis) {
        HANDLER.postDelayed(runnable, delayMillis);
    }

    public static void cancelRunOnUIThread(Runnable runnable) {
        HANDLER.removeCallbacks(runnable);
    }

    public static void runOnNetworkThread(Runnable runnable) {
        THREAD_POOL_EXECUTOR.execute(runnable);
    }

    public static boolean isRunOnUIThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }
    private ThreadUtils() {
        // Noninstantiable utility class.
    }
}
