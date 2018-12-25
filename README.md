# :electric_plug: Jacobin

[![jitpack][jitpack]][jitpack-url]
[![javadocs][javadocs]][javadocs-url]
[![tests][tests]][tests-url]
[![license][license]][license-url]

**Jacobin** is the ultimate binary reading and writing library. It provides high performance and easy-to-use 
binary `DataReader`s, `DataWriter`s and `FileEditor`s, a class that supports both reading and writing to a 
random access file.

## Features

- Primitive signed and unsigned reading and writing methods
- Supports parsing and writing UTF-8 strings
- Memory-efficient offset manipulation (by only keeping the needed byte buffers loaded) 
- Different implementations for each [endianness](https://en.wikipedia.org/wiki/Endianness) format
- Atomic offset setting, only reads and writes require synchronization
- `Readable` and `Writable` interfaces
- 64-bit (`long`) offsets

## Getting started

You can install Jacobin using [Maven](https://maven.apache.org/) by adding the [JitPack](https://jitpack.io/) repository to your `pom.xml` file:

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

You can find instructions for other package managers such as Gradle on the [JitPack project page](https://jitpack.io/#hugmanrique/Jacobin).

## Creating a `DataReader`

Jacobin works by wrapping Java streams. Let's try to read data from a `ByteArrayInputStream`:

```java
ByteArrayInputStream stream = new ByteArrayInputStream(
    new byte[] { 0x78, 0x56, 0x34, 0x12 }
);
```

Now, let's create a `DataReader` instance:

```java
LittleEndianDataReader reader = new LittleEndianDataReader(stream);
```

That's it! You can now use any endianness-dependent methods such as `#readUInt32`:

```java
long value = reader.readUInt32(); // 0x12345678
```

Let's say we want to go back to the beginning, skip two bytes and read an `int16`:

```java
reader.reset();
reader.skip(2);

int otherValue = reader.readInt16(); // 0x1234
```

You can read more about all the available methods on the [`LittleEndianDataReader`](https://jitpack.io/com/github/hugmanrique/Jacobin/rewrite-a96d4eac17-1/javadoc/me/hugmanrique/jacobin/reader/LittleEndianDataReader.html) 
and [`BigEndianDataReader`]() javadocs pages.

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