/*
 * Copyright 2002-2008 the original author or authors.
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

package org.springframework.integration.config.annotation;

import org.springframework.integration.endpoint.MessageEndpoint;

/**
 * Strategy interface for post-processing annotated methods.
 * 
 * @author Mark Fisher
 */
public interface AnnotationMethodPostProcessor {

	Object postProcess(Object bean, String beanName, Class<?> originalBeanClass);

	MessageEndpoint createEndpoint(Object bean, String beanName, Class<?> originalBeanClass,
			org.springframework.integration.annotation.MessageEndpoint endpointAnnotation);

}
