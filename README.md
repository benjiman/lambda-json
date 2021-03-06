# lambda-json

Exploring an idea of using Java lambda functions to read and write JSON

## Writing JSON

```java
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
```

## Reading JSON

```java
String input = "{ \"id\": 1111, \"name\": \"Kat\" }";

String result = JSON.parse(input)
    .map((id, name) ->
        id + "_" + name
    );

assertEquals("1111_Kat", result);
```

Where the json keys are not valid java identifiers we can alias them

```java
String input = "{ \"id\": 1111, \"name\": \"Kat\", \"favourite number\": 10}";

String result = JSON.aliasing(num -> "favourite number")
    .parse(input)
    .map((id, name, num) ->
          id + "_" + name + "_" + num
    );

assertEquals("1111_Kat_10", result);
```

Can also specify the type of the values

```java
String input = "{ \"id\": 10 }";

int result = JSON.parse(input)
    .map((Integer id) ->
        id + 5
    );

assertEquals(15, result);

```

and hence extract nested values

```java

String input = "{ \"id\": 1111, \"names\": { \"first\": \"benji\", \"last\": \"weber\" } }";
String result = JSON.parse(input)
    .map((String id, JSON names) ->
        id + "_" + names.map((first, last) -> first + "_" + last)
    );

assertEquals("1111_benji_weber", result);

```

extracting arrays is possible too

```java
String input = "{ \"ids\": [ 1111, 2222, 3333 ] } ";

List<Integer> result = JSON.parse(input)
    .map((Integer[] ids) -> asList(ids));

assertEquals(asList(1111, 2222, 3333), result);
```

as are nested objects in arrays
```java
String input = "{ \"people\": [ { \"name\": \"benji\" } , { \"name\": \"bob\" } ] } ";

List<String> result = JSON.parse(input)
    .map((JSON[] people) ->
        Stream.of(people)
            .map(person ->
                person.map((String name) -> name)
            ).collect(toList())
    );

assertEquals(asList("benji", "bob"), result);
```
