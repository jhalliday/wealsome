## Wealsome: An experimental integration of WebAssembly and Java, using Project Panama.

Do you want to run a [webassembly](https://webassembly.org/) module from Java? Perhaps we can help with that...

Do you want to do it in a robust, well-tested, feature-complete way?  Ahh, perhaps not then.

This is an early stage experimental approach to access a native wasm implementation from the JVM.

It is somewhat similar in function to the [bluejekyll](https://github.com/bluejekyll/wasmtime-java) or [kawamuray](https://github.com/kawamuray/wasmtime-java) integrations for wasmtime
 and the [wasmerio](https://github.com/wasmerio/wasmer-java) integration for [wasmer](https://wasmer.io/),
but where they all use JNI, we use [Project Panama](https://jdk.java.net/panama/).

It does not serve the same function as [Asmble](https://github.com/cretz/asmble) or [GraalVM](https://www.graalvm.org/reference-manual/wasm/) which both run wasm code on the JVM by cross-compiling it to bytecode,
nor is it for the reverse process of running Java on a wasm engine as [TeaVM](http://teavm.org/), [JWebAssembly](https://github.com/i-net-software/JWebAssembly), [CheerpJ](https://leaningtech.com/cheerpj/) or [Bytecoder](https://github.com/mirkosertic/Bytecoder) do.

### Setup

Note that most webassembly implementations, the C API that the spec group defines for accessing them, and Project Panama are
all still under development, so there is little chance this will work with anything but the **specific versions**
given below. Even then, the wealsome code is neither feature complete nor well tested. It may be a suitable starting place for your own experiments, but that's about all.

Still want to try it? Then you'll need some tools...

**jextract** is required for code generation.

Where JNI uses javac -h to generate C stubs from Java code, the panama approach is the reverse:
jextract reads a .h file and generates Java code to access it.
jextract is available as part of the [Project Panama Early-Access Builds.](https://jdk.java.net/panama/)
We used build 17-panama+3-167 (2021/5/18).

**JDK 17** is needed to run the existing code.

Or rather more accurately, you need a JDK recent enough to have the panama jdk.incubator.foreign module that jextract generates code to target.
It's safest to use the same JDK you got jextract from to ensure compatibility, but at present a stock [OpenJDK 17](https://jdk.java.net/17/) also works.

**wasmtime**

Any non-browser webassembly implementation that supports the [c-api](https://github.com/WebAssembly/wasm-c-api) should work,
but we've only tested [wasmtime 0.30.0](https://github.com/bytecodealliance/wasmtime/releases/tag/v0.30.0). Get the c-api bundle, which has the header file and the library binary.

**examples** of wasm modules to run (optional)

The wasmtime c-api bundle doesn't include the c-api examples,
but you can get them from the [wasm-c-api](https://github.com/WebAssembly/wasm-c-api/tree/c9d31284651b975f05ac27cee0bab1377560b87e/example) repository.

## Build Steps

1. Edit the `pom.xml` file to set the paths for `jextract.path` and `wasmtime.path` properties to the places you put the tools.

2. Optionally, edit `ExampleHelper.java` to set the path to the examples if you wish to use them.

3. Run `mvn clean exec:exec@wasm` to have [maven](http://maven.apache.org/) invoke jextract,
which will read the `include/wasm.h` file from the c-api bundle and generate Java files into `target/generated-sources`directory.

4. Compile the project with `mvn compile`

## Running Examples

The wasm c-api provides some example C programs showing how the C API works.
These have been translated, in a fairly literal way, to Java, preserving the C
version as comments for illustrative purposes. The Java programs in the wealsome `examples` package
need to be able to access both the pamana support code and the wasm native library,
so run them with additional java flags:

`--add-modules=jdk.incubator.foreign --enable-native-access=ALL-UNNAMED -Djava.library.path=/path/to/wasmtime-c-api/lib`

Or use `mvn exec:exec@example` which will manage the arguments for you.

## Logging

Edit `logback.xml` to change the log level - try `ALL` or `TRACE` instead of `INFO` if you'd like to see more.
Note that the wasmtime library also has logging - set environment variable `RUST_LOG=info`

## Issues

### Missing components
Some of the c-api examples are not ported to Java, because they don't currently work even from C when used with the wasmtime implementation.
The C API spec is only partially implemented by the ([rust](https://www.rust-lang.org/)) wasmtime engine at this time.
Many wasm_thing_same methods used by the examples are missing.

Some further interesting quirks arise as a result of the additional language in the chain.
Functions implemented in the .h file but not the rust code are still available to C clients,
because their C toolchain compiles them into the client binary rather than linking to implementations in the wasmtime library that's built from the rust toolchain.
However, they are not available to non-C clients using the C API, because e.g. jextract generates accessors but not implementation for them, assuming the implemtnation will be provided by the native library.
This is arguably a fault in the wasmtime build, but since it works fine from C, perhaps also arguably not.

### Memory management

Memory management is painful. Wealsome uses AutoClosable and ResourceScope to do a lot of the heavy lifting,
but careful attention must still be paid to ensuring the Java and native code agree on memory ownership and lifetime.
Cases where memory ownership crosses from one to the other are particulatly tricky, as panama will actively try to prevent leaks by closing
(releasing) things it allocated, which is Not Good when the native side has taken ownership and expects to handle the release itself.
Some c-api examples also use stack-allocated structs, which isn't an option in Java.

Detecting and debugging native memory leaks may be facilitited by use of [Address Sanitizer](https://github.com/google/sanitizers/wiki/AddressSanitizer) (asan), a valgrind-like tool for instrumenting memory use.
To track memory across both the JVM and the wasm engine, both must be built with asan support.

For the JVM, building [jdk 17](https://github.com/openjdk/jdk/tree/jdk-17+35) (you may want the [panama repo](https://github.com/openjdk/panama-foreign)) from source:

`configure --enable-asan`

For the [wasmtime](https://github.com/bytecodealliance/wasmtime/) build:
1. use the rust [nightly channel](https://rust-lang.github.io/rustup/concepts/channels.html), as asan support isn't in the mainline yet.
2. `RUSTFLAGS="-Zsanitizer=leak" cargo build`

At runtime, set environment variable to [configure asan](https://github.com/google/sanitizers/wiki/AddressSanitizerFlags) e.g. `ASAN_OPTIONS="detect_leaks=1 log_path=myfile" java ...`
You may also benefit from jvm options `-XX:+PreserveFramePointer -XX:+DumpPerfMapAtExit` as asan doesn't understand the JIT and won't give stack frame names for Java code, only the JVM native code and the rust wasm code.
The asan leak logs can be patched with the code location information from the JVM log written by DumpPerfMapAtExit, making them much more useful.

## Roadmap and Contributing

This project is currently suspended until the ecosystem matures further.
In particular, our use cases require running wasm code that can call back to Java functions.
In the now withdrawn JSR-223 scripting API, an embedded scripting language (practically always javascript) could have exposure to Java objects and methods via bind().
Equivalent functionality is impractically difficult for wasm code at present, but the situation should improve with the [Interface Types Proposal](https://github.com/WebAssembly/interface-types/blob/main/proposals/interface-types/Explainer.md).
Once wasm has that and panama is out of incubator, we may revisit the work.
Meanwhile don't expect much progress or a quick response to questions, but feel free to fork it for your own purposes.