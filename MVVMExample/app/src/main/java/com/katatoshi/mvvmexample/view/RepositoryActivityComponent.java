package com.katatoshi.mvvmexample.view;

import com.katatoshi.mvvmexample.api.github.SearchRepositoriesApi;
import com.katatoshi.mvvmexample.viewmodel.ViewModelModule;

import dagger.BindsInstance;
import dagger.Subcomponent;

@Subcomponent(modules = {
        ViewModelModule.class
})
public interface RepositoryActivityComponent {

    void inject(RepositoryActivity activity);

    @Subcomponent.Builder
    interface Builder {

        @BindsInstance
        Builder item(SearchRepositoriesApi.Result.Item item);

        RepositoryActivityComponent build();
    }
}
