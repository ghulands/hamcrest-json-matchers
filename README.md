# hamcrest-json-matchers

Hamcrest Matchers for matching JSON Strings, GSON Objects, Vertx JsonObjects.

## Installation

### Maven

	<repositories>
		<repository>
			<id>hamcrest-json-matchers-mvn-repo</id>
			<url>https://raw.github.com/Crunc/hamcrest-json-matchers/mvn-repo/</url>
			<snapshots>
				<enabled>true</enabled>
				<updatePolicy>always</updatePolicy>
			</snapshots>
		</repository>
	</repositories>
	
    <dependency>
        <groupId>de.crunc</groupId>
        <artifactId>hamcrest-json-matchers</artifactId>
        <version>0.1-SNAPSHOT</version>
        <scope>test</scope>
    </dependency>

## Usage

### JSON strings

```java
import static de.crunc.hamcrest.json.JsonMatchers.isJsonObject;

// ...

@Test
public void testJsonStringEquals() {

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
import static de.crunc.hamcrest.json.JsonMatchers.isJsonObject;

// ...

@Test
public void testJsonStringEquals() {

    JsonObject someObject = new JsonObject();
    someObject.add("foo", new JsonPrimitive(31));

    JsonArray someArray = new JsonArray();
    someArray.add(new JsonPrimitive(1));
    someArray.add(new JsonPrimitive(2));
    someArray.add(new JsonPrimitive(3));

    JsonObject json = new JsonObject();
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