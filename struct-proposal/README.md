# Structures

Structures are files that auto-generate a Java class file (we can possibly extend it to other languages too) which can read from a `ByteStreamReader` and write to a `ByteStreamWriter`.

Structures are meant to be type-safe and allow you to define the mutability of each field.

Additionally, even though Jacobin prioritizes sequential reading, there's syntax to perform absolute reads.

## `.struct` files

Let's create a `Pokemon.struct` file under `src/main/structs`:

```
package me.hugmanrique.pokedata;

String[12] name;
mut uint8 = 0;

// Skip 2 padding bytes
skip 2;

uint16 weight = 0;
```

## Older proposals

```
package me.hugmanrique.pokedata;

let name: String[12];
let mut height: uint8 = 0;

// Skip 2 padding bytes
skip 2;

let mut weight: uint16 = 0;
```