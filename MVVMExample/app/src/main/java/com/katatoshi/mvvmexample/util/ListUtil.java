package com.katatoshi.mvvmexample.util;

import android.support.annotation.NonNull;

import java.util.List;

/**
 * List のユーティリティ。
 */
public class ListUtil {

    /**
     * 一つ目のリストの要素を二つ目のリストの要素に置き換えます。
     *
     * @param dstList 置き換え先のリスト（状態が更新されます）
     * @param srcList 置き換え元のリスト（状態は更新されません）
     * @param <T>     要素の型
     */
    public static <T> void replace(@NonNull List<T> dstList, @NonNull List<T> srcList) {
        int dstListSize = dstList.size();
        int srcListSize = srcList.size();

        if (dstListSize < srcListSize) {
            for (int i = 0; i < srcListSize; i++) {
                if (i < dstListSize) {
                    dstList.set(i, srcList.get(i));
                } else {
                    dstList.add(srcList.get(i));
                }
            }
        } else {
            for (int i = 0; i < dstListSize; i++) {
                if (i < srcListSize) {
                    dstList.set(i, srcList.get(i));
                } else {
                    dstList.remove(srcListSize);
                }
            }
        }
    }
}
