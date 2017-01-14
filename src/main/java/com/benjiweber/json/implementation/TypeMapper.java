package com.benjiweber.json.implementation;

import com.benjiweber.json.JSON;

import javax.json.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

class TypeMapper {
    public static Object valueOf(JsonValue value, Class<?> type) {
        if (value == null) return value;
        if (value.getValueType() == JsonValue.ValueType.NUMBER) {
            if (type == Double.class) return ((JsonNumber) value).doubleValue();
            if (type == Integer.class) return ((JsonNumber) value).intValue();
            if (type == BigInteger.class) return ((JsonNumber) value).bigIntegerValue();
            if (type == BigDecimal.class) return ((JsonNumber) value).bigDecimalValue();
        }
        if (value.getValueType() == JsonValue.ValueType.STRING) return ((JsonString) value).getString();
        if (value.getValueType() == JsonValue.ValueType.OBJECT) return new JSONOperations((JsonObject) value);
        if (value.getValueType() == JsonValue.ValueType.ARRAY) {
            if (type.getComponentType().isAssignableFrom(Integer.class)) return ((JsonArray) value).getValuesAs(JsonNumber.class).stream().map(JsonNumber::intValue).collect(toList()).toArray(new Integer[0]);
            if (type.getComponentType().isAssignableFrom(JSON.class)) return ((JsonArray) value).getValuesAs(JsonObject.class).stream().map(JSONOperations::new).collect(toList()).toArray(new JSONOperations[0]);
            if (type.getComponentType().isAssignableFrom(String.class)) return ((JsonArray) value).getValuesAs(JsonString.class).stream().map(JsonString::getString).collect(toList()).toArray(new String[0]);
        }
        return value.toString();
    }
}
