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
public class Account {

    private String name;
    private long accountNumber;
    private double balance;
    @JsonProperty("isActive")
    private boolean isActive;
    private OtherAccount[] favoriteAccounts;

    @JsonCreator
    public Account(String name, long accountNumber, double balance, boolean isActive, OtherAccount[] favoriteAccounts) {
        this.name = name;
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.isActive = isActive;
        this.favoriteAccounts = favoriteAccounts;
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
     * @return account property
     */
    public long getAccountNumber() {
        return accountNumber;
    }

    /**
     *
     * @return balance property
     */
    public double getBalance() {
        return balance;
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
     * @return favoriteAccounts property
     */
    public OtherAccount[] getFavoriteAccounts() {
        return favoriteAccounts;
    }

    /**
     *
     * @return String representation of the class
     */
    @Override
    public String toString() {
        return "{\n" +
                String.format("\"name\": \"%s\",\n", name) +
                String.format("\"accountNumber\": %d,\n", accountNumber) +
                String.format("\"balance\": %f,\n", balance) +
                String.format("\"isActive\": %s,\n", isActive) +
                String.format("\"favoriteAccounts\": [%s,%s,%s]\n", favoriteAccounts[0], favoriteAccounts[1], favoriteAccounts[2]) +
                "}\n";
    }
}