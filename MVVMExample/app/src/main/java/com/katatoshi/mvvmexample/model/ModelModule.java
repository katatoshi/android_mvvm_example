package com.katatoshi.mvvmexample.model;

import com.katatoshi.mvvmexample.api.github.SearchRepositoriesApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * model パッケージのクラスのモジュール。
 */
@Module
public class ModelModule {

    @Singleton
    @Provides
    public static SearchRepositoriesModel provideMainModel(SearchRepositoriesApi searchRepositoriesApi) {
        return new SearchRepositoriesModel(searchRepositoriesApi);
    }
}
