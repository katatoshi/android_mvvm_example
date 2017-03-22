package com.katatoshi.mvvmexample.util.databinding.recyclerview;

/**
 * T 型の ViewModel からなる RecyclerView の各アイテムの LongClickListener。
 */
public interface OnItemLongClickListener<T> {

    void onLongClick(T item);
}
