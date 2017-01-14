package com.benjiweber.json.implementation;

import com.benjiweber.json.JSON;
import com.benjiweber.json.tuples.*;

import javax.json.JsonObject;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

public class JSONOperations implements JSON {

    private final JsonObject json;
    private Map<String, String> aliases;

    public JSONOperations(JsonObject json) {
        this(json, emptyList());
    }

    public JSONOperations(JsonObject json, List<KeyAlias> aliases) {
        this.json = json;
        this.aliases = aliases.stream().collect(Collectors.toMap(KeyAlias::name, KeyAlias::value));
    }

    public JSONOperations aliasing(KeyAlias... aliases) {
        return new JSONOperations(json, asList(aliases));
    }

    public <T> void to(UniTuple<T> tuple) {
        tuple.apply((T)value(tuple,0));
    }
    public <T,U> void to(BiTuple<T,U> tuple) {
        tuple.apply((T)value(tuple,0), (U)value(tuple,1));
    }
    public <T,U,V> void to(TriTuple<T,U,V> tuple) {
        tuple.apply((T)value(tuple,0), (U)value(tuple,1), (V) value(tuple,2));
    }
    public <T,R> R map(UniTupleMapper<T,R> tuple) {
        return tuple.apply((T)value(tuple, 0));
    }
    public <T,U,R> R map(BiTupleMapper<T,U,R> tuple) {
        return tuple.apply((T)value(tuple, 0), (U)value(tuple, 1));
    }
    public <T,U,V,R> R map(TriTupleMapper<T,U,V,R> tuple) {
        return tuple.apply((T)value(tuple, 0), (U)value(tuple, 1), (V)value(tuple, 2));
    }

    private Object value(Tuple tuple, int n) {
        return TypeMapper.valueOf(json.get(name(tuple, n)), type(tuple, n));
    }

    private String name(Tuple tuple, int n) {
        return aliased(tuple.parameter(n).getName());
    }

    private Class<?> type(Tuple tuple, int n) {
        return tuple.parameter(n).getType();
    }

    private String aliased(String name) {
        return aliases.getOrDefault(name, name);
    }
}
