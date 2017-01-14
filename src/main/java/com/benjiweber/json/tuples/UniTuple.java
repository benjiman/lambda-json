package com.benjiweber.json.tuples;

public interface UniTuple<T> extends Tuple  {
    void apply(T t);
}