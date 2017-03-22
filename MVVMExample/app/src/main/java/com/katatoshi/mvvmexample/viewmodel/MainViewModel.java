package com.katatoshi.mvvmexample.viewmodel;

import android.databinding.Observable;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

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

    public final ObservableList<String> sampleList = new ObservableArrayList<>();

    /**
     * データを読み込みます。
     */
    public void load() {
        mainText.set(mainModel.getMainText());

        sampleList.add("apple");
        sampleList.add("banana");
        sampleList.add("grape");
        sampleList.add("cherry");
        sampleList.add("strawberry");
        sampleList.add("meron");
    }

    /**
     * メッセージを表示します。
     */
    public void showMessage() {
        delegate.showMessage("メインのテキストを変更");
    }

    /**
     * アイテムをクリックされたときのメッセージを表示します。
     *
     * @param viewModel アイテム
     */
    public void showClickMessage(String viewModel) {
        delegate.showClickMessage("clicked: " + viewModel);
    }


    /**
     * アイテムをロングクリックされたときのメッセージを表示します。
     *
     * @param viewModel アイテム
     */
    public void showLongClickMessage(String viewModel) {
        delegate.showLongClickMessage("long clicked: " + viewModel);
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

        void showClickMessage(String message);

        void showLongClickMessage(String message);
    }
    //endregion
}
