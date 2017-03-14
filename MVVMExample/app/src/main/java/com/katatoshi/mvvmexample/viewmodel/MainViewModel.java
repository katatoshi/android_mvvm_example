package com.katatoshi.mvvmexample.viewmodel;

import android.databinding.ObservableField;

import com.katatoshi.mvvmexample.AppApplication;
import com.katatoshi.mvvmexample.model.MainModel;

import javax.inject.Inject;

/**
 * メインの ViewModel。
 */
public class MainViewModel {

    public MainViewModel(Delegate delegate) {
        AppApplication.getInstance().getComponent().inject(this);

        this.delegate = delegate;
    }

    @Inject
    MainModel mainModel;

    public final ObservableField<String> mainText = new ObservableField<>();

    /**
     * データを読み込みます。
     */
    public void load() {
        mainText.set(mainModel.getMainText());
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
