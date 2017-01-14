package com.benjiweber.json;

import com.benjiweber.json.implementation.JSONOperations;
import com.benjiweber.json.implementation.JsonBuilder;
import com.benjiweber.json.tuples.*;
import com.benjiweber.typeref.NamedValue;

import javax.json.Json;
import java.io.StringReader;
import java.util.Collections;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;


public interface JSON {

    static JSONOperations parse(String raw) {
        return parse(Collections.emptyList(), raw);
    }

    static JSONOperations parse(List<KeyAlias> aliases, String raw) {
        return new JSONOperations(
            Json.createReader(
                new StringReader(raw)
            ).readObject(),
            aliases
        );
    }

    interface JsonKey<T> extends NamedValue<T> {}

    interface BuildJson {
        JSON $(JsonKey... keys);
        JSON parse(String raw);
    }

    static BuildJson aliasing(KeyAlias... aliases) {
        return new BuildJson() {
            public JSON $(JsonKey... keys) { return JSON.$(asList(aliases), keys); }
            public JSON parse(String raw) { return JSON.parse(asList(aliases), raw); }
        };
    }

    static JSON $(JsonKey... keys) {
        return $(emptyList(), keys);
    }

    interface KeyAlias extends NamedValue<String> {}

    static JSON $(List<KeyAlias> aliases, JsonKey... keys) {
        return JsonBuilder.build(aliases, keys);
    }
    static <T> T[] $(T... values) {
        return values;
    }

    <T> void to(UniTuple<T> tuple);
    <T,U> void to(BiTuple<T,U> tuple);
    <T,U,V> void to(TriTuple<T,U,V> tuple);

    <T,R> R map(UniTupleMapper<T,R> tuple);
    <T,U,R> R map(BiTupleMapper<T,U,R> tuple);
    <T,U,V,R> R map(TriTupleMapper<T,U,V,R> tuple);
}
