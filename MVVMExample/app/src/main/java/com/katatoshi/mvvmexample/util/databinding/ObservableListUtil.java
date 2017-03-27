package com.katatoshi.mvvmexample.util.databinding;

import android.databinding.ObservableList;

import java.util.List;

import java8.util.function.Function;

/**
 * ObservableList のユーティリティ。
 */
public class ObservableListUtil {

    /**
     * ObservableList （先導するリスト）の変更に追従するようなコールバック。追従するリストと追従するリストへの関数を与えて使用します。
     *
     * @param <T> 先導するリストの要素の型
     * @param <U> 追従するリストの要素の型
     */
    public static class FollowingCallback<T, U> extends ObservableList.OnListChangedCallback<ObservableList<T>> {

        /**
         * 追従するリストと追従するリストへの関数からコールバックを生成します。
         *
         * @param follower 追従するリスト
         * @param mapper 先導するリストの要素から追従するリストの要素への関数
         */
        public FollowingCallback(List<U> follower, Function<T, U> mapper) {
            this.follower = follower;
            this.mapper = mapper;
        }

        private final List<U> follower;

        private final Function<T, U> mapper;

        @Override
        public void onChanged(ObservableList<T> sender) {
            // 呼ばれない。
        }

        @Override
        public void onItemRangeChanged(ObservableList<T> sender, int positionStart, int itemCount) {
            for (int i = positionStart; i < positionStart + itemCount; i++) {
                follower.set(i, mapper.apply(sender.get(i)));
            }
        }

        @Override
        public void onItemRangeInserted(ObservableList<T> sender, int positionStart, int itemCount) {
            for (int i = positionStart; i < positionStart + itemCount; i++) {
                follower.add(i, mapper.apply(sender.get(i)));
            }
        }

        @Override
        public void onItemRangeMoved(ObservableList<T> sender, int fromPosition, int toPosition, int itemCount) {
            // 呼ばれない。
        }

        @Override
        public void onItemRangeRemoved(ObservableList<T> sender, int positionStart, int itemCount) {
            for (int i = positionStart; i < positionStart + itemCount; i++) {
                follower.remove(positionStart);
            }
        }
    }
}
