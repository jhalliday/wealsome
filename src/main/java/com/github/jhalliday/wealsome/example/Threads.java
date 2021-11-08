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
 * @see <a href="https://github.com/WebAssembly/wasm-c-api/blob/master/example/threads.c">threads.c</a>
 */
public class Threads {

    // #include <inttypes.h>
    // #include <stdio.h>
    // #include <stdlib.h>
    // #include <string.h>
    // #include <pthread.h>
    // #include <unistd.h>
    //
    // #include "wasm.h"
    //
    // #define own
    //
    // const int N_THREADS = 10;
    private static final int N_THREADS = 1;
    // const int N_REPS = 3;
    private static final int N_REPS = 3;

    // // A function to be called from Wasm code.
    // own wasm_trap_t* callback(const wasm_val_vec_t* args, wasm_val_vec_t* results) {
    private static class Callback implements wasm_func_callback_t {
        @Override
        public MemoryAddress apply(MemoryAddress params, MemoryAddress results) {
            ResourceScope resourceScope = ExamplesHelper.newResourceScope();
            //   assert(args->data[0].kind == WASM_I32);
            //   printf("> Thread %d running\n", args->data[0].of.i32);
            System.out.printf("> Thread %d running\n", -1); // TODO
            resourceScope.close();
            //   return NULL;
            return MemoryAddress.NULL;
            // }
        }
    }

    // typedef struct {
    //   wasm_engine_t* engine;
    //   wasm_shared_module_t* module;
    //   int id;
    // } thread_args;
    private static record ThreadArgs(Engine engine, SharedModule sharedModule, int id) {
    }

    // void* run(void* args_abs) {
    private static class ExampleRunnable implements Runnable {
        //   thread_args* args = (thread_args*)args_abs;

        private final ThreadArgs args;

        private ExampleRunnable(ThreadArgs args) {
            this.args = args;
        }

        @Override
        public void run() {
            ResourceScope resourceScope = ExamplesHelper.newResourceScope();

            //   // Rereate store and module.
            //   own wasm_store_t* store = wasm_store_new(args->engine);
            Store store = new Store(args.engine());
            //   own wasm_module_t* module = wasm_module_obtain(store, args->module);
            Module module = new Module(store, args.sharedModule());

            //   // Run the example N times.
            //   for (int i = 0; i < N_REPS; ++i) {
            for (int i = 0; i < N_REPS; i++) {
                //     usleep(100000);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }

                System.out.println("thread " + args.id() + " rep " + i);

                //     // Create imports.
                //     own wasm_functype_t* func_type = wasm_functype_new_1_0(wasm_valtype_new_i32());
                FuncType funcType = FuncType.new_1_0(resourceScope, ValType.I32);
                //     own wasm_func_t* func = wasm_func_new(store, func_type, callback);
                Func func = null;
                try {
                    func = new Func(store, funcType, new Callback());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //     wasm_functype_delete(func_type);
                funcType.close();

                // TODO

                //     wasm_val_t val = WASM_I32_VAL((int32_t)args->id);
                Val val = new Val(resourceScope, ValType.I32, args.id);
                //     own wasm_globaltype_t* global_type =
                //       wasm_globaltype_new(wasm_valtype_new_i32(), WASM_CONST);
                GlobalType globalType = new GlobalType(resourceScope, ValType.I32, true);
                //     own wasm_global_t* global = wasm_global_new(store, global_type, &val);
                Global global = new Global(resourceScope, store, globalType, val);
                //     wasm_globaltype_delete(global_type);
                globalType.close();

                //     // Instantiate.
                //     wasm_extern_t* externs[] = {
                //       wasm_func_as_extern(func), wasm_global_as_extern(global),
                //     };
                //     wasm_extern_vec_t imports = WASM_ARRAY_VEC(externs);
                ExternVec imports = new ExternVec(resourceScope, List.of(func, global));
                //     own wasm_instance_t* instance =
                //       wasm_instance_new(store, module, &imports, NULL);
                Instance instance = new Instance(store, module, imports); // TODO fails
                //     if (!instance) {
                //       printf("> Error instantiating module!\n");
                //       return NULL;
                //     }

                //     wasm_func_delete(func);
                func.close();
                //     wasm_global_delete(global);
                //
                //     // Extract export.
                //     own wasm_extern_vec_t exports;
                ExternVec exports = new ExternVec(resourceScope);
                //     wasm_instance_exports(instance, &exports);
                instance.exports(exports);
                //     if (exports.size == 0) {
                //       printf("> Error accessing exports!\n");
                //       return NULL;
                //     }
                if (exports.size() == 0) {
                    System.out.println("> Error accessing exports!");
                    return;
                }
                //     const wasm_func_t *run_func = wasm_extern_as_func(exports.data[0]);
                WasmFunc runFunc = exports.asFunc(0);
                //     if (run_func == NULL) {
                //       printf("> Error accessing export!\n");
                //       return NULL;
                //     }
                //
                //     wasm_instance_delete(instance);
                instance.close();

                //     // Call.
                //     wasm_val_vec_t empty = WASM_EMPTY_VEC;
                ValVec params = new ValVec(resourceScope);
                ValVec results = new ValVec(resourceScope);
                //     if (wasm_func_call(run_func, &empty, &empty)) {
                runFunc.apply(params, results);
                //       printf("> Error calling function!\n");
                //       return NULL;
                //     }

                //     wasm_extern_vec_delete(&exports);
                params.close();
                results.close();
                //   }
            }

            //   wasm_module_delete(module);
            module.close();
            //   wasm_store_delete(store);
            store.close();

            resourceScope.close();

            //   free(args_abs);
            //
            //   return NULL;
            // }
        }
    }

    // int main(int argc, const char *argv[]) {
    public static void main(String[] args) throws Exception {
        ExamplesHelper.loadNativeImpl();
        ResourceScope resourceScope = ExamplesHelper.newResourceScope();

        //   // Initialize.
        //   wasm_engine_t* engine = wasm_engine_new();
        Engine engine = new Engine();
        //
        //   // Load binary.
        //   FILE* file = fopen("threads.wasm", "rb");
        //   if (!file) {
        //     printf("> Error loading module!\n");
        //     return 1;
        //   }
        //   fseek(file, 0L, SEEK_END);
        //   size_t file_size = ftell(file);
        //   fseek(file, 0L, SEEK_SET);
        byte[] fileContent = ExamplesHelper.readWasm("threads.wasm");
        //   wasm_byte_vec_t binary;
        //   wasm_byte_vec_new_uninitialized(&binary, file_size);
        ByteVec binary = new ByteVec(resourceScope, fileContent);
        //   if (fread(binary.data, file_size, 1, file) != 1) {
        //     printf("> Error loading module!\n");
        //     return 1;
        //   }
        //   fclose(file);
        //
        //   // Compile and share.
        //   own wasm_store_t* store = wasm_store_new(engine);
        Store store = new Store(engine);
        //   own wasm_module_t* module = wasm_module_new(store, &binary);
        Module module = new Module(store, binary);
        //   if (!module) {
        //     printf("> Error compiling module!\n");
        //     return 1;
        //   }
        //
        //   wasm_byte_vec_delete(&binary);
        binary.close();

        //   own wasm_shared_module_t* shared = wasm_module_share(module);
        SharedModule shared = new SharedModule(module);

        //   wasm_module_delete(module);
        module.close();
        //   wasm_store_delete(store);
        store.close();

        //   // Spawn threads.
        //   pthread_t threads[N_THREADS];
        Thread[] threads = new Thread[N_THREADS];
        //   for (int i = 0; i < N_THREADS; i++) {
        for (int i = 0; i < N_THREADS; i++) {
            //     thread_args* args = malloc(sizeof(thread_args));
            //     args->id = i;
            //     args->engine = engine;
            //     args->module = shared;
            ThreadArgs threadArgs = new ThreadArgs(engine, shared, i);
            //     printf("Initializing thread %d...\n", i);
            System.out.printf("Initializing thread %d...\n", i);
            //     pthread_create(&threads[i], NULL, &run, args);
            threads[i] = new Thread(new ExampleRunnable(threadArgs));
            threads[i].start();
            //   }
        }

        // for (int i = 0; i < N_THREADS; i++) {
        for (int i = 0; i < N_THREADS; i++) {
            //     printf("Waiting for thread: %d\n", i);
            System.out.printf("Waiting for thread: %d\n", i);
            //     pthread_join(threads[i], NULL);
            threads[i].join();
            //   }
        }

        //   wasm_shared_module_delete(shared);
        shared.close();
        //   wasm_engine_delete(engine);
        engine.close();

        //   return 0;
        // }
    }
}
/*

(module
  (func $message (import "" "hello") (param i32))
  (global $id (import "" "id") i32)
  (func (export "run") (call $message (global.get $id)))
)

expect output to be some non-deterministic variation on:

Initializing thread 0...
Initializing thread 1...
Initializing thread 2...
Initializing thread 3...
Initializing thread 4...
Initializing thread 5...
Initializing thread 6...
Initializing thread 7...
Initializing thread 8...
Initializing thread 9...
Waiting for thread: 0
> Thread 1 running
> Thread 6 running
> Thread 5 running
> Thread 2 running
> Thread 3 running
> Thread 4 running
> Thread 8 running
> Thread 9 running
> Thread 7 running
> Thread 0 running
> Thread 1 running
> Thread 2 running
> Thread 6 running
> Thread 5 running
> Thread 3 running
> Thread 4 running
> Thread 8 running
> Thread 0 running
> Thread 7 running
> Thread 9 running
> Thread 1 running
> Thread 2 running
> Thread 6 running
> Thread 5 running
> Thread 4 running
> Thread 8 running
> Thread 3 running
> Thread 0 running
> Thread 7 running
> Thread 9 running
Waiting for thread: 1
Waiting for thread: 2
Waiting for thread: 3
Waiting for thread: 4
Waiting for thread: 5
Waiting for thread: 6
Waiting for thread: 7
Waiting for thread: 8
Waiting for thread: 9


 */