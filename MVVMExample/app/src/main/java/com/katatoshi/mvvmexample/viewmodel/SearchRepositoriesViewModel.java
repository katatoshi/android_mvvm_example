package com.katatoshi.mvvmexample.viewmodel;

import android.databinding.Observable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.katatoshi.mvvmexample.AppApplication;
import com.katatoshi.mvvmexample.api.github.SearchRepositoriesApi;
import com.katatoshi.mvvmexample.model.BaseModel;
import com.katatoshi.mvvmexample.model.SearchRepositoriesModel;
import com.katatoshi.mvvmexample.util.ListUtil;
import com.katatoshi.mvvmexample.util.databinding.ObservableListUtil;

import javax.inject.Inject;

import java8.util.function.Function;
import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;

/**
 * GitHub リポジトリ検索 Model。
 */
public class SearchRepositoriesViewModel {

    public SearchRepositoriesViewModel(Delegate delegate) {
        AppApplication.getInstance().getComponent().inject(this);

        this.delegate = delegate;

        queryText.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                searchRepositoriesModel.setQueryText(queryText.get());
            }
        });
    }

    @Inject
    SearchRepositoriesModel searchRepositoriesModel;

    public final ObservableList<RepositoryViewModel> repositoryViewModelList = new ObservableArrayList<>();

    public final ObservableField<String> queryText = new ObservableField<>();

    public final ObservableBoolean searching = new ObservableBoolean();

    /**
     * フォアグラウンドに遷移したときに呼ばれるべき処理を実行します。
     */
    public void onResume() {
        //region コールバックの Model への追加。
        searchRepositoriesModel.addPropertyChangeListener(searchRepositoriesModelPropertyChangeListener);
        searchRepositoriesModel.addOnRepositoryListChangedCallback(onRepositoryListChangedCallback);
        //endregion

        ListUtil.replace(repositoryViewModelList, StreamSupport.stream(searchRepositoriesModel.getRepositoryList())
                .map(new Function<SearchRepositoriesApi.Result.Item, RepositoryViewModel>() {
                    @Override
                    public RepositoryViewModel apply(SearchRepositoriesApi.Result.Item item) {
                        return new RepositoryViewModel(item);
                    }
                })
                .collect(Collectors.<RepositoryViewModel>toList()));

        queryText.set(searchRepositoriesModel.getQueryText());

        searching.set(searchRepositoriesModel.isSearching());
    }

    /**
     * バックグラウンドに遷移したときに呼ばれるべき処理を実行します。
     */
    public void onPause() {
        //region コールバックの Model からの削除。
        searchRepositoriesModel.removeRepositoryListChangedCallback(onRepositoryListChangedCallback);
        searchRepositoriesModel.removePropertyChangeListener(searchRepositoriesModelPropertyChangeListener);
        //endregion
    }

    /**
     * リポジトリを検索します。
     */
    public void search() {
        searchRepositoriesModel.search();
    }

    /**
     * リポジトリの言語を表示します。
     */
    public void showRepositoryLanguage(RepositoryViewModel repositoryViewModel) {
        delegate.showMessage(repositoryViewModel.language);
    }

    /**
     * SearchRepositoriesModel の変更を観測するコールバック。
     */
    private BaseModel.PropertyChangeListener searchRepositoriesModelPropertyChangeListener = new BaseModel.PropertyChangeListener() {
        @Override
        public void propertyChange(String propertyName) {
            switch (propertyName) {
                case SearchRepositoriesModel.PROPERTY_QUERY_TEXT:
                    queryText.set(searchRepositoriesModel.getQueryText());
                    break;
                case SearchRepositoriesModel.PROPERTY_SEARCING:
                    searching.set(searchRepositoriesModel.isSearching());
                    break;
            }
        }
    };

    /**
     * SearchRepositoriesModel の repositoryList の変更を観測するコールバック。
     */
    private ObservableList.OnListChangedCallback<ObservableList<SearchRepositoriesApi.Result.Item>> onRepositoryListChangedCallback = new ObservableListUtil.FollowingCallback<>(
            // follower
            repositoryViewModelList,
            // mapper
            new Function<SearchRepositoriesApi.Result.Item, RepositoryViewModel>() {
                @Override
                public RepositoryViewModel apply(SearchRepositoriesApi.Result.Item item) {
                    return new RepositoryViewModel(item);
                }
            });


    //region Activity に移譲するメソッドたち。
    private final Delegate delegate;

    public interface Delegate {

        void showMessage(String message);
    }
    //endregion
}
