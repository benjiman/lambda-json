package com.benjiweber.json.tuples;

public interface BiTupleMapper<T,U,R> extends Tuple {
    R apply(T t, U u);
}