package com.sxdtdx.aitou;

import android.app.Application;
import android.content.Context;

import cn.bmob.v3.Bmob;

/**
 * Created by lenovo on 2017/4/26.
 */

public class MyApplication extends Application {

    private static final String APPLICATION_ID_AI_TOU = "538a4a4bd7a1288078923bd628dac2bd";

    private static Context sContext;

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
        Bmob.initialize(this, APPLICATION_ID_AI_TOU);
    }

    public static Context getContext() {
        return sContext;
    }

    @Override
    public void onTerminate() {
        sContext = null;
        super.onTerminate();
    }

    public static void exit() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
