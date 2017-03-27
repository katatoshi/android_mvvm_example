package com.katatoshi.mvvmexample.api.github;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * api.github パッケージのクラスのモジュール。
 */
@Module
public class GitHubApiModule {

    @Singleton
    @Provides
    SearchRepositoriesApi provideSearchRepositoriesApi() {
        return new SearchRepositoriesApi();
    }
};
