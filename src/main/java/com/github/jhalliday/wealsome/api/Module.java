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
 * A compiled WebAssembly module.
 *
 * @author Jonathan Halliday (jonathan.halliday@redhat.com)
 * @see <a href="https://docs.rs/wasmtime/0.30.0/wasmtime/#core-concepts">Module</a>
 */
public class Module implements Addressable, AutoCloseable {

    private static final Logger logger = Logger.getLogger(Module.class);

    private final Store store;
    private final MemoryAddress address;

    public Module(Store store, ByteVec binary) {
        this(store, binary, false);
    }

    public Module(Store store, SharedModule sharedModule) {
        this.store = store;
        address = WasmAPI.module_obtain(store, sharedModule);
    }

    public Module(Store store, ByteVec byteVec, boolean deserialize) {
        if (logger.isTraceEnabled()) {
            logger.tracev("entry with {0}, {1} {2}", store, byteVec, deserialize);
        }

        this.store = store;

        if (!deserialize) {
            address = WasmAPI.module_new(store, byteVec);
        } else {
            address = WasmAPI.module_deserialize(store, byteVec);
        }

        if (logger.isTraceEnabled()) {
            logger.tracev("exit {0}", this);
        }
    }

    public ByteVec serialize(ResourceScope resourceScope) {
        MemorySegment byte_vec_t = wasm_byte_vec_t.allocate(resourceScope);
        WasmAPI.module_serialize(address, byte_vec_t.address());
        return new ByteVec(resourceScope, byte_vec_t);
    }

    public void imports(ImportTypeVec importTypeVec) {
        WasmAPI.module_imports(this, importTypeVec);
    }

    public void exports(ExportTypeVec exportTypeVec) {
        WasmAPI.module_exports(this, exportTypeVec);
    }

    @Override
    public MemoryAddress address() {
        return address;
    }

    @Override
    public void close() {
        WasmAPI.module_delete(address);
    }

    @Override
    public String toString() {
        return "Module{" +
                "store=" + store +
                ", address=" + address +
                '}';
    }
}
