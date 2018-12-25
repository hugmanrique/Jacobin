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

## Reading binary data

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

You can read more about all the available methods on the [`LittleEndianDataReader`](https://jitpack.io/com/github/hugmanrique/Jacobin/master-SNAPSHOT/javadoc/me/hugmanrique/jacobin/reader/LittleEndianDataReader.html) 
and [`BigEndianDataReader`](https://jitpack.io/com/github/hugmanrique/Jacobin/master-SNAPSHOT/javadoc/me/hugmanrique/jacobin/reader/BigEndianDataReader.html) javadoc pages.

> You should always follow general [Java I/O](https://docs.oracle.com/javase/tutorial/essential/io/) good practices such as closing your streams when they are no longer needed. `DataReader` implements `Closeable`. 😊

## Writing binary data

Jacobin also provides an `OutputStream` wrapper called `DataWriter`:

```java
ByteArrayOutputStream stream = new ByteArrayOutputStream();
BigEndianDataWriter writer = new BigEndianDataWriter(stream);
```

Let's try to write two `int64`s:

```java
writer.writeInt64(0x123456789ABCDEF0L);
writer.writeInt64(0x9ABCDEF012345678L);
```

You can check the data was written correctly by converting the `OutputStream` to a byte array:

```java
byte[] writtenBytes = stream.toByteArray(); // { 0x12, 0x34, ..., 0x56, 0x78 }
```

You can read more about all the available methods on the [`LittleEndianDataWriter`](https://jitpack.io/com/github/hugmanrique/Jacobin/master-SNAPSHOT/javadoc/me/hugmanrique/jacobin/writer/LittleEndianDataWriter.html) 
and [`BigEndianDataWriter`](https://jitpack.io/com/github/hugmanrique/Jacobin/master-SNAPSHOT/javadoc/me/hugmanrique/jacobin/writer/BigEndianDataWriter.html) javadoc pages.


## Working with random access files

A random access file behaves like a large array of bytes stored in the file system. 
A `FileEditor` provides a `Readable` and `Writable` implementation that supports both
reading and writing to a random access file.

```java
File myAwesomeFile = Paths.get("foo/bar.dat").toFile();

FileEditor editor = new LittleEndianFileEditor(myAwesomeFile);
```

Now, let's try to move the offset to the eighth byte (starting from `0`) and write the bytes of an array in that position:

```java
byte[] importantData = new byte[] { 0x12, 0x34, 0x56 };

editor.setOffset(8);
editor.write(importantData);
```

Jacobin tries to follow the same semantics as the Java I/O streams API, so you are probably already familiar with most of them 😎

You can read more about the [`LittleEndianFileEditor`](https://jitpack.io/com/github/hugmanrique/Jacobin/master-SNAPSHOT/javadoc/me/hugmanrique/jacobin/editor/LittleEndianFileEditor.html) 
and [`BigEndianFileEditor`](https://jitpack.io/com/github/hugmanrique/Jacobin/master-SNAPSHOT/javadoc/me/hugmanrique/jacobin/editor/BigEndianFileEditor.html) classes on their javadoc pages.

Make sure to checkout the [Javadocs overview page][javadocs-url] to see a list of all the available classes and utilities.

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