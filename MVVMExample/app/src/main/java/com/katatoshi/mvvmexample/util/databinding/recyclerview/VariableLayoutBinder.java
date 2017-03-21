package com.katatoshi.mvvmexample.util.databinding.recyclerview;

/**
 * variableId と layoutId を紐づけるひと。
 */
public class VariableLayoutBinder {

    public VariableLayoutBinder(int variableId, int layoutId) {
        this.variableId = variableId;
        this.layoutId = layoutId;
    }

    public final int variableId;

    public final int layoutId;
}
