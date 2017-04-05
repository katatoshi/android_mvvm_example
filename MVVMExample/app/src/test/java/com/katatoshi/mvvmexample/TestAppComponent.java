package com.katatoshi.mvvmexample;

import com.katatoshi.mvvmexample.api.github.TestGitHubApiModule;
import com.katatoshi.mvvmexample.model.ModelModule;
import com.katatoshi.mvvmexample.model.SearchRepositoriesModelTest;

import javax.inject.Singleton;

import dagger.Component;

/**
 * ローカルユニットテストの依存関係。
 */
@Singleton
@Component(modules = {
        TestGitHubApiModule.class,
        ModelModule.class
})
public interface TestAppComponent extends AppComponent {

    void inject(SearchRepositoriesModelTest.検索結果が0件の場合 test);
    void inject(SearchRepositoriesModelTest.検索結果が1件の場合 test);
    void inject(SearchRepositoriesModelTest.レスポンスを受け取ったがステータスコードがエラーコードの場合 test);
    void inject(SearchRepositoriesModelTest.通信に関する例外が発生した場合 test);
    void inject(SearchRepositoriesModelTest.検索結果が1件存在する状態でもう一度検索するとエラーコードが返ってきた場合 test);
    void inject(SearchRepositoriesModelTest.検索結果が1件存在する状態でもう一度検索すると通信に関する例外が発生した場合 test);
}
