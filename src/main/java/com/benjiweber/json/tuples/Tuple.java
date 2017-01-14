package com.benjiweber.json.tuples;

import com.benjiweber.typeref.MethodFinder;

import java.lang.reflect.Parameter;
import java.util.Objects;

public interface Tuple extends MethodFinder {
    default void checkParametersEnabled() {
        if(!MethodFinder.super.parameter(0).isNamePresent()) {
            throw new IllegalStateException("You need to compile with javac -parameters for parameter reflection to work; You also need java 8u60 or newer to use it with lambdas");
        }
    }

    default Parameter parameter(int n) {
        checkParametersEnabled();
        return MethodFinder.super.parameter(n);
    }
}
