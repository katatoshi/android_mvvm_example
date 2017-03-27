package com.katatoshi.mvvmexample.util;

import java8.util.function.Consumer;

public class Either<L, R> {

    public static <L, R> Either<L, R> left(L left) {
        if (left == null) {
            throw new NullPointerException();
        }

        return new Either<>(left, null);
    }

    public static <L, R> Either<L, R> right(R right) {
        if (right == null) {
            throw new NullPointerException();
        }

        return new Either<>(null, right);
    }

    private Either(L left, R right) {
        this.left = left;
        this.right = right;
    }

    private final L left;

    private final R right;

    public void ifLeft(Consumer<? super L> action) {
        if (left != null) {
            action.accept(left);
        }
    }

    public void ifRight(Consumer<? super R> action) {
        if (right != null) {
            action.accept(right);
        }
    }

    public void ifLeftOrRight(Consumer<? super L> leftAction, Consumer<? super R> rightAction) {
        if (left != null) {
            leftAction.accept(left);
        } else {
            rightAction.accept(right);
        }
    }
}
