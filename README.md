#### _Grains_
... is a small Java framework for generating immutable, extensible objects.

1. Create an interface with getters:
```java
    @GrainSchema
    public interface Order {

        String getProduct();

        int getQuantity();
    }
```

2. Run the Grains Maven plugin.

3. Use the generated _Factory_, _Builder_, and _Grain_ classes:
```java
    OrderBuilder builder = OrderFactory.newBuilder();
    builder.setProduct("apples");
    builder.setQuantity(13);
    OrderGrain order = builder.build();
    
    System.out.println(order instanceof Order);  // prints: true
    System.out.println(order.getProduct());      // prints: apples
    
    System.out.println(order instanceof Map);    // prints: true
    System.out.println(order.get("quantity"));   // prints: 13
    System.out.println(order.entrySet());        // prints: [product=apples, quantity=13]
    
    OrderGrain changed = order.withQuantity(9);  // immutable :)
    System.out.println(changed);                 // prints: {product=apples, quantity=9}
    System.out.println(order);                   // prints: {product=apples, quantity=13}
    
    changed = changed.with("notes", "shipped");  // extensible :)
    System.out.println(changed);                 // prints: {product=apples, quantity=9, notes=shipped}
```

[See the wiki for documentation](https://github.com/cambecc/grains/wiki), or continue reading the quickstart...

---

##### Something

A _grain_ is an immutable _Map<String, Object>_ that implements an interface of getters like the one shown
above. Because grains are maps, the _.equals_ and _.hashCode_ methods are well defined:

```java
    Map<String, Object> map = new HashMap<>();
    map.put("product", "apples");
    map.put("quantity", 13);
    System.out.println(order.equals(map));                  // prints: true
    System.out.println(order.hashCode() == map.hashCode()); // prints: true
```

Because grains are immutable, thread synchronization is not required when changing values, and the _.clone_ method
becomes superfluous.

In fact, using grains means not having to deal with methods inherited from Object because they are all taken care of.

##### Serialization

Native support for Java serialization:
```java
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    new ObjectOutputStream(out).writeObject(order);

    ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
    Object obj = new ObjectInputStream(in).readObject();

    System.out.println(obj instanceof OrderGrain);  // prints: true
    System.out.println(obj);                        // prints: {product=apples, quantity=13}
```

[Jackson](http://wiki.fasterxml.com/JacksonHome) support allows serialization to JSON, Smile, YAML, etc.:
```java
    ObjectMapper mapper = JacksonTools.newGrainsObjectMapper();  // in grains-jackson module

    String json = mapper.writeValueAsString(order);
    OrderGrain obj = mapper.readValue(json, OrderGrain.class);
    
    System.out.println(json);               // prints: {"product":"apples","quantity":13}
    System.out.println(order.equals(obj));  // prints: true
```

[MessagePack](http://msgpack.org) serialization support:
 available by adding the _grains-msgpack_ dependency:
```java
    MessagePack msgpack = MessagePackTools.newGrainsMessagePack();  // in grains-msgpack module

    byte[] data = msgpack.write(order);
    Object obj = msgpack.read(data, OrderGrain.class);

    System.out.println(obj instanceof OrderGrain);  // prints: true
    System.out.println(obj);                        // prints: {product=apples, quantity=13}
```

[Kryo](http://code.google.com/p/kryo/) serialization support:
```java
    Kryo kryo = KryoTools.newGrainsKryo();  // in grains-kryo module

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

1. Java developers want to define their object model with Java rather than some esoteric DSL in a text file.
2. Java developers want generated code to be both _visible_ and _readable_.
3. Immutability is a "best practice".

You write your object model as a set of Java interfaces and the Grains framework generates all the monotonous,
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
