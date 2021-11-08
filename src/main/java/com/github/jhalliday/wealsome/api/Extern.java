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
import jdk.incubator.foreign.ResourceScope;
import org.jboss.logging.Logger;

/**
 * @author Jonathan Halliday (jonathan.halliday@redhat.com)
 */
public abstract class Extern implements Addressable {

    private static final Logger logger = Logger.getLogger(Extern.class);

    protected final MemoryAddress address;

    protected Extern(ResourceScope resourceScope, MemoryAddress address) {
        //this.resourceScope = resourceScope;
        this.address = address;
    }

    @Override
    public MemoryAddress address() {
        return address;
    }

    public abstract ExternKind getKind();

}
