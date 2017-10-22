package com.katatoshi.mvvmexample;

import android.app.Application;

import com.katatoshi.mvvmexample.api.github.GitHubApiModule;
import com.katatoshi.mvvmexample.model.ModelModule;
import com.katatoshi.mvvmexample.view.ActivityModule;
import com.katatoshi.mvvmexample.view.RepositoryActivityComponent;
import com.katatoshi.mvvmexample.view.SearchRepositoriesActivityComponent;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;

/**
 * Dagger2 で依存注入するために必要なインターフェース。
 */
@Singleton
@Component(modules = {
        GitHubApiModule.class,
        ModelModule.class,
        ActivityModule.class
})
public interface AppComponent {

    SearchRepositoriesActivityComponent.Builder searchRepositoriesActivityComponentBuilder();

    RepositoryActivityComponent.Builder repositoryActivityComponentBuilder();

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }
}
