package com.katatoshi.mvvmexample.viewmodel;

import android.databinding.Observable;
import android.databinding.ObservableField;

import com.katatoshi.mvvmexample.AppApplication;
import com.katatoshi.mvvmexample.BR;
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
        delegate.showMessage("メインのテキストを変更");
    }

    /**
     * メインのテキストを変更します。
     */
    public void changeMainText() {
        mainModel.setMainText("メインのテキストが変更されました。");
    }


    //region Model の変更通知を観測するコールバック。
    private Observable.OnPropertyChangedCallback onPropertyChangedCallback = new Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(Observable sender, int propertyId) {
            // switch (propertyId) で case BR.mainText とすると Android Studio 上でなぜか Constant expression required とエラーがでる（定数のはずなのに）。
            // ビルドはできるので無視して switch を使っても問題ないとも考えられるが、常にエラー表示となるのは紛らわしいのでやめておく。
            // Kotlin の when 式なら定数でなくてもよいので何の問題もない。
            if (propertyId == BR.mainText) {
                mainText.set(mainModel.getMainText());
            }
        }
    };

    public void addOnPropertyChangedCallback() {
        mainModel.addOnPropertyChangedCallback(onPropertyChangedCallback);
    }

    public void removeOnPropertyChangedCallback() {
        mainModel.removeOnPropertyChangedCallback(onPropertyChangedCallback);
    }
    //endregion


    //region Activity に移譲するメソッドたち。
    private final Delegate delegate;

    public interface Delegate {
        void showMessage(String message);
    }
    //endregion
}
