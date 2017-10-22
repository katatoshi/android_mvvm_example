package com.katatoshi.mvvmexample.viewmodel;

import com.katatoshi.mvvmexample.api.github.SearchRepositoriesApi;

import org.greenrobot.eventbus.EventBus;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;

import javax.inject.Inject;

/**
 * リポジトリ検索の個々の結果の ViewModel。
 */
public class RepositoryViewModel {

    @Inject
    public RepositoryViewModel(SearchRepositoriesApi.Result.Item item) {
        this.item = item;
    }

    private final EventBus eventBus = EventBus.builder().build();

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
        eventBus.post(new ShowBrowserEvent(item.htmlUrl));
    }


    //region EventBus
    public void registerEventBus(Object subscriber) {
        eventBus.register(subscriber);
    }

    public void unregisterEventBus(Object subscriber) {
        eventBus.unregister(subscriber);
    }

    public static class ShowBrowserEvent {

        ShowBrowserEvent(String url) {
            this.url = url;
        }

        public final String url;
    }
    //endregion
}
