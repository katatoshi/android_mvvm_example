package com.katatoshi.mvvmexample.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.katatoshi.mvvmexample.BR;
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
 * メインの Model。
 */
public class SearchRepositoriesModel extends BaseObservable {

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


    //region 検索中かどうかのプロパティ。
    private boolean searching;

    @Bindable
    public boolean isSearching() {
        return searching;
    }

    public void setSearching(boolean searching) {
        if (Objects.equals(this.searching, searching)) {
            return;
        }

        this.searching = searching;
        notifyPropertyChanged(BR.searching);
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

        CompletableFuture<Either<Integer, SearchRepositoriesApi.Result>> completableFuture = searchRepositoriesApi.get("topic:android+topic:mvvm", null, null);

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
