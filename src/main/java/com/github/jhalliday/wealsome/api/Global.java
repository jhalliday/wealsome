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

import jdk.incubator.foreign.ResourceScope;
import org.jboss.logging.Logger;

/**
 * @author Jonathan Halliday (jonathan.halliday@redhat.com)
 */
public class Global extends Extern implements AutoCloseable {

    private static final Logger logger = Logger.getLogger(Global.class);

    private final Store store;
    private final GlobalType globalType;
    private final Val val;

    public Global(ResourceScope resourceScope, Store store, GlobalType globalType, Val val) {
        super(resourceScope, WasmAPI.global_new(store, globalType, val));
        this.store = store;
        this.globalType = globalType;
        this.val = val;
    }

    @Override
    public ExternKind getKind() {
        return ExternKind.WASM_EXTERN_GLOBAL;
    }

    @Override
    public void close() {
        WasmAPI.global_delete(address);
    }

}
