package com.katatoshi.mvvmexample.model;

import java8.util.function.Consumer;

/**
 * ラムダ式からリスナーを生成できる BaseModel.PropertyChangeListener の実装です。
 */
public class PropertyChangeListener extends BaseModel.PropertyChangeListener {

    public PropertyChangeListener(Consumer<String> propertyChange) {
        this.propertyChange = propertyChange;
    }

    private final Consumer<String> propertyChange;

    @Override
    public void propertyChange(String propertyName) {
        propertyChange.accept(propertyName);
    }
}
