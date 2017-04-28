package com.sxdtdx.aitou;

import android.app.Application;
import android.content.Context;

/**
 * Created by lenovo on 2017/4/26.
 */

public class MyApplication extends Application {

    private static Context sContext;
    private static MyApplication instance;

    /**
     * 获取当前的Application
     *
     * @return Application
     */
    public static MyApplication getInstance() {
        if (instance == null) {
            instance = new MyApplication();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }

    public static Context getContext() {
        return sContext;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    public static void exit() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
