package com.katatoshi.mvvmexample;

import com.katatoshi.mvvmexample.model.ModelModule;
import com.katatoshi.mvvmexample.viewmodel.MainViewModel;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Dagger2 で依存を注入するために必要なインターフェース。
 */
@Singleton
@Component(modules = {
        ModelModule.class
})
public interface AppComponent {

    void inject(MainViewModel viewModel);
}
