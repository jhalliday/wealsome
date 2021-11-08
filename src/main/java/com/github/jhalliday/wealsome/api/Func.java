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
import com.github.jhalliday.wealsome.generated.wasm_func_callback_t;
import jdk.incubator.foreign.MemoryAddress;
import org.jboss.logging.Logger;

/**
 * A WebAssembly (or host) function.
 *
 * @author Jonathan Halliday (jonathan.halliday@redhat.com)
 * @see <a href="https://docs.rs/wasmtime/0.30.0/wasmtime/#core-concepts">Func</a>
 */
public class Func extends Extern implements AutoCloseable {

    private static final Logger logger = Logger.getLogger(Func.class);

    public Func(Store store, FuncType funcType, wasm_func_callback_t callback) {
        super(null, WasmAPI.func_new(store, funcType, wasm_func_callback_t.allocate(callback)));
    }

    public Func(Store store, FuncType funcType, MemoryAddress stub) {
        super(null, WasmAPI.func_new(store, funcType, stub));
    }

    public void apply(ValVec params, ValVec results) {
        Wasm.wasm_func_call(address, params.address(), results.address());
    }

    @Override
    public ExternKind getKind() {
        return ExternKind.WASM_EXTERN_FUNC;
    }

    @Override
    public void close() {
        WasmAPI.func_delete(address);
    }
}
