/*
 * Copyright 2017-2021 original authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.micronaut.serde.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import io.micronaut.core.annotation.Introspected;
import io.micronaut.serde.Deserializer;
import io.micronaut.serde.Serializer;

/**
 * A Serde is an annotation that can be applied to type to indicate that
 * it is allowed to be serialized and deserialized to and from a format like JSON.
 *
 * <p>This annotation is meta-annotated with {@link Serdeable.Serializable} and
 * {@link Serdeable.Deserializable} which allow a type to be either serializable or deserializable</p>
 *
 * @author graemerocher
 * @since 1.0.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Introspected
@Serdeable.Serializable
@Serdeable.Deserializable
public @interface Serdeable {
    /**
     * Annotation used to indicate a type is serializable.
     */
    @Introspected
    @interface Serializable {
        /**
         * @return The {@link io.micronaut.serde.Serializer} to use.
         */
        Class<? extends Serializer> serializer() default Serializer.class;
    }

    /**
     * Annotation used to indicate a type is deserializable.
     */
    @Introspected
    @interface Deserializable {
        Class<? extends Deserializer> deserializer() default Deserializer.class;
    }
}