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

import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.ResourceScope;
import org.jboss.logging.Logger;

import java.util.List;

/**
 * A WebAssembly (or host) function type.
 *
 * @author Jonathan Halliday (jonathan.halliday@redhat.com)
 */
public class FuncType extends ExternType implements AutoCloseable {

    private static final Logger logger = Logger.getLogger(FuncType.class);

    private final boolean own;

    public FuncType(ValtypeVec params, ValtypeVec results) {
        super(null, WasmAPI.functype_new(params.address(), results.address()));
        own = true;
    }

    protected FuncType(ResourceScope resourceScope, MemoryAddress address) {
        super(resourceScope, address);
        own = false;
    }

    @Override
    public ExternKind getKind() {
        return ExternKind.WASM_EXTERN_FUNC;
    }

    public ValtypeVec paramTypes() {
        MemoryAddress memoryAddress = WasmAPI.functype_params(address);
        return new ValtypeVec(resourceScope, memoryAddress);
    }

    public ValtypeVec resultTypes() {
        MemoryAddress memoryAddress = WasmAPI.functype_results(address);
        return new ValtypeVec(resourceScope, memoryAddress);
    }

    public static FuncType new_0_0(ResourceScope resourceScope) {
        ValtypeVec paramsValtypeVec = new ValtypeVec(resourceScope);
        ValtypeVec resultsValtypeVec = new ValtypeVec(resourceScope);
        return new FuncType(paramsValtypeVec, resultsValtypeVec);
    }

    public static FuncType new_1_1(ResourceScope resourceScope, ValType paramType, ValType resultType) {
        ValtypeVec paramsValtypeVec = new ValtypeVec(resourceScope, List.of(paramType));
        ValtypeVec resultsValtypeVec = new ValtypeVec(resourceScope, List.of(resultType));
        return new FuncType(paramsValtypeVec, resultsValtypeVec);
    }

    public static FuncType new_0_1(ResourceScope resourceScope, ValType resultType) {
        ValtypeVec paramsValtypeVec = new ValtypeVec(resourceScope);
        ValtypeVec resultsValtypeVec = new ValtypeVec(resourceScope, List.of(resultType));
        return new FuncType(paramsValtypeVec, resultsValtypeVec);
    }

    public static FuncType new_1_0(ResourceScope resourceScope, ValType paramType) {
        ValtypeVec paramsValtypeVec = new ValtypeVec(resourceScope, List.of(paramType));
        ValtypeVec resultsValtypeVec = new ValtypeVec(resourceScope);
        return new FuncType(paramsValtypeVec, resultsValtypeVec);
    }

    @Override
    public MemoryAddress address() {
        return address;
    }

    @Override
    public void close() {
        if (own) {
            WasmAPI.functype_delete(address);
        }
    }
}
