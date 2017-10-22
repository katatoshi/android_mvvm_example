package com.katatoshi.mvvmexample.view;

import dagger.Module;

@Module(subcomponents = {
        SearchRepositoriesActivityComponent.class,
        RepositoryActivityComponent.class
})
public class ActivityModule {
}
