package com.katatoshi.mvvmexample.viewmodel;

import android.databinding.ObservableField;

/**
 * メインの ViewModel。
 */
public class MainViewModel {

    public MainViewModel(Delegate delegate) {
        this.delegate = delegate;
    }

    public final ObservableField<String> mainText = new ObservableField<>();

    /**
     * データを読み込みます。
     */
    public void load() {
        mainText.set("Hello World!");
    }

    /**
     * メッセージを表示します。
     */
    public void showMessage() {
        delegate.showMessage("Replace with your own action");
    }


    //region Activity に移譲するメソッドたち。
    private final Delegate delegate;

    public interface Delegate {
        void showMessage(String message);
    }
    //endregion
}
