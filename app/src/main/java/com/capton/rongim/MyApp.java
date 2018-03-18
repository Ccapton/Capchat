package com.capton.rongim;

import com.capton.common.base.App;

import io.rong.imkit.RongIM;

/**
 * Created by capton on 2018/3/18.
 */

public class MyApp extends App {

    @Override
    public void onCreate() {
        super.onCreate();
        RongIM.init(this,"'kj7swf8oki6i2'");
    }


}
