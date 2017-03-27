package com.katatoshi.mvvmexample.model;

import com.katatoshi.mvvmexample.api.github.SearchRepositoriesApi;

import dagger.Module;
import dagger.Provides;

/**
 * model パッケージのクラスのモジュール。
 */
@Module
public class ModelModule {

    @Provides
    SearchRepositoriesModel provideMainModel(SearchRepositoriesApi searchRepositoriesApi) {
        return new SearchRepositoriesModel(searchRepositoriesApi);
    }
}
