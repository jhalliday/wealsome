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

import jdk.incubator.foreign.*;
import org.jboss.logging.Logger;

/**
 * @author Jonathan Halliday (jonathan.halliday@redhat.com)
 */
public class Trap implements Addressable, AutoCloseable {

    private static final Logger logger = Logger.getLogger(Trap.class);

    public final MemorySegment memorySegment;
    private final MemoryAddress address;

    public Trap(ResourceScope resourceScope) {
        memorySegment = SegmentAllocator.ofScope(resourceScope).allocate(WasmAPI.pointerMemoryLayout);
        address = memorySegment.address();
    }

    public Trap(Store store, ByteVec message) {
        memorySegment = null;
        address = WasmAPI.trap_new(store.address(), message.address());
    }

    @Override
    public MemoryAddress address() {
        return address;
    }

    @Override
    public void close() {
        WasmAPI.trap_delete(address);
    }

}
