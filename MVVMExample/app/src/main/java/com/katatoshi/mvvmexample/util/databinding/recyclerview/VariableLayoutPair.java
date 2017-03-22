package com.katatoshi.mvvmexample.util.databinding.recyclerview;

/**
 * variableId と layoutId のペア。
 */
public class VariableLayoutPair {

    public VariableLayoutPair(int variableId, int layoutId) {
        this.variableId = variableId;
        this.layoutId = layoutId;
    }

    public final int variableId;

    public final int layoutId;
}
