# :electric_plug: Jacobin

[![jitpack][jitpack]][jitpack-url]
[![javadocs][javadocs]][javadocs-url]
[![tests][tests]][tests-url]
[![license][license]][license-url]

Jacobin provides high performance binary streams reader and writer implementations, as well as bidirectional stream implementations.

## Features

- Supports efficient offset setting (by only keeping the needed byte buffers in memory)
- Has different implementations for each [endianness](https://en.wikipedia.org/wiki/Endianness) type
- Easy to make stream accesses and writes thread-safe
- Uses the [Builder pattern](https://en.wikipedia.org/wiki/Builder_pattern) to create the reader, writers and bidirectional streams

## Getting started

Install Jacobin using [Maven](https://maven.apache.org/) by adding the JitPack repository to your `pom.xml` file:

```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>
```

Next, add the `Jacobin` dependency:

```xml
<dependency>
    <groupId>com.github.hugmanrique</groupId>
    <artifactId>Jacobin</artifactId>
    <version>master-SNAPSHOT</version>
</dependency>
```

You will need to have Java 8 version 45 or later (older versions _might_ work).

## Creating a ByteStreamReader

Let's get started by creating a little-endian `ByteStreamReader`:

```java
ByteStreamReader reader = new ByteStreamReaderBuilder()
                .stream(new byte[] { 0x34, 0x12 })
                .build();
```

In this case we used the `byte[]` stream method, which will internally create a `ByteArrayInputStream`. Jacobin will use the native [endianness](https://en.wikipedia.org/wiki/Endianness) by default (which in [Intel and AMD modern CPUs is little-endian](https://en.wikipedia.org/wiki/Endianness#Current_architectures)), but you can change this behaviour by calling `#order(ByteOrder)`.

That's it! We can now call any method available in `ByteStreamReader` e.g.:

```java
try {
    reader.readInt16(); // will be 0x1234
} catch (IOException e) {
    e.printStackTrace();
}
```

## Creating a ByteStreamWriter

TODO

## Creating an InOutByteStream

TODO

Check out the [Javadocs][javadocs-url] to see a list of all the available classes and methods.

## License

[MIT](LICENSE) &copy; [Hugo Manrique](https://hugmanrique.me)

[jitpack]: https://jitpack.io/v/hugmanrique/Jacobin.svg
[jitpack-url]: https://jitpack.io/#hugmanrique/Jacobin
[javadocs]: https://img.shields.io/badge/javadocs-master--SNAPSHOT-green.svg
[javadocs-url]: https://jitpack.io/com/github/hugmanrique/Jacobin/master-SNAPSHOT/javadoc/
[tests]: https://img.shields.io/travis/hugmanrique/Jacobin/master.svg
[tests-url]: https://travis-ci.org/hugmanrique/Jacobin
[license]: https://img.shields.io/github/license/hugmanrique/Jacobin.svg
[license-url]: LICENSE