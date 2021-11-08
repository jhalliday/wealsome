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

import jdk.incubator.foreign.MemoryAddress;

/**
 * @author Jonathan Halliday (jonathan.halliday@redhat.com)
 */
public enum ExternKind {

    WASM_EXTERN_FUNC(0),
    WASM_EXTERN_GLOBAL(1),
    WASM_EXTERN_TABLE(2),
    WASM_EXTERN_MEMORY(3);

    private final int cnum;

    ExternKind(int cnum) {
        this.cnum = cnum;
    }

    private static ExternKind fromCNum(int cnum) {
        return ExternKind.values()[cnum];
    }

    public static ExternKind valueOf(MemoryAddress memoryAddress) {
        byte kind = WasmAPI.externtype_kind(memoryAddress);
        return fromCNum(kind);
    }
}
