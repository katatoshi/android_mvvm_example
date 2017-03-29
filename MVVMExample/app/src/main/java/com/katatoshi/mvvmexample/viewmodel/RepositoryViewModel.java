package com.katatoshi.mvvmexample.viewmodel;

import com.katatoshi.mvvmexample.api.github.SearchRepositoriesApi;

/**
 * リポジトリ検索の個々の結果の ViewModel。
 */
public class RepositoryViewModel {

    public RepositoryViewModel(SearchRepositoriesApi.Result.Item item) {
        id = item.id;
        name = item.name;
        fullName = item.fullName;
        descripition = item.description;
        language = item.language;
    }

    public final int id;

    public final String name;

    public final String fullName;

    public final String descripition;

    public final String language;
}
