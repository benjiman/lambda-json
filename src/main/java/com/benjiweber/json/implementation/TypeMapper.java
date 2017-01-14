package com.benjiweber.json.implementation;

import javax.json.JsonNumber;
import javax.json.JsonObject;
import javax.json.JsonString;
import javax.json.JsonValue;
import java.math.BigDecimal;
import java.math.BigInteger;

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
        return value.toString();
    }
}
