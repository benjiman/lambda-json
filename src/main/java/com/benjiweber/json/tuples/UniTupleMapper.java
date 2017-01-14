package com.benjiweber.json.tuples;

public interface UniTupleMapper<T,R> extends Tuple  {
    R apply(T t);
}