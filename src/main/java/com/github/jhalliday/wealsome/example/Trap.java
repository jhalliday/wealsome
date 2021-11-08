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
 * @see <a href="https://github.com/WebAssembly/wasm-c-api/blob/master/example/trap.c">trap.c</a>
 */
public class Trap {

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
    // own wasm_trap_t* fail_callback(
    //   void* env, const wasm_val_vec_t* args, wasm_val_vec_t* results
    // ) {
    //   printf("Calling back...\n");
    //   own wasm_name_t message;
    //   wasm_name_new_from_string_nt(&message, "callback abort");
    //   own wasm_trap_t* trap = wasm_trap_new((wasm_store_t*)env, &message);
    //   wasm_name_delete(&message);
    //   return trap;
    // }
    private static class FailCallback implements wasm_func_callback_t {
        @Override
        public MemoryAddress apply(MemoryAddress params, MemoryAddress results) {
            System.out.println("Calling back...");
            return MemoryAddress.NULL;
        }
    }

    // void print_frame(wasm_frame_t* frame) {
    //   printf("> %p @ 0x%zx = %"PRIu32".0x%zx\n",
    //     wasm_frame_instance(frame),
    //     wasm_frame_module_offset(frame),
    //     wasm_frame_func_index(frame),
    //     wasm_frame_func_offset(frame)
    //   );
    // }

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
        //   FILE* file = fopen("trap.wasm", "rb");
        //   if (!file) {
        //     printf("> Error loading module!\n");
        //     return 1;
        //   }
        //   fseek(file, 0L, SEEK_END);
        //   size_t file_size = ftell(file);
        //   fseek(file, 0L, SEEK_SET);
        byte[] fileContent = ExamplesHelper.readWasm("trap.wasm");
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

        //   // Create external print functions.
        //   printf("Creating callback...\n");
        System.out.println("Creating callback...");
        //   own wasm_functype_t* fail_type =
        //     wasm_functype_new_0_1(wasm_valtype_new_i32());
        FuncType failType = FuncType.new_0_1(resourceScope, ValType.I32);
        //   own wasm_func_t* fail_func =
        //     wasm_func_new_with_env(store, fail_type, fail_callback, store, NULL);
        Func failFunc = new Func(store, failType, new FailCallback());

        //   wasm_functype_delete(fail_type);
        failType.close();

        //   // Instantiate.
        //   printf("Instantiating module...\n");
        System.out.println("Instantiating module...");
        //   wasm_extern_t* externs[] = { wasm_func_as_extern(fail_func) };
        //   wasm_extern_vec_t imports = WASM_ARRAY_VEC(externs);
        ExternVec imports = new ExternVec(resourceScope, List.of(failFunc));
        //   own wasm_instance_t* instance =
        //     wasm_instance_new(store, module, &imports, NULL);
        Instance instance = new Instance(store, module, imports);
        //   if (!instance) {
        //     printf("> Error instantiating module!\n");
        //     return 1;
        //   }

        //   wasm_func_delete(fail_func);
        failFunc.close();

        //   // Extract export.
        //   printf("Extracting exports...\n");
        System.out.println("Extracting exports...");
        //   own wasm_extern_vec_t exports;
        ExternVec exports = new ExternVec(resourceScope);
        //   wasm_instance_exports(instance, &exports);
        instance.exports(exports);
        //   if (exports.size < 2) {
        //     printf("> Error accessing exports!\n");
        //     return 1;
        //   }
        if (exports.size() != 2) {
            System.out.println("> Error accessing exports!");
            return;
        }

        //   wasm_module_delete(module);
        module.close();
        //   wasm_instance_delete(instance);
        instance.close();

        //   // Call.
        //   for (int i = 0; i < 2; ++i) {
        for (int i = 0; i < 2; i++) {
            //     const wasm_func_t* func = wasm_extern_as_func(exports.data[i]);
            WasmFunc func = exports.asFunc(i);
            //     if (func == NULL) {
            //       printf("> Error accessing export!\n");
            //       return 1;
            //     }

            //     printf("Calling export %d...\n", i);
            System.out.printf("Calling export %d...\n", i);
            //     wasm_val_vec_t args = WASM_EMPTY_VEC;
            ValVec params = new ValVec(resourceScope);
            //     wasm_val_vec_t results = WASM_EMPTY_VEC;
            ValVec results = new ValVec(resourceScope, List.of(new UnVal(ValType.ANYREF, 0))); // TODO is the example broken?
            //     own wasm_trap_t* trap = wasm_func_call(func, &args, &results);
            func.apply(params, results);
            //     if (!trap) {
            //       printf("> Error calling function, expected trap!\n");
            //       return 1;
            //     }

            //     printf("Printing message...\n");
            System.out.println("Printing message...");
            //     own wasm_name_t message;
            //     wasm_trap_message(trap, &message);
            //     printf("> %s\n", message.data);
            System.out.println("");

            //     printf("Printing origin...\n");
            System.out.println("Printing origin...");
            //     own wasm_frame_t* frame = wasm_trap_origin(trap);
            //     if (frame) {
            //       print_frame(frame);
            //       wasm_frame_delete(frame);
            //     } else {
            //       printf("> Empty origin.\n");
            System.out.println("> Empty origin.");
            //     }
            //
            //     printf("Printing trace...\n");
            System.out.println("Printing trace...");
            //     own wasm_frame_vec_t trace;
            //     wasm_trap_trace(trap, &trace);
            //     if (trace.size > 0) {
            //       for (size_t i = 0; i < trace.size; ++i) {
            //         print_frame(trace.data[i]);
            //       }
            //     } else {
            //       printf("> Empty trace.\n");
            System.out.println("> Empty trace.");
            //     }

            //     wasm_frame_vec_delete(&trace);
            //     wasm_trap_delete(trap);
            //     wasm_name_delete(&message);

            params.close();
            results.close();
            //   }
        }

        //   wasm_extern_vec_delete(&exports);
        exports.close();

        //   // Shut down.
        //   printf("Shutting down...\n");
        System.out.println("Shutting down...");
        //   wasm_store_delete(store);
        store.close();
        //   wasm_engine_delete(engine);
        engine.close();

        resourceScope.close();

        //   // All done.
        //   printf("Done.\n");
        System.out.println("Done.");
        //   return 0;
        // }
    }
}
/*

(module
  (func $callback (import "" "callback") (result i32))
  (func (export "callback") (result i32) (call $callback))
  (func (export "unreachable") (result i32) (unreachable) (i32.const 1))
)

expect output:

Initializing...
Loading binary...
Compiling module...
Creating callback...
Instantiating module...
Extracting exports...
Calling export 0...
Printing message...
> wrong number of results provided
Printing origin...
> Empty origin.
Printing trace...
> Empty trace.
Calling export 1...
Printing message...
> wrong number of results provided
Printing origin...
> Empty origin.
Printing trace...
> Empty trace.
Shutting down...
Done.

 */