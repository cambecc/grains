#### _Grains_
... is a small Java framework for generating immutable and extensible objects.

1. Create an interface with getters:
```java
    @GrainSchema
    public interface Order {

        String getProduct();

        int getQuantity();
    }
```

2. Run the Grains Maven plugin (which processes anything annotated with `@GrainSchema`).

3. Start using the generated implementation:
```java
    OrderBuilder builder = OrderFactory.newBuilder();
    builder.setProduct("apples");
    builder.setQuantity(13);
    OrderGrain order = builder.build();
    
    System.out.println(order instanceof Order); // prints: true
    System.out.println(order.getProduct());     // prints: apples
    System.out.println(order.get("quantity"));  // prints: 13
    System.out.println(order.entrySet());       // prints: [product=apples, quantity=13]
```

[See the wiki for documentation](https://github.com/cambecc/grains/wiki), or continue reading the accelerated
introduction...

---

#### Additional details

The Grains Maven plugin generates source for both a builder...
```java
    public interface OrderBuilder implements Order, GrainBuilder {

        OrderBuilder setProduct(String product);

        OrderBuilder setQuantity(int quantity);

        OrderGrain build();
    }
```
... and an immutable version, called a _grain_:
```java
    public interface OrderGrain implements Order, Grain {

        OrderGrain withProduct(String product);

        OrderGrain withQuantity(int quantity);
    }
```

Once built, a grain can be modified using _with_ methods, creating new grains while leaving the original unchanged:
```java
    OrderGrain more = order.withQuantity(23);
    System.out.println(more.getQuantity());   // prints: 32
    System.out.println(order.getQuantity());  // prints: 13
```

Grains and their builders are maps:
```java
    System.out.println(order instanceof Map);    // prints: true
    System.out.println(order.get("product"));    // prints: apples
    System.out.println(order.get("quantity"));   // prints: 13

    System.out.println(order);                   // prints: {product=apples, quantity=13}
    System.out.println(order.keySet());          // prints: {product, quantity}
    System.out.println(order.values());          // prints: {apples, 13}

    System.out.println(builder instanceof Map);  // prints: true
```

They are _extensible_ if necessary:
```java
    builder.put("buyer", "bob");
    System.out.println(builder);         // prints: {product=apples, quantity=13, buyer=bob}
    builder.remove("buyer");
    System.out.println(builder);         // prints: {product=apples, quantity=13}

    order = order.with("buyer", "bob");
    System.out.println(order);           // prints: {product=apples, quantity=13, buyer=bob}
    order = order.without("buyer");
    System.out.println(order);           // prints: {product=apples, quantity=13}
```

But they perform like plain old Java objects, using fields to store properties defined by the original interface:
```java
    private static final class OrderGrainImpl implements OrderGrain, Serializable {
        private final String product;
        private final int quantity;

        public String getProduct() { return this.product; }
        public int getQuantity() { return this.quantity; }

        ...
    }
```

The _equals_ and _hashCode_ methods are well defined by the Map contract. All maps, including grains, are equal if
they have the same keys and values:
```java
    Map<String, Object> map = new HashMap<>();
    map.put("product", "apples");
    map.put("quantity", 13);
    System.out.println(map.equals(order));                  // prints: true
    System.out.println(map.hashCode() == order.hashCode()); // prints: true

    System.out.println(builder.equals(order));              // prints: true
    builder.setQuantity(3);
    System.out.println(builder.equals(order));              // prints: false
```

And Java serialization is supported natively:
```java
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    new ObjectOutputStream(out).writeObject(order);

    ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
    Object obj = new ObjectInputStream(in).readObject();

    System.out.println(obj instanceof OrderGrain);  // prints: true
    System.out.println(obj);                        // prints: {product=apples, quantity=13}
    System.out.println(order.equals(obj));          // prints: true
```

[Jackson](http://wiki.fasterxml.com/JacksonHome) data types are available by adding the `grains-jackson` dependency.
These data types can be used for all Jackson supported data formats, such as JSON, Smile, etc.
Example use:
```java
    ObjectMapper mapper = JacksonTools.newGrainsObjectMapper();

    String json = mapper.writeValueAsString(order);
    OrderGrain obj = mapper.readValue(json, OrderGrain.class);
    
    System.out.println(json);               // prints: {"product":"apples","quantity":13}
    System.out.println(order.equals(obj));  // prints: true
```

[MessagePack](http://msgpack.org) serialization is available by adding the `grains-msgpack` dependency. Example usage:
```java
    MessagePack msgpack = MessagePackTools.newGrainsMessagePack();

    byte[] data = msgpack.write(order);
    Object obj = msgpack.read(data, OrderGrain.class);

    System.out.println(obj instanceof OrderGrain);  // prints: true
```

[Kryo](http://code.google.com/p/kryo/) serialization is available by adding the `grains-kryo` dependency. Example
usage:
```java
    Kryo kryo = KryoTools.newGrainsKryo();

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    Output output = new Output(out);
    kryo.writeClassAndObject(output, order);
    output.close();

    Input input = new Input(new ByteArrayInputStream(out.toByteArray()));
    Object obj = kryo.readClassAndObject(input);
    input.close();

    System.out.println(obj instanceof OrderGrain);  // prints: true
```

Motivation
----------

The Grains framework is built around three key principles:

1. Java developers want to define their data model with Java.
2. Java developers want generated code to be both _visible_ and _readable_.
3. Immutability is a "best practice".

You write your data model as a set of Java interfaces and the Grains framework generates all the monotonous,
otherwise manual coding of getters, setters, equals, hashCode, builders, factories, serialization, etc.

Requirements
------------
* Java 7
* Maven 3

Usage
-----

1. Clone the project and run _mvn install_ (required because artifacts are not yet deployed to maven central).

2. Decide in which package your hand-written grain schema interfaces will reside, something like _com.acme.model_.

3. Configure Maven to pre-compile this package and all sub-packages during the _generate-sources_ phase:

    ```xml
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.1</version>
        <configuration>
            <source>1.7</source>
            <target>1.7</target>
        </configuration>
        <executions>
            <execution>
                <phase>generate-sources</phase>
                <goals><goal>compile</goal></goals>
                <configuration>
                    <includes>
                        <include>com/acme/model/**</include>  <!-- SPECIFY PACKAGE HERE -->
                    </includes>
                </configuration>
            </execution>
        </executions>
    </plugin>
    ```

3. Enable the _grains-plugin_ and bind it to the _generate-sources_ phase:

    ```xml
    <plugin>
        <groupId>net.nullschool</groupId>
        <artifactId>grains-plugin</artifactId>
        <version>0.9.0-SNAPSHOT</version>
        <executions>
            <execution>
                <phase>generate-sources</phase>
                <goals><goal>generate</goal></goals>
            </execution>
        </executions>
    </plugin>
    ```
    
4. Finally, add the _grains-core_ dependency to your project:

    ```xml
    <dependency>
        <groupId>net.nullschool</groupId>
        <artifactId>grains-core</artifactId>
        <version>0.9.0-SNAPSHOT</version>
    </dependency>
    ```

Done. Now any interface located under _com.acme.model_ and annotated with `@GrainSchema` will have a grain implementation
generated when _mvn compile_  is invoked. By default, all generated sources appear
in the _target/generated-sources/grains/com/acme/model_ directory.
