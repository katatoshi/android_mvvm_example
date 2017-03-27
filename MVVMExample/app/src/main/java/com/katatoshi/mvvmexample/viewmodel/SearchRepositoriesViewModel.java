package com.katatoshi.mvvmexample.viewmodel;

import android.databinding.Observable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableList;

import com.katatoshi.mvvmexample.AppApplication;
import com.katatoshi.mvvmexample.BR;
import com.katatoshi.mvvmexample.api.github.SearchRepositoriesApi;
import com.katatoshi.mvvmexample.model.SearchRepositoriesModel;
import com.katatoshi.mvvmexample.util.ListUtil;

import javax.inject.Inject;

import java8.util.function.Function;
import java8.util.stream.Collectors;
import java8.util.stream.StreamSupport;

/**
 * メインの ViewModel。
 */
public class SearchRepositoriesViewModel {

    public SearchRepositoriesViewModel(Delegate delegate) {
        AppApplication.getInstance().getComponent().inject(this);

        this.delegate = delegate;
    }

    @Inject
    SearchRepositoriesModel searchRepositoriesModel;

    public final ObservableList<RepositoryViewModel> repositoryViewModelList = new ObservableArrayList<>();

    public final ObservableBoolean searching = new ObservableBoolean();

    /**
     * フォアグラウンドに遷移したときに呼ばれるべき処理を実行します。
     */
    public void onResume() {
        //region コールバックの Model への追加。
        searchRepositoriesModel.addOnPropertyChangedCallback(onPropertyChangedCallback);
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

        searching.set(searchRepositoriesModel.isSearching());
    }

    /**
     * バックグラウンドに遷移したときに呼ばれるべき処理を実行します。
     */
    public void onPause() {
        //region コールバックの Model からの削除。
        searchRepositoriesModel.removeRepositoryListChangedCallback(onRepositoryListChangedCallback);
        searchRepositoriesModel.removeOnPropertyChangedCallback(onPropertyChangedCallback);
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
    private Observable.OnPropertyChangedCallback onPropertyChangedCallback = new Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(Observable sender, int propertyId) {
            // switch (propertyId) で case BR.searching とすると Android Studio 上でなぜか Constant expression required とエラーがでる（定数のはずなのに）。
            // ビルドはできるので無視して switch を使っても問題ないとも考えられるが、常にエラー表示となるのは紛らわしいのでやめておく。
            // Kotlin の when 式なら定数でなくてもよいので何の問題もない。
            if (propertyId == BR.searching) {
                searching.set(searchRepositoriesModel.isSearching());
            }
        }
    };

    /**
     * SearchRepositoriesModel の repositoryList の変更を観測するコールバック。
     */
    private ObservableList.OnListChangedCallback<ObservableList<SearchRepositoriesApi.Result.Item>> onRepositoryListChangedCallback = new ObservableList.OnListChangedCallback<ObservableList<SearchRepositoriesApi.Result.Item>>() {

        @Override
        public void onChanged(ObservableList<SearchRepositoriesApi.Result.Item> sender) {
            // 呼ばれない。
        }

        @Override
        public void onItemRangeChanged(ObservableList<SearchRepositoriesApi.Result.Item> sender, int positionStart, int itemCount) {
            for (int i = positionStart; i < positionStart + itemCount; i++) {
                repositoryViewModelList.set(i, new RepositoryViewModel(searchRepositoriesModel.getRepositoryList().get(i)));
            }
        }

        @Override
        public void onItemRangeInserted(ObservableList<SearchRepositoriesApi.Result.Item> sender, int positionStart, int itemCount) {
            for (int i = positionStart; i < positionStart + itemCount; i++) {
                repositoryViewModelList.add(i, new RepositoryViewModel(searchRepositoriesModel.getRepositoryList().get(i)));
            }
        }

        @Override
        public void onItemRangeMoved(ObservableList<SearchRepositoriesApi.Result.Item> sender, int fromPosition, int toPosition, int itemCount) {
            // 呼ばれない。
        }

        @Override
        public void onItemRangeRemoved(ObservableList<SearchRepositoriesApi.Result.Item> sender, int positionStart, int itemCount) {
            for (int i = positionStart; i < positionStart + itemCount; i++) {
                repositoryViewModelList.remove(positionStart);
            }
        }
    };


    //region Activity に移譲するメソッドたち。
    private final Delegate delegate;

    public interface Delegate {

        void showMessage(String message);
    }
    //endregion
}
