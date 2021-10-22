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
package io.micronaut.serde;

import java.io.IOException;

import io.micronaut.core.annotation.Indexed;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.core.annotation.Nullable;
import io.micronaut.core.beans.BeanIntrospection;
import io.micronaut.core.type.Argument;
import io.micronaut.serde.exceptions.SerdeException;

/**
 * Models a build time serializer. That is a class computed at build-time that can
 * be used to serialize an instance of {@link T}.
 *
 * @param <T> The type to be serialized
 * @author Jonas Konrad
 * @author graemerocher
 */
@Indexed(Serializer.class)
public interface Serializer<T> {

    /**
     * Serializes the given value using the passed {@link Encoder}.
     * @param encoder The encoder to use
     * @param context The encoder context, never {@code null}
     * @param value The value, can be {@code null}
     * @param generics The generic arguments for this type, if any
     * @throws IOException If an error occurs during serialization
     */
    void serialize(
            @NonNull Encoder encoder,
            @NonNull EncoderContext context,
            @NonNull T value,
            @NonNull Argument<? extends T> type,
            Argument<?>... generics) throws IOException;

    /**
     * Used for {@code JsonInclude.Include#NON_EMPTY} checking.
     */
    default boolean isEmpty(@Nullable T value) {
        return false;
    }

    /**
     * Context object passes to the {@link #serialize(Encoder, io.micronaut.serde.Serializer.EncoderContext, Object, io.micronaut.core.type.Argument, io.micronaut.core.type.Argument[])}  method.
     */
    interface EncoderContext {
        /**
         * Finds a serializer for the given type.
         * @param forType The type
         * @param <T> The generic type
         * @return The serializer
         * @throws io.micronaut.serde.exceptions.SerdeException if an exception occurs
         */
        @NonNull <T> Serializer<? super T> findSerializer(@NonNull Argument<? extends T> forType)
            throws SerdeException;

        /**
         * Finds a serializer for the given type.
         * @param forType The type
         * @param <T> The generic type
         * @return The serializer
         * @throws io.micronaut.serde.exceptions.SerdeException if an exception occurs
         */
        default @NonNull <T> Serializer<? super T> findSerializer(@NonNull Class<? extends T> forType)
                throws SerdeException {
            return findSerializer(Argument.of(forType));
        }

        /**
         * Gets an introspection for the given type for serialization.
         * @param type The type
         * @param <T> The generic type
         * @return The introspection, never {@code null}
         * @throws io.micronaut.core.beans.exceptions.IntrospectionException if no introspection exists
         */
        @NonNull <T> BeanIntrospection<T> getSerializableIntrospection(@NonNull Argument<T> type);
    }
}