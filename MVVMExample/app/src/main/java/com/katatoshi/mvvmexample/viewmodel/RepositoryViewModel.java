package com.katatoshi.mvvmexample.viewmodel;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.katatoshi.mvvmexample.api.github.SearchRepositoriesApi;

import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

/**
 * リポジトリ検索の個々の結果の ViewModel。
 */
public class RepositoryViewModel {

    public RepositoryViewModel(@Nullable Delegate delegate, @NonNull SearchRepositoriesApi.Result.Item item) {
        this.delegate = delegate;

        this.item = item;
    }

    public RepositoryViewModel(@NonNull SearchRepositoriesApi.Result.Item item) {
        this(null, item);
    }

    @NonNull
    public final SearchRepositoriesApi.Result.Item item;

    public String getFullName() {
        return item.fullName;
    }

    public String getDescription() {
        return item.description;
    }

    public String getLanguage() {
        return item.language;
    }

    public DateTime getUpdatedAt() {
        return ISODateTimeFormat.dateTimeParser().parseDateTime(item.updatedAt);
    }

    public void showHtmlUrl() {
        if (delegate != null) {
            delegate.showBrowser(item.htmlUrl);
        }
    }


    //region Activity, Fragment に委譲するメソッドたち。
    @Nullable
    private final Delegate delegate;

    public interface Delegate {

        void showBrowser(String url);
    }
    //endregion
}
