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

import com.github.jhalliday.wealsome.generated.wasm_val_t;
import com.github.jhalliday.wealsome.generated.wasm_val_vec_t;
import jdk.incubator.foreign.Addressable;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;
import org.jboss.logging.Logger;

import java.util.List;

/**
 * An array of Values. (wasm_val_vec_t*)
 *
 * @author Jonathan Halliday (jonathan.halliday@redhat.com)
 */
public class ValVec implements Addressable, AutoCloseable {

    private static final Logger logger = Logger.getLogger(ValVec.class);

    private final ResourceScope resourceScope;
    public final MemorySegment val_vec_t;
    private final boolean shouldDelete;

    public ValVec(ResourceScope resourceScope) {
        this.resourceScope = resourceScope;
        val_vec_t = wasm_val_vec_t.allocate(resourceScope);
        WasmAPI.val_vec_new_empty(this);
        shouldDelete = false; // nothing to do, it's empty
    }

    public ValVec(ResourceScope resourceScope, MemoryAddress memoryAddress) {
        this.resourceScope = resourceScope;
        val_vec_t = wasm_val_vec_t.ofAddress(memoryAddress, resourceScope);
        shouldDelete = false; // ?
    }

    public ValVec(ResourceScope resourceScope, List<UnVal> values) {
        this.resourceScope = resourceScope;
        val_vec_t = wasm_val_vec_t.allocate(resourceScope);
        shouldDelete = false; // the memorySegment cleaner will handle it. Don't double-free!

        MemorySegment memorySegment = wasm_val_t.allocateArray(values.size(), resourceScope);
        for (int i = 0; i < values.size(); i++) {
            UnVal val = values.get(i);
            wasm_val_t.kind$set(memorySegment, i, (byte) val.valType.ordinal());
            MemorySegment slice = wasm_val_t.of$slice(memorySegment.asSlice(wasm_val_t.sizeof() * i));
            wasm_val_t.of.i32$set(slice, (int) val.value);
        }
        wasm_val_vec_t.size$set(val_vec_t, values.size());
        wasm_val_vec_t.data$set(val_vec_t, memorySegment.address());
    }

    public UnVal read(int index) {
        MemoryAddress memoryAddress = wasm_val_vec_t.data$get(val_vec_t);
        MemorySegment memorySegment = memoryAddress.asSegment(wasm_val_t.sizeof() * size(), resourceScope);
        int t = wasm_val_t.kind$get(memorySegment, index);
        ValType valType = ValType.values()[t]; // dirty
        Number value = null;
        MemorySegment slice = wasm_val_t.of$slice(memorySegment.asSlice(index * wasm_val_t.sizeof()));
        switch (valType) {
            // TODO others
            case I32: {
                value = wasm_val_t.of.i32$get(slice);
                break;
            }
            case I64:
                value = wasm_val_t.of.i64$get(slice);
                break;
        }
        return new UnVal(valType, value);
    }

    public long size() {
        return wasm_val_vec_t.size$get(val_vec_t);
    }

    @Override
    public MemoryAddress address() {
        return val_vec_t.address();
    }

    @Override
    public void close() {
        if (shouldDelete) {
            WasmAPI.val_vec_delete(val_vec_t);
        }
    }
}
