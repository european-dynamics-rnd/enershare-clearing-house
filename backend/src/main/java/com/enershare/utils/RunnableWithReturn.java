package com.enershare.utils;

@FunctionalInterface
public interface RunnableWithReturn<T> {
    T run();
}
