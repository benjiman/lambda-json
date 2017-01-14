package com.benjiweber.json.tuples;

public interface TriTupleMapper<T,U,V,R> extends Tuple {
    R apply(T t, U u, V v);
}