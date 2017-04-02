package com.katatoshi.mvvmexample.util.databinding;

import android.databinding.Observable;

import java8.util.function.BiConsumer;

/**
 * ラムダ式からリスナーを生成できる Observable.OnPropertyChangedCallback の実装。
 */
public class OnPropertyChangedCallback extends Observable.OnPropertyChangedCallback {

    public OnPropertyChangedCallback(BiConsumer<Observable, Integer> onPropertyChanged) {
        this.onPropertyChanged = onPropertyChanged;
    }

    private final BiConsumer<Observable, Integer> onPropertyChanged;

    @Override
    public void onPropertyChanged(Observable sender, int propertyId) {
        onPropertyChanged.accept(sender, propertyId);
    }
}
