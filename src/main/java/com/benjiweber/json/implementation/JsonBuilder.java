package com.benjiweber.json.implementation;

import com.benjiweber.json.JSON;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
import java.util.List;
import java.util.stream.Stream;

public class JsonBuilder {
    public static JSON build(List<JSON.KeyAlias> aliases, JSON.JsonKey... keys) {
        Namer namer = new Namer(aliases);
        JsonObjectBuilder json = Json.createObjectBuilder();

        Stream.of(keys)
                .forEach(key -> {
                    if (key.value() instanceof String) json.add(namer.name(key.name()), (String)key.value());
                    else if (key.value() instanceof Integer) json.add(namer.name(key.name()), (Integer)key.value());
                    else if (key.value() instanceof Double) json.add(namer.name(key.name()), (Double)key.value());
                    else if (key.value() instanceof JSONOperations) json.add(namer.name(key.name()), (((JSONOperations)key.value()).unwrap()));
                    else if (key.value().getClass().isArray()) {
                        JsonArrayBuilder array = Json.createArrayBuilder();
                        Stream.of((Object[]) key.value())
                                .forEach(item -> {
                                    if (item instanceof String) array.add(item.toString());
                                    else if (item instanceof Integer) array.add((Integer)item);
                                    else if (item instanceof Double) array.add((Double)item);
                                    else if (item instanceof JSONOperations) array.add(((JSONOperations)item).unwrap());
                                });
                        json.add(namer.name(key.name()), array);
                    }
                });

        return new JSONOperations(json.build());
    }
}
