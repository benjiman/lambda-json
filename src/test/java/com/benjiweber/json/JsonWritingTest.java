package com.benjiweber.json;

import org.junit.Test;

import static com.benjiweber.json.JSON.$;
import static org.junit.Assert.assertEquals;

public class JsonWritingTest {

    @Test
    public void example_writing_json() {
        JSON json = $(
            id -> 1111,
            name -> "benji",
            nums -> $(10, 11, 12)
        );

        assertEquals(
            "{\"id\":1111,\"name\":\"benji\",\"nums\":[10,11,12]}",
            json.toString()
        );
    }

    @Test
    public void example_writing_json_string_array() {
        JSON json = $(
            id -> 1111,
            name -> "benji",
            nums -> $("10", "11", "12")
        );

        assertEquals(
            "{\"id\":1111,\"name\":\"benji\",\"nums\":[\"10\",\"11\",\"12\"]}",
            json.toString()
        );
    }


    @Test
    public void example_writing_json_nested_object() {
        JSON json = $(
            people -> $(
                $(
                    name -> "benji",
                    num -> 10,
                    likes -> $("star", "trek", 2)
                ),
                $(
                    name -> "bertie",
                    num -> 4,
                    likes -> $("jam", "bread")
                )
            )
        );

        assertEquals(
            "{\"people\":[{\"name\":\"benji\",\"num\":10,\"likes\":[\"star\",\"trek\",2]},{\"name\":\"bertie\",\"num\":4,\"likes\":[\"jam\",\"bread\"]}]}",
            json.toString()
        );
    }

    @Test
    public void aliasing() {
        JSON json = JSON.aliasing(nums -> "favourite numbers").$(
            id -> 1111,
            name -> "benji",
            nums -> $(10, 11, 12)
        );

        assertEquals(
            "{\"id\":1111,\"name\":\"benji\",\"favourite numbers\":[10,11,12]}",
            json.toString()
        );
    }

}
