### _Grains_

... is a small Java framework that helps you make immutable, thread-safe, versionable objects.
Spend less time on boring boilerplate code and more time solving problems.

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
    
    changed = changed.with("RMA", "9928");       // extensible and versionable :)
    System.out.println(changed);                 // prints: {product=apples, quantity=9, RMA=9928}
```

---------------------------------------------------------------------------------------------------

#### Serialization

[MessagePack](http://msgpack.org) serialization (with the _grains-msgpack_ library):
```java
    MessagePack msgpack = MessagePackTools.newGrainsMessagePack();

    byte[] data = msgpack.write(order);
    OrderGrain unpacked = msgpack.read(data, OrderGrain.class);

    System.out.println(unpacked.equals(order));  // prints: true
```

Native support for Java serialization:
```java
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    new ObjectOutputStream(out).writeObject(order);

    ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
    Object read = new ObjectInputStream(in).readObject();

    System.out.println(read.equals(order));  // prints: true
```

---------------------------------------------------------------------------------------------------

#### Setup

_The Grains framework ([published on Maven Central](http://search.maven.org/#search|ga|1|g%3A%22net.nullschool%22%20grains))
requires Java 7 or greater, and Maven 2.2.1 or greater._

1. Create a new package that will contain your object model, for example: _com.acme.model_

2. Configure your POM to pre-compile this package during the _generate-sources_ phase:

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
                        <include>com/acme/model/**</include>
                    </includes>
                </configuration>
            </execution>
        </executions>
    </plugin>
    ```

3. Configure the _grains-plugin_ to run during the _generate-sources_ phase:

    ```xml
    <plugin>
        <groupId>net.nullschool</groupId>
        <artifactId>grains-plugin</artifactId>
        <version>0.8.0</version>
        <executions>
            <execution>
                <phase>generate-sources</phase>
                <goals><goal>generate</goal></goals>
            </execution>
        </executions>
    </plugin>
    ```

4. Add a dependency on _grains-core_:

    ```xml
    <dependency>
        <groupId>net.nullschool</groupId>
        <artifactId>grains-core</artifactId>
        <version>0.8.0</version>
    </dependency>
    ```

5. _(optional)_ Add a dependency on the serialization library of your choice, such as:

    ```xml
    <dependency>
        <groupId>net.nullschool</groupId>
        <artifactId>grains-msgpack</artifactId>
        <version>0.8.0</version>
    </dependency>
    ```

Done! Any interface in _com.acme.model_ annotated with _@GrainSchema_ will have a grain
implementation generated when _mvn compile_ is invoked. By default, all generated sources appear in
the _target/generated-sources/grains/com/acme/model_ directory.

[See the wiki for more details](https://github.com/cambecc/grains/wiki).

#### Acknowledgements

[Clojure's defrecord macro](http://clojure.org/datatypes) provided the main inspiration for grains.

_Grains uses [Semantic Versioning](http://semver.org/)_
