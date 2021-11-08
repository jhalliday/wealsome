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
import jdk.incubator.foreign.ResourceScope;

/**
 * Java language translation of the C API usage example of the same name.
 * The C version is preserved inline as comments for comparison purposes.
 *
 * @see <a href="https://github.com/WebAssembly/wasm-c-api/blob/master/example/reflect.c">reflect.c</a>
 */
public class Reflect {

    // #include <stdio.h>
    // #include <stdlib.h>
    // #include <string.h>
    // #include <inttypes.h>
    //
    // #include "wasm.h"
    //
    // #define own
    //
    // void print_mutability(wasm_mutability_t mut) {
    //   switch (mut) {
    //     case WASM_VAR: printf("var"); break;
    //     case WASM_CONST: printf("const"); break;
    //   }
    // }
    private static void printMutability(GlobalType globalType) {
        if (globalType.isMutable()) {
            System.out.print("val");
        } else {
            System.out.print("const");
        }
    }


    // void print_limits(const wasm_limits_t* limits) {
    //   printf("%ud", limits->min);
    //   if (limits->max < wasm_limits_max_default) printf(" %ud", limits->max);
    // }
    private static void printLimits(Limits limits) {
        System.out.printf("%dd", limits.getMin());
        if (limits.getMax() != -1) {
            System.out.printf(" %dd", limits.getMax());
        }
    }

    // void print_valtype(const wasm_valtype_t* type) {
    //   switch (wasm_valtype_kind(type)) {
    //     case WASM_I32: printf("i32"); break;
    //     case WASM_I64: printf("i64"); break;
    //     case WASM_F32: printf("f32"); break;
    //     case WASM_F64: printf("f64"); break;
    //     case WASM_ANYREF: printf("anyref"); break;
    //     case WASM_FUNCREF: printf("funcref"); break;
    //   }
    // }
    private static void printValType(ValType valType) {
        switch (valType) {
            case I32 -> System.out.print("i32");
            case I64 -> System.out.print("i64");
            case F32 -> System.out.print("f32");
            case F64 -> System.out.print("f64");
            case ANYREF -> System.out.print("anyref");
            case FUNCREF -> System.out.print("funcref");
        }
    }

    // void print_valtypes(const wasm_valtype_vec_t* types) {
    //   bool first = true;
    //   for (size_t i = 0; i < types->size; ++i) {
    //     if (first) {
    //       first = false;
    //     } else {
    //       printf(" ");
    //     }
    //     print_valtype(types->data[i]);
    //   }
    // }
    private static void printValTypes(ValtypeVec types) {
        boolean first = true;
        for (int i = 0; i < types.size(); i++) {
            if (first) {
                first = false;
            } else {
                System.out.print(" ");
            }
            printValType(types.get(i));
        }
    }

    // void print_externtype(const wasm_externtype_t* type) {
    private static void printExternType(ExternType externType) {
        //   switch (wasm_externtype_kind(type)) {
        switch (externType.getKind()) {
            //     case WASM_EXTERN_FUNC: {
            //       const wasm_functype_t* functype =
            //         wasm_externtype_as_functype_const(type);
            //       printf("func ");
            //       print_valtypes(wasm_functype_params(functype));
            //       printf(" -> ");
            //       print_valtypes(wasm_functype_results(functype));
            //     } break;
            case WASM_EXTERN_FUNC: {
                FuncType funcType = (FuncType) externType;
                System.out.print("func ");
                printValTypes(funcType.paramTypes());
                System.out.print(" --> ");
                printValTypes(funcType.resultTypes());
            }
            break;
            //     case WASM_EXTERN_GLOBAL: {
            //       const wasm_globaltype_t* globaltype =
            //         wasm_externtype_as_globaltype_const(type);
            //       printf("global ");
            //       print_mutability(wasm_globaltype_mutability(globaltype));
            //       printf(" ");
            //       print_valtype(wasm_globaltype_content(globaltype));
            //     } break;
            case WASM_EXTERN_GLOBAL: {
                GlobalType globalType = (GlobalType) externType;
                System.out.print("global ");
                printMutability(globalType);
                System.out.print(" ");
                printValType(globalType.getContent());
            }
            break;
            //     case WASM_EXTERN_TABLE: {
            //       const wasm_tabletype_t* tabletype =
            //         wasm_externtype_as_tabletype_const(type);
            //       printf("table ");
            //       print_limits(wasm_tabletype_limits(tabletype));
            //       printf(" ");
            //       print_valtype(wasm_tabletype_element(tabletype));
            //     } break;
            case WASM_EXTERN_TABLE: {
                TableType tableType = (TableType) externType;
                System.out.print("table ");
                printLimits(tableType.getLimits());
                System.out.print(" ");
                printValType(tableType.getValType());
            }
            break;
            //     case WASM_EXTERN_MEMORY: {
            //       const wasm_memorytype_t* memorytype =
            //         wasm_externtype_as_memorytype_const(type);
            //       printf("memory ");
            //       print_limits(wasm_memorytype_limits(memorytype));
            //     } break;
            case WASM_EXTERN_MEMORY: {
                MemoryType memoryType = (MemoryType) externType;
                System.out.print("memory ");
                printLimits(memoryType.getLimits());
            }
            break;
            //   }
            // }
        }
    }

    // void print_name(const wasm_name_t* name) {
    //   printf("\"%.*s\"", (int)name->size, name->data);
    // }
    private static void printName(ByteVec name) {
        System.out.print("\"" + name.asString() + "\"");
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
        //   FILE* file = fopen("reflect.wasm", "rb");
        //   if (!file) {
        //     printf("> Error loading module!\n");
        //     return 1;
        //   }
        //   fseek(file, 0L, SEEK_END);
        //   size_t file_size = ftell(file);
        //   fseek(file, 0L, SEEK_SET);
        byte[] fileContent = ExamplesHelper.readWasm("reflect.wasm");
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

        //   // Instantiate.
        //   printf("Instantiating module...\n");
        System.out.println("Instantiating module...");
        //   wasm_extern_vec_t imports = WASM_EMPTY_VEC;
        ExternVec imports = new ExternVec(resourceScope);
        //   own wasm_instance_t* instance =
        //     wasm_instance_new(store, module, &imports, NULL);
        Instance instance = new Instance(store, module, imports);
        //   if (!instance) {
        //     printf("> Error instantiating module!\n");
        //     return 1;
        //   }

        //   // Extract export.
        //   printf("Extracting export...\n");
        System.out.println("Extracting export...");
        //   own wasm_exporttype_vec_t export_types;
        ExportTypeVec exportTypes = new ExportTypeVec(resourceScope);
        //   own wasm_extern_vec_t exports;
        ExternVec exports = new ExternVec(resourceScope);
        //   wasm_module_exports(module, &export_types);
        module.exports(exportTypes);
        //   wasm_instance_exports(instance, &exports);
        instance.exports(exports);
        //   assert(exports.size == export_types.size);
        //   for (size_t i = 0; i < exports.size; ++i) {
        for (int i = 0; i < exports.size(); i++) {
            //     assert(wasm_extern_kind(exports.data[i]) ==
            //       wasm_externtype_kind(wasm_exporttype_type(export_types.data[i])));
            //     printf("> export %zu ", i);
            System.out.printf("> export %d ", i);
            //     print_name(wasm_exporttype_name(export_types.data[i]));
            printName(exportTypes.name(i));
            //     printf("\n");
            System.out.println();
            //     printf(">> initial: ");
            System.out.print(">> initial: ");
            //     print_externtype(wasm_exporttype_type(export_types.data[i]));
            printExternType(exportTypes.getExternType(i));
            //     printf("\n");
            System.out.println();
            //     printf(">> current: ");
            System.out.print(">> current: ");
            //     own wasm_externtype_t* current = wasm_extern_type(exports.data[i]);
            //     print_externtype(current);
            ExternType current = exports.getExternType(i);
            printExternType(current);
            //     wasm_externtype_delete(current);
            //     printf("\n");
            System.out.println();
            //     if (wasm_extern_kind(exports.data[i]) == WASM_EXTERN_FUNC) {
            if (exports.getExternType(i).getKind().equals(ExternKind.WASM_EXTERN_FUNC)) {
                //       wasm_func_t* func = wasm_extern_as_func(exports.data[i]);
                WasmFunc func = exports.asFunc(i);
                //       printf(">> in-arity: %zu", wasm_func_param_arity(func));
                System.out.printf(">> in-arity: %d", func.getParamArity());
                //       printf(", out-arity: %zu\n", wasm_func_result_arity(func));
                System.out.printf(", out-arity: %d\n", func.getResultArity());
                //     }
            }
            //   }
        }

        //   wasm_module_delete(module);
        module.close();
        //   wasm_instance_delete(instance);
        instance.close();
        //   wasm_extern_vec_delete(&exports);
        exports.close();
        //   wasm_exporttype_vec_delete(&export_types);

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
  (func (export "func") (param i32 f64 f32) (result i32) (unreachable))
  (global (export "global") f64 (f64.const 0))
  (table (export "table") 0 50 anyfunc)
  (memory (export "memory") 1)
)

expect output:

Initializing...
Loading binary...
Compiling module...
Instantiating module...
Extracting export...
> export 0 "func"
>> initial: func i32 f64 f32 -> i32
>> current: func i32 f64 f32 -> i32
>> in-arity: 3, out-arity: 1
> export 1 "global"
>> initial: global const f64
>> current: global const f64
> export 2 "table"
>> initial: table 0d 50d funcref
>> current: table 0d 50d funcref
> export 3 "memory"
>> initial: memory 1d
>> current: memory 1d
Shutting down...
Done.

 */