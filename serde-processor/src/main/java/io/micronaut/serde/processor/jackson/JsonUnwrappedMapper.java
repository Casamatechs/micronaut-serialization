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
package io.micronaut.serde.processor.jackson;

import java.util.Collections;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import io.micronaut.core.annotation.AnnotationValue;
import io.micronaut.core.annotation.AnnotationValueBuilder;
import io.micronaut.inject.annotation.TypedAnnotationMapper;
import io.micronaut.inject.visitor.VisitorContext;
import io.micronaut.serde.annotation.SerdeConfig;

public class JsonUnwrappedMapper implements TypedAnnotationMapper<JsonUnwrapped> {
    @Override
    public Class<JsonUnwrapped> annotationType() {
        return JsonUnwrapped.class;
    }

    @Override
    public List<AnnotationValue<?>> map(AnnotationValue<JsonUnwrapped> annotation, VisitorContext visitorContext) {
        final AnnotationValueBuilder<SerdeConfig.Unwrapped> unwrapped =
                AnnotationValue.builder(SerdeConfig.Unwrapped.class);
        annotation.stringValue(SerdeConfig.Unwrapped.PREFIX).ifPresent(v -> unwrapped.member(
                SerdeConfig.Unwrapped.PREFIX, v
        ));
        annotation.stringValue(SerdeConfig.Unwrapped.SUFFIX).ifPresent(v -> unwrapped.member(
                SerdeConfig.Unwrapped.SUFFIX, v
        ));
        return Collections.singletonList(unwrapped.build());
    }
}