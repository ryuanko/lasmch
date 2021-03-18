package com.lasmch.util;

@FunctionalInterface
public interface Transform<T, R> {

    R apply(T t);
}

