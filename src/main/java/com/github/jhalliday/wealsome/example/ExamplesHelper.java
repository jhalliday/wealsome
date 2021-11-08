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
package com.github.jhalliday.wealsome.example;

import jdk.incubator.foreign.ResourceScope;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class ExamplesHelper {

    private static final File scriptDir = new File("/wasmtime.git/crates/c-api/wasm-c-api/example");

    public static byte[] readWasm(String filename) {
        try {
            byte[] data = Files.readAllBytes(new File(scriptDir, filename).toPath());
            return data;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void loadNativeImpl() {
        System.loadLibrary("wasmtime");
    }

    public static ResourceScope newResourceScope() {
        return ResourceScope.newConfinedScope();
    }
}
