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

import com.github.jhalliday.wealsome.generated.wasm_byte_vec_t;
import jdk.incubator.foreign.Addressable;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;
import org.jboss.logging.Logger;

/**
 * An array of bytes. (wasm_byte_vec_t*)
 *
 * @author Jonathan Halliday (jonathan.halliday@redhat.com)
 */
public class ByteVec implements Addressable, AutoCloseable {

    private static final Logger logger = Logger.getLogger(ByteVec.class);

    private final ResourceScope resourceScope;
    private final MemorySegment byte_vec_t;

    public ByteVec(ResourceScope resourceScope, MemoryAddress memoryAddress) {
        this.resourceScope = resourceScope;
        byte_vec_t = wasm_byte_vec_t.ofAddress(memoryAddress, resourceScope);
    }

    public ByteVec(ResourceScope resourceScope, MemorySegment memorySegment) {
        this.resourceScope = resourceScope;
        byte_vec_t = memorySegment;
    }

    public ByteVec(ResourceScope resourceScope, byte[] data) {
        if (logger.isTraceEnabled()) {
            logger.tracev("entry");
        }

        this.resourceScope = resourceScope;
        byte_vec_t = wasm_byte_vec_t.allocate(resourceScope);

        WasmAPI.byte_vec_new_uninitialized(byte_vec_t, data.length);
        MemoryAddress dataMemoryAddress = wasm_byte_vec_t.data$get(byte_vec_t);
        dataMemoryAddress.asSegment(data.length, resourceScope).asByteBuffer().put(data);

        if (logger.isTraceEnabled()) {
            logger.tracev("exit {0}", this);
        }
    }

    public long size() {
        return wasm_byte_vec_t.size$get(byte_vec_t);
    }

    public String asString() {
        MemoryAddress dataMemoryAddress = wasm_byte_vec_t.data$get(byte_vec_t);
        byte[] data = dataMemoryAddress.asSegment(wasm_byte_vec_t.size$get(byte_vec_t), resourceScope).toByteArray();
        return new String(data); // TODO trailing null?
    }

    @Override
    public MemoryAddress address() {
        return byte_vec_t.address();
    }

    @Override
    public void close() {
        WasmAPI.byte_vec_delete(byte_vec_t);
    }

    @Override
    public String toString() {
        return "ByteVec{" +
                "resourceScope=" + resourceScope +
                ", byte_vec_t=" + byte_vec_t.address() +
                '}';
    }
}
