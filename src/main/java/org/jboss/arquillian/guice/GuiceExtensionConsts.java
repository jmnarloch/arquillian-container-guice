/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.arquillian.guice;

/**
 * Defines a set of consts used in this component.
 *
 * @author <a href="mailto:jmnarloch@gmail.com">Jakub Narloch</a>
 * @version $Revision: $
 */
public final class GuiceExtensionConsts {

    /**
     * <p>Creates new instance of {@link GuiceExtensionConsts} class.</p>
     *
     * <p>Private constructor prevents from instantiation outside this class.</p>
     */
    public GuiceExtensionConsts() {
        // empty constructor
    }

    /**
     * Represents the fully qualified name for {@link com.google.inject.Injector}.
     */
    public static final String INJECTOR =  "com.google.inject.Injector";

    /**
     * Represents the Guice maven artifact name.
     */
    public static final String GUICE_ARTIFACT_NAME = "com.google.inject:guice";

    /**
     * Represents the default Guice maven artifact version.
     */
    public static final String GUICE_ARTIFACT_VERSION = "3.0";
}
