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

import com.github.jhalliday.wealsome.generated.wasm_valtype_vec_t;
import jdk.incubator.foreign.*;
import org.jboss.logging.Logger;

import java.util.List;

/**
 * An array of valtypes. (wasm_valtype_vec_t*)
 *
 * @author Jonathan Halliday (jonathan.halliday@redhat.com)
 */
public class ValtypeVec implements Addressable, AutoCloseable {

    private static final Logger logger = Logger.getLogger(ValtypeVec.class);

    private final ResourceScope resourceScope;
    private final MemorySegment valtype_vec_t;
    private final boolean shouldDelete;

    public ValtypeVec(ResourceScope resourceScope) {
        if (logger.isTraceEnabled()) {

        }

        this.resourceScope = resourceScope;
        valtype_vec_t = wasm_valtype_vec_t.allocate(resourceScope);

        WasmAPI.valtype_vec_new_empty(valtype_vec_t);
        shouldDelete = false; // it's empty
    }

    public ValtypeVec(ResourceScope resourceScope, MemoryAddress address) {
        this.resourceScope = resourceScope;
        valtype_vec_t = wasm_valtype_vec_t.ofAddress(address, resourceScope);
        shouldDelete = false;
    }

    public ValtypeVec(ResourceScope resourceScope, List<ValType> values) {

        this.resourceScope = resourceScope;
        valtype_vec_t = wasm_valtype_vec_t.allocate(resourceScope);
        shouldDelete = false; // the memorySegment cleaner will handle it. Don't double-free!

        if (values == null || values.isEmpty()) {
            WasmAPI.valtype_vec_new_empty(valtype_vec_t);
        } else {

            // TODO don't use managed segment here.
            //long addr = Unsafe.getUnsafe().allocateMemory(WasmAPI.pointerMemoryLayout.byteSize()*values.size());
            MemorySegment memorySegment = SegmentAllocator.ofScope(resourceScope).allocateArray(WasmAPI.pointerMemoryLayout, values.size());
            for (int i = 0; i < values.size(); i++) {
                ValType valType = values.get(i);
                MemoryAddress address = WasmAPI.valtype_new((byte) valType.ordinal()); // dirty tricks...
                WasmAPI.ptr$VH.set(memorySegment.asSlice(WasmAPI.pointerMemoryLayout.byteSize() * i), address);
            }

            wasm_valtype_vec_t.size$set(valtype_vec_t, values.size());
            wasm_valtype_vec_t.data$set(valtype_vec_t, memorySegment.address());
        }

    }

    public long size() {
        return wasm_valtype_vec_t.size$get(valtype_vec_t);
    }

    public ValType get(int i) {
        MemoryAddress memoryAddress = wasm_valtype_vec_t.data$get(valtype_vec_t);
        MemorySegment externSegment = memoryAddress.asSegment(WasmAPI.pointerMemoryLayout.byteSize() * size(), resourceScope);
        MemoryAddress memoryAddress1 = WasmAPI.ptr$get(externSegment, i);
        return ValType.valueOf(memoryAddress1);
    }

    @Override
    public MemoryAddress address() {
        return valtype_vec_t.address();
    }

    @Override
    public void close() {
        if (shouldDelete) {
            WasmAPI.valtype_vec_delete(valtype_vec_t);
        }
    }

    @Override
    public String toString() {
        return "ValtypeVec{" +
                "resourceScope=" + resourceScope +
                ", valtype_vec_t=" + valtype_vec_t +
                '}';
    }
}
