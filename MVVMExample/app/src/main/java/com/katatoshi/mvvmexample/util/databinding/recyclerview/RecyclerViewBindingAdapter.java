package com.katatoshi.mvvmexample.util.databinding.recyclerview;

import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import java.util.Collection;

/**
 * データバインディング対応 RecyclerViewAdapter 用の BindingAdapter。
 * <p>
 * 参考： https://github.com/radzio/android-data-binding-recyclerview
 */
public class RecyclerViewBindingAdapter {

    @BindingAdapter({"binder", "itemCollection"})
    public static <T> void setDataBindingRecyclerViewAttributes(RecyclerView recyclerView, VariableLayoutBinder binder, Collection<T> itemCollection) {
        SimpleRecyclerViewAdapter<T> adapter = new SimpleRecyclerViewAdapter<>(binder, itemCollection);
        recyclerView.setAdapter(adapter);
    }
}
