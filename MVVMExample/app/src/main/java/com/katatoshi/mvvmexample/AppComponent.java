package com.katatoshi.mvvmexample;

import com.katatoshi.mvvmexample.api.github.GitHubApiModule;
import com.katatoshi.mvvmexample.model.SearchRepositoriesModel;
import com.katatoshi.mvvmexample.model.ModelModule;
import com.katatoshi.mvvmexample.viewmodel.SearchRepositoriesViewModel;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Dagger2 で依存を注入するために必要なインターフェース。
 */
@Singleton
@Component(modules = {
        GitHubApiModule.class,
        ModelModule.class
})
public interface AppComponent {

    void inject(SearchRepositoriesModel model);

    void inject(SearchRepositoriesViewModel viewModel);
}
