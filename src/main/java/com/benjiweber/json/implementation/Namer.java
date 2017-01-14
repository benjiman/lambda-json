package com.benjiweber.json.implementation;

import com.benjiweber.json.JSON;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toMap;

public class Namer {

    private final Map<String, String> aliases;
    public Namer(List<JSON.KeyAlias> aliases) {
        this.aliases = aliases.stream().collect(toMap(JSON.KeyAlias::name, JSON.KeyAlias::value));
    }

    public String name(String name) {
        return aliases.getOrDefault(name, name);
    }
}
