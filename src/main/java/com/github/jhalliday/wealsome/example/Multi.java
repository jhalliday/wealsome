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
 * @see <a href="https://github.com/WebAssembly/wasm-c-api/blob/master/example/multi.c">multi.c</a>
 */
public class Multi {

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
    // own wasm_trap_t* callback(
    //   const wasm_val_vec_t* args, wasm_val_vec_t* results
    // ) {
    private static class Callback implements wasm_func_callback_t {
        @Override
        public MemoryAddress apply(MemoryAddress params, MemoryAddress results) {
            ResourceScope resourceScope = ExamplesHelper.newResourceScope();
            //   printf("Calling back...\n> ");
            System.out.println("Calling back...");
            ValVec paramsValVec = new ValVec(resourceScope, params);
            //   printf("> %"PRIu32" %"PRIu64" %"PRIu64" %"PRIu32"\n",
            //     args->data[0].of.i32, args->data[1].of.i64,
            //     args->data[2].of.i64, args->data[3].of.i32);
            System.out.println(">> " + paramsValVec.read(0).value + " " + paramsValVec.read(1).value
                    + " " + paramsValVec.read(2).value + " " + paramsValVec.read(3).value);
            //   printf("\n");
            System.out.println();

            MemorySegment paramsSegment = wasm_val_vec_t.data$get(wasm_val_vec_t.ofAddress(params, resourceScope))
                    .asSegment(wasm_val_t.sizeof() * 4, resourceScope);
            MemorySegment resultsSegment = wasm_val_vec_t.data$get(wasm_val_vec_t.ofAddress(results, resourceScope))
                    .asSegment(wasm_val_t.sizeof() * 4, resourceScope);
            //   wasm_val_copy(&results->data[0], &args->data[3]);
            WasmAPI.val_copy(resultsSegment, paramsSegment.asSlice(wasm_val_t.sizeof() * 3));
            //   wasm_val_copy(&results->data[1], &args->data[1]);
            WasmAPI.val_copy(resultsSegment.asSlice(wasm_val_t.sizeof()), paramsSegment.asSlice(wasm_val_t.sizeof()));
            //   wasm_val_copy(&results->data[2], &args->data[2]);
            WasmAPI.val_copy(resultsSegment.asSlice(wasm_val_t.sizeof() * 2), paramsSegment.asSlice(wasm_val_t.sizeof() * 2));
            //   wasm_val_copy(&results->data[3], &args->data[0]);
            WasmAPI.val_copy(resultsSegment.asSlice(wasm_val_t.sizeof() * 3), paramsSegment);
            //   return NULL;
            // }
            return MemoryAddress.NULL;
        }
    }

    // // A function closure.
    // own wasm_trap_t* closure_callback(
    //   void* env, const wasm_val_vec_t* args, wasm_val_vec_t* results
    // ) {
    //   int i = *(int*)env;
    //   printf("Calling back closure...\n");
    //   printf("> %d\n", i);
    //
    //   results->data[0].kind = WASM_I32;
    //   results->data[0].of.i32 = (int32_t)i;
    //   return NULL;
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
        //   FILE* file = fopen("multi.wasm", "rb");
        //   if (!file) {
        //     printf("> Error loading module!\n");
        //     return 1;
        //   }
        //   fseek(file, 0L, SEEK_END);
        //   size_t file_size = ftell(file);
        //   fseek(file, 0L, SEEK_SET);
        byte[] fileContent = ExamplesHelper.readWasm("multi.wasm");
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
        //   wasm_valtype_t* types[4] = {
        //     wasm_valtype_new_i32(), wasm_valtype_new_i64(),
        //     wasm_valtype_new_i64(), wasm_valtype_new_i32()
        //   };
        List<ValType> types = List.of(ValType.I32, ValType.I64, ValType.I64, ValType.I32);
        //   own wasm_valtype_vec_t tuple1, tuple2;
        //   wasm_valtype_vec_new(&tuple1, 4, types);
        ValtypeVec tuple1 = new ValtypeVec(resourceScope, types);
        //   wasm_valtype_vec_copy(&tuple2, &tuple1);
        ValtypeVec tuple2 = new ValtypeVec(resourceScope, types);
        //   own wasm_functype_t* callback_type = wasm_functype_new(&tuple1, &tuple2);
        FuncType callbackType = new FuncType(tuple1, tuple2);
        //   own wasm_func_t* callback_func =
        //     wasm_func_new(store, callback_type, callback);
        Func callbackFunc = new Func(store, callbackType, new Callback());

        //   wasm_functype_delete(callback_type);
        callbackType.close();

        //   // Instantiate.
        //   printf("Instantiating module...\n");
        System.out.println("Instantiating module...");
        //   wasm_extern_t* externs[] = { wasm_func_as_extern(callback_func) };
        //   wasm_extern_vec_t imports = WASM_ARRAY_VEC(externs);
        ExternVec imports = new ExternVec(resourceScope, List.of(callbackFunc));
        //   own wasm_instance_t* instance =
        //     wasm_instance_new(store, module, &imports, NULL);
        Instance instance = new Instance(store, module, imports);
        //   if (!instance) {
        //     printf("> Error instantiating module!\n");
        //     return 1;
        //   }

        //   wasm_func_delete(callback_func);
        callbackFunc.close();

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
        //   wasm_val_t vals[4] = {
        //     WASM_I32_VAL(1), WASM_I64_VAL(2), WASM_I64_VAL(3), WASM_I32_VAL(4)
        //   };
        //   wasm_val_t res[4] = {
        //     WASM_INIT_VAL, WASM_INIT_VAL, WASM_INIT_VAL, WASM_INIT_VAL
        //   };
        //   wasm_val_vec_t args = WASM_ARRAY_VEC(vals);
        ValVec params = new ValVec(resourceScope, List.of(
                new UnVal(ValType.I32, 1), new UnVal(ValType.I64, 2),
                new UnVal(ValType.I64, 3), new UnVal(ValType.I32, 4)));
        //   wasm_val_vec_t results = WASM_ARRAY_VEC(res);
        ValVec results = new ValVec(resourceScope, List.of(
                new UnVal(ValType.ANYREF, 0), new UnVal(ValType.ANYREF, 0),
                new UnVal(ValType.ANYREF, 0), new UnVal(ValType.ANYREF, 0)));
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
        //   printf("> %"PRIu32" %"PRIu64" %"PRIu64" %"PRIu32"\n",
        //     res[0].of.i32, res[1].of.i64, res[2].of.i64, res[3].of.i32);
        System.out.println(results.read(0).value + " " + results.read(1).value
                + " " + results.read(2).value + " " + results.read(3).value);

        //   assert(res[0].of.i32 == 4);
        //   assert(res[1].of.i64 == 3);
        //   assert(res[2].of.i64 == 2);
        //   assert(res[3].of.i32 == 1);

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
  (func $f (import "" "f") (param i32 i64 i64 i32) (result i32 i64 i64 i32))

  (func $g (export "g") (param i32 i64 i64 i32) (result i32 i64 i64 i32)
    (call $f (local.get 0) (local.get 2) (local.get 1) (local.get 3))
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
> > 1 3 2 4

Printing result...
> 4 3 2 1
Shutting down...
Done.

 */