# lambda-json

Proof of concept using lambda functions to extract values from JSON

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

String result = JSON.parse(input)
    .aliasing(num -> "favourite number")
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