package com.benjiweber.json;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.*;

public class JSONTest {

    @Test
    public void simple_example() {
        String input = "{ \"id\": 1111, \"name\": \"Kat\" }";

        String result = JSON.parse(input)
            .map((id, name) ->
                id + "_" + name
            );

        assertEquals("1111_Kat", result);
    }

    @Test
    public void aliasing() {
        String input = "{ \"id\": 1111, \"name\": \"Kat\", \"favourite number\": 10}";

        String result = JSON.parse(input)
            .aliasing(num -> "favourite number")
            .map((id, name, num) ->
                  id + "_" + name + "_" + num
            );

        assertEquals("1111_Kat_10", result);
    }


    @Test
    public void simple_typed_extraction() {
        String input = "{ \"id\": 10 }";

        int result = JSON.parse(input)
            .map((Integer id) ->
                    id + 5
            );

        assertEquals(15, result);
    }

    @Test
    public void simple_array_extraction() {
        String input = "{ \"ids\": [ 1111, 2222, 3333 ] } ";

        List<Integer> result = JSON.parse(input)
            .map((Integer[] ids) -> asList(ids));

        assertEquals(asList(1111, 2222, 3333), result);
    }

    @Test
    public void string_array_extraction() {
        String input = "{ \"greetings\": [ \"hello\", \"world\" ] } ";

        List<String> result = JSON.parse(input)
            .map((String[] greetings) -> asList(greetings));

        assertEquals(asList("hello", "world"), result);
    }

    @Test
    public void nested_array_extraction() {
        String input = "{ \"people\": [ { \"name\": \"benji\" } , { \"name\": \"bob\" } ] } ";

        List<String> result = JSON.parse(input)
            .map((JSON[] people) ->
                Stream.of(people)
                    .map(person ->
                        person.map((String name) -> name)
                    ).collect(toList())
            );

        assertEquals(asList("benji", "bob"), result);
    }

    @Test
    public void extract_typed() {
        String input = "{ \"id\": 1111, \"name\": \"Kat\", \"favourite number\": 10}";
        String result = JSON.parse(input)
            .aliasing(num -> "favourite number")
            .map((Integer id, String name, Integer num) ->
                    (id + 5) + "_" + name + "_" + (num + 5)
            );

        assertEquals("1116_Kat_15", result);
    }

    @Test
    public void nested_example() {
        String input = "{ \"id\": 1111, \"names\": { \"first\": \"benji\", \"last\": \"weber\" } }";
        String result = JSON.parse(input)
                .map((String id, JSON names) ->
                        id + "_" + names.map((first, last) -> first + "_" + last)
                );

        assertEquals("1111_benji_weber", result);
    }

    @Test
    public void extract_typed_floating_point() {
        String input = "{ \"id\": 1111, \"name\": \"Kat\", \"favourite number\": 10}";
        String result = JSON.parse(input)
            .aliasing(num -> "favourite number")
            .map((Double id, String name, Double num) ->
                    (id + 5) + "_" + name + "_" + (num + 5)
            );

        assertEquals("1116.0_Kat_15.0", result);
    }

    @Test
    public void map_single_value() {
        String greeting =
            JSON.parse("{ \"hello\": \"world\" }")
                .map(hello -> hello.toString());

        assertEquals("world", greeting);
    }

    @Test
    public void extract_single_value() {
        JSON.parse("{ \"hello\": \"world\" }")
            .to(hello -> assertEquals(hello, "world"));
    }

    @Test
    public void map_two_values() {
        String greeting =
                JSON.parse("{ \"hello\": \"world\", \"goodbye\": \"order\" }")
                        .map((hello, goodbye) -> hello.toString() + " " + goodbye.toString());

        assertEquals("world order", greeting);
    }

    @Test
    public void extract_two_values() {
        JSON.parse("{ \"hello\": \"world\", \"goodbye\": \"order\" }")
            .to((hello, goodbye) -> {
                assertEquals("world", hello);
                assertEquals("order", goodbye);
            });
    }

    @Test
    public void map_three_values() {
        String greeting =
            JSON.parse("{ \"hello\": \"world\", \"goodbye\": \"order\", \"still\": \"waiting\" }")
                .map((hello, goodbye, still) -> hello.toString() + " " + goodbye.toString() + " " + still);

        assertEquals("world order waiting", greeting);
    }

    @Test
    public void extract_three_values() {
        JSON.parse("{ \"hello\": \"world\", \"goodbye\": \"order\", \"still\": \"waiting\" }")
            .to((hello, goodbye, still) -> {
                assertEquals("world", hello);
                assertEquals("order", goodbye);
                assertEquals("waiting", still);
            });
    }

    public static <T> T[] array(T... ts) {
        return ts;
    }

}