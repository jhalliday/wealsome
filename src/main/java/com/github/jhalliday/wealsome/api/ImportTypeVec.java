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
import com.github.jhalliday.wealsome.generated.wasm_importtype_vec_t;
import com.github.jhalliday.wealsome.generated.wasm_valtype_vec_t;
import jdk.incubator.foreign.Addressable;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;
import org.jboss.logging.Logger;

/**
 * @author Jonathan Halliday (jonathan.halliday@redhat.com)
 */
public class ImportTypeVec implements Addressable {

    private static final Logger logger = Logger.getLogger(ImportTypeVec.class);

    private final ResourceScope resourceScope;
    private final MemorySegment importtype_vec_t;

    public ImportTypeVec(ResourceScope resourceScope) {
        this.resourceScope = resourceScope;
        importtype_vec_t = wasm_importtype_vec_t.allocate(resourceScope);
    }

    public long size() {
        return wasm_importtype_vec_t.size$get(importtype_vec_t);
    }

    @Override
    public MemoryAddress address() {
        return importtype_vec_t.address();
    }
}
