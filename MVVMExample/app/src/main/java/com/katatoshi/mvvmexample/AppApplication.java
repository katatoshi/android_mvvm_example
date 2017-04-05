package com.katatoshi.mvvmexample;

import android.app.Application;
import android.support.annotation.VisibleForTesting;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * アプリの Application クラス。
 */
public class AppApplication extends Application {

    /**
     * アプリの Application クラスのインスタンス。
     */
    private static AppApplication instance;

    /**
     * テスト用にアプリの Application クラスのインスタンスをセットします。
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    public static void setInstance(AppApplication instance) {
        AppApplication.instance = instance;
    }

    /**
     * クラスの依存関係のインスタンスのゲッター。ViewModel などで inject するために使います。
     *
     * @return クラスの依存関係
     */
    public static AppComponent getComponent() {
        return instance.component;
    }

    /**
     * テスト用にクラスの依存関係のインスタンスをセットします。
     *
     * @param component クラスの依存関係
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    public static void setComponent(AppComponent component) {
        instance.component = component;
    }

    /**
     * クラスの依存関係のインスタンス。
     */
    private AppComponent component;

    @Override
    public void onCreate() {
        super.onCreate();

        JodaTimeAndroid.init(this);

        instance = this;

        component = DaggerAppComponent.create();
    }
}
