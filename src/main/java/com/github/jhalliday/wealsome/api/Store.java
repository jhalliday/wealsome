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
 * A container (i.e. context) for all information related to WebAssembly objects such as functions, instances, memories, etc.
 *
 * @author Jonathan Halliday (jonathan.halliday@redhat.com)
 * @see <a href="https://docs.rs/wasmtime/0.30.0/wasmtime/#core-concepts">Store</a>
 */
public class Store implements Addressable, AutoCloseable {

    private static final Logger logger = Logger.getLogger(Store.class);

    private final Engine engine;
    private final MemoryAddress address;

    public Store(Engine engine) {
        if (logger.isTraceEnabled()) {
            logger.tracev("entry with {0}", engine);
        }

        this.engine = engine;
        address = WasmAPI.store_new(engine);

        if (logger.isTraceEnabled()) {
            logger.tracev("exit {0}", this);
        }
    }

    public Func newFunc(FuncType funcType, MemoryAddress stub) {
        return new Func(this, funcType, stub);
    }

    public Module deserializeModule(ByteVec byteVec) {
        Module module = new Module(this, byteVec, true);
        return module;
    }

    public Module obtainModule(SharedModule sharedModule) {
        Module module = new Module(this, sharedModule);
        return module;
    }

    @Override
    public MemoryAddress address() {
        return address;
    }

    @Override
    public void close() {
        WasmAPI.store_delete(address);
    }

    @Override
    public String toString() {
        return "Store{" +
                "engine=" + engine +
                ", address=" + address +
                '}';
    }
}
