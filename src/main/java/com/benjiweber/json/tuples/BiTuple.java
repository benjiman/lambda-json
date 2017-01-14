package com.benjiweber.json.tuples;

public interface BiTuple<T,U> extends Tuple  {
    void apply(T t, U u);
}