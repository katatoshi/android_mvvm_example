package com.katatoshi.mvvmexample.model;

import android.databinding.BaseObservable;

/**
 * メインの Model。
 */
public class MainModel extends BaseObservable {

    //region メインのテキストのプロパティ。
    private String mainText = "Hello World!";

    public String getMainText() {
        return mainText;
    }
    //endregion
}
