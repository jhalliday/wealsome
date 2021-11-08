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
import com.github.jhalliday.wealsome.generated.wasm_val_t;
import com.github.jhalliday.wealsome.generated.wasm_val_vec_t;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;

import java.util.List;

/**
 * Java language translation of the C API usage example of the same name.
 * The C version is preserved inline as comments for comparison purposes.
 *
 * @see <a href="https://github.com/WebAssembly/wasm-c-api/blob/master/example/callback.c">callback.c</a>
 */
public class Callback {

    // #include <stdio.h>
    // #include <stdlib.h>
    // #include <string.h>
    // #include <inttypes.h>
    //
    // #include "wasm.h"
    //
    // #define own
    //
    // // Print a Wasm value
    // void wasm_val_print(wasm_val_t val) {
    //   switch (val.kind) {
    //     case WASM_I32: {
    //       printf("%" PRIu32, val.of.i32);
    //     } break;
    //     case WASM_I64: {
    //       printf("%" PRIu64, val.of.i64);
    //     } break;
    //     case WASM_F32: {
    //       printf("%f", val.of.f32);
    //     } break;
    //     case WASM_F64: {
    //       printf("%g", val.of.f64);
    //     } break;
    //     case WASM_ANYREF:
    //     case WASM_FUNCREF: {
    //       if (val.of.ref == NULL) {
    //         printf("null");
    //       } else {
    //         printf("ref(%p)", val.of.ref);
    //       }
    //     } break;
    //   }
    // }
    //
    // // A function to be called from Wasm code.
    // own wasm_trap_t* print_callback(
    //   const wasm_val_vec_t* args, wasm_val_vec_t* results
    // ) {
    private static class PrintCallback implements wasm_func_callback_t {
        @Override
        public MemoryAddress apply(MemoryAddress params, MemoryAddress results) {
            ResourceScope resourceScope = ExamplesHelper.newResourceScope();
            //   printf("Calling back...\n> ");
            System.out.println("Calling back...");
            //   wasm_val_print(args->data[0]);
            //   printf("\n");
            ValVec paramsValVec = new ValVec(resourceScope, params);
            System.out.println("> " + paramsValVec.read(0).value);

            //   wasm_val_copy(&results->data[0], &args->data[0]);
            WasmAPI.val_copy(
                    wasm_val_vec_t.data$get(results.asSegment(wasm_val_vec_t.sizeof(), resourceScope)),
                    wasm_val_vec_t.data$get(params.asSegment(wasm_val_vec_t.sizeof(), resourceScope)));
            resourceScope.close();
            //   return NULL;
            return MemoryAddress.NULL;
            // }
        }
    }

    // // A function closure.
    // own wasm_trap_t* closure_callback(
    //   void* env, const wasm_val_vec_t* args, wasm_val_vec_t* results
    // ) {
    private static class ClosureCallback implements wasm_func_callback_t {
        @Override
        public MemoryAddress apply(MemoryAddress params, MemoryAddress results) {
            ResourceScope resourceScope = ExamplesHelper.newResourceScope();
            //   int i = *(int*)env;
            //   printf("Calling back closure...\n");
            System.out.println("Calling back closure...");
            //   printf("> %d\n", i);
            //
            //   results->data[0].kind = WASM_I32;
            //   results->data[0].of.i32 = (int32_t)i;
            //   return NULL;
            // }

            MemorySegment val_vec_t = wasm_val_vec_t.ofAddress(results, resourceScope);
            System.out.println("TODO env");

            UnVal val = new UnVal(ValType.I32, 42);

            MemorySegment valSegment = wasm_val_t.ofAddress(wasm_val_vec_t.data$get(val_vec_t), resourceScope);
            wasm_val_t.kind$set(valSegment, (byte) val.valType.ordinal());
            MemorySegment slice = wasm_val_t.of$slice(valSegment);
            wasm_val_t.of.i32$set(slice, (int) val.value);

            // TODO env ptr, print.

            resourceScope.close();
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
        //   FILE* file = fopen("callback.wasm", "rb");
        //   if (!file) {
        //     printf("> Error loading module!\n");
        //     return 1;
        //   }
        //   fseek(file, 0L, SEEK_END);
        //   size_t file_size = ftell(file);
        //   fseek(file, 0L, SEEK_SET);
        byte[] fileContent = ExamplesHelper.readWasm("callback.wasm");
        //   wasm_byte_vec_t binary;
        //   wasm_byte_vec_new_uninitialized(&binary, file_size);
        ByteVec binary = new ByteVec(resourceScope, fileContent);
        //   if (fread(binary.data, file_size, 1, file) != 1) {
        //     printf("> Error loading module!\n");
        //     return 1;
        //   }
        //   fclose(file);
        //
        //   // Compile.
        //   printf("Compiling module...\n");
        System.out.println("Compiling module...");
        //   own wasm_module_t* module = wasm_module_new(store, &binary);
        Module module = new Module(store, binary);
        //   if (!module) {
        //     printf("> Error compiling module!\n");
        //     return 1;
        //   }
        //
        //   wasm_byte_vec_delete(&binary);
        binary.close();

        //   // Create external print functions.
        //   printf("Creating callback...\n");
        System.out.println("Creating callback...");
        //   own wasm_functype_t* print_type = wasm_functype_new_1_1(wasm_valtype_new_i32(), wasm_valtype_new_i32());
        FuncType printType = FuncType.new_1_1(resourceScope, ValType.I32, ValType.I32);
        //   own wasm_func_t* print_func = wasm_func_new(store, print_type, print_callback);
        Func printFunc = new Func(store, printType, new PrintCallback());

        //   int i = 42;
        //   own wasm_functype_t* closure_type = wasm_functype_new_0_1(wasm_valtype_new_i32());
        FuncType closureType = FuncType.new_0_1(resourceScope, ValType.I32);
        //   own wasm_func_t* closure_func = wasm_func_new_with_env(store, closure_type, closure_callback, &i, NULL);
        Func closureFunc = new Func(store, closureType, new ClosureCallback());

        //   wasm_functype_delete(print_type);
        printType.close();
        //   wasm_functype_delete(closure_type);
        closureType.close();

        //   // Instantiate.
        //   printf("Instantiating module...\n");
        System.out.println("Instantiating module...");
        //   wasm_extern_t* externs[] = {
        //     wasm_func_as_extern(print_func), wasm_func_as_extern(closure_func)
        //   };
        //   wasm_extern_vec_t imports = WASM_ARRAY_VEC(externs);
        ExternVec imports = new ExternVec(resourceScope, List.of(printFunc, closureFunc));
        //   own wasm_instance_t* instance =
        //     wasm_instance_new(store, module, &imports, NULL);
        Instance instance = new Instance(store, module, imports);
        //   if (!instance) {
        //     printf("> Error instantiating module!\n");
        //     return 1;
        //   }

        //   wasm_func_delete(print_func);
        printFunc.close();
        //   wasm_func_delete(closure_func);
        closureFunc.close();

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

        //   wasm_module_delete(module);
        module.close();
        //   wasm_instance_delete(instance);
        instance.close();

        //   // Call.
        //   printf("Calling export...\n");
        System.out.println("Calling export...");
        //   wasm_val_t as[2] = { WASM_I32_VAL(3), WASM_I32_VAL(4) };
        //   wasm_val_t rs[1] = { WASM_INIT_VAL };
        //   wasm_val_vec_t args = WASM_ARRAY_VEC(as);
        ValVec params = new ValVec(resourceScope, List.of(new UnVal(ValType.I32, 3), new UnVal(ValType.I32, 4)));
        //   wasm_val_vec_t results = WASM_ARRAY_VEC(rs);
        ValVec results = new ValVec(resourceScope, List.of(new UnVal(ValType.ANYREF, 0)));
        //   if (wasm_func_call(run_func, &args, &results)) {
        //     printf("> Error calling function!\n");
        //     return 1;
        //   }
        runFunc.apply(params, results);

        //   wasm_extern_vec_delete(&exports);
        exports.close();

        //   // Print result.
        //   printf("Printing result...\n");
        System.out.println("Printing result...");
        //   printf("> %u\n", rs[0].of.i32);
        System.out.println(results.read(0).value);

        params.close();
        results.close();

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
  (func $print (import "" "print") (param i32) (result i32))
  (func $closure (import "" "closure") (result i32))
  (func (export "run") (param $x i32) (param $y i32) (result i32)
    (i32.add
      (call $print (i32.add (local.get $x) (local.get $y)))
      (call $closure)
    )
  )
)

expect output:

Initializing...
Loading binary...
Compiling module...
Creating callback...
Instantiating module...
Extracting export...
Calling export...
Calling back...
> 7
Calling back closure...
> 42
Printing result...
> 49
Shutting down...
Done.

 */