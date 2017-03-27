package com.katatoshi.mvvmexample.util.databinding;

import android.databinding.BindingAdapter;
import android.support.v4.widget.SwipeRefreshLayout;

/**
 * DataBinding のカスタムセッター。
 */
public class BindingAdapterMethod {

    @BindingAdapter("onRefresh")
    public static void onRefreshListener(SwipeRefreshLayout swipeRefreshLayout, SwipeRefreshLayout.OnRefreshListener listener) {
        swipeRefreshLayout.setOnRefreshListener(listener);
    }
}
