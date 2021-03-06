package com.katatoshi.mvvmexample.util.databinding;

import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.databinding.InverseBindingListener;
import android.support.v7.widget.SearchView;

import java8.util.Objects;

/**
 * SearchView のデータバインディング拡張。
 */
public class SearchViewBindingAdapter {

    @BindingAdapter("query")
    public static void setQuery(SearchView searchView, String query) {
        if (Objects.equals(searchView.getQuery().toString(), query)) {
            return;
        }

        searchView.setQuery(query, false);
    }

    //region 双方向バインディングとリスナーバインディング。
    @InverseBindingAdapter(attribute = "query", event = "queryAttrChanged")
    public static String getQuery(SearchView searchView) {
        return searchView.getQuery().toString();
    }

    @BindingAdapter("onQueryTextSubmit")
    public static void setOnQueryTextSubmitListener(SearchView searchView, OnQueryTextSubmitListener onQueryTextSubmitListener) {
        setOnQueryTextListener(searchView, onQueryTextSubmitListener, null);
    }

    @BindingAdapter("queryAttrChanged")
    public static void setQueryAttrChanged(SearchView searchView, InverseBindingListener queryAttrChanged) {
        setOnQueryTextListener(searchView, null, queryAttrChanged);
    }

    @BindingAdapter({"onQueryTextSubmit", "queryAttrChanged"})
    public static void setOnQueryTextListener(SearchView searchView, final OnQueryTextSubmitListener onQueryTextSubmitListener, final InverseBindingListener queryAttrChanged) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                if (onQueryTextSubmitListener != null) {
                    onQueryTextSubmitListener.onQueryTextSubmit(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (queryAttrChanged != null) {
                    queryAttrChanged.onChange();
                }
                return false;
            }
        });
    }
    //endregion

    public interface OnQueryTextSubmitListener {

        void onQueryTextSubmit(String query);
    }
}
