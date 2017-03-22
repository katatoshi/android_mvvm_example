package com.katatoshi.mvvmexample.util.databinding.recyclerview;

import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import java.util.Collection;

/**
 * データバインディング対応 RecyclerViewAdapter 用のユーティリティ。
 */
public class DataBindingRecyclerViewUtil {

    public static <T> void bind(RecyclerView recyclerView, @NonNull Collection<T> itemCollection, int variableId, @LayoutRes int layoutId, @Nullable OnItemClickListener<T> onItemClickListener, @Nullable OnItemLongClickListener<T> onItemLongClickListener) {
        SimpleRecyclerViewAdapter<T> adapter = new SimpleRecyclerViewAdapter<>(itemCollection, new VariableLayoutPair(variableId, layoutId), onItemClickListener, onItemLongClickListener);
        recyclerView.setAdapter(adapter);
    }

    public static <T> void bind(RecyclerView recyclerView, Collection<T> itemCollection, int variableId, @LayoutRes int layoutId, @Nullable OnItemClickListener<T> onItemClickListener) {
        bind(recyclerView, itemCollection, variableId, layoutId, onItemClickListener, null);
    }

    public static <T> void bind(RecyclerView recyclerView, Collection<T> itemCollection, int variableId, @LayoutRes int layoutId) {
        bind(recyclerView, itemCollection, variableId, layoutId, null, null);
    }
}
