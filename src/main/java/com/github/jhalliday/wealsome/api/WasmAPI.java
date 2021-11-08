/*
 * Copyright Red Hat
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
package com.github.jhalliday.wealsome.api;

import com.github.jhalliday.wealsome.generated.Wasm;
import jdk.incubator.foreign.*;
import org.jboss.logging.Logger;

import java.lang.invoke.VarHandle;

/**
 * @author Jonathan Halliday (jonathan.halliday@redhat.com)
 */
public class WasmAPI {

    private static final Logger logger = Logger.getLogger(WasmAPI.class);

    public static final MemoryLayout pointerMemoryLayout = CLinker.C_POINTER;
    public static final VarHandle ptr$VH = MemoryHandles.asAddressVarHandle(pointerMemoryLayout.varHandle(long.class));

    public static MemoryAddress ptr$get(MemorySegment seg) {
        return (jdk.incubator.foreign.MemoryAddress) ptr$VH.get(seg);
    }

    public static MemoryAddress ptr$get(MemorySegment seg, long index) {
        return (MemoryAddress) ptr$VH.get(seg.asSlice(index * pointerMemoryLayout.byteSize()));
    }

    ///////////////////////////////

    /**
     * void wasm_byte_vec_new_empty(wasm_byte_vec_t* out);
     */
    public static void byte_vec_new_empty(Addressable out) {
        if (logger.isTraceEnabled()) {
            logger.tracev("byte_vec_new_empty(out=" + out + ")");
        }
        Wasm.wasm_byte_vec_new_empty(out);
    }

    /**
     * void wasm_byte_vec_new_uninitialized(wasm_byte_vec_t* out, size_t);
     */
    public static void byte_vec_new_uninitialized(Addressable out, long x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("byte_vec_new_uninitialized(out=" + out + ", x1=" + x1 + ")");
        }
        Wasm.wasm_byte_vec_new_uninitialized(out, x1);
    }

    /**
     * void wasm_byte_vec_new(wasm_byte_vec_t* out, size_t, wasm_byte_t const[]);
     */
    public static void byte_vec_new(Addressable out, long x1, Addressable x2) {
        if (logger.isTraceEnabled()) {
            logger.tracev("byte_vec_new(out=" + out + ", x1=" + x1 + ", x2=" + x2 + ")");
        }
        Wasm.wasm_byte_vec_new(out, x1, x2);
    }

    /**
     * void wasm_byte_vec_copy(wasm_byte_vec_t* out, const wasm_byte_vec_t*);
     */
    public static void byte_vec_copy(Addressable out, Addressable x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("byte_vec_copy(out=" + out + ", x1=" + x1 + ")");
        }
        Wasm.wasm_byte_vec_copy(out, x1);
    }

    /**
     * void wasm_byte_vec_delete(wasm_byte_vec_t*);
     */
    public static void byte_vec_delete(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("byte_vec_delete(x0=" + x0 + ")");
        }
        Wasm.wasm_byte_vec_delete(x0);
    }

    /**
     * static inline void wasm_name_new_from_string(wasm_name_t* out, const char* s);
     */
    public static void name_new_from_string(Addressable out, Addressable s) {
        if (logger.isTraceEnabled()) {
            logger.tracev("name_new_from_string(out=" + out + ", s=" + s + ")");
        }
        Wasm.wasm_name_new_from_string(out, s);
    }

    /**
     * static inline void wasm_name_new_from_string_nt(wasm_name_t* out, const char* s);
     */
    public static void name_new_from_string_nt(Addressable out, Addressable s) {
        if (logger.isTraceEnabled()) {
            logger.tracev("name_new_from_string_nt(out=" + out + ", s=" + s + ")");
        }
        Wasm.wasm_name_new_from_string_nt(out, s);
    }

    /**
     * void wasm_config_delete(wasm_config_t*);
     */
    public static void config_delete(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("config_delete(x0=" + x0 + ")");
        }
        Wasm.wasm_config_delete(x0);
    }

    /**
     * wasm_config_t* wasm_config_new(void);
     */
    public static MemoryAddress config_new() {
        if (logger.isTraceEnabled()) {
            logger.tracev("config_new()");
        }
        return Wasm.wasm_config_new();
    }

    /**
     * void wasm_engine_delete(wasm_engine_t*);
     */
    public static void engine_delete(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("engine_delete(x0=" + x0 + ")");
        }
        Wasm.wasm_engine_delete(x0);
    }

    /**
     * wasm_engine_t* wasm_engine_new(void);
     */
    public static MemoryAddress engine_new() {
        if (logger.isTraceEnabled()) {
            logger.tracev("engine_new()");
        }
        return Wasm.wasm_engine_new();
    }

    /**
     * wasm_engine_t* wasm_engine_new_with_config(wasm_config_t*);
     */
    public static MemoryAddress engine_new_with_config(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("engine_new_with_config(x0=" + x0 + ")");
        }
        return Wasm.wasm_engine_new_with_config(x0);
    }

    /**
     * void wasm_store_delete(wasm_store_t*);
     */
    public static void store_delete(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("store_delete(x0=" + x0 + ")");
        }
        Wasm.wasm_store_delete(x0);
    }

    /**
     * wasm_store_t* wasm_store_new(wasm_engine_t*);
     */
    public static MemoryAddress store_new(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("store_new(x0=" + x0 + ")");
        }
        return Wasm.wasm_store_new(x0);
    }

    /**
     * void wasm_valtype_delete(wasm_valtype_t*);
     */
    public static void valtype_delete(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("valtype_delete(x0=" + x0 + ")");
        }
        Wasm.wasm_valtype_delete(x0);
    }

    /**
     * void wasm_valtype_vec_new_empty(wasm_valtype_vec_t* out);
     */
    public static void valtype_vec_new_empty(Addressable out) {
        if (logger.isTraceEnabled()) {
            logger.tracev("valtype_vec_new_empty(out=" + out + ")");
        }
        Wasm.wasm_valtype_vec_new_empty(out);
    }

    /**
     * void wasm_valtype_vec_new_uninitialized(wasm_valtype_vec_t* out, size_t);
     */
    public static void valtype_vec_new_uninitialized(Addressable out, long x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("valtype_vec_new_uninitialized(out=" + out + ", x1=" + x1 + ")");
        }
        Wasm.wasm_valtype_vec_new_uninitialized(out, x1);
    }

    /**
     * void wasm_valtype_vec_new(wasm_valtype_vec_t* out, size_t, wasm_valtype_t * const[]);
     */
    public static void valtype_vec_new(Addressable out, long x1, Addressable x2) {
        if (logger.isTraceEnabled()) {
            logger.tracev("valtype_vec_new(out=" + out + ", x1=" + x1 + ", x2=" + x2 + ")");
        }
        Wasm.wasm_valtype_vec_new(out, x1, x2);
    }

    /**
     * void wasm_valtype_vec_copy(wasm_valtype_vec_t* out, const wasm_valtype_vec_t*);
     */
    public static void valtype_vec_copy(Addressable out, Addressable x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("valtype_vec_copy(out=" + out + ", x1=" + x1 + ")");
        }
        Wasm.wasm_valtype_vec_copy(out, x1);
    }

    /**
     * void wasm_valtype_vec_delete(wasm_valtype_vec_t*);
     */
    public static void valtype_vec_delete(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("valtype_vec_delete(x0=" + x0 + ")");
        }
        Wasm.wasm_valtype_vec_delete(x0);
    }

    /**
     * wasm_valtype_t* wasm_valtype_copy(const wasm_valtype_t*);
     */
    public static MemoryAddress valtype_copy(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("valtype_copy(x0=" + x0 + ")");
        }
        return Wasm.wasm_valtype_copy(x0);
    }

    /**
     * wasm_valtype_t* wasm_valtype_new(wasm_valkind_t);
     */
    public static MemoryAddress valtype_new(byte x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("valtype_new(x0=" + x0 + ")");
        }
        return Wasm.wasm_valtype_new(x0);
    }

    /**
     * wasm_valkind_t wasm_valtype_kind(const wasm_valtype_t*);
     */
    public static byte valtype_kind(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("valtype_kind(x0=" + x0 + ")");
        }
        return Wasm.wasm_valtype_kind(x0);
    }

    /**
     * static inline _Bool wasm_valkind_is_num(wasm_valkind_t k);
     */
    public static byte valkind_is_num(byte k) {
        if (logger.isTraceEnabled()) {
            logger.tracev("valkind_is_num(k=" + k + ")");
        }
        return Wasm.wasm_valkind_is_num(k);
    }

    /**
     * static inline _Bool wasm_valkind_is_ref(wasm_valkind_t k);
     */
    public static byte valkind_is_ref(byte k) {
        if (logger.isTraceEnabled()) {
            logger.tracev("valkind_is_ref(k=" + k + ")");
        }
        return Wasm.wasm_valkind_is_ref(k);
    }

    /**
     * static inline _Bool wasm_valtype_is_num(const wasm_valtype_t* t);
     */
    public static byte valtype_is_num(Addressable t) {
        if (logger.isTraceEnabled()) {
            logger.tracev("valtype_is_num(t=" + t + ")");
        }
        return Wasm.wasm_valtype_is_num(t);
    }

    /**
     * static inline _Bool wasm_valtype_is_ref(const wasm_valtype_t* t);
     */
    public static byte valtype_is_ref(Addressable t) {
        if (logger.isTraceEnabled()) {
            logger.tracev("valtype_is_ref(t=" + t + ")");
        }
        return Wasm.wasm_valtype_is_ref(t);
    }

    /**
     * void wasm_functype_delete(wasm_functype_t*);
     */
    public static void functype_delete(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("functype_delete(x0=" + x0 + ")");
        }
        Wasm.wasm_functype_delete(x0);
    }

    /**
     * void wasm_functype_vec_new_empty(wasm_functype_vec_t* out);
     */
    public static void functype_vec_new_empty(Addressable out) {
        if (logger.isTraceEnabled()) {
            logger.tracev("functype_vec_new_empty(out=" + out + ")");
        }
        Wasm.wasm_functype_vec_new_empty(out);
    }

    /**
     * void wasm_functype_vec_new_uninitialized(wasm_functype_vec_t* out, size_t);
     */
    public static void functype_vec_new_uninitialized(Addressable out, long x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("functype_vec_new_uninitialized(out=" + out + ", x1=" + x1 + ")");
        }
        Wasm.wasm_functype_vec_new_uninitialized(out, x1);
    }

    /**
     * void wasm_functype_vec_new(wasm_functype_vec_t* out, size_t, wasm_functype_t * const[]);
     */
    public static void functype_vec_new(Addressable out, long x1, Addressable x2) {
        if (logger.isTraceEnabled()) {
            logger.tracev("functype_vec_new(out=" + out + ", x1=" + x1 + ", x2=" + x2 + ")");
        }
        Wasm.wasm_functype_vec_new(out, x1, x2);
    }

    /**
     * void wasm_functype_vec_copy(wasm_functype_vec_t* out, const wasm_functype_vec_t*);
     */
    public static void functype_vec_copy(Addressable out, Addressable x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("functype_vec_copy(out=" + out + ", x1=" + x1 + ")");
        }
        Wasm.wasm_functype_vec_copy(out, x1);
    }

    /**
     * void wasm_functype_vec_delete(wasm_functype_vec_t*);
     */
    public static void functype_vec_delete(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("functype_vec_delete(x0=" + x0 + ")");
        }
        Wasm.wasm_functype_vec_delete(x0);
    }

    /**
     * wasm_functype_t* wasm_functype_copy(const wasm_functype_t*);
     */
    public static MemoryAddress functype_copy(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("functype_copy(x0=" + x0 + ")");
        }
        return Wasm.wasm_functype_copy(x0);
    }

    /**
     * wasm_functype_t* wasm_functype_new(wasm_valtype_vec_t* params, wasm_valtype_vec_t* results);
     */
    public static MemoryAddress functype_new(Addressable params, Addressable results) {
        if (logger.isTraceEnabled()) {
            logger.tracev("functype_new(params=" + params + ", results=" + results + ")");
        }
        return Wasm.wasm_functype_new(params, results);
    }

    /**
     * const wasm_valtype_vec_t* wasm_functype_params(const wasm_functype_t*);
     */
    public static MemoryAddress functype_params(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("functype_params(x0=" + x0 + ")");
        }
        return Wasm.wasm_functype_params(x0);
    }

    /**
     * const wasm_valtype_vec_t* wasm_functype_results(const wasm_functype_t*);
     */
    public static MemoryAddress functype_results(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("functype_results(x0=" + x0 + ")");
        }
        return Wasm.wasm_functype_results(x0);
    }

    /**
     * void wasm_globaltype_delete(wasm_globaltype_t*);
     */
    public static void globaltype_delete(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("globaltype_delete(x0=" + x0 + ")");
        }
        Wasm.wasm_globaltype_delete(x0);
    }

    /**
     * void wasm_globaltype_vec_new_empty(wasm_globaltype_vec_t* out);
     */
    public static void globaltype_vec_new_empty(Addressable out) {
        if (logger.isTraceEnabled()) {
            logger.tracev("globaltype_vec_new_empty(out=" + out + ")");
        }
        Wasm.wasm_globaltype_vec_new_empty(out);
    }

    /**
     * void wasm_globaltype_vec_new_uninitialized(wasm_globaltype_vec_t* out, size_t);
     */
    public static void globaltype_vec_new_uninitialized(Addressable out, long x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("globaltype_vec_new_uninitialized(out=" + out + ", x1=" + x1 + ")");
        }
        Wasm.wasm_globaltype_vec_new_uninitialized(out, x1);
    }

    /**
     * void wasm_globaltype_vec_new(wasm_globaltype_vec_t* out, size_t, wasm_globaltype_t * const[]);
     */
    public static void globaltype_vec_new(Addressable out, long x1, Addressable x2) {
        if (logger.isTraceEnabled()) {
            logger.tracev("globaltype_vec_new(out=" + out + ", x1=" + x1 + ", x2=" + x2 + ")");
        }
        Wasm.wasm_globaltype_vec_new(out, x1, x2);
    }

    /**
     * void wasm_globaltype_vec_copy(wasm_globaltype_vec_t* out, const wasm_globaltype_vec_t*);
     */
    public static void globaltype_vec_copy(Addressable out, Addressable x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("globaltype_vec_copy(out=" + out + ", x1=" + x1 + ")");
        }
        Wasm.wasm_globaltype_vec_copy(out, x1);
    }

    /**
     * void wasm_globaltype_vec_delete(wasm_globaltype_vec_t*);
     */
    public static void globaltype_vec_delete(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("globaltype_vec_delete(x0=" + x0 + ")");
        }
        Wasm.wasm_globaltype_vec_delete(x0);
    }

    /**
     * wasm_globaltype_t* wasm_globaltype_copy(const wasm_globaltype_t*);
     */
    public static MemoryAddress globaltype_copy(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("globaltype_copy(x0=" + x0 + ")");
        }
        return Wasm.wasm_globaltype_copy(x0);
    }

    /**
     * wasm_globaltype_t* wasm_globaltype_new(wasm_valtype_t*, wasm_mutability_t);
     */
    public static MemoryAddress globaltype_new(Addressable x0, byte x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("globaltype_new(x0=" + x0 + ", x1=" + x1 + ")");
        }
        return Wasm.wasm_globaltype_new(x0, x1);
    }

    /**
     * const wasm_valtype_t* wasm_globaltype_content(const wasm_globaltype_t*);
     */
    public static MemoryAddress globaltype_content(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("globaltype_content(x0=" + x0 + ")");
        }
        return Wasm.wasm_globaltype_content(x0);
    }

    /**
     * wasm_mutability_t wasm_globaltype_mutability(const wasm_globaltype_t*);
     */
    public static byte globaltype_mutability(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("globaltype_mutability(x0=" + x0 + ")");
        }
        return Wasm.wasm_globaltype_mutability(x0);
    }

    /**
     * void wasm_tabletype_delete(wasm_tabletype_t*);
     */
    public static void tabletype_delete(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("tabletype_delete(x0=" + x0 + ")");
        }
        Wasm.wasm_tabletype_delete(x0);
    }

    /**
     * void wasm_tabletype_vec_new_empty(wasm_tabletype_vec_t* out);
     */
    public static void tabletype_vec_new_empty(Addressable out) {
        if (logger.isTraceEnabled()) {
            logger.tracev("tabletype_vec_new_empty(out=" + out + ")");
        }
        Wasm.wasm_tabletype_vec_new_empty(out);
    }

    /**
     * void wasm_tabletype_vec_new_uninitialized(wasm_tabletype_vec_t* out, size_t);
     */
    public static void tabletype_vec_new_uninitialized(Addressable out, long x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("tabletype_vec_new_uninitialized(out=" + out + ", x1=" + x1 + ")");
        }
        Wasm.wasm_tabletype_vec_new_uninitialized(out, x1);
    }

    /**
     * void wasm_tabletype_vec_new(wasm_tabletype_vec_t* out, size_t, wasm_tabletype_t * const[]);
     */
    public static void tabletype_vec_new(Addressable out, long x1, Addressable x2) {
        if (logger.isTraceEnabled()) {
            logger.tracev("tabletype_vec_new(out=" + out + ", x1=" + x1 + ", x2=" + x2 + ")");
        }
        Wasm.wasm_tabletype_vec_new(out, x1, x2);
    }

    /**
     * void wasm_tabletype_vec_copy(wasm_tabletype_vec_t* out, const wasm_tabletype_vec_t*);
     */
    public static void tabletype_vec_copy(Addressable out, Addressable x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("tabletype_vec_copy(out=" + out + ", x1=" + x1 + ")");
        }
        Wasm.wasm_tabletype_vec_copy(out, x1);
    }

    /**
     * void wasm_tabletype_vec_delete(wasm_tabletype_vec_t*);
     */
    public static void tabletype_vec_delete(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("tabletype_vec_delete(x0=" + x0 + ")");
        }
        Wasm.wasm_tabletype_vec_delete(x0);
    }

    /**
     * wasm_tabletype_t* wasm_tabletype_copy(const wasm_tabletype_t*);
     */
    public static MemoryAddress tabletype_copy(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("tabletype_copy(x0=" + x0 + ")");
        }
        return Wasm.wasm_tabletype_copy(x0);
    }

    /**
     * wasm_tabletype_t* wasm_tabletype_new(wasm_valtype_t*, const wasm_limits_t*);
     */
    public static MemoryAddress tabletype_new(Addressable x0, Addressable x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("tabletype_new(x0=" + x0 + ", x1=" + x1 + ")");
        }
        return Wasm.wasm_tabletype_new(x0, x1);
    }

    /**
     * const wasm_valtype_t* wasm_tabletype_element(const wasm_tabletype_t*);
     */
    public static MemoryAddress tabletype_element(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("tabletype_element(x0=" + x0 + ")");
        }
        return Wasm.wasm_tabletype_element(x0);
    }

    /**
     * const wasm_limits_t* wasm_tabletype_limits(const wasm_tabletype_t*);
     */
    public static MemoryAddress tabletype_limits(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("tabletype_limits(x0=" + x0 + ")");
        }
        return Wasm.wasm_tabletype_limits(x0);
    }

    /**
     * void wasm_memorytype_delete(wasm_memorytype_t*);
     */
    public static void memorytype_delete(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("memorytype_delete(x0=" + x0 + ")");
        }
        Wasm.wasm_memorytype_delete(x0);
    }

    /**
     * void wasm_memorytype_vec_new_empty(wasm_memorytype_vec_t* out);
     */
    public static void memorytype_vec_new_empty(Addressable out) {
        if (logger.isTraceEnabled()) {
            logger.tracev("memorytype_vec_new_empty(out=" + out + ")");
        }
        Wasm.wasm_memorytype_vec_new_empty(out);
    }

    /**
     * void wasm_memorytype_vec_new_uninitialized(wasm_memorytype_vec_t* out, size_t);
     */
    public static void memorytype_vec_new_uninitialized(Addressable out, long x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("memorytype_vec_new_uninitialized(out=" + out + ", x1=" + x1 + ")");
        }
        Wasm.wasm_memorytype_vec_new_uninitialized(out, x1);
    }

    /**
     * void wasm_memorytype_vec_new(wasm_memorytype_vec_t* out, size_t, wasm_memorytype_t * const[]);
     */
    public static void memorytype_vec_new(Addressable out, long x1, Addressable x2) {
        if (logger.isTraceEnabled()) {
            logger.tracev("memorytype_vec_new(out=" + out + ", x1=" + x1 + ", x2=" + x2 + ")");
        }
        Wasm.wasm_memorytype_vec_new(out, x1, x2);
    }

    /**
     * void wasm_memorytype_vec_copy(wasm_memorytype_vec_t* out, const wasm_memorytype_vec_t*);
     */
    public static void memorytype_vec_copy(Addressable out, Addressable x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("memorytype_vec_copy(out=" + out + ", x1=" + x1 + ")");
        }
        Wasm.wasm_memorytype_vec_copy(out, x1);
    }

    /**
     * void wasm_memorytype_vec_delete(wasm_memorytype_vec_t*);
     */
    public static void memorytype_vec_delete(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("memorytype_vec_delete(x0=" + x0 + ")");
        }
        Wasm.wasm_memorytype_vec_delete(x0);
    }

    /**
     * wasm_memorytype_t* wasm_memorytype_copy(const wasm_memorytype_t*);
     */
    public static MemoryAddress memorytype_copy(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("memorytype_copy(x0=" + x0 + ")");
        }
        return Wasm.wasm_memorytype_copy(x0);
    }

    /**
     * wasm_memorytype_t* wasm_memorytype_new(const wasm_limits_t*);
     */
    public static MemoryAddress memorytype_new(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("memorytype_new(x0=" + x0 + ")");
        }
        return Wasm.wasm_memorytype_new(x0);
    }

    /**
     * const wasm_limits_t* wasm_memorytype_limits(const wasm_memorytype_t*);
     */
    public static MemoryAddress memorytype_limits(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("memorytype_limits(x0=" + x0 + ")");
        }
        return Wasm.wasm_memorytype_limits(x0);
    }

    /**
     * void wasm_externtype_delete(wasm_externtype_t*);
     */
    public static void externtype_delete(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("externtype_delete(x0=" + x0 + ")");
        }
        Wasm.wasm_externtype_delete(x0);
    }

    /**
     * void wasm_externtype_vec_new_empty(wasm_externtype_vec_t* out);
     */
    public static void externtype_vec_new_empty(Addressable out) {
        if (logger.isTraceEnabled()) {
            logger.tracev("externtype_vec_new_empty(out=" + out + ")");
        }
        Wasm.wasm_externtype_vec_new_empty(out);
    }

    /**
     * void wasm_externtype_vec_new_uninitialized(wasm_externtype_vec_t* out, size_t);
     */
    public static void externtype_vec_new_uninitialized(Addressable out, long x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("externtype_vec_new_uninitialized(out=" + out + ", x1=" + x1 + ")");
        }
        Wasm.wasm_externtype_vec_new_uninitialized(out, x1);
    }

    /**
     * void wasm_externtype_vec_new(wasm_externtype_vec_t* out, size_t, wasm_externtype_t * const[]);
     */
    public static void externtype_vec_new(Addressable out, long x1, Addressable x2) {
        if (logger.isTraceEnabled()) {
            logger.tracev("externtype_vec_new(out=" + out + ", x1=" + x1 + ", x2=" + x2 + ")");
        }
        Wasm.wasm_externtype_vec_new(out, x1, x2);
    }

    /**
     * void wasm_externtype_vec_copy(wasm_externtype_vec_t* out, const wasm_externtype_vec_t*);
     */
    public static void externtype_vec_copy(Addressable out, Addressable x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("externtype_vec_copy(out=" + out + ", x1=" + x1 + ")");
        }
        Wasm.wasm_externtype_vec_copy(out, x1);
    }

    /**
     * void wasm_externtype_vec_delete(wasm_externtype_vec_t*);
     */
    public static void externtype_vec_delete(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("externtype_vec_delete(x0=" + x0 + ")");
        }
        Wasm.wasm_externtype_vec_delete(x0);
    }

    /**
     * wasm_externtype_t* wasm_externtype_copy(const wasm_externtype_t*);
     */
    public static MemoryAddress externtype_copy(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("externtype_copy(x0=" + x0 + ")");
        }
        return Wasm.wasm_externtype_copy(x0);
    }

    /**
     * wasm_externkind_t wasm_externtype_kind(const wasm_externtype_t*);
     */
    public static byte externtype_kind(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("externtype_kind(x0=" + x0 + ")");
        }
        return Wasm.wasm_externtype_kind(x0);
    }

    /**
     * wasm_externtype_t* wasm_functype_as_externtype(wasm_functype_t*);
     */
    public static MemoryAddress functype_as_externtype(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("functype_as_externtype(x0=" + x0 + ")");
        }
        return Wasm.wasm_functype_as_externtype(x0);
    }

    /**
     * wasm_externtype_t* wasm_globaltype_as_externtype(wasm_globaltype_t*);
     */
    public static MemoryAddress globaltype_as_externtype(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("globaltype_as_externtype(x0=" + x0 + ")");
        }
        return Wasm.wasm_globaltype_as_externtype(x0);
    }

    /**
     * wasm_externtype_t* wasm_tabletype_as_externtype(wasm_tabletype_t*);
     */
    public static MemoryAddress tabletype_as_externtype(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("tabletype_as_externtype(x0=" + x0 + ")");
        }
        return Wasm.wasm_tabletype_as_externtype(x0);
    }

    /**
     * wasm_externtype_t* wasm_memorytype_as_externtype(wasm_memorytype_t*);
     */
    public static MemoryAddress memorytype_as_externtype(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("memorytype_as_externtype(x0=" + x0 + ")");
        }
        return Wasm.wasm_memorytype_as_externtype(x0);
    }

    /**
     * wasm_functype_t* wasm_externtype_as_functype(wasm_externtype_t*);
     */
    public static MemoryAddress externtype_as_functype(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("externtype_as_functype(x0=" + x0 + ")");
        }
        return Wasm.wasm_externtype_as_functype(x0);
    }

    /**
     * wasm_globaltype_t* wasm_externtype_as_globaltype(wasm_externtype_t*);
     */
    public static MemoryAddress externtype_as_globaltype(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("externtype_as_globaltype(x0=" + x0 + ")");
        }
        return Wasm.wasm_externtype_as_globaltype(x0);
    }

    /**
     * wasm_tabletype_t* wasm_externtype_as_tabletype(wasm_externtype_t*);
     */
    public static MemoryAddress externtype_as_tabletype(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("externtype_as_tabletype(x0=" + x0 + ")");
        }
        return Wasm.wasm_externtype_as_tabletype(x0);
    }

    /**
     * wasm_memorytype_t* wasm_externtype_as_memorytype(wasm_externtype_t*);
     */
    public static MemoryAddress externtype_as_memorytype(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("externtype_as_memorytype(x0=" + x0 + ")");
        }
        return Wasm.wasm_externtype_as_memorytype(x0);
    }

    /**
     * const wasm_externtype_t* wasm_functype_as_externtype_const(const wasm_functype_t*);
     */
    public static MemoryAddress functype_as_externtype_const(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("functype_as_externtype_const(x0=" + x0 + ")");
        }
        return Wasm.wasm_functype_as_externtype_const(x0);
    }

    /**
     * const wasm_externtype_t* wasm_globaltype_as_externtype_const(const wasm_globaltype_t*);
     */
    public static MemoryAddress globaltype_as_externtype_const(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("globaltype_as_externtype_const(x0=" + x0 + ")");
        }
        return Wasm.wasm_globaltype_as_externtype_const(x0);
    }

    /**
     * const wasm_externtype_t* wasm_tabletype_as_externtype_const(const wasm_tabletype_t*);
     */
    public static MemoryAddress tabletype_as_externtype_const(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("tabletype_as_externtype_const(x0=" + x0 + ")");
        }
        return Wasm.wasm_tabletype_as_externtype_const(x0);
    }

    /**
     * const wasm_externtype_t* wasm_memorytype_as_externtype_const(const wasm_memorytype_t*);
     */
    public static MemoryAddress memorytype_as_externtype_const(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("memorytype_as_externtype_const(x0=" + x0 + ")");
        }
        return Wasm.wasm_memorytype_as_externtype_const(x0);
    }

    /**
     * const wasm_functype_t* wasm_externtype_as_functype_const(const wasm_externtype_t*);
     */
    public static MemoryAddress externtype_as_functype_const(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("externtype_as_functype_const(x0=" + x0 + ")");
        }
        return Wasm.wasm_externtype_as_functype_const(x0);
    }

    /**
     * const wasm_globaltype_t* wasm_externtype_as_globaltype_const(const wasm_externtype_t*);
     */
    public static MemoryAddress externtype_as_globaltype_const(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("externtype_as_globaltype_const(x0=" + x0 + ")");
        }
        return Wasm.wasm_externtype_as_globaltype_const(x0);
    }

    /**
     * const wasm_tabletype_t* wasm_externtype_as_tabletype_const(const wasm_externtype_t*);
     */
    public static MemoryAddress externtype_as_tabletype_const(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("externtype_as_tabletype_const(x0=" + x0 + ")");
        }
        return Wasm.wasm_externtype_as_tabletype_const(x0);
    }

    /**
     * const wasm_memorytype_t* wasm_externtype_as_memorytype_const(const wasm_externtype_t*);
     */
    public static MemoryAddress externtype_as_memorytype_const(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("externtype_as_memorytype_const(x0=" + x0 + ")");
        }
        return Wasm.wasm_externtype_as_memorytype_const(x0);
    }

    /**
     * void wasm_importtype_delete(wasm_importtype_t*);
     */
    public static void importtype_delete(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("importtype_delete(x0=" + x0 + ")");
        }
        Wasm.wasm_importtype_delete(x0);
    }

    /**
     * void wasm_importtype_vec_new_empty(wasm_importtype_vec_t* out);
     */
    public static void importtype_vec_new_empty(Addressable out) {
        if (logger.isTraceEnabled()) {
            logger.tracev("importtype_vec_new_empty(out=" + out + ")");
        }
        Wasm.wasm_importtype_vec_new_empty(out);
    }

    /**
     * void wasm_importtype_vec_new_uninitialized(wasm_importtype_vec_t* out, size_t);
     */
    public static void importtype_vec_new_uninitialized(Addressable out, long x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("importtype_vec_new_uninitialized(out=" + out + ", x1=" + x1 + ")");
        }
        Wasm.wasm_importtype_vec_new_uninitialized(out, x1);
    }

    /**
     * void wasm_importtype_vec_new(wasm_importtype_vec_t* out, size_t, wasm_importtype_t * const[]);
     */
    public static void importtype_vec_new(Addressable out, long x1, Addressable x2) {
        if (logger.isTraceEnabled()) {
            logger.tracev("importtype_vec_new(out=" + out + ", x1=" + x1 + ", x2=" + x2 + ")");
        }
        Wasm.wasm_importtype_vec_new(out, x1, x2);
    }

    /**
     * void wasm_importtype_vec_copy(wasm_importtype_vec_t* out, const wasm_importtype_vec_t*);
     */
    public static void importtype_vec_copy(Addressable out, Addressable x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("importtype_vec_copy(out=" + out + ", x1=" + x1 + ")");
        }
        Wasm.wasm_importtype_vec_copy(out, x1);
    }

    /**
     * void wasm_importtype_vec_delete(wasm_importtype_vec_t*);
     */
    public static void importtype_vec_delete(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("importtype_vec_delete(x0=" + x0 + ")");
        }
        Wasm.wasm_importtype_vec_delete(x0);
    }

    /**
     * wasm_importtype_t* wasm_importtype_copy(const wasm_importtype_t*);
     */
    public static MemoryAddress importtype_copy(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("importtype_copy(x0=" + x0 + ")");
        }
        return Wasm.wasm_importtype_copy(x0);
    }

    /**
     * wasm_importtype_t* wasm_importtype_new(wasm_name_t* module, wasm_name_t* name, wasm_externtype_t*);
     */
    public static MemoryAddress importtype_new(Addressable module, Addressable name, Addressable x2) {
        if (logger.isTraceEnabled()) {
            logger.tracev("importtype_new(module=" + module + ", name=" + name + ", x2=" + x2 + ")");
        }
        return Wasm.wasm_importtype_new(module, name, x2);
    }

    /**
     * const wasm_name_t* wasm_importtype_module(const wasm_importtype_t*);
     */
    public static MemoryAddress importtype_module(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("importtype_module(x0=" + x0 + ")");
        }
        return Wasm.wasm_importtype_module(x0);
    }

    /**
     * const wasm_name_t* wasm_importtype_name(const wasm_importtype_t*);
     */
    public static MemoryAddress importtype_name(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("importtype_name(x0=" + x0 + ")");
        }
        return Wasm.wasm_importtype_name(x0);
    }

    /**
     * const wasm_externtype_t* wasm_importtype_type(const wasm_importtype_t*);
     */
    public static MemoryAddress importtype_type(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("importtype_type(x0=" + x0 + ")");
        }
        return Wasm.wasm_importtype_type(x0);
    }

    /**
     * void wasm_exporttype_delete(wasm_exporttype_t*);
     */
    public static void exporttype_delete(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("exporttype_delete(x0=" + x0 + ")");
        }
        Wasm.wasm_exporttype_delete(x0);
    }

    /**
     * void wasm_exporttype_vec_new_empty(wasm_exporttype_vec_t* out);
     */
    public static void exporttype_vec_new_empty(Addressable out) {
        if (logger.isTraceEnabled()) {
            logger.tracev("exporttype_vec_new_empty(out=" + out + ")");
        }
        Wasm.wasm_exporttype_vec_new_empty(out);
    }

    /**
     * void wasm_exporttype_vec_new_uninitialized(wasm_exporttype_vec_t* out, size_t);
     */
    public static void exporttype_vec_new_uninitialized(Addressable out, long x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("exporttype_vec_new_uninitialized(out=" + out + ", x1=" + x1 + ")");
        }
        Wasm.wasm_exporttype_vec_new_uninitialized(out, x1);
    }

    /**
     * void wasm_exporttype_vec_new(wasm_exporttype_vec_t* out, size_t, wasm_exporttype_t * const[]);
     */
    public static void exporttype_vec_new(Addressable out, long x1, Addressable x2) {
        if (logger.isTraceEnabled()) {
            logger.tracev("exporttype_vec_new(out=" + out + ", x1=" + x1 + ", x2=" + x2 + ")");
        }
        Wasm.wasm_exporttype_vec_new(out, x1, x2);
    }

    /**
     * void wasm_exporttype_vec_copy(wasm_exporttype_vec_t* out, const wasm_exporttype_vec_t*);
     */
    public static void exporttype_vec_copy(Addressable out, Addressable x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("exporttype_vec_copy(out=" + out + ", x1=" + x1 + ")");
        }
        Wasm.wasm_exporttype_vec_copy(out, x1);
    }

    /**
     * void wasm_exporttype_vec_delete(wasm_exporttype_vec_t*);
     */
    public static void exporttype_vec_delete(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("exporttype_vec_delete(x0=" + x0 + ")");
        }
        Wasm.wasm_exporttype_vec_delete(x0);
    }

    /**
     * wasm_exporttype_t* wasm_exporttype_copy(const wasm_exporttype_t*);
     */
    public static MemoryAddress exporttype_copy(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("exporttype_copy(x0=" + x0 + ")");
        }
        return Wasm.wasm_exporttype_copy(x0);
    }

    /**
     * wasm_exporttype_t* wasm_exporttype_new(wasm_name_t*, wasm_externtype_t*);
     */
    public static MemoryAddress exporttype_new(Addressable x0, Addressable x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("exporttype_new(x0=" + x0 + ", x1=" + x1 + ")");
        }
        return Wasm.wasm_exporttype_new(x0, x1);
    }

    /**
     * const wasm_name_t* wasm_exporttype_name(const wasm_exporttype_t*);
     */
    public static MemoryAddress exporttype_name(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("exporttype_name(x0=" + x0 + ")");
        }
        return Wasm.wasm_exporttype_name(x0);
    }

    /**
     * const wasm_externtype_t* wasm_exporttype_type(const wasm_exporttype_t*);
     */
    public static MemoryAddress exporttype_type(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("exporttype_type(x0=" + x0 + ")");
        }
        return Wasm.wasm_exporttype_type(x0);
    }

    /**
     * void wasm_val_delete(wasm_val_t* v);
     */
    public static void val_delete(Addressable v) {
        if (logger.isTraceEnabled()) {
            logger.tracev("val_delete(v=" + v + ")");
        }
        Wasm.wasm_val_delete(v);
    }

    /**
     * void wasm_val_copy(wasm_val_t* out, const wasm_val_t*);
     */
    public static void val_copy(Addressable out, Addressable x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("val_copy(out=" + out + ", x1=" + x1 + ")");
        }
        Wasm.wasm_val_copy(out, x1);
    }

    /**
     * void wasm_val_vec_new_empty(wasm_val_vec_t* out);
     */
    public static void val_vec_new_empty(Addressable out) {
        if (logger.isTraceEnabled()) {
            logger.tracev("val_vec_new_empty(out=" + out + ")");
        }
        Wasm.wasm_val_vec_new_empty(out);
    }

    /**
     * void wasm_val_vec_new_uninitialized(wasm_val_vec_t* out, size_t);
     */
    public static void val_vec_new_uninitialized(Addressable out, long x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("val_vec_new_uninitialized(out=" + out + ", x1=" + x1 + ")");
        }
        Wasm.wasm_val_vec_new_uninitialized(out, x1);
    }

    /**
     * void wasm_val_vec_new(wasm_val_vec_t* out, size_t, wasm_val_t const[]);
     */
    public static void val_vec_new(Addressable out, long x1, Addressable x2) {
        if (logger.isTraceEnabled()) {
            logger.tracev("val_vec_new(out=" + out + ", x1=" + x1 + ", x2=" + x2 + ")");
        }
        Wasm.wasm_val_vec_new(out, x1, x2);
    }

    /**
     * void wasm_val_vec_copy(wasm_val_vec_t* out, const wasm_val_vec_t*);
     */
    public static void val_vec_copy(Addressable out, Addressable x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("val_vec_copy(out=" + out + ", x1=" + x1 + ")");
        }
        Wasm.wasm_val_vec_copy(out, x1);
    }

    /**
     * void wasm_val_vec_delete(wasm_val_vec_t*);
     */
    public static void val_vec_delete(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("val_vec_delete(x0=" + x0 + ")");
        }
        Wasm.wasm_val_vec_delete(x0);
    }

    /**
     * void wasm_ref_delete(wasm_ref_t*);
     */
    public static void ref_delete(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("ref_delete(x0=" + x0 + ")");
        }
        Wasm.wasm_ref_delete(x0);
    }

    /**
     * wasm_ref_t* wasm_ref_copy(const wasm_ref_t*);
     */
    public static MemoryAddress ref_copy(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("ref_copy(x0=" + x0 + ")");
        }
        return Wasm.wasm_ref_copy(x0);
    }

    /**
     * _Bool wasm_ref_same(const wasm_ref_t*, const wasm_ref_t*);
     */
    public static byte ref_same(Addressable x0, Addressable x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("ref_same(x0=" + x0 + ", x1=" + x1 + ")");
        }
        return Wasm.wasm_ref_same(x0, x1);
    }

    /**
     * void* wasm_ref_get_host_info(const wasm_ref_t*);
     */
    public static MemoryAddress ref_get_host_info(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("ref_get_host_info(x0=" + x0 + ")");
        }
        return Wasm.wasm_ref_get_host_info(x0);
    }

    /**
     * void wasm_ref_set_host_info(wasm_ref_t*, void*);
     */
    public static void ref_set_host_info(Addressable x0, Addressable x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("ref_set_host_info(x0=" + x0 + ", x1=" + x1 + ")");
        }
        Wasm.wasm_ref_set_host_info(x0, x1);
    }

    /**
     * void wasm_ref_set_host_info_with_finalizer(wasm_ref_t*, void*, void (*)(void*));
     */
    public static void ref_set_host_info_with_finalizer(Addressable x0, Addressable x1, Addressable x2) {
        if (logger.isTraceEnabled()) {
            logger.tracev("ref_set_host_info_with_finalizer(x0=" + x0 + ", x1=" + x1 + ", x2=" + x2 + ")");
        }
        Wasm.wasm_ref_set_host_info_with_finalizer(x0, x1, x2);
    }

    /**
     * void wasm_frame_delete(wasm_frame_t*);
     */
    public static void frame_delete(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("frame_delete(x0=" + x0 + ")");
        }
        Wasm.wasm_frame_delete(x0);
    }

    /**
     * void wasm_frame_vec_new_empty(wasm_frame_vec_t* out);
     */
    public static void frame_vec_new_empty(Addressable out) {
        if (logger.isTraceEnabled()) {
            logger.tracev("frame_vec_new_empty(out=" + out + ")");
        }
        Wasm.wasm_frame_vec_new_empty(out);
    }

    /**
     * void wasm_frame_vec_new_uninitialized(wasm_frame_vec_t* out, size_t);
     */
    public static void frame_vec_new_uninitialized(Addressable out, long x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("frame_vec_new_uninitialized(out=" + out + ", x1=" + x1 + ")");
        }
        Wasm.wasm_frame_vec_new_uninitialized(out, x1);
    }

    /**
     * void wasm_frame_vec_new(wasm_frame_vec_t* out, size_t, wasm_frame_t * const[]);
     */
    public static void frame_vec_new(Addressable out, long x1, Addressable x2) {
        if (logger.isTraceEnabled()) {
            logger.tracev("frame_vec_new(out=" + out + ", x1=" + x1 + ", x2=" + x2 + ")");
        }
        Wasm.wasm_frame_vec_new(out, x1, x2);
    }

    /**
     * void wasm_frame_vec_copy(wasm_frame_vec_t* out, const wasm_frame_vec_t*);
     */
    public static void frame_vec_copy(Addressable out, Addressable x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("frame_vec_copy(out=" + out + ", x1=" + x1 + ")");
        }
        Wasm.wasm_frame_vec_copy(out, x1);
    }

    /**
     * void wasm_frame_vec_delete(wasm_frame_vec_t*);
     */
    public static void frame_vec_delete(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("frame_vec_delete(x0=" + x0 + ")");
        }
        Wasm.wasm_frame_vec_delete(x0);
    }

    /**
     * wasm_frame_t* wasm_frame_copy(const wasm_frame_t*);
     */
    public static MemoryAddress frame_copy(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("frame_copy(x0=" + x0 + ")");
        }
        return Wasm.wasm_frame_copy(x0);
    }

    /**
     * struct wasm_instance_t* wasm_frame_instance(const wasm_frame_t*);
     */
    public static MemoryAddress frame_instance(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("frame_instance(x0=" + x0 + ")");
        }
        return Wasm.wasm_frame_instance(x0);
    }

    /**
     * uint32_t wasm_frame_func_index(const wasm_frame_t*);
     */
    public static int frame_func_index(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("frame_func_index(x0=" + x0 + ")");
        }
        return Wasm.wasm_frame_func_index(x0);
    }

    /**
     * size_t wasm_frame_func_offset(const wasm_frame_t*);
     */
    public static long frame_func_offset(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("frame_func_offset(x0=" + x0 + ")");
        }
        return Wasm.wasm_frame_func_offset(x0);
    }

    /**
     * size_t wasm_frame_module_offset(const wasm_frame_t*);
     */
    public static long frame_module_offset(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("frame_module_offset(x0=" + x0 + ")");
        }
        return Wasm.wasm_frame_module_offset(x0);
    }

    /**
     * void wasm_trap_delete(wasm_trap_t*);
     */
    public static void trap_delete(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("trap_delete(x0=" + x0 + ")");
        }
        Wasm.wasm_trap_delete(x0);
    }

    /**
     * wasm_trap_t* wasm_trap_copy(const wasm_trap_t*);
     */
    public static MemoryAddress trap_copy(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("trap_copy(x0=" + x0 + ")");
        }
        return Wasm.wasm_trap_copy(x0);
    }

    /**
     * _Bool wasm_trap_same(const wasm_trap_t*, const wasm_trap_t*);
     */
    public static byte trap_same(Addressable x0, Addressable x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("trap_same(x0=" + x0 + ", x1=" + x1 + ")");
        }
        return Wasm.wasm_trap_same(x0, x1);
    }

    /**
     * void* wasm_trap_get_host_info(const wasm_trap_t*);
     */
    public static MemoryAddress trap_get_host_info(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("trap_get_host_info(x0=" + x0 + ")");
        }
        return Wasm.wasm_trap_get_host_info(x0);
    }

    /**
     * void wasm_trap_set_host_info(wasm_trap_t*, void*);
     */
    public static void trap_set_host_info(Addressable x0, Addressable x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("trap_set_host_info(x0=" + x0 + ", x1=" + x1 + ")");
        }
        Wasm.wasm_trap_set_host_info(x0, x1);
    }

    /**
     * void wasm_trap_set_host_info_with_finalizer(wasm_trap_t*, void*, void (*)(void*));
     */
    public static void trap_set_host_info_with_finalizer(Addressable x0, Addressable x1, Addressable x2) {
        if (logger.isTraceEnabled()) {
            logger.tracev("trap_set_host_info_with_finalizer(x0=" + x0 + ", x1=" + x1 + ", x2=" + x2 + ")");
        }
        Wasm.wasm_trap_set_host_info_with_finalizer(x0, x1, x2);
    }

    /**
     * wasm_ref_t* wasm_trap_as_ref(wasm_trap_t*);
     */
    public static MemoryAddress trap_as_ref(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("trap_as_ref(x0=" + x0 + ")");
        }
        return Wasm.wasm_trap_as_ref(x0);
    }

    /**
     * wasm_trap_t* wasm_ref_as_trap(wasm_ref_t*);
     */
    public static MemoryAddress ref_as_trap(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("ref_as_trap(x0=" + x0 + ")");
        }
        return Wasm.wasm_ref_as_trap(x0);
    }

    /**
     * const wasm_ref_t* wasm_trap_as_ref_const(const wasm_trap_t*);
     */
    public static MemoryAddress trap_as_ref_const(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("trap_as_ref_const(x0=" + x0 + ")");
        }
        return Wasm.wasm_trap_as_ref_const(x0);
    }

    /**
     * const wasm_trap_t* wasm_ref_as_trap_const(const wasm_ref_t*);
     */
    public static MemoryAddress ref_as_trap_const(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("ref_as_trap_const(x0=" + x0 + ")");
        }
        return Wasm.wasm_ref_as_trap_const(x0);
    }

    /**
     * wasm_trap_t* wasm_trap_new(wasm_store_t* store, const wasm_message_t*);
     */
    public static MemoryAddress trap_new(Addressable store, Addressable x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("trap_new(store=" + store + ", x1=" + x1 + ")");
        }
        return Wasm.wasm_trap_new(store, x1);
    }

    /**
     * void wasm_trap_message(const wasm_trap_t*, wasm_message_t* out);
     */
    public static void trap_message(Addressable x0, Addressable out) {
        if (logger.isTraceEnabled()) {
            logger.tracev("trap_message(x0=" + x0 + ", out=" + out + ")");
        }
        Wasm.wasm_trap_message(x0, out);
    }

    /**
     * wasm_frame_t* wasm_trap_origin(const wasm_trap_t*);
     */
    public static MemoryAddress trap_origin(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("trap_origin(x0=" + x0 + ")");
        }
        return Wasm.wasm_trap_origin(x0);
    }

    /**
     * void wasm_trap_trace(const wasm_trap_t*, wasm_frame_vec_t* out);
     */
    public static void trap_trace(Addressable x0, Addressable out) {
        if (logger.isTraceEnabled()) {
            logger.tracev("trap_trace(x0=" + x0 + ", out=" + out + ")");
        }
        Wasm.wasm_trap_trace(x0, out);
    }

    /**
     * void wasm_foreign_delete(wasm_foreign_t*);
     */
    public static void foreign_delete(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("foreign_delete(x0=" + x0 + ")");
        }
        Wasm.wasm_foreign_delete(x0);
    }

    /**
     * wasm_foreign_t* wasm_foreign_copy(const wasm_foreign_t*);
     */
    public static MemoryAddress foreign_copy(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("foreign_copy(x0=" + x0 + ")");
        }
        return Wasm.wasm_foreign_copy(x0);
    }

    /**
     * _Bool wasm_foreign_same(const wasm_foreign_t*, const wasm_foreign_t*);
     */
    public static byte foreign_same(Addressable x0, Addressable x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("foreign_same(x0=" + x0 + ", x1=" + x1 + ")");
        }
        return Wasm.wasm_foreign_same(x0, x1);
    }

    /**
     * void* wasm_foreign_get_host_info(const wasm_foreign_t*);
     */
    public static MemoryAddress foreign_get_host_info(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("foreign_get_host_info(x0=" + x0 + ")");
        }
        return Wasm.wasm_foreign_get_host_info(x0);
    }

    /**
     * void wasm_foreign_set_host_info(wasm_foreign_t*, void*);
     */
    public static void foreign_set_host_info(Addressable x0, Addressable x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("foreign_set_host_info(x0=" + x0 + ", x1=" + x1 + ")");
        }
        Wasm.wasm_foreign_set_host_info(x0, x1);
    }

    /**
     * void wasm_foreign_set_host_info_with_finalizer(wasm_foreign_t*, void*, void (*)(void*));
     */
    public static void foreign_set_host_info_with_finalizer(Addressable x0, Addressable x1, Addressable x2) {
        if (logger.isTraceEnabled()) {
            logger.tracev("foreign_set_host_info_with_finalizer(x0=" + x0 + ", x1=" + x1 + ", x2=" + x2 + ")");
        }
        Wasm.wasm_foreign_set_host_info_with_finalizer(x0, x1, x2);
    }

    /**
     * wasm_ref_t* wasm_foreign_as_ref(wasm_foreign_t*);
     */
    public static MemoryAddress foreign_as_ref(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("foreign_as_ref(x0=" + x0 + ")");
        }
        return Wasm.wasm_foreign_as_ref(x0);
    }

    /**
     * wasm_foreign_t* wasm_ref_as_foreign(wasm_ref_t*);
     */
    public static MemoryAddress ref_as_foreign(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("ref_as_foreign(x0=" + x0 + ")");
        }
        return Wasm.wasm_ref_as_foreign(x0);
    }

    /**
     * const wasm_ref_t* wasm_foreign_as_ref_const(const wasm_foreign_t*);
     */
    public static MemoryAddress foreign_as_ref_const(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("foreign_as_ref_const(x0=" + x0 + ")");
        }
        return Wasm.wasm_foreign_as_ref_const(x0);
    }

    /**
     * const wasm_foreign_t* wasm_ref_as_foreign_const(const wasm_ref_t*);
     */
    public static MemoryAddress ref_as_foreign_const(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("ref_as_foreign_const(x0=" + x0 + ")");
        }
        return Wasm.wasm_ref_as_foreign_const(x0);
    }

    /**
     * wasm_foreign_t* wasm_foreign_new(wasm_store_t*);
     */
    public static MemoryAddress foreign_new(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("foreign_new(x0=" + x0 + ")");
        }
        return Wasm.wasm_foreign_new(x0);
    }

    /**
     * void wasm_module_delete(wasm_module_t*);
     */
    public static void module_delete(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("module_delete(x0=" + x0 + ")");
        }
        Wasm.wasm_module_delete(x0);
    }

    /**
     * wasm_module_t* wasm_module_copy(const wasm_module_t*);
     */
    public static MemoryAddress module_copy(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("module_copy(x0=" + x0 + ")");
        }
        return Wasm.wasm_module_copy(x0);
    }

    /**
     * _Bool wasm_module_same(const wasm_module_t*, const wasm_module_t*);
     */
    public static byte module_same(Addressable x0, Addressable x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("module_same(x0=" + x0 + ", x1=" + x1 + ")");
        }
        return Wasm.wasm_module_same(x0, x1);
    }

    /**
     * void* wasm_module_get_host_info(const wasm_module_t*);
     */
    public static MemoryAddress module_get_host_info(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("module_get_host_info(x0=" + x0 + ")");
        }
        return Wasm.wasm_module_get_host_info(x0);
    }

    /**
     * void wasm_module_set_host_info(wasm_module_t*, void*);
     */
    public static void module_set_host_info(Addressable x0, Addressable x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("module_set_host_info(x0=" + x0 + ", x1=" + x1 + ")");
        }
        Wasm.wasm_module_set_host_info(x0, x1);
    }

    /**
     * void wasm_module_set_host_info_with_finalizer(wasm_module_t*, void*, void (*)(void*));
     */
    public static void module_set_host_info_with_finalizer(Addressable x0, Addressable x1, Addressable x2) {
        if (logger.isTraceEnabled()) {
            logger.tracev("module_set_host_info_with_finalizer(x0=" + x0 + ", x1=" + x1 + ", x2=" + x2 + ")");
        }
        Wasm.wasm_module_set_host_info_with_finalizer(x0, x1, x2);
    }

    /**
     * wasm_ref_t* wasm_module_as_ref(wasm_module_t*);
     */
    public static MemoryAddress module_as_ref(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("module_as_ref(x0=" + x0 + ")");
        }
        return Wasm.wasm_module_as_ref(x0);
    }

    /**
     * wasm_module_t* wasm_ref_as_module(wasm_ref_t*);
     */
    public static MemoryAddress ref_as_module(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("ref_as_module(x0=" + x0 + ")");
        }
        return Wasm.wasm_ref_as_module(x0);
    }

    /**
     * const wasm_ref_t* wasm_module_as_ref_const(const wasm_module_t*);
     */
    public static MemoryAddress module_as_ref_const(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("module_as_ref_const(x0=" + x0 + ")");
        }
        return Wasm.wasm_module_as_ref_const(x0);
    }

    /**
     * const wasm_module_t* wasm_ref_as_module_const(const wasm_ref_t*);
     */
    public static MemoryAddress ref_as_module_const(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("ref_as_module_const(x0=" + x0 + ")");
        }
        return Wasm.wasm_ref_as_module_const(x0);
    }

    /**
     * void wasm_shared_module_delete(wasm_shared_module_t*);
     */
    public static void shared_module_delete(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("shared_module_delete(x0=" + x0 + ")");
        }
        Wasm.wasm_shared_module_delete(x0);
    }

    /**
     * wasm_shared_module_t* wasm_module_share(const wasm_module_t*);
     */
    public static MemoryAddress module_share(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("module_share(x0=" + x0 + ")");
        }
        return Wasm.wasm_module_share(x0);
    }

    /**
     * wasm_module_t* wasm_module_obtain(wasm_store_t*, const wasm_shared_module_t*);
     */
    public static MemoryAddress module_obtain(Addressable x0, Addressable x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("module_obtain(x0=" + x0 + ", x1=" + x1 + ")");
        }
        return Wasm.wasm_module_obtain(x0, x1);
    }

    /**
     * wasm_module_t* wasm_module_new(wasm_store_t*, const wasm_byte_vec_t* binary);
     */
    public static MemoryAddress module_new(Addressable x0, Addressable binary) {
        if (logger.isTraceEnabled()) {
            logger.tracev("module_new(x0=" + x0 + ", binary=" + binary + ")");
        }
        return Wasm.wasm_module_new(x0, binary);
    }

    /**
     * _Bool wasm_module_validate(wasm_store_t*, const wasm_byte_vec_t* binary);
     */
    public static byte module_validate(Addressable x0, Addressable binary) {
        if (logger.isTraceEnabled()) {
            logger.tracev("module_validate(x0=" + x0 + ", binary=" + binary + ")");
        }
        return Wasm.wasm_module_validate(x0, binary);
    }

    /**
     * void wasm_module_imports(const wasm_module_t*, wasm_importtype_vec_t* out);
     */
    public static void module_imports(Addressable x0, Addressable out) {
        if (logger.isTraceEnabled()) {
            logger.tracev("module_imports(x0=" + x0 + ", out=" + out + ")");
        }
        Wasm.wasm_module_imports(x0, out);
    }

    /**
     * void wasm_module_exports(const wasm_module_t*, wasm_exporttype_vec_t* out);
     */
    public static void module_exports(Addressable x0, Addressable out) {
        if (logger.isTraceEnabled()) {
            logger.tracev("module_exports(x0=" + x0 + ", out=" + out + ")");
        }
        Wasm.wasm_module_exports(x0, out);
    }

    /**
     * void wasm_module_serialize(const wasm_module_t*, wasm_byte_vec_t* out);
     */
    public static void module_serialize(Addressable x0, Addressable out) {
        if (logger.isTraceEnabled()) {
            logger.tracev("module_serialize(x0=" + x0 + ", out=" + out + ")");
        }
        Wasm.wasm_module_serialize(x0, out);
    }

    /**
     * wasm_module_t* wasm_module_deserialize(wasm_store_t*, const wasm_byte_vec_t*);
     */
    public static MemoryAddress module_deserialize(Addressable x0, Addressable x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("module_deserialize(x0=" + x0 + ", x1=" + x1 + ")");
        }
        return Wasm.wasm_module_deserialize(x0, x1);
    }

    /**
     * void wasm_func_delete(wasm_func_t*);
     */
    public static void func_delete(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("func_delete(x0=" + x0 + ")");
        }
        Wasm.wasm_func_delete(x0);
    }

    /**
     * wasm_func_t* wasm_func_copy(const wasm_func_t*);
     */
    public static MemoryAddress func_copy(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("func_copy(x0=" + x0 + ")");
        }
        return Wasm.wasm_func_copy(x0);
    }

    /**
     * _Bool wasm_func_same(const wasm_func_t*, const wasm_func_t*);
     */
    public static byte func_same(Addressable x0, Addressable x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("func_same(x0=" + x0 + ", x1=" + x1 + ")");
        }
        return Wasm.wasm_func_same(x0, x1);
    }

    /**
     * void* wasm_func_get_host_info(const wasm_func_t*);
     */
    public static MemoryAddress func_get_host_info(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("func_get_host_info(x0=" + x0 + ")");
        }
        return Wasm.wasm_func_get_host_info(x0);
    }

    /**
     * void wasm_func_set_host_info(wasm_func_t*, void*);
     */
    public static void func_set_host_info(Addressable x0, Addressable x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("func_set_host_info(x0=" + x0 + ", x1=" + x1 + ")");
        }
        Wasm.wasm_func_set_host_info(x0, x1);
    }

    /**
     * void wasm_func_set_host_info_with_finalizer(wasm_func_t*, void*, void (*)(void*));
     */
    public static void func_set_host_info_with_finalizer(Addressable x0, Addressable x1, Addressable x2) {
        if (logger.isTraceEnabled()) {
            logger.tracev("func_set_host_info_with_finalizer(x0=" + x0 + ", x1=" + x1 + ", x2=" + x2 + ")");
        }
        Wasm.wasm_func_set_host_info_with_finalizer(x0, x1, x2);
    }

    /**
     * wasm_ref_t* wasm_func_as_ref(wasm_func_t*);
     */
    public static MemoryAddress func_as_ref(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("func_as_ref(x0=" + x0 + ")");
        }
        return Wasm.wasm_func_as_ref(x0);
    }

    /**
     * wasm_func_t* wasm_ref_as_func(wasm_ref_t*);
     */
    public static MemoryAddress ref_as_func(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("ref_as_func(x0=" + x0 + ")");
        }
        return Wasm.wasm_ref_as_func(x0);
    }

    /**
     * const wasm_ref_t* wasm_func_as_ref_const(const wasm_func_t*);
     */
    public static MemoryAddress func_as_ref_const(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("func_as_ref_const(x0=" + x0 + ")");
        }
        return Wasm.wasm_func_as_ref_const(x0);
    }

    /**
     * const wasm_func_t* wasm_ref_as_func_const(const wasm_ref_t*);
     */
    public static MemoryAddress ref_as_func_const(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("ref_as_func_const(x0=" + x0 + ")");
        }
        return Wasm.wasm_ref_as_func_const(x0);
    }

    /**
     * wasm_func_t* wasm_func_new(wasm_store_t*, const wasm_functype_t*, wasm_func_callback_t);
     */
    public static MemoryAddress func_new(Addressable x0, Addressable x1, Addressable x2) {
        if (logger.isTraceEnabled()) {
            logger.tracev("func_new(x0=" + x0 + ", x1=" + x1 + ", x2=" + x2 + ")");
        }
        return Wasm.wasm_func_new(x0, x1, x2);
    }

    /**
     * wasm_func_t* wasm_func_new_with_env(wasm_store_t*, const wasm_functype_t* type, wasm_func_callback_with_env_t,void* env, void (*finalizer)(void*));
     */
    public static MemoryAddress func_new_with_env(Addressable x0, Addressable type, Addressable x2, Addressable env, Addressable finalizer) {
        if (logger.isTraceEnabled()) {
            logger.tracev("func_new_with_env(x0=" + x0 + ", type=" + type + ", x2=" + x2 + ", env=" + env + ", finalizer=" + finalizer + ")");
        }
        return Wasm.wasm_func_new_with_env(x0, type, x2, env, finalizer);
    }

    /**
     * wasm_functype_t* wasm_func_type(const wasm_func_t*);
     */
    public static MemoryAddress func_type(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("func_type(x0=" + x0 + ")");
        }
        return Wasm.wasm_func_type(x0);
    }

    /**
     * size_t wasm_func_param_arity(const wasm_func_t*);
     */
    public static long func_param_arity(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("func_param_arity(x0=" + x0 + ")");
        }
        return Wasm.wasm_func_param_arity(x0);
    }

    /**
     * size_t wasm_func_result_arity(const wasm_func_t*);
     */
    public static long func_result_arity(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("func_result_arity(x0=" + x0 + ")");
        }
        return Wasm.wasm_func_result_arity(x0);
    }

    /**
     * wasm_trap_t* wasm_func_call(const wasm_func_t*, const wasm_val_vec_t* args, wasm_val_vec_t* results);
     */
    public static MemoryAddress func_call(Addressable x0, Addressable args, Addressable results) {
        if (logger.isTraceEnabled()) {
            logger.tracev("func_call(x0=" + x0 + ", args=" + args + ", results=" + results + ")");
        }
        return Wasm.wasm_func_call(x0, args, results);
    }

    /**
     * void wasm_global_delete(wasm_global_t*);
     */
    public static void global_delete(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("global_delete(x0=" + x0 + ")");
        }
        Wasm.wasm_global_delete(x0);
    }

    /**
     * wasm_global_t* wasm_global_copy(const wasm_global_t*);
     */
    public static MemoryAddress global_copy(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("global_copy(x0=" + x0 + ")");
        }
        return Wasm.wasm_global_copy(x0);
    }

    /**
     * _Bool wasm_global_same(const wasm_global_t*, const wasm_global_t*);
     */
    public static byte global_same(Addressable x0, Addressable x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("global_same(x0=" + x0 + ", x1=" + x1 + ")");
        }
        return Wasm.wasm_global_same(x0, x1);
    }

    /**
     * void* wasm_global_get_host_info(const wasm_global_t*);
     */
    public static MemoryAddress global_get_host_info(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("global_get_host_info(x0=" + x0 + ")");
        }
        return Wasm.wasm_global_get_host_info(x0);
    }

    /**
     * void wasm_global_set_host_info(wasm_global_t*, void*);
     */
    public static void global_set_host_info(Addressable x0, Addressable x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("global_set_host_info(x0=" + x0 + ", x1=" + x1 + ")");
        }
        Wasm.wasm_global_set_host_info(x0, x1);
    }

    /**
     * void wasm_global_set_host_info_with_finalizer(wasm_global_t*, void*, void (*)(void*));
     */
    public static void global_set_host_info_with_finalizer(Addressable x0, Addressable x1, Addressable x2) {
        if (logger.isTraceEnabled()) {
            logger.tracev("global_set_host_info_with_finalizer(x0=" + x0 + ", x1=" + x1 + ", x2=" + x2 + ")");
        }
        Wasm.wasm_global_set_host_info_with_finalizer(x0, x1, x2);
    }

    /**
     * wasm_ref_t* wasm_global_as_ref(wasm_global_t*);
     */
    public static MemoryAddress global_as_ref(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("global_as_ref(x0=" + x0 + ")");
        }
        return Wasm.wasm_global_as_ref(x0);
    }

    /**
     * wasm_global_t* wasm_ref_as_global(wasm_ref_t*);
     */
    public static MemoryAddress ref_as_global(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("ref_as_global(x0=" + x0 + ")");
        }
        return Wasm.wasm_ref_as_global(x0);
    }

    /**
     * const wasm_ref_t* wasm_global_as_ref_const(const wasm_global_t*);
     */
    public static MemoryAddress global_as_ref_const(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("global_as_ref_const(x0=" + x0 + ")");
        }
        return Wasm.wasm_global_as_ref_const(x0);
    }

    /**
     * const wasm_global_t* wasm_ref_as_global_const(const wasm_ref_t*);
     */
    public static MemoryAddress ref_as_global_const(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("ref_as_global_const(x0=" + x0 + ")");
        }
        return Wasm.wasm_ref_as_global_const(x0);
    }

    /**
     * wasm_global_t* wasm_global_new(wasm_store_t*, const wasm_globaltype_t*, const wasm_val_t*);
     */
    public static MemoryAddress global_new(Addressable x0, Addressable x1, Addressable x2) {
        if (logger.isTraceEnabled()) {
            logger.tracev("global_new(x0=" + x0 + ", x1=" + x1 + ", x2=" + x2 + ")");
        }
        return Wasm.wasm_global_new(x0, x1, x2);
    }

    /**
     * wasm_globaltype_t* wasm_global_type(const wasm_global_t*);
     */
    public static MemoryAddress global_type(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("global_type(x0=" + x0 + ")");
        }
        return Wasm.wasm_global_type(x0);
    }

    /**
     * void wasm_global_get(const wasm_global_t*, wasm_val_t* out);
     */
    public static void global_get(Addressable x0, Addressable out) {
        if (logger.isTraceEnabled()) {
            logger.tracev("global_get(x0=" + x0 + ", out=" + out + ")");
        }
        Wasm.wasm_global_get(x0, out);
    }

    /**
     * void wasm_global_set(wasm_global_t*, const wasm_val_t*);
     */
    public static void global_set(Addressable x0, Addressable x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("global_set(x0=" + x0 + ", x1=" + x1 + ")");
        }
        Wasm.wasm_global_set(x0, x1);
    }

    /**
     * void wasm_table_delete(wasm_table_t*);
     */
    public static void table_delete(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("table_delete(x0=" + x0 + ")");
        }
        Wasm.wasm_table_delete(x0);
    }

    /**
     * wasm_table_t* wasm_table_copy(const wasm_table_t*);
     */
    public static MemoryAddress table_copy(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("table_copy(x0=" + x0 + ")");
        }
        return Wasm.wasm_table_copy(x0);
    }

    /**
     * _Bool wasm_table_same(const wasm_table_t*, const wasm_table_t*);
     */
    public static byte table_same(Addressable x0, Addressable x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("table_same(x0=" + x0 + ", x1=" + x1 + ")");
        }
        return Wasm.wasm_table_same(x0, x1);
    }

    /**
     * void* wasm_table_get_host_info(const wasm_table_t*);
     */
    public static MemoryAddress table_get_host_info(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("table_get_host_info(x0=" + x0 + ")");
        }
        return Wasm.wasm_table_get_host_info(x0);
    }

    /**
     * void wasm_table_set_host_info(wasm_table_t*, void*);
     */
    public static void table_set_host_info(Addressable x0, Addressable x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("table_set_host_info(x0=" + x0 + ", x1=" + x1 + ")");
        }
        Wasm.wasm_table_set_host_info(x0, x1);
    }

    /**
     * void wasm_table_set_host_info_with_finalizer(wasm_table_t*, void*, void (*)(void*));
     */
    public static void table_set_host_info_with_finalizer(Addressable x0, Addressable x1, Addressable x2) {
        if (logger.isTraceEnabled()) {
            logger.tracev("table_set_host_info_with_finalizer(x0=" + x0 + ", x1=" + x1 + ", x2=" + x2 + ")");
        }
        Wasm.wasm_table_set_host_info_with_finalizer(x0, x1, x2);
    }

    /**
     * wasm_ref_t* wasm_table_as_ref(wasm_table_t*);
     */
    public static MemoryAddress table_as_ref(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("table_as_ref(x0=" + x0 + ")");
        }
        return Wasm.wasm_table_as_ref(x0);
    }

    /**
     * wasm_table_t* wasm_ref_as_table(wasm_ref_t*);
     */
    public static MemoryAddress ref_as_table(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("ref_as_table(x0=" + x0 + ")");
        }
        return Wasm.wasm_ref_as_table(x0);
    }

    /**
     * const wasm_ref_t* wasm_table_as_ref_const(const wasm_table_t*);
     */
    public static MemoryAddress table_as_ref_const(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("table_as_ref_const(x0=" + x0 + ")");
        }
        return Wasm.wasm_table_as_ref_const(x0);
    }

    /**
     * const wasm_table_t* wasm_ref_as_table_const(const wasm_ref_t*);
     */
    public static MemoryAddress ref_as_table_const(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("ref_as_table_const(x0=" + x0 + ")");
        }
        return Wasm.wasm_ref_as_table_const(x0);
    }

    /**
     * wasm_table_t* wasm_table_new(wasm_store_t*, const wasm_tabletype_t*, wasm_ref_t* init);
     */
    public static MemoryAddress table_new(Addressable x0, Addressable x1, Addressable init) {
        if (logger.isTraceEnabled()) {
            logger.tracev("table_new(x0=" + x0 + ", x1=" + x1 + ", init=" + init + ")");
        }
        return Wasm.wasm_table_new(x0, x1, init);
    }

    /**
     * wasm_tabletype_t* wasm_table_type(const wasm_table_t*);
     */
    public static MemoryAddress table_type(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("table_type(x0=" + x0 + ")");
        }
        return Wasm.wasm_table_type(x0);
    }

    /**
     * wasm_ref_t* wasm_table_get(const wasm_table_t*, wasm_table_size_t index);
     */
    public static MemoryAddress table_get(Addressable x0, int index) {
        if (logger.isTraceEnabled()) {
            logger.tracev("table_get(x0=" + x0 + ", index=" + index + ")");
        }
        return Wasm.wasm_table_get(x0, index);
    }

    /**
     * _Bool wasm_table_set(wasm_table_t*, wasm_table_size_t index, wasm_ref_t*);
     */
    public static byte table_set(Addressable x0, int index, Addressable x2) {
        if (logger.isTraceEnabled()) {
            logger.tracev("table_set(x0=" + x0 + ", index=" + index + ", x2=" + x2 + ")");
        }
        return Wasm.wasm_table_set(x0, index, x2);
    }

    /**
     * wasm_table_size_t wasm_table_size(const wasm_table_t*);
     */
    public static int table_size(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("table_size(x0=" + x0 + ")");
        }
        return Wasm.wasm_table_size(x0);
    }

    /**
     * _Bool wasm_table_grow(wasm_table_t*, wasm_table_size_t delta, wasm_ref_t* init);
     */
    public static byte table_grow(Addressable x0, int delta, Addressable init) {
        if (logger.isTraceEnabled()) {
            logger.tracev("table_grow(x0=" + x0 + ", delta=" + delta + ", init=" + init + ")");
        }
        return Wasm.wasm_table_grow(x0, delta, init);
    }

    /**
     * void wasm_memory_delete(wasm_memory_t*);
     */
    public static void memory_delete(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("memory_delete(x0=" + x0 + ")");
        }
        Wasm.wasm_memory_delete(x0);
    }

    /**
     * wasm_memory_t* wasm_memory_copy(const wasm_memory_t*);
     */
    public static MemoryAddress memory_copy(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("memory_copy(x0=" + x0 + ")");
        }
        return Wasm.wasm_memory_copy(x0);
    }

    /**
     * _Bool wasm_memory_same(const wasm_memory_t*, const wasm_memory_t*);
     */
    public static byte memory_same(Addressable x0, Addressable x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("memory_same(x0=" + x0 + ", x1=" + x1 + ")");
        }
        return Wasm.wasm_memory_same(x0, x1);
    }

    /**
     * void* wasm_memory_get_host_info(const wasm_memory_t*);
     */
    public static MemoryAddress memory_get_host_info(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("memory_get_host_info(x0=" + x0 + ")");
        }
        return Wasm.wasm_memory_get_host_info(x0);
    }

    /**
     * void wasm_memory_set_host_info(wasm_memory_t*, void*);
     */
    public static void memory_set_host_info(Addressable x0, Addressable x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("memory_set_host_info(x0=" + x0 + ", x1=" + x1 + ")");
        }
        Wasm.wasm_memory_set_host_info(x0, x1);
    }

    /**
     * void wasm_memory_set_host_info_with_finalizer(wasm_memory_t*, void*, void (*)(void*));
     */
    public static void memory_set_host_info_with_finalizer(Addressable x0, Addressable x1, Addressable x2) {
        if (logger.isTraceEnabled()) {
            logger.tracev("memory_set_host_info_with_finalizer(x0=" + x0 + ", x1=" + x1 + ", x2=" + x2 + ")");
        }
        Wasm.wasm_memory_set_host_info_with_finalizer(x0, x1, x2);
    }

    /**
     * wasm_ref_t* wasm_memory_as_ref(wasm_memory_t*);
     */
    public static MemoryAddress memory_as_ref(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("memory_as_ref(x0=" + x0 + ")");
        }
        return Wasm.wasm_memory_as_ref(x0);
    }

    /**
     * wasm_memory_t* wasm_ref_as_memory(wasm_ref_t*);
     */
    public static MemoryAddress ref_as_memory(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("ref_as_memory(x0=" + x0 + ")");
        }
        return Wasm.wasm_ref_as_memory(x0);
    }

    /**
     * const wasm_ref_t* wasm_memory_as_ref_const(const wasm_memory_t*);
     */
    public static MemoryAddress memory_as_ref_const(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("memory_as_ref_const(x0=" + x0 + ")");
        }
        return Wasm.wasm_memory_as_ref_const(x0);
    }

    /**
     * const wasm_memory_t* wasm_ref_as_memory_const(const wasm_ref_t*);
     */
    public static MemoryAddress ref_as_memory_const(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("ref_as_memory_const(x0=" + x0 + ")");
        }
        return Wasm.wasm_ref_as_memory_const(x0);
    }

    /**
     * wasm_memory_t* wasm_memory_new(wasm_store_t*, const wasm_memorytype_t*);
     */
    public static MemoryAddress memory_new(Addressable x0, Addressable x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("memory_new(x0=" + x0 + ", x1=" + x1 + ")");
        }
        return Wasm.wasm_memory_new(x0, x1);
    }

    /**
     * wasm_memorytype_t* wasm_memory_type(const wasm_memory_t*);
     */
    public static MemoryAddress memory_type(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("memory_type(x0=" + x0 + ")");
        }
        return Wasm.wasm_memory_type(x0);
    }

    /**
     * byte_t* wasm_memory_data(wasm_memory_t*);
     */
    public static MemoryAddress memory_data(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("memory_data(x0=" + x0 + ")");
        }
        return Wasm.wasm_memory_data(x0);
    }

    /**
     * size_t wasm_memory_data_size(const wasm_memory_t*);
     */
    public static long memory_data_size(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("memory_data_size(x0=" + x0 + ")");
        }
        return Wasm.wasm_memory_data_size(x0);
    }

    /**
     * wasm_memory_pages_t wasm_memory_size(const wasm_memory_t*);
     */
    public static int memory_size(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("memory_size(x0=" + x0 + ")");
        }
        return Wasm.wasm_memory_size(x0);
    }

    /**
     * _Bool wasm_memory_grow(wasm_memory_t*, wasm_memory_pages_t delta);
     */
    public static byte memory_grow(Addressable x0, int delta) {
        if (logger.isTraceEnabled()) {
            logger.tracev("memory_grow(x0=" + x0 + ", delta=" + delta + ")");
        }
        return Wasm.wasm_memory_grow(x0, delta);
    }

    /**
     * void wasm_extern_delete(wasm_extern_t*);
     */
    public static void extern_delete(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("extern_delete(x0=" + x0 + ")");
        }
        Wasm.wasm_extern_delete(x0);
    }

    /**
     * wasm_extern_t* wasm_extern_copy(const wasm_extern_t*);
     */
    public static MemoryAddress extern_copy(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("extern_copy(x0=" + x0 + ")");
        }
        return Wasm.wasm_extern_copy(x0);
    }

    /**
     * _Bool wasm_extern_same(const wasm_extern_t*, const wasm_extern_t*);
     */
    public static byte extern_same(Addressable x0, Addressable x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("extern_same(x0=" + x0 + ", x1=" + x1 + ")");
        }
        return Wasm.wasm_extern_same(x0, x1);
    }

    /**
     * void* wasm_extern_get_host_info(const wasm_extern_t*);
     */
    public static MemoryAddress extern_get_host_info(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("extern_get_host_info(x0=" + x0 + ")");
        }
        return Wasm.wasm_extern_get_host_info(x0);
    }

    /**
     * void wasm_extern_set_host_info(wasm_extern_t*, void*);
     */
    public static void extern_set_host_info(Addressable x0, Addressable x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("extern_set_host_info(x0=" + x0 + ", x1=" + x1 + ")");
        }
        Wasm.wasm_extern_set_host_info(x0, x1);
    }

    /**
     * void wasm_extern_set_host_info_with_finalizer(wasm_extern_t*, void*, void (*)(void*));
     */
    public static void extern_set_host_info_with_finalizer(Addressable x0, Addressable x1, Addressable x2) {
        if (logger.isTraceEnabled()) {
            logger.tracev("extern_set_host_info_with_finalizer(x0=" + x0 + ", x1=" + x1 + ", x2=" + x2 + ")");
        }
        Wasm.wasm_extern_set_host_info_with_finalizer(x0, x1, x2);
    }

    /**
     * wasm_ref_t* wasm_extern_as_ref(wasm_extern_t*); wasm_extern_t* wasm_ref_as_extern(wasm_ref_t*);
     */
    public static MemoryAddress extern_as_ref(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("extern_as_ref(x0=" + x0 + ")");
        }
        return Wasm.wasm_extern_as_ref(x0);
    }

    /**
     * wasm_ref_t* wasm_extern_as_ref(wasm_extern_t*); wasm_extern_t* wasm_ref_as_extern(wasm_ref_t*);
     */
    public static MemoryAddress ref_as_extern(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("ref_as_extern(x0=" + x0 + ")");
        }
        return Wasm.wasm_ref_as_extern(x0);
    }

    /**
     * const wasm_ref_t* wasm_extern_as_ref_const(const wasm_extern_t*);
     */
    public static MemoryAddress extern_as_ref_const(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("extern_as_ref_const(x0=" + x0 + ")");
        }
        return Wasm.wasm_extern_as_ref_const(x0);
    }

    /**
     * const wasm_extern_t* wasm_ref_as_extern_const(const wasm_ref_t*);
     */
    public static MemoryAddress ref_as_extern_const(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("ref_as_extern_const(x0=" + x0 + ")");
        }
        return Wasm.wasm_ref_as_extern_const(x0);
    }

    /**
     * void wasm_extern_vec_new_empty(wasm_extern_vec_t* out);
     */
    public static void extern_vec_new_empty(Addressable out) {
        if (logger.isTraceEnabled()) {
            logger.tracev("extern_vec_new_empty(out=" + out + ")");
        }
        Wasm.wasm_extern_vec_new_empty(out);
    }

    /**
     * void wasm_extern_vec_new_uninitialized(wasm_extern_vec_t* out, size_t);
     */
    public static void extern_vec_new_uninitialized(Addressable out, long x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("extern_vec_new_uninitialized(out=" + out + ", x1=" + x1 + ")");
        }
        Wasm.wasm_extern_vec_new_uninitialized(out, x1);
    }

    /**
     * void wasm_extern_vec_new(wasm_extern_vec_t* out, size_t, wasm_extern_t * const[]);
     */
    public static void extern_vec_new(Addressable out, long x1, Addressable x2) {
        if (logger.isTraceEnabled()) {
            logger.tracev("extern_vec_new(out=" + out + ", x1=" + x1 + ", x2=" + x2 + ")");
        }
        Wasm.wasm_extern_vec_new(out, x1, x2);
    }

    /**
     * void wasm_extern_vec_copy(wasm_extern_vec_t* out, const wasm_extern_vec_t*);
     */
    public static void extern_vec_copy(Addressable out, Addressable x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("extern_vec_copy(out=" + out + ", x1=" + x1 + ")");
        }
        Wasm.wasm_extern_vec_copy(out, x1);
    }

    /**
     * void wasm_extern_vec_delete(wasm_extern_vec_t*);
     */
    public static void extern_vec_delete(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("extern_vec_delete(x0=" + x0 + ")");
        }
        Wasm.wasm_extern_vec_delete(x0);
    }

    /**
     * wasm_externkind_t wasm_extern_kind(const wasm_extern_t*);
     */
    public static byte extern_kind(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("extern_kind(x0=" + x0 + ")");
        }
        return Wasm.wasm_extern_kind(x0);
    }

    /**
     * wasm_externtype_t* wasm_extern_type(const wasm_extern_t*);
     */
    public static MemoryAddress extern_type(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("extern_type(x0=" + x0 + ")");
        }
        return Wasm.wasm_extern_type(x0);
    }

    /**
     * wasm_extern_t* wasm_func_as_extern(wasm_func_t*);
     */
    public static MemoryAddress func_as_extern(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("func_as_extern(x0=" + x0 + ")");
        }
        return Wasm.wasm_func_as_extern(x0);
    }

    /**
     * wasm_extern_t* wasm_global_as_extern(wasm_global_t*);
     */
    public static MemoryAddress global_as_extern(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("global_as_extern(x0=" + x0 + ")");
        }
        return Wasm.wasm_global_as_extern(x0);
    }

    /**
     * wasm_extern_t* wasm_table_as_extern(wasm_table_t*);
     */
    public static MemoryAddress table_as_extern(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("table_as_extern(x0=" + x0 + ")");
        }
        return Wasm.wasm_table_as_extern(x0);
    }

    /**
     * wasm_extern_t* wasm_memory_as_extern(wasm_memory_t*);
     */
    public static MemoryAddress memory_as_extern(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("memory_as_extern(x0=" + x0 + ")");
        }
        return Wasm.wasm_memory_as_extern(x0);
    }

    /**
     * wasm_func_t* wasm_extern_as_func(wasm_extern_t*);
     */
    public static MemoryAddress extern_as_func(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("extern_as_func(x0=" + x0 + ")");
        }
        return Wasm.wasm_extern_as_func(x0);
    }

    /**
     * wasm_global_t* wasm_extern_as_global(wasm_extern_t*);
     */
    public static MemoryAddress extern_as_global(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("extern_as_global(x0=" + x0 + ")");
        }
        return Wasm.wasm_extern_as_global(x0);
    }

    /**
     * wasm_table_t* wasm_extern_as_table(wasm_extern_t*);
     */
    public static MemoryAddress extern_as_table(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("extern_as_table(x0=" + x0 + ")");
        }
        return Wasm.wasm_extern_as_table(x0);
    }

    /**
     * wasm_memory_t* wasm_extern_as_memory(wasm_extern_t*);
     */
    public static MemoryAddress extern_as_memory(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("extern_as_memory(x0=" + x0 + ")");
        }
        return Wasm.wasm_extern_as_memory(x0);
    }

    /**
     * const wasm_extern_t* wasm_func_as_extern_const(const wasm_func_t*);
     */
    public static MemoryAddress func_as_extern_const(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("func_as_extern_const(x0=" + x0 + ")");
        }
        return Wasm.wasm_func_as_extern_const(x0);
    }

    /**
     * const wasm_extern_t* wasm_global_as_extern_const(const wasm_global_t*);
     */
    public static MemoryAddress global_as_extern_const(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("global_as_extern_const(x0=" + x0 + ")");
        }
        return Wasm.wasm_global_as_extern_const(x0);
    }

    /**
     * const wasm_extern_t* wasm_table_as_extern_const(const wasm_table_t*);
     */
    public static MemoryAddress table_as_extern_const(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("table_as_extern_const(x0=" + x0 + ")");
        }
        return Wasm.wasm_table_as_extern_const(x0);
    }

    /**
     * const wasm_extern_t* wasm_memory_as_extern_const(const wasm_memory_t*);
     */
    public static MemoryAddress memory_as_extern_const(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("memory_as_extern_const(x0=" + x0 + ")");
        }
        return Wasm.wasm_memory_as_extern_const(x0);
    }

    /**
     * const wasm_func_t* wasm_extern_as_func_const(const wasm_extern_t*);
     */
    public static MemoryAddress extern_as_func_const(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("extern_as_func_const(x0=" + x0 + ")");
        }
        return Wasm.wasm_extern_as_func_const(x0);
    }

    /**
     * const wasm_global_t* wasm_extern_as_global_const(const wasm_extern_t*);
     */
    public static MemoryAddress extern_as_global_const(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("extern_as_global_const(x0=" + x0 + ")");
        }
        return Wasm.wasm_extern_as_global_const(x0);
    }

    /**
     * const wasm_table_t* wasm_extern_as_table_const(const wasm_extern_t*);
     */
    public static MemoryAddress extern_as_table_const(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("extern_as_table_const(x0=" + x0 + ")");
        }
        return Wasm.wasm_extern_as_table_const(x0);
    }

    /**
     * const wasm_memory_t* wasm_extern_as_memory_const(const wasm_extern_t*);
     */
    public static MemoryAddress extern_as_memory_const(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("extern_as_memory_const(x0=" + x0 + ")");
        }
        return Wasm.wasm_extern_as_memory_const(x0);
    }

    /**
     * void wasm_instance_delete(wasm_instance_t*);
     */
    public static void instance_delete(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("instance_delete(x0=" + x0 + ")");
        }
        Wasm.wasm_instance_delete(x0);
    }

    /**
     * wasm_instance_t* wasm_instance_copy(const wasm_instance_t*);
     */
    public static MemoryAddress instance_copy(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("instance_copy(x0=" + x0 + ")");
        }
        return Wasm.wasm_instance_copy(x0);
    }

    /**
     * _Bool wasm_instance_same(const wasm_instance_t*, const wasm_instance_t*);
     */
    public static byte instance_same(Addressable x0, Addressable x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("instance_same(x0=" + x0 + ", x1=" + x1 + ")");
        }
        return Wasm.wasm_instance_same(x0, x1);
    }

    /**
     * void* wasm_instance_get_host_info(const wasm_instance_t*);
     */
    public static MemoryAddress instance_get_host_info(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("instance_get_host_info(x0=" + x0 + ")");
        }
        return Wasm.wasm_instance_get_host_info(x0);
    }

    /**
     * void wasm_instance_set_host_info(wasm_instance_t*, void*);
     */
    public static void instance_set_host_info(Addressable x0, Addressable x1) {
        if (logger.isTraceEnabled()) {
            logger.tracev("instance_set_host_info(x0=" + x0 + ", x1=" + x1 + ")");
        }
        Wasm.wasm_instance_set_host_info(x0, x1);
    }

    /**
     * void wasm_instance_set_host_info_with_finalizer(wasm_instance_t*, void*, void (*)(void*));
     */
    public static void instance_set_host_info_with_finalizer(Addressable x0, Addressable x1, Addressable x2) {
        if (logger.isTraceEnabled()) {
            logger.tracev("instance_set_host_info_with_finalizer(x0=" + x0 + ", x1=" + x1 + ", x2=" + x2 + ")");
        }
        Wasm.wasm_instance_set_host_info_with_finalizer(x0, x1, x2);
    }

    /**
     * wasm_ref_t* wasm_instance_as_ref(wasm_instance_t*);
     */
    public static MemoryAddress instance_as_ref(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("instance_as_ref(x0=" + x0 + ")");
        }
        return Wasm.wasm_instance_as_ref(x0);
    }

    /**
     * wasm_instance_t* wasm_ref_as_instance(wasm_ref_t*);
     */
    public static MemoryAddress ref_as_instance(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("ref_as_instance(x0=" + x0 + ")");
        }
        return Wasm.wasm_ref_as_instance(x0);
    }

    /**
     * const wasm_ref_t* wasm_instance_as_ref_const(const wasm_instance_t*);
     */
    public static MemoryAddress instance_as_ref_const(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("instance_as_ref_const(x0=" + x0 + ")");
        }
        return Wasm.wasm_instance_as_ref_const(x0);
    }

    /**
     * const wasm_instance_t* wasm_ref_as_instance_const(const wasm_ref_t*);
     */
    public static MemoryAddress ref_as_instance_const(Addressable x0) {
        if (logger.isTraceEnabled()) {
            logger.tracev("ref_as_instance_const(x0=" + x0 + ")");
        }
        return Wasm.wasm_ref_as_instance_const(x0);
    }

    /**
     * wasm_instance_t* wasm_instance_new(wasm_store_t*, const wasm_module_t*, const wasm_extern_vec_t* imports,wasm_trap_t**);
     */
    public static MemoryAddress instance_new(Addressable x0, Addressable x1, Addressable imports, Addressable x3) {
        if (logger.isTraceEnabled()) {
            logger.tracev("instance_new(x0=" + x0 + ", x1=" + x1 + ", imports=" + imports + ", x3=" + x3 + ")");
        }
        return Wasm.wasm_instance_new(x0, x1, imports, x3);
    }

    /**
     * void wasm_instance_exports(const wasm_instance_t*, wasm_extern_vec_t* out);
     */
    public static void instance_exports(Addressable x0, Addressable out) {
        if (logger.isTraceEnabled()) {
            logger.tracev("instance_exports(x0=" + x0 + ", out=" + out + ")");
        }
        Wasm.wasm_instance_exports(x0, out);
    }

    /**
     * static inline wasm_valtype_t* wasm_valtype_new_i32(void);
     */
    public static MemoryAddress valtype_new_i32() {
        if (logger.isTraceEnabled()) {
            logger.tracev("valtype_new_i32()");
        }
        return Wasm.wasm_valtype_new_i32();
    }

    /**
     * static inline wasm_valtype_t* wasm_valtype_new_i64(void);
     */
    public static MemoryAddress valtype_new_i64() {
        if (logger.isTraceEnabled()) {
            logger.tracev("valtype_new_i64()");
        }
        return Wasm.wasm_valtype_new_i64();
    }

    /**
     * static inline wasm_valtype_t* wasm_valtype_new_f32(void);
     */
    public static MemoryAddress valtype_new_f32() {
        if (logger.isTraceEnabled()) {
            logger.tracev("valtype_new_f32()");
        }
        return Wasm.wasm_valtype_new_f32();
    }

    /**
     * static inline wasm_valtype_t* wasm_valtype_new_f64(void);
     */
    public static MemoryAddress valtype_new_f64() {
        if (logger.isTraceEnabled()) {
            logger.tracev("valtype_new_f64()");
        }
        return Wasm.wasm_valtype_new_f64();
    }

    /**
     * static inline wasm_valtype_t* wasm_valtype_new_anyref(void);
     */
    public static MemoryAddress valtype_new_anyref() {
        if (logger.isTraceEnabled()) {
            logger.tracev("valtype_new_anyref()");
        }
        return Wasm.wasm_valtype_new_anyref();
    }

    /**
     * static inline wasm_valtype_t* wasm_valtype_new_funcref(void);
     */
    public static MemoryAddress valtype_new_funcref() {
        if (logger.isTraceEnabled()) {
            logger.tracev("valtype_new_funcref()");
        }
        return Wasm.wasm_valtype_new_funcref();
    }

    /**
     * static inline wasm_functype_t* wasm_functype_new_0_0(void);
     */
    public static MemoryAddress functype_new_0_0() {
        if (logger.isTraceEnabled()) {
            logger.tracev("functype_new_0_0()");
        }
        return Wasm.wasm_functype_new_0_0();
    }

    /**
     * static inline wasm_functype_t* wasm_functype_new_1_0(wasm_valtype_t* p);
     */
    public static MemoryAddress functype_new_1_0(Addressable p) {
        if (logger.isTraceEnabled()) {
            logger.tracev("functype_new_1_0(p=" + p + ")");
        }
        return Wasm.wasm_functype_new_1_0(p);
    }

    /**
     * static inline wasm_functype_t* wasm_functype_new_2_0(wasm_valtype_t* p1, wasm_valtype_t* p2);
     */
    public static MemoryAddress functype_new_2_0(Addressable p1, Addressable p2) {
        if (logger.isTraceEnabled()) {
            logger.tracev("functype_new_2_0(p1=" + p1 + ", p2=" + p2 + ")");
        }
        return Wasm.wasm_functype_new_2_0(p1, p2);
    }

    /**
     * static inline wasm_functype_t* wasm_functype_new_3_0(wasm_valtype_t* p1, wasm_valtype_t* p2, wasm_valtype_t* p3);
     */
    public static MemoryAddress functype_new_3_0(Addressable p1, Addressable p2, Addressable p3) {
        if (logger.isTraceEnabled()) {
            logger.tracev("functype_new_3_0(p1=" + p1 + ", p2=" + p2 + ", p3=" + p3 + ")");
        }
        return Wasm.wasm_functype_new_3_0(p1, p2, p3);
    }

    /**
     * static inline wasm_functype_t* wasm_functype_new_0_1(wasm_valtype_t* r);
     */
    public static MemoryAddress functype_new_0_1(Addressable r) {
        if (logger.isTraceEnabled()) {
            logger.tracev("functype_new_0_1(r=" + r + ")");
        }
        return Wasm.wasm_functype_new_0_1(r);
    }

    /**
     * static inline wasm_functype_t* wasm_functype_new_1_1(wasm_valtype_t* p, wasm_valtype_t* r);
     */
    public static MemoryAddress functype_new_1_1(Addressable p, Addressable r) {
        if (logger.isTraceEnabled()) {
            logger.tracev("functype_new_1_1(p=" + p + ", r=" + r + ")");
        }
        return Wasm.wasm_functype_new_1_1(p, r);
    }

    /**
     * static inline wasm_functype_t* wasm_functype_new_2_1(wasm_valtype_t* p1, wasm_valtype_t* p2, wasm_valtype_t* r);
     */
    public static MemoryAddress functype_new_2_1(Addressable p1, Addressable p2, Addressable r) {
        if (logger.isTraceEnabled()) {
            logger.tracev("functype_new_2_1(p1=" + p1 + ", p2=" + p2 + ", r=" + r + ")");
        }
        return Wasm.wasm_functype_new_2_1(p1, p2, r);
    }

    /**
     * static inline wasm_functype_t* wasm_functype_new_3_1(wasm_valtype_t* p1, wasm_valtype_t* p2, wasm_valtype_t* p3,wasm_valtype_t* r);
     */
    public static MemoryAddress functype_new_3_1(Addressable p1, Addressable p2, Addressable p3, Addressable r) {
        if (logger.isTraceEnabled()) {
            logger.tracev("functype_new_3_1(p1=" + p1 + ", p2=" + p2 + ", p3=" + p3 + ", r=" + r + ")");
        }
        return Wasm.wasm_functype_new_3_1(p1, p2, p3, r);
    }

    /**
     * static inline wasm_functype_t* wasm_functype_new_0_2(wasm_valtype_t* r1, wasm_valtype_t* r2);
     */
    public static MemoryAddress functype_new_0_2(Addressable r1, Addressable r2) {
        if (logger.isTraceEnabled()) {
            logger.tracev("functype_new_0_2(r1=" + r1 + ", r2=" + r2 + ")");
        }
        return Wasm.wasm_functype_new_0_2(r1, r2);
    }

    /**
     * static inline wasm_functype_t* wasm_functype_new_1_2(wasm_valtype_t* p, wasm_valtype_t* r1, wasm_valtype_t* r2);
     */
    public static MemoryAddress functype_new_1_2(Addressable p, Addressable r1, Addressable r2) {
        if (logger.isTraceEnabled()) {
            logger.tracev("functype_new_1_2(p=" + p + ", r1=" + r1 + ", r2=" + r2 + ")");
        }
        return Wasm.wasm_functype_new_1_2(p, r1, r2);
    }

    /**
     * static inline wasm_functype_t* wasm_functype_new_2_2(wasm_valtype_t* p1, wasm_valtype_t* p2,wasm_valtype_t* r1, wasm_valtype_t* r2);
     */
    public static MemoryAddress functype_new_2_2(Addressable p1, Addressable p2, Addressable r1, Addressable r2) {
        if (logger.isTraceEnabled()) {
            logger.tracev("functype_new_2_2(p1=" + p1 + ", p2=" + p2 + ", r1=" + r1 + ", r2=" + r2 + ")");
        }
        return Wasm.wasm_functype_new_2_2(p1, p2, r1, r2);
    }

    /**
     * static inline wasm_functype_t* wasm_functype_new_3_2(wasm_valtype_t* p1, wasm_valtype_t* p2, wasm_valtype_t* p3,wasm_valtype_t* r1, wasm_valtype_t* r2);
     */
    public static MemoryAddress functype_new_3_2(Addressable p1, Addressable p2, Addressable p3, Addressable r1, Addressable r2) {
        if (logger.isTraceEnabled()) {
            logger.tracev("functype_new_3_2(p1=" + p1 + ", p2=" + p2 + ", p3=" + p3 + ", r1=" + r1 + ", r2=" + r2 + ")");
        }
        return Wasm.wasm_functype_new_3_2(p1, p2, p3, r1, r2);
    }

    /**
     * static inline void wasm_val_init_ptr(wasm_val_t* out, void* p);
     */
    public static void val_init_ptr(Addressable out, Addressable p) {
        if (logger.isTraceEnabled()) {
            logger.tracev("val_init_ptr(out=" + out + ", p=" + p + ")");
        }
        Wasm.wasm_val_init_ptr(out, p);
    }

    /**
     * static inline void* wasm_val_ptr(const wasm_val_t* val);
     */
    public static MemoryAddress val_ptr(Addressable val) {
        if (logger.isTraceEnabled()) {
            logger.tracev("val_ptr(val=" + val + ")");
        }
        return Wasm.wasm_val_ptr(val);
    }
}
