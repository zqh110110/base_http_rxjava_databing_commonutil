/**
 *
 */
package com.smcb.simulatedstock.base;


import android.app.Application;

import com.smcb.simulatedstock.BuildConfig;
import com.smcb.simulatedstock.TradeMainUtil;
import com.smcb.simulatedstock.library.log.KLog;

public class MyApp extends Application {

    public static MyApp app;

    @Override
    public void onCreate() {
        super.onCreate();
        KLog.init(BuildConfig.DEBUG);
        app = this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }
}
