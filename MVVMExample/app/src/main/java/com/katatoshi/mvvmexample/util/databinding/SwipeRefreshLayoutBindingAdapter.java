package com.katatoshi.mvvmexample.util.databinding;

import android.databinding.BindingAdapter;
import android.support.v4.widget.SwipeRefreshLayout;

/**
 * SwipeRefreshLayout のデータバインディング拡張。
 */
public class SwipeRefreshLayoutBindingAdapter {

    @BindingAdapter("onRefresh")
    public static void onRefreshListener(SwipeRefreshLayout swipeRefreshLayout, SwipeRefreshLayout.OnRefreshListener listener) {
        swipeRefreshLayout.setOnRefreshListener(listener);
    }
}
