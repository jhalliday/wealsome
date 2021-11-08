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
 * A global compilation environment for WebAssembly.
 *
 * @author Jonathan Halliday (jonathan.halliday@redhat.com)
 * @see <a href="https://docs.rs/wasmtime/0.30.0/wasmtime/#core-concepts">Engine</a>
 */
public class Engine implements Addressable, AutoCloseable {

    private static final Logger logger = Logger.getLogger(Engine.class);

    private final MemoryAddress address;

    public Engine() {
        if (logger.isTraceEnabled()) {
            logger.tracev("entry");
        }

        address = WasmAPI.engine_new();

        if (logger.isTraceEnabled()) {
            logger.tracev("exit {0}", this);
        }
    }

    // TODO wasm_config_t optional arg

    public Store newStore() {
        return new Store(this);
    }

    @Override
    public MemoryAddress address() {
        return address;
    }

    @Override
    public void close() {
        WasmAPI.engine_delete(address);
    }

    @Override
    public String toString() {
        return "Engine{" +
                "address=" + address +
                '}';
    }
}
