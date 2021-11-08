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
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.ResourceScope;
import org.jboss.logging.Logger;

/**
 * @author Jonathan Halliday (jonathan.halliday@redhat.com)
 */
public class GlobalType extends ExternType implements AutoCloseable {

    private static final Logger logger = Logger.getLogger(GlobalType.class);

    public GlobalType(ResourceScope resourceScope, ValType valType, boolean isConst) {
        super(resourceScope);

        MemoryAddress valTypeAddress = WasmAPI.valtype_new((byte) valType.ordinal()); // dirty tricks...
        this.address = WasmAPI.globaltype_new(valTypeAddress, isConst ? (byte) Wasm.WASM_CONST() : (byte) Wasm.WASM_VAR());
    }

    protected GlobalType(ResourceScope resourceScope, MemoryAddress address) {
        super(resourceScope, address);
    }

    @Override
    public ExternKind getKind() {
        return ExternKind.WASM_EXTERN_GLOBAL;
    }

    public ValType getContent() {
        return ValType.valueOf(WasmAPI.globaltype_content(address));
    }

    public boolean isMutable() {
        byte b = WasmAPI.globaltype_mutability(address);
        return b != 0;
    }

    @Override
    public void close() {
        WasmAPI.globaltype_delete(address);
    }

}
