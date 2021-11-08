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

import jdk.incubator.foreign.Addressable;
import jdk.incubator.foreign.MemoryAddress;
import org.jboss.logging.Logger;

/**
 * An instantiated WebAssembly module.
 *
 * @author Jonathan Halliday (jonathan.halliday@redhat.com)
 * @see <a href="https://docs.rs/wasmtime/0.30.0/wasmtime/#core-concepts">Instance</a>
 */
public class Instance implements Addressable, AutoCloseable {

    private static final Logger logger = Logger.getLogger(Instance.class);

    private final Store store;
    private final Module module;
    private final MemoryAddress address;

    public Instance(Store store, Module module, ExternVec imports) {
        this(store, module, imports, null);
    }

    public Instance(Store store, Module module, ExternVec imports, Trap trap) {
        if (logger.isTraceEnabled()) {
            logger.tracev("entry");
        }

        this.store = store;
        this.module = module;

        MemoryAddress trapAddress = trap == null ? MemoryAddress.NULL : trap.address();
        address = WasmAPI.instance_new(store.address(), module.address(), imports, trapAddress);

        if (logger.isTraceEnabled()) {
            logger.tracev("exit {0}", this);
        }
    }

    public void exports(ExternVec exports) {
        WasmAPI.instance_exports(address(), exports);
    }

    @Override
    public MemoryAddress address() {
        return address;
    }

    @Override
    public void close() {
        WasmAPI.instance_delete(address);
    }

    @Override
    public String toString() {
        return "Instance{" +
                "store=" + store +
                ", module=" + module +
                ", address=" + address +
                '}';
    }
}
