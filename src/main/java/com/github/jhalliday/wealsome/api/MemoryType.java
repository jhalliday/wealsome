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

/**
 * @author Jonathan Halliday (jonathan.halliday@redhat.com)
 */
public class MemoryType extends ExternType {

    private static final Logger logger = Logger.getLogger(MemoryType.class);

    protected MemoryType(ResourceScope resourceScope, MemoryAddress address) {
        super(resourceScope, address);
    }

    @Override
    public ExternKind getKind() {
        return ExternKind.WASM_EXTERN_MEMORY;
    }

    public Limits getLimits() {
        return new Limits(resourceScope, WasmAPI.memorytype_limits(address));
    }
}
