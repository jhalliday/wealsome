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

import com.github.jhalliday.wealsome.generated.wasm_extern_vec_t;
import jdk.incubator.foreign.*;
import org.jboss.logging.Logger;

import java.util.List;

/**
 * An array of externs. (wasm_extern_vec_t*)
 *
 * @author Jonathan Halliday (jonathan.halliday@redhat.com)
 */
public class ExternVec implements Addressable, AutoCloseable {

    private static final Logger logger = Logger.getLogger(ExternVec.class);

    private final ResourceScope resourceScope;
    private final MemorySegment extern_vec_t;

    public ExternVec(ResourceScope resourceScope) {
        this.resourceScope = resourceScope;
        extern_vec_t = wasm_extern_vec_t.allocate(resourceScope);
        WasmAPI.extern_vec_new_empty(extern_vec_t);
    }

    public ExternVec(ResourceScope resourceScope, List<? extends Extern> externList) {
        this.resourceScope = resourceScope;
        extern_vec_t = wasm_extern_vec_t.allocate(resourceScope);

        MemorySegment memorySegment = SegmentAllocator.ofScope(resourceScope).allocateArray(WasmAPI.pointerMemoryLayout, externList.size());
        for (int i = 0; i < externList.size(); i++) {
            MemorySegment slice = memorySegment.asSlice(WasmAPI.pointerMemoryLayout.byteSize() * i);
            WasmAPI.ptr$VH.set(slice, externList.get(i).address());
        }

        WasmAPI.extern_vec_new(extern_vec_t, externList.size(), memorySegment.address());
    }

    public WasmFunc asFunc(int index) {
        MemoryAddress iMemoryAddress2 = WasmAPI.ptr$get(data().asSegment(WasmAPI.pointerMemoryLayout.byteSize() * size(), resourceScope), index);
        WasmFunc func = new WasmFunc(iMemoryAddress2);
        return func;
    }

    public long size() {
        return wasm_extern_vec_t.size$get(extern_vec_t);
    }

    public MemoryAddress data() {
        MemoryAddress iMemoryAddress = wasm_extern_vec_t.data$get(extern_vec_t);
        return iMemoryAddress;
    }

    public ExternType getExternType(int i) {
        MemoryAddress dataAddress = wasm_extern_vec_t.data$get(extern_vec_t);
        MemoryAddress address = WasmAPI.ptr$get(dataAddress.asSegment(100, resourceScope), i);
        MemoryAddress externTypeAddress = WasmAPI.extern_type(address);
        // TODO dedup
        ExternKind externKind = ExternKind.valueOf(externTypeAddress);
        return switch (externKind) {
            case WASM_EXTERN_FUNC -> new FuncType(resourceScope, WasmAPI.externtype_as_functype(externTypeAddress));
            case WASM_EXTERN_GLOBAL -> new GlobalType(resourceScope, WasmAPI.externtype_as_globaltype(externTypeAddress));
            case WASM_EXTERN_TABLE -> new TableType(resourceScope, WasmAPI.externtype_as_tabletype(externTypeAddress));
            case WASM_EXTERN_MEMORY -> new MemoryType(resourceScope, WasmAPI.externtype_as_memorytype(externTypeAddress));
            default -> throw new IllegalArgumentException();
        };
    }

    public ExternVec of(MemoryAddress memoryAddress) {
        wasm_extern_vec_t.size$set(extern_vec_t, 1);
        wasm_extern_vec_t.data$set(extern_vec_t, memoryAddress);
        return this;
    }

    @Override
    public MemoryAddress address() {
        return extern_vec_t.address();
    }

    @Override
    public void close() {

//        wasm_extern_vec_t.size$set(extern_vec_t, 0);
        //wasm_extern_vec_t.data$set(extern_vec_t, MemoryAddress.NULL);

        WasmAPI.extern_vec_delete(extern_vec_t);
    }
}
