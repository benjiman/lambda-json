package com.benjiweber.json;

import com.benjiweber.json.implementation.JSONOperations;
import com.benjiweber.json.tuples.*;
import com.benjiweber.typeref.NamedValue;

import javax.json.*;
import java.io.StringReader;


public interface JSON {

    static JSONOperations parse(String raw) {
        return new JSONOperations(
            Json.createReader(
                new StringReader(raw)
            ).readObject()
        );
    }

    interface KeyAlias extends NamedValue<String> {}
    JSONOperations aliasing(KeyAlias... aliases);

    <T> void to(UniTuple<T> tuple);
    <T,U> void to(BiTuple<T,U> tuple);
    <T,U,V> void to(TriTuple<T,U,V> tuple);

    <T,R> R map(UniTupleMapper<T,R> tuple);
    <T,U,R> R map(BiTupleMapper<T,U,R> tuple);
    <T,U,V,R> R map(TriTupleMapper<T,U,V,R> tuple);
}
