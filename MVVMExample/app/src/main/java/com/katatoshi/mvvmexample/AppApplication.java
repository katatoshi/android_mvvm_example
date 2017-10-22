package com.katatoshi.mvvmexample;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * アプリの Application クラス。
 */
public class AppApplication extends Application {

    /**
     * クラスの依存関係のインスタンス。
     */
    private AppComponent component;

    public AppComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        JodaTimeAndroid.init(this);

        component = DaggerAppComponent.builder()
                .application(this)
                .build();
    }
}
