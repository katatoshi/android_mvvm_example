package com.katatoshi.mvvmexample;

import android.app.Application;

/**
 * アプリの Application クラス。
 */
public class AppApplication extends Application {

    private static AppApplication instance;

    public static AppApplication getInstance() {
        return instance;
    }

    protected void setInstance(AppApplication appApplication) {
        instance = appApplication;
    }

    private AppComponent component;

    public AppComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        setInstance(this);

        component = DaggerAppComponent.builder().build();
    }
}
