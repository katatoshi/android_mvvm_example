package com.katatoshi.mvvmexample.util.databinding.recyclerview;

/**
 * T 型の ViewModel からなる RecyclerView の各アイテムの ClickListener。
 */
public interface OnItemClickListener<T> {

    void onClick(T item);
}
