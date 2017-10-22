package com.katatoshi.mvvmexample.viewmodel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.katatoshi.mvvmexample.api.github.SearchRepositoriesApi;
import com.katatoshi.mvvmexample.model.BaseModel;
import com.katatoshi.mvvmexample.model.PropertyChangeListener;
import com.katatoshi.mvvmexample.model.SearchRepositoriesModel;
import com.katatoshi.mvvmexample.util.ListUtil;
import com.katatoshi.mvvmexample.util.databinding.ObservableListUtil;
import com.katatoshi.mvvmexample.util.databinding.OnPropertyChangedCallback;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;

/**
 * GitHub リポジトリ検索 Model。
 */
public class SearchRepositoriesViewModel {

    @Inject
    public SearchRepositoriesViewModel(SearchRepositoriesModel searchRepositoriesModel) {
        this.searchRepositoriesModel = searchRepositoriesModel;

        searchRepositoriesModelPropertyChangeListener = new PropertyChangeListener(
                propertyName -> {
                    switch (propertyName) {
                        case SearchRepositoriesModel.PROPERTY_QUERY_TEXT:
                            queryText.set(searchRepositoriesModel.getQueryText());
                            break;
                        case SearchRepositoriesModel.PROPERTY_SEARCING:
                            searching.set(searchRepositoriesModel.isSearching());
                            break;
                    }
                }
        );

        // queryText は Activity, Fragment からも変更されうるのでコールバックを追加。
        queryText.addOnPropertyChangedCallback(new OnPropertyChangedCallback((sender, propertyId) -> searchRepositoriesModel.setQueryText(queryText.get())));
    }

    private final SearchRepositoriesModel searchRepositoriesModel;

    private final EventBus eventBus = EventBus.builder().build();

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
                .map(RepositoryViewModel::new)
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
     * リポジトリを表示します。
     */
    public void showRepository(RepositoryViewModel repositoryViewModel) {
        eventBus.post(new ShowRepositoryEvent(repositoryViewModel.item));
    }

    /**
     * SearchRepositoriesModel の変更を観測するコールバック。
     */
    private final BaseModel.PropertyChangeListener searchRepositoriesModelPropertyChangeListener;

    /**
     * SearchRepositoriesModel の repositoryList の変更を観測するコールバック。
     */
    private ObservableList.OnListChangedCallback<ObservableList<SearchRepositoriesApi.Result.Item>> onRepositoryListChangedCallback = new ObservableListUtil.FollowingCallback<>(
            // follower
            repositoryViewModelList,
            // mapper
            RepositoryViewModel::new
    );


    //region EventBus
    public void registerEventBus(Object subscriber) {
        eventBus.register(subscriber);
    }

    public void unregisterEventBus(Object subscriber) {
        eventBus.unregister(subscriber);
    }

    public static class ShowRepositoryEvent {

        ShowRepositoryEvent(SearchRepositoriesApi.Result.Item item) {
            this.item = item;
        }

        public final SearchRepositoriesApi.Result.Item item;
    }
    //endregion
}
