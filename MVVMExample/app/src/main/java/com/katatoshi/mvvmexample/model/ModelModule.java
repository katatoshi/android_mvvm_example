package com.katatoshi.mvvmexample.model;

import dagger.Module;
import dagger.Provides;

/**
 * model パッケージのクラスのモジュール。
 */
@Module
public class ModelModule {

    @Provides
    MainModel provideMainModel() {
        return new MainModel();
    }
}
