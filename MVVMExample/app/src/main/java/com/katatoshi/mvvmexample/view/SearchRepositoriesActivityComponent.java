package com.katatoshi.mvvmexample.view;

import com.katatoshi.mvvmexample.viewmodel.ViewModelModule;

import dagger.Subcomponent;

@Subcomponent(modules = {
        ViewModelModule.class
})
public interface SearchRepositoriesActivityComponent {

    void inject(SearchRepositoriesActivity activity);

    @Subcomponent.Builder
    interface Builder {

        SearchRepositoriesActivityComponent build();
    }
}
