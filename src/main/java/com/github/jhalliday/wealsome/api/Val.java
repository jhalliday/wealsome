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
import jdk.incubator.foreign.Addressable;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;
import org.jboss.logging.Logger;

/**
 * @author Jonathan Halliday (jonathan.halliday@redhat.com)
 */
public class Val implements Addressable, AutoCloseable {

    private static final Logger logger = Logger.getLogger(Val.class);

    public final ResourceScope resourceScope;
    public final ValType valType;
    public final Number value;
    public final MemorySegment val_t;

    public Val(ResourceScope resourceScope, ValType valType, Number value) {
        this.resourceScope = resourceScope;
        this.valType = valType;
        this.value = value;

        val_t = wasm_val_t.allocate(resourceScope);
        wasm_val_t.kind$set(val_t, (byte) valType.ordinal());
        switch (valType) {
            case I32 -> wasm_val_t.of.i32$set(wasm_val_t.of$slice(val_t), (int) value);
            case I64 -> wasm_val_t.of.i64$set(wasm_val_t.of$slice(val_t), (long) value);
            case F32 -> wasm_val_t.of.f32$set(wasm_val_t.of$slice(val_t), (float) value);
            case F64 -> wasm_val_t.of.f64$set(wasm_val_t.of$slice(val_t), (double) value);
        }
    }

    @Override
    public MemoryAddress address() {
        return val_t.address();
    }

    @Override
    public void close() {
        WasmAPI.val_delete(val_t);
    }
}
