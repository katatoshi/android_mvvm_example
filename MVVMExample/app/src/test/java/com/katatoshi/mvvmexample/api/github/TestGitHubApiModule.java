package com.katatoshi.mvvmexample.api.github;

import org.mockito.Mockito;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * ローカルユニットテスト用の api.github パッケージのクラスのモジュール。
 */
@Module
public class TestGitHubApiModule {

    @Singleton
    @Provides
    SearchRepositoriesApi provideSearchRepositoriesApi() {
        return Mockito.mock(SearchRepositoriesApi.class);
    }
};
