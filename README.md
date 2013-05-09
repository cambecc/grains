Grains
======

"Grains" is a small framework that generates an immutable implementation of your data model. You write your data
model as a set of Java interfaces and the Grains framework generates all the monotonous, otherwise manual
coding of getters, setters, equals, hashCode, builders, factories, serialization, etc.

Grains is built around three key principles:

1. Java developers want to define their data model with Java.
2. Java developers want generated code to be both _visible_ and _readable_.
3. Immutability is a "best practice".

Instant Tutorial
----------------

Start with an interface you write that describes your object, and annotate it as a "grain schema":
```java
    @GrainSchema
    public interface Order {

        String getProduct();

        int getQuantity();
    }
```

Run a Maven plugin, which generates a builder pattern for your interface:
```java
    public interface OrderBuilder implements Order, GrainBuilder {

        OrderBuilder setProduct(String product);

        OrderBuilder setQuantity(int quantity);
    }
```

An immutable implementation of your interface is also generated:
```java
    public interface OrderGrain implements Order, Grain {

        OrderGrain withProduct(String product);

        OrderGrain withQuantity(int quantity);
    }
```

Use the generated factory to create a new builder:
```java
    OrderBuilder builder = OrderFactory.builder();
    builder.setProduct("apples");
    builder.setQuantity(13);
```

The builder builds a _grain_, i.e., an immutable instance of your interface:
```java
    OrderGrain order = builder.build();
    System.out.println(order.getProduct());  // prints: apples
    System.out.println(order.getQuantity()); // prints: 13
```

Once built, a grain can be modified using _with_ methods, creating distinct new grains yet leaving the original
unchanged:
```java
    OrderGrain more = order.withQuantity(23);
    System.out.println(more.getQuantity());  // prints: 32
    System.out.println(order.getQuantity()); // prints: 13
```

Grains are maps (the builders are, too):
```java
    System.out.println(order);          // prints: {product=apples, quantity=13}
    System.out.println(order.keySet()); // prints: {product, quantity}
    System.out.println(order.values()); // prints: {apples, 13}

    System.out.println(order.get("product")); // prints: apples
```

You can put arbitrary items into any grain or builder, or remove them:
```java
    builder.put("buyer", "bob");
    System.out.println(builder);  // prints: {product=apples, quantity=13, buyer=bob}
    builder.remove("buyer");
    System.out.println(builder);  // prints: {product=apples, quantity=13}

    order = order.with("buyer", "bob");
    System.out.println(order);  // prints: {product=apples, quantity=13, buyer=bob}
    order = order.without("buyer");
    System.out.println(order);  // prints: {product=apples, quantity=13}
```

The `equals` and `hashCode` methods are well defined for maps, and so by extension are well defined for grains and
builders, too:
```java
    Map<String, Object> map = new HashMap<>();
    map.put("product", "apples");
    map.put("quantity", 13);
    System.out.println(map.equals(order));                  // prints: true
    System.out.println(map.hashCode() == order.hashCode()); // prints: true

    System.out.println(builder.equals(order));  // prints: true
    builder.setQuantity(3);
    System.out.println(builder.equals(order));  // prints: false
```
