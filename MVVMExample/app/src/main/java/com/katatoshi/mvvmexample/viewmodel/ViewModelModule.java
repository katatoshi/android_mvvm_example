package com.katatoshi.mvvmexample.viewmodel;

import com.katatoshi.mvvmexample.api.github.SearchRepositoriesApi;
import com.katatoshi.mvvmexample.model.SearchRepositoriesModel;

import dagger.Module;
import dagger.Provides;

/**
 * model パッケージのクラスのモジュール。
 */
@Module
public class ViewModelModule {

    @Provides
    public static SearchRepositoriesViewModel provideSearchRepositoriesViewModel(SearchRepositoriesModel searchRepositoriesModel) {
        return new SearchRepositoriesViewModel(searchRepositoriesModel);
    }

    @Provides
    public static RepositoryViewModel provideRepositoryViewModel(SearchRepositoriesApi.Result.Item item) {
        return new RepositoryViewModel(item);
    }
}
