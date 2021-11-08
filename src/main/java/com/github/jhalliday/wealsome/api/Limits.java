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

import com.github.jhalliday.wealsome.generated.wasm_limits_t;
import jdk.incubator.foreign.Addressable;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemorySegment;
import jdk.incubator.foreign.ResourceScope;
import org.jboss.logging.Logger;

/**
 * @author Jonathan Halliday (jonathan.halliday@redhat.com)
 */
public class Limits implements Addressable {

    private static final Logger logger = Logger.getLogger(Limits.class);

    private final ResourceScope resourceScope;
    private final MemorySegment limits_t;

    public Limits(ResourceScope resourceScope, MemoryAddress memoryAddress) {
        this.resourceScope = resourceScope;
        limits_t = wasm_limits_t.ofAddress(memoryAddress, resourceScope);
    }

    public long getMin() {
        return wasm_limits_t.min$get(limits_t);
    }

    public long getMax() {
        return wasm_limits_t.max$get(limits_t);
    }

    @Override
    public MemoryAddress address() {
        return limits_t.address();
    }

}
