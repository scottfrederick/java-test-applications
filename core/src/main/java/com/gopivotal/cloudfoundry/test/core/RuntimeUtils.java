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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility methods used to get information about the current runtime
 */
@Component
public final class RuntimeUtils {

    private final Map<String, String> environment;

    private final RuntimeMXBean runtimeMXBean;

    private final Map<Object, Object> systemProperties;

    private final Environment springEnvironment;

    private static final String[] REQUIRED_PROPERTIES = {
            "cloud.application.app-id",
            "cloud.application.instance-id"
    };

    @Autowired
    public RuntimeUtils(Environment springEnvironment) {
        this(System.getenv(), ManagementFactory.getRuntimeMXBean(), System.getProperties(), springEnvironment);
    }

    RuntimeUtils(Map<String, String> environment, RuntimeMXBean runtimeMXBean, Map<Object, Object> systemProperties, Environment springEnvironment) {
        this.environment = environment;
        this.runtimeMXBean = runtimeMXBean;
        this.systemProperties = systemProperties;
        this.springEnvironment = springEnvironment;
    }

    public List<String> classPath() {
        return Arrays.asList(this.runtimeMXBean.getClassPath().split(":"));
    }

    public Map<String, String> environmentVariables() {
        return this.environment;
    }

    public List<String> inputArguments() {
        return this.runtimeMXBean.getInputArguments();
    }

    public Map<Object, Object> systemProperties() {
        return this.systemProperties;
    }

    public List<String> springProfiles() {
        return Arrays.asList(this.springEnvironment.getActiveProfiles());
    }

    public Map<String, String> cloudProperties() {
        Map<String, String> properties = new HashMap<>();
        for (String propertyName : REQUIRED_PROPERTIES) {
            properties.put(propertyName, this.springEnvironment.getProperty(propertyName));
        }
        return properties;
    }
}
