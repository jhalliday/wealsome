/*
 * Copyright Red Hat (Java version), from WebAssembly.org (C version). License common to both.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.jhalliday.wealsome.example;

import com.github.jhalliday.wealsome.api.Module;
import com.github.jhalliday.wealsome.api.*;
import com.github.jhalliday.wealsome.generated.wasm_func_callback_t;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.ResourceScope;

import java.util.List;

/**
 * Java language translation of the C API usage example of the same name.
 * The C version is preserved inline as comments for comparison purposes.
 *
 * @see <a href="https://github.com/WebAssembly/wasm-c-api/blob/master/example/serialize.c">serialize.c</a>
 */
public class Serialize {

    // #include <stdio.h>
    // #include <stdlib.h>
    // #include <string.h>
    // #include <inttypes.h>
    //
    // #include "wasm.h"
    //
    // #define own
    //
    // // A function to be called from Wasm code.
    // own wasm_trap_t* hello_callback(
    //   const wasm_val_vec_t* args, wasm_val_vec_t* results
    // ) {
    //   printf("Calling back...\n");
    //   printf("> Hello World!\n");
    //   return NULL;
    // }
    private static class HelloCallback implements wasm_func_callback_t {
        @Override
        public MemoryAddress apply(MemoryAddress params, MemoryAddress results) {
            System.out.println("Calling back...");
            System.out.println("> Hello World!");
            return MemoryAddress.NULL;
        }
    }

    // int main(int argc, const char* argv[]) {
    public static void main(String[] args) throws Exception {
        ExamplesHelper.loadNativeImpl();
        ResourceScope resourceScope = ExamplesHelper.newResourceScope();

        //   // Initialize.
        //   printf("Initializing...\n");
        System.out.println("Initializing...");
        //   wasm_engine_t* engine = wasm_engine_new();
        Engine engine = new Engine();
        //   wasm_store_t* store = wasm_store_new(engine);
        Store store = new Store(engine);

        //   // Load binary.
        //   printf("Loading binary...\n");
        System.out.println("Loading binary...");
        //   FILE* file = fopen("serialize.wasm", "rb");
        //   if (!file) {
        //     printf("> Error loading module!\n");
        //     return 1;
        //   }
        //   fseek(file, 0L, SEEK_END);
        //   size_t file_size = ftell(file);
        //   fseek(file, 0L, SEEK_SET);
        byte[] fileContent = ExamplesHelper.readWasm("serialize.wasm");
        //   wasm_byte_vec_t binary;
        //   wasm_byte_vec_new_uninitialized(&binary, file_size);
        ByteVec binary = new ByteVec(resourceScope, fileContent);
        //   if (fread(binary.data, file_size, 1, file) != 1) {
        //     printf("> Error loading module!\n");
        //     return 1;
        //   }
        //   fclose(file);

        //   // Compile.
        //   printf("Compiling module...\n");
        System.out.println("Compiling module...");
        //   own wasm_module_t* module = wasm_module_new(store, &binary);
        Module module = new Module(store, binary);
        //   if (!module) {
        //     printf("> Error compiling module!\n");
        //     return 1;
        //   }

        //   wasm_byte_vec_delete(&binary);
        binary.close();

        //   // Serialize module.
        //   printf("Serializing module...\n");
        System.out.println("Serializing module...");
        //   own wasm_byte_vec_t serialized;
        //   wasm_module_serialize(module, &serialized);
        ByteVec serialized = module.serialize(resourceScope);

        //   wasm_module_delete(module);
        module.close();

        //   // Deserialize module.
        //   printf("Deserializing module...\n");
        System.out.println("Deserializing module...");
        //   own wasm_module_t* deserialized = wasm_module_deserialize(store, &serialized);
        Module deserialized = store.deserializeModule(serialized);
        //   if (!deserialized) {
        //     printf("> Error deserializing module!\n");
        //     return 1;
        //   }

        //   wasm_byte_vec_delete(&serialized);
        serialized.close();

        //   // Create external print functions.
        //   printf("Creating callback...\n");
        System.out.println("Creating callback...");
        //   own wasm_functype_t* hello_type = wasm_functype_new_0_0();
        FuncType helloType = FuncType.new_0_0(resourceScope);
        //   own wasm_func_t* hello_func =
        //     wasm_func_new(store, hello_type, hello_callback);
        Func helloFunc = new Func(store, helloType, new HelloCallback());

        //   wasm_functype_delete(hello_type);
        helloType.close();

        //   // Instantiate.
        //   printf("Instantiating deserialized module...\n");
        System.out.println("Instantiating deserialized module...");
        //   wasm_extern_t* externs[] = { wasm_func_as_extern(hello_func) };
        //   wasm_extern_vec_t imports = WASM_ARRAY_VEC(externs);
        ExternVec imports = new ExternVec(resourceScope, List.of(helloFunc));
        //   own wasm_instance_t* instance =
        //     wasm_instance_new(store, deserialized, &imports, NULL);
        Instance instance = new Instance(store, deserialized, imports);
        //   if (!instance) {
        //     printf("> Error instantiating module!\n");
        //     return 1;
        //   }

        //   wasm_func_delete(hello_func);
        helloFunc.close();

        //   // Extract export.
        //   printf("Extracting export...\n");
        System.out.println("Extracting export...");
        //   own wasm_extern_vec_t exports;
        ExternVec exports = new ExternVec(resourceScope);
        //   wasm_instance_exports(instance, &exports);
        instance.exports(exports);
        //   if (exports.size == 0) {
        //     printf("> Error accessing exports!\n");
        //     return 1;
        //   }
        if (exports.size() == 0) {
            System.out.println("> Error accessing exports!");
            return;
        }

        //   const wasm_func_t* run_func = wasm_extern_as_func(exports.data[0]);
        WasmFunc runFunc = exports.asFunc(0);
        //   if (run_func == NULL) {
        //     printf("> Error accessing export!\n");
        //     return 1;
        //   }
        //
        //   wasm_module_delete(deserialized);
        deserialized.close();
        //   wasm_instance_delete(instance);
        instance.close();

        //   // Call.
        //   printf("Calling export...\n");
        System.out.println("Calling export...");
        //   wasm_val_vec_t empty = WASM_EMPTY_VEC;
        ValVec params = new ValVec(resourceScope);
        ValVec results = new ValVec(resourceScope);
        //   if (wasm_func_call(run_func, &empty, &empty)) {
        //     printf("> Error calling function!\n");
        //     return 1;
        //   }
        runFunc.apply(params, results);

        params.close();
        results.close();

        //   wasm_extern_vec_delete(&exports);
        exports.close();

        //   // Shut down.
        //   printf("Shutting down...\n");
        System.out.println("Shutting down...");
        //   wasm_store_delete(store);
        store.close();
        //   wasm_engine_delete(engine);
        engine.close();

        results.close();

        //   // All done.
        //   printf("Done.\n");
        System.out.println("Done.");
        //   return 0;
        // }
    }
}
/*

(module
  (func $hello (import "" "hello"))
  (func (export "run") (call $hello))
)

expect output:

Initializing...
Loading binary...
Compiling module...
Serializing module...
Deserializing module...
Creating callback...
Instantiating deserialized module...
Extracting export...
Calling export...
Calling back...
> Hello World!
Shutting down...
Done.

 */