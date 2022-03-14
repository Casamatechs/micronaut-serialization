/*
 * Copyright 2017-2022 original authors
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
package io.micronaut.serde.adhoc.pojo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.micronaut.serde.annotation.Serdeable;

/**
 *
 */
@Serdeable
public class OtherAccount {
    private String name;
    private long accountNumber;
    @JsonProperty("isActive")
    private boolean isActive;

    @JsonCreator
    public OtherAccount(String name, long accountNumber, boolean isActive) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.isActive = isActive;
    }

    /**
     *
     * @return name property
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return accountNumber property
     */
    public long getAccountNumber() {
        return accountNumber;
    }

    /**
     *
     * @return isActive property
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "{\n" +
                String.format("\"name\": \"%s\",\n", name) +
                String.format("\"accountNumber\": %d,\n", accountNumber) +
                String.format("\"isActive\": %s,\n", isActive) +
                "}\n";
    }
}
