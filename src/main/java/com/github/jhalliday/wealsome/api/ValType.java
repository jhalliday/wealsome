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
import jdk.incubator.foreign.MemoryAddress;

/**
 * @author Jonathan Halliday (jonathan.halliday@redhat.com)
 */
public enum ValType {
    I32(Wasm.WASM_I32()),
    I64(Wasm.WASM_I64()),
    F32(Wasm.WASM_F32()),
    F64(Wasm.WASM_F64()),
    ANYREF(-128),
    FUNCREF(-127);
//        ,
//        EXTERN_REF,
//        V128,

    private final int cValue;

    ValType(int cValue) {
        this.cValue = cValue;
    }

    private static ValType fromCNum(int cnum) {
        for (ValType valType : ValType.values()) {
            if (valType.cValue == cnum) {
                return valType;
            }
        }
        return null;
    }

    public static ValType valueOf(MemoryAddress memoryAddress) {
        byte kind = WasmAPI.valtype_kind(memoryAddress);
        return fromCNum(kind);
    }
}
