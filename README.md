# hamcrest-json-matchers

Hamcrest Matchers for matching
 
* JSON Strings
* GSON JsonObjects
* Vert.x JsonObjects
* json.org JSONObjects

## Installation

### Maven

    <repositories>
        <repository>
            <id>ossrh</id>
            <url>https://oss.sonatype.org/content/repositories/snapshots</url>
            <snapshots>
                <enabled>true</enabled>
                <updatePolicy>always</updatePolicy>
            </snapshots>
        </repository>
    </repositories>
	
    <dependency>
        <groupId>de.crunc</groupId>
        <artifactId>hamcrest-json-matchers</artifactId>
        <version>0.2-SNAPSHOT</version>
        <scope>test</scope>
    </dependency>

## Usage

### JSON strings

```java

    import org.junit.Test;

    import static de.crunc.hamcrest.json.JsonMatchers.isJsonArray;
    import static de.crunc.hamcrest.json.JsonMatchers.isJsonObject;
    import static org.hamcrest.MatcherAssert.assertThat;

    // ...

    @Test
    public void testString() {

        String json = "{\"someObject\":{\"foo\":31},\"someArray\":[1,2,3]}";

        assertThat(json, isJsonObject()
                .prop("someObject", isJsonObject()
                        .prop("foo", 31))
                .prop("someArray", isJsonArray()
                        .item(1)
                        .item(2)
                        .item(3)));
    }
```

### GSON

```java

    import org.junit.Test;

    import static de.crunc.hamcrest.json.JsonMatchers.isJsonArray;
    import static de.crunc.hamcrest.json.JsonMatchers.isJsonObject;
    import static org.hamcrest.MatcherAssert.assertThat;

    // ...

    @Test
    public void testGson() {

        com.google.gson.JsonObject someObject = new com.google.gson.JsonObject();
        someObject.add("foo", new com.google.gson.JsonPrimitive(31));

        com.google.gson.JsonArray someArray = new com.google.gson.JsonArray();
        someArray.add(new com.google.gson.JsonPrimitive(1));
        someArray.add(new com.google.gson.JsonPrimitive(2));
        someArray.add(new com.google.gson.JsonPrimitive(3));

        com.google.gson.JsonObject json = new com.google.gson.JsonObject();
        json.add("someObject", someObject);
        json.add("someArray", someArray);

        assertThat(json, isJsonObject()
                .prop("someObject", isJsonObject()
                        .prop("foo", 31))
                .prop("someArray", isJsonArray()
                        .item(1)
                        .item(2)
                        .item(3)));
    }
```

### Vert.x

```java

    import org.junit.Test;

    import static de.crunc.hamcrest.json.JsonMatchers.isJsonArray;
    import static de.crunc.hamcrest.json.JsonMatchers.isJsonObject;
    import static org.hamcrest.MatcherAssert.assertThat;

    // ...

    @Test
    public void testVertx() {

        io.vertx.core.json.JsonObject someObject = new io.vertx.core.json.JsonObject();
        someObject.putNumber("foo", 31);

        io.vertx.core.json.JsonArray someArray = new io.vertx.core.json.JsonArray();
        someArray.addNumber(1);
        someArray.addNumber(2);
        someArray.addNumber(3);

        io.vertx.core.json.JsonObject json = new io.vertx.core.json.JsonObject();
        json.putObject("someObject", someObject);
        json.putArray("someArray", someArray);

        assertThat(json, isJsonObject()
                .prop("someObject", isJsonObject()
                        .prop("foo", 31))
                .prop("someArray", isJsonArray()
                        .item(1)
                        .item(2)
                        .item(3)));
    }
```

### json.org

```java

    import org.junit.Test;

    import static de.crunc.hamcrest.json.JsonMatchers.isJsonArray;
    import static de.crunc.hamcrest.json.JsonMatchers.isJsonObject;
    import static org.hamcrest.MatcherAssert.assertThat;

    // ...

    @Test
    public void testJsonOrg() {

        org.json.JSONObject someObject = new org.json.JSONObject()
                .put("foo", 31);

        org.json.JSONArray someArray = new org.json.JSONArray();
        someArray.put(1);
        someArray.put(2);
        someArray.put(3);

        org.json.JSONObject json = new org.json.JSONObject()
                .put("someObject", someObject)
                .put("someArray", someArray);

        assertThat(json, isJsonObject()
                .prop("someObject", isJsonObject()
                        .prop("foo", 31))
                .prop("someArray", isJsonArray()
                        .item(1)
                        .item(2)
                        .item(3)));
    }
```

## Compatibility

Tested with all combinations of the following versions

### GSON

* 2.3.1
* 2.3
* 2.2.4
* 2.2.3
* 2.2.2
* 2.2.1
* 2.2
* 2.1
* 2.0

### Vert.x

* 2.1.5
* 2.1.4
* 2.1.3
* 2.1.2
* 2.1.1
* 2.1
* 2.0.2-final
* 2.0.1-final
* 2.0.0-final

### json.org

* 20141113
* 20140107
* 20131018
* 20090211
* 20080701
* 20070829
