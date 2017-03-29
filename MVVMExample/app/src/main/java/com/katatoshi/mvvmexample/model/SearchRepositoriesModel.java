package com.katatoshi.mvvmexample.model;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.katatoshi.mvvmexample.api.github.SearchRepositoriesApi;
import com.katatoshi.mvvmexample.util.Either;
import com.katatoshi.mvvmexample.util.ListUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import java8.util.Objects;
import java8.util.concurrent.CompletableFuture;
import java8.util.function.Consumer;
import java8.util.function.Function;

/**
 * GitHub リポジトリ検索 Model。
 */
public class SearchRepositoriesModel extends BaseModel {

    @Inject
    SearchRepositoriesModel(SearchRepositoriesApi searchRepositoriesApi) {
        this.searchRepositoriesApi = searchRepositoriesApi;
    }

    private final SearchRepositoriesApi searchRepositoriesApi;


    //region Search Repositories の結果のリスト。
    private final ObservableList<SearchRepositoriesApi.Result.Item> repositoryList = new ObservableArrayList<>();

    public List<SearchRepositoriesApi.Result.Item> getRepositoryList() {
        return new ArrayList<>(repositoryList);
    }

    public void addOnRepositoryListChangedCallback(ObservableList.OnListChangedCallback<ObservableList<SearchRepositoriesApi.Result.Item>> callback) {
        repositoryList.addOnListChangedCallback(callback);
    }

    public void removeRepositoryListChangedCallback(ObservableList.OnListChangedCallback<ObservableList<SearchRepositoriesApi.Result.Item>> callback) {
        repositoryList.removeOnListChangedCallback(callback);
    }
    //endregion

    //region 検索クエリ文字列のプロパティ。
    public static final String PROPERTY_QUERY_TEXT = "PROPERTY_QUERY_TEXT";

    private String queryText = "topic:android+topic:mvvm";

    public String getQueryText() {
        return queryText;
    }

    public void setQueryText(String queryText) {
        if (Objects.equals(this.queryText, queryText)) {
            return;
        }

        this.queryText = queryText;
        firePropertyChange(PROPERTY_QUERY_TEXT);
    }
    //endregion


    //region 検索中かどうかのプロパティ。
    public static final String PROPERTY_SEARCING = "PROPERTY_SEARCING";

    private boolean searching;

    public boolean isSearching() {
        return searching;
    }

    public void setSearching(boolean searching) {
        if (Objects.equals(this.searching, searching)) {
            return;
        }

        this.searching = searching;
        firePropertyChange(PROPERTY_SEARCING);
    }
    //endregion


    /**
     * リポジトリを検索します。
     */
    public void search() {
        if (isSearching()) {
            return;
        }

        setSearching(true);

        CompletableFuture<Either<Integer, SearchRepositoriesApi.Result>> completableFuture = searchRepositoriesApi.get(getQueryText(), null, null);

        completableFuture.thenAccept(new Consumer<Either<Integer, SearchRepositoriesApi.Result>>() {
            @Override
            public void accept(Either<Integer, SearchRepositoriesApi.Result> integerResultEither) {
                integerResultEither.ifRight(new Consumer<SearchRepositoriesApi.Result>() {
                    @Override
                    public void accept(SearchRepositoriesApi.Result result) {
                        ListUtil.replace(repositoryList, result.items);
                    }
                });
                setSearching(false);
            }
        });

        completableFuture.exceptionally(new Function<Throwable, Either<Integer, SearchRepositoriesApi.Result>>() {
            @Override
            public Either<Integer, SearchRepositoriesApi.Result> apply(Throwable throwable) {
                setSearching(false);
                return null;
            }
        });
    }
}
