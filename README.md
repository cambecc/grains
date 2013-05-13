Grains
======

"Grains" is a small Java framework for generating immutable, extensible data models.

Start with an interface:
```java
    @GrainSchema
    public interface Order {

        String getProduct();

        int getQuantity();
    }
```

Then run the Grains Maven plugin, which generates source for both a builder...
```java
    public interface OrderBuilder implements Order, GrainBuilder {

        OrderBuilder setProduct(String product);

        OrderBuilder setQuantity(int quantity);

        OrderGrain build();
    }
```
... and an immutable representation, called a _grain_:
```java
    public interface OrderGrain implements Order, Grain {

        OrderGrain withProduct(String product);

        OrderGrain withQuantity(int quantity);
    }
```

Then use the generated factory pattern to create new builders and new grains:
```java
    OrderBuilder builder = OrderFactory.newBuilder();
    builder.setProduct("apples");
    builder.setQuantity(13);

    OrderGrain order = builder.build();
    System.out.println(order.getProduct());   // prints: apples
    System.out.println(order.getQuantity());  // prints: 13
```

Once built, a grain can be modified using _with_ methods, creating new grains while leaving the original unchanged:
```java
    OrderGrain more = order.withQuantity(23);
    System.out.println(more.getQuantity());   // prints: 32
    System.out.println(order.getQuantity());  // prints: 13
```

Grains are maps (the builders are, too):
```java
    System.out.println(order instanceof Map);    // prints: true
    System.out.println(order.get("product"));    // prints: apples
    System.out.println(order.get("quantity"));   // prints: 13

    System.out.println(order);                   // prints: {product=apples, quantity=13}
    System.out.println(order.keySet());          // prints: {product, quantity}
    System.out.println(order.values());          // prints: {apples, 13}

    System.out.println(builder instanceof Map);  // prints: true
```

Grains and builders are _extensible_, just like maps:
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

Because the `equals` and `hashCode` methods are well defined for maps, they are by extension well defined for grains
and builders, too:
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

Motivation
----------

The Grains framework is built around three key principles:

1. Java developers want to define their data model with Java.
2. Java developers want generated code to be both _visible_ and _readable_.
3. Immutability is a "best practice".

You write your data model as a set of Java interfaces and the Grains framework generates all the monotonous,
otherwise manual coding of getters, setters, equals, hashCode, builders, factories, serialization, etc.
