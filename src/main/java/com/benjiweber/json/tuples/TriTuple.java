package com.benjiweber.json.tuples;

public interface TriTuple<T,U,V> extends Tuple {
    void apply(T t, U u, V v);
}