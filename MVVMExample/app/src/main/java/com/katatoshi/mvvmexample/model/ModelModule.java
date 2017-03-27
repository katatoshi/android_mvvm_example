package com.katatoshi.mvvmexample.model;

import dagger.Module;
import dagger.Provides;

/**
 * model パッケージのクラスのモジュール。
 */
@Module
public class ModelModule {

    @Provides
    SearchRepositoriesModel provideMainModel() {
        return new SearchRepositoriesModel();
    }
}
