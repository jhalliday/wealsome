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
import com.github.jhalliday.wealsome.api.Trap;
import com.github.jhalliday.wealsome.api.*;
import com.github.jhalliday.wealsome.generated.wasm_byte_vec_t;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;

/**
 * Java language translation of the C API usage example of the same name.
 * The C version is preserved inline as comments for comparison purposes.
 *
 * @see <a href="https://github.com/WebAssembly/wasm-c-api/blob/master/example/start.c">start.c</a>
 */
public class Start {

    // #include <stdio.h>
    // #include <stdlib.h>
    // #include <string.h>
    // #include <inttypes.h>
    //
    // #include "wasm.h"
    //
    // #define own
    //
    //
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
        //   FILE* file = fopen("start.wasm", "rb");
        //   if (!file) {
        //     printf("> Error loading module!\n");
        //     return 1;
        //   }
        //   fseek(file, 0L, SEEK_END);
        //   size_t file_size = ftell(file);
        //   fseek(file, 0L, SEEK_SET);
        byte[] fileContent = ExamplesHelper.readWasm("start.wasm");
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
        //
        //   wasm_byte_vec_delete(&binary);
        binary.close();

        //   // Instantiate.
        //   printf("Instantiating module...\n");
        System.out.println("Instantiating module...");
        //   wasm_extern_vec_t imports = WASM_EMPTY_VEC;
        ExternVec imports = new ExternVec(resourceScope);
        //   own wasm_trap_t* trap = NULL;
        Trap trap = new Trap(resourceScope);
        MemoryAddress before = WasmAPI.ptr$get(trap.memorySegment);
        //   own wasm_instance_t* instance =
        //     wasm_instance_new(store, module, &imports, &trap);
        Instance instance = new Instance(store, module, imports, trap); // TODO
        MemoryAddress after = WasmAPI.ptr$get(trap.memorySegment);

        MemoryAddress s = WasmAPI.ptr$get(after.asSegment(WasmAPI.pointerMemoryLayout.byteSize(), resourceScope));

        //   if (instance || !trap) {
        //     printf("> Error instantiating module, expected trap!\n");
        //     return 1;
        //   }
        //
        //   wasm_module_delete(module);
        module.close();

        //   // Print result.
        //   printf("Printing message...\n");
        System.out.println("Printing message...");
        //   own wasm_name_t message;
        MemorySegment byte_vec_t = wasm_byte_vec_t.allocate(resourceScope);
        ByteVec message = new ByteVec(resourceScope, byte_vec_t);
        //   wasm_trap_message(trap, &message);
        WasmAPI.trap_message(after, message);
        //   printf("> %s\n", message.data);
        System.out.println("> " + message.asString());

        //   printf("Printing origin...\n");
        System.out.println("Printing origin...");
        //   own wasm_frame_t* frame = wasm_trap_origin(trap);
        MemoryAddress frame = WasmAPI.trap_origin(after); // TODO abstraction

        //   if (frame) {
        if (frame.address() != MemoryAddress.NULL) {
            //     print_frame(frame);
            //     wasm_frame_delete(frame);
            //   } else {
        } else {
            //     printf("> Empty origin.\n");
            System.out.println("> Empty origin.");
            //   }
        }

        //   printf("Printing trace...\n");
        System.out.println("Printing trace...");
        //   own wasm_frame_vec_t trace;
        FrameVec trace = new FrameVec(resourceScope);
        //   wasm_trap_trace(trap, &trace);
        WasmAPI.trap_trace(after, trace.address());

        //   if (trace.size > 0) {
        if (trace.size() > 0) {
            //     for (size_t i = 0; i < trace.size; ++i) {
            //       print_frame(trace.data[i]);
            //     }
            //   } else {
        } else {
            //     printf("> Empty trace.\n");
            System.out.println("> Empty trace.");
            //   }
        }

        //   wasm_frame_vec_delete(&trace);
        trace.close();
        //   wasm_trap_delete(trap);
        trap.close();
        //   wasm_name_delete(&message);
        message.close();

        //   // Shut down.
        //   printf("Shutting down...\n");
        System.out.println("Shutting down...");
        //   wasm_store_delete(store);
        store.close();
        //   wasm_engine_delete(engine);
        engine.close();

        //   // All done.
        //   printf("Done.\n");
        System.out.println("Done.");
        //   return 0;
        // }
    }
}
/*

(module
  (func $start (unreachable))
  (start $start)
)

expect output:

Initializing...
Loading binary...
Compiling module...
Instantiating module...
Printing message...
> wasm trap: unreachable
wasm backtrace:
    0:   0x2e - <unknown>!<wasm function 0>

Printing origin...
> Empty origin.
Printing trace...
> Empty trace.
Shutting down...
Done.

 */
