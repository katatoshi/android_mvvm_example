package com.katatoshi.mvvmexample.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.katatoshi.mvvmexample.BR;

import java8.util.Objects;

/**
 * メインの Model。
 */
public class MainModel extends BaseObservable {

    //region メインのテキストのプロパティ。
    private String mainText = "Hello World!";

    @Bindable
    public String getMainText() {
        return mainText;
    }

    public void setMainText(String mainText) {
        if (Objects.equals(this.mainText, mainText)) {
            return;
        }

        this.mainText = mainText;
        notifyPropertyChanged(BR.mainText);
    }
    //endregion
}
