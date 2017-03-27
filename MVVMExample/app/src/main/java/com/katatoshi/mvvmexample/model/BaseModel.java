package com.katatoshi.mvvmexample.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeSupport;

/**
 * Model のベースクラス。プロパティの変更通知、リスナーの追加、削除の仕組みを提供します。
 */
public abstract class BaseModel {

    private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertyChangeSupport.removePropertyChangeListener(listener);
    }

    protected void firePropertyChange(String propertyName) {
        propertyChangeSupport.firePropertyChange(propertyName, null, null);
    }

    public static abstract class PropertyChangeListener implements java.beans.PropertyChangeListener {

        public abstract void propertyChange(String propertyName);

        @Override
        public void propertyChange(PropertyChangeEvent evt) {
            propertyChange(evt.getPropertyName());
        }
    }
}
