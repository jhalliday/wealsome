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

import com.github.jhalliday.wealsome.generated.wasm_exporttype_vec_t;
import jdk.incubator.foreign.Addressable;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;
import org.jboss.logging.Logger;

/*
 * @author Jonathan Halliday (jonathan.halliday@redhat.com)
 */
public class ExportTypeVec implements Addressable {

    private static final Logger logger = Logger.getLogger(ExportTypeVec.class);

    private final ResourceScope resourceScope;
    private final MemorySegment exporttype_vec_t;

    public ExportTypeVec(ResourceScope resourceScope) {
        this.resourceScope = resourceScope;
        exporttype_vec_t = wasm_exporttype_vec_t.allocate(resourceScope);
    }

    public long size() {
        return wasm_exporttype_vec_t.size$get(exporttype_vec_t);
    }

    public ByteVec name(int i) {
        MemoryAddress dataAddress = wasm_exporttype_vec_t.data$get(exporttype_vec_t);
        MemoryAddress address = WasmAPI.exporttype_name(WasmAPI.ptr$get(dataAddress.asSegment(100, resourceScope), i));
        ByteVec byteVec = new ByteVec(resourceScope, address);
        return byteVec;
    }

    public ExternType getExternType(int i) {
        MemoryAddress dataAddress = wasm_exporttype_vec_t.data$get(exporttype_vec_t);
        MemoryAddress address = WasmAPI.ptr$get(dataAddress.asSegment(100, resourceScope), i);
        MemoryAddress externTypeAddress = WasmAPI.exporttype_type(address);
        ExternKind externKind = ExternKind.valueOf(externTypeAddress);
        return switch (externKind) {
            case WASM_EXTERN_FUNC -> new FuncType(resourceScope, WasmAPI.externtype_as_functype(externTypeAddress));
            case WASM_EXTERN_GLOBAL -> new GlobalType(resourceScope, WasmAPI.externtype_as_globaltype(externTypeAddress));
            case WASM_EXTERN_TABLE -> new TableType(resourceScope, WasmAPI.externtype_as_tabletype(externTypeAddress));
            case WASM_EXTERN_MEMORY -> new MemoryType(resourceScope, WasmAPI.externtype_as_memorytype(externTypeAddress));
            default -> throw new IllegalArgumentException();
        };
    }

    @Override
    public MemoryAddress address() {
        return exporttype_vec_t.address();
    }
}
