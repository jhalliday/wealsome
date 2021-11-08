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
import org.jboss.logging.Logger;

/**
 * @author Jonathan Halliday (jonathan.halliday@redhat.com)
 */
public class WasmFunc implements Addressable {

    private static final Logger logger = Logger.getLogger(WasmFunc.class);

    private final MemoryAddress address;

    public WasmFunc(MemoryAddress address) {
        this.address = address;
    }

    public void apply(ValVec params, ValVec results) {
        WasmAPI.func_call(address, params.address(), results.address());
    }

    public long getParamArity() {
        return WasmAPI.func_param_arity(address);
    }

    public long getResultArity() {
        return WasmAPI.func_result_arity(address);
    }

    @Override
    public MemoryAddress address() {
        return address;
    }
}
