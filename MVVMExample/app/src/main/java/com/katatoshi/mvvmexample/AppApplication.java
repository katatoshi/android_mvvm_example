package com.katatoshi.mvvmexample;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * アプリの Application クラス。
 */
public class AppApplication extends Application {


    //region アプリの Application クラスのインスタンス。どこからでも利用できるようにするために用意。
    private static AppApplication instance;

    public static AppApplication getInstance() {
        return instance;
    }

    protected void setInstance(AppApplication appApplication) {
        instance = appApplication;
    }
    //endregion


    //region AppComponent のインスタンス。ViewModel, Activity, Fragment などで inject する。
    private AppComponent component;

    public AppComponent getComponent() {
        return component;
    }
    //endregion


    @Override
    public void onCreate() {
        super.onCreate();

        JodaTimeAndroid.init(this);

        setInstance(this);

        component = DaggerAppComponent.builder().build();
    }
}
