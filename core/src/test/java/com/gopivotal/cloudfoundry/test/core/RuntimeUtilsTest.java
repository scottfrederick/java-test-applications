/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gopivotal.cloudfoundry.test.core;

import org.junit.Test;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;

import java.lang.management.RuntimeMXBean;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public final class RuntimeUtilsTest {

    private final Map<String, String> environment = new HashMap<>();

    private final RuntimeMXBean runtimeMXBean = mock(RuntimeMXBean.class);

    private final Map<Object, Object> systemProperties = new HashMap<>();

    private final Environment springEnvironment = new StandardEnvironment();

    private final RuntimeUtils runtimeUtils = new RuntimeUtils(this.environment, this.runtimeMXBean,
            this.systemProperties, this.springEnvironment);

    @Test
    public void classPath() {
        when(this.runtimeMXBean.getClassPath()).thenReturn("alpha:bravo");

        assertEquals(Arrays.asList("alpha", "bravo"), this.runtimeUtils.classPath());
    }

    @Test
    public void environmentVariables() {
        assertSame(this.environment, this.runtimeUtils.environmentVariables());
    }

    @Test
    public void inputArguments() {
        List<String> inputArguments = new ArrayList<>();
        when(this.runtimeMXBean.getInputArguments()).thenReturn(inputArguments);

        assertSame(inputArguments, this.runtimeUtils.inputArguments());
    }

    @Test
    public void systemProperties() {
        assertSame(this.systemProperties, this.runtimeUtils.systemProperties());
    }

    @Test
    public void springProfiles() {
        assertTrue(this.runtimeUtils.springProfiles().isEmpty());
    }

    @Test
    public void cloudProperties() {
        assertEquals(2, this.runtimeUtils.cloudProperties().size());
        assertNull(this.runtimeUtils.cloudProperties().get("cloud.application.app-id"));
        assertNull(this.runtimeUtils.cloudProperties().get("cloud.application.instance-id"));
    }
}
