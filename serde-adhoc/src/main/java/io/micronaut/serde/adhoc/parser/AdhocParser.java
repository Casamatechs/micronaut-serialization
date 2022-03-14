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
package io.micronaut.serde.adhoc.parser;

import java.io.Closeable;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 *
 */
public class AdhocParser implements Closeable {

    protected static final int INT_QUOTE = '"';
    protected static final int ZERO = '0';
    protected static final int NINE = '9';
    protected static final int T = 't';
    protected static final int F = 'f';

    private int posArray;
    private int posAcc;
    private int posOacc;

    private byte[] byteArray;

    private HashMap<String, Object> jsonKeyValues;

    private final String[] ACC_KEYS = {"name", "accountNumber", "balance", "isActive", "favoriteAccounts"};

    private boolean processingArray;

    private String processingKey;

    protected enum JsonToken {
        KEY, VALUE
    }

    public AdhocParser(byte[] input) {
        this.byteArray = input;
        this.posArray = 0;
        this.posAcc = 0;
        this.posOacc = 0;
        this.jsonKeyValues = new HashMap<>();
        this.processingArray = false;
        this.processingKey = null;
    }

    /**
     * Closes this stream and releases any system resources associated
     * with it. If the stream is already closed then invoking this
     * method has no effect.
     *
     * <p> As noted in {@link AutoCloseable#close()}, cases where the
     * close may fail require careful attention. It is strongly advised
     * to relinquish the underlying resources and to internally
     * <em>mark</em> the {@code Closeable} as closed, prior to throwing
     * the {@code IOException}.
     *
     * @throws IOException if an I/O error occurs
     */
    @Override
    public void close() throws IOException {

    }

    /**
     *
     * @return Next processed token by the parser
     */
    public JsonToken nextToken() {
        if (processingKey == null) {
            while (posArray < byteArray.length) {
                int pos = posArray++;
                if (byteArray[pos] == INT_QUOTE) { // Here we already know that is a key
                    processingKey = ACC_KEYS[posAcc++];
                    posArray += processingKey.length() + 1;
                    return JsonToken.KEY;
                }
            }
            return null; // This is the end of the JSON file.
        } else {
            if (processingKey.equals(ACC_KEYS[0])) { // name
                findNextQuote();
                int iChar = ++posArray;
                findNextQuote();
                int fChar = posArray++;
                if (!processingArray) {
                    this.jsonKeyValues.put(processingKey, new String(Arrays.copyOfRange(byteArray, iChar, fChar), StandardCharsets.UTF_8));
                } else {
                    getOthersAccounts()[this.posOacc].put(processingKey, new String(Arrays.copyOfRange(byteArray, iChar, fChar), StandardCharsets.UTF_8));
                }
                this.processingKey = null;
            } else if (processingKey.equals(ACC_KEYS[1])) { //accountNumber
                findNextDigit();
                if (!processingArray) {
                    this.jsonKeyValues.put(processingKey, parseAccountNumber());
                } else {
                    getOthersAccounts()[this.posOacc].put(processingKey, parseAccountNumber());
                    this.posAcc++; // We have to skip the balance key while composing the array
                }
                this.processingKey = null;
            } else if (processingKey.equals(ACC_KEYS[2])) { // balance
                findNextDigit();
                this.jsonKeyValues.put(processingKey, parseBalance());
                this.processingKey = null;
            } else if (processingKey.equals(ACC_KEYS[3])) { // isActive
                if (!processingArray) {
                    this.jsonKeyValues.put(processingKey, findBooleanValue());
                } else {
                    getOthersAccounts()[this.posOacc++].put(processingKey, findBooleanValue());
                    this.posAcc = 0;
                }
                this.processingKey = null;
            } else if (processingKey.equals(ACC_KEYS[4])) { // otherAccounts
                this.processingArray = true;
                this.jsonKeyValues.put(processingKey, new Map[]{new HashMap<String, Object>(), new HashMap<String, Object>(), new HashMap<String, Object>()});
                this.processingKey = null;
                this.posAcc = 0;
            } else {
                System.err.println("Non expected token found in JSON");
            }
            return JsonToken.VALUE;
        }
    }

    private void findNextQuote() {
        while (byteArray[posArray] != INT_QUOTE) {
            posArray++;
        }
    }

    private void findNextDigit() {
        while (byteArray[posArray] < ZERO || byteArray[posArray] > NINE) {
            posArray += 1;
        }
    }

    private long parseAccountNumber() {
        // This method for now emulates in some ways Jackson implementation, as I suppose it's optimized enough
        long accNumber = (byteArray[posArray++] - '0') * 1_000_000_000L;
        for (int i = 8; i >= 0; i--) {
            accNumber += (byteArray[posArray++] - '0') * longPow(10, i);
        }
        return accNumber;
    }

    private long longPow(int a, int b) { // Exponentation by squaring https://stackoverflow.com/questions/29996070/using-int-double-and-long-in-calculation-of-powers
        long res = 1;
        long sq = a;
        while (b > 0) {
            if (b % 2 == 1) {
                res *= sq;
            }
            sq = sq * sq;
            b /= 2;
        }
        return res;
    }

    private double parseBalance() { // We rely on the Double parsing provided by Java
        String balance = new String(byteArray, posArray, 6, StandardCharsets.UTF_8);
        posArray += 6;
        return Double.parseDouble(balance);
    }

    private boolean findBooleanValue() {
        while (byteArray[posArray] != F && byteArray[posArray] != T) {
            posArray += 1;
        }
        if (byteArray[posArray] == F) {
            posArray += 5;
            return false;
        } else {
            posArray += 4;
            return true;
        }
    }

    private Map[] getOthersAccounts() {
        return (Map[]) this.jsonKeyValues.get(ACC_KEYS[4]);
    }

    /**
     *
     * @return Returns a @link{Map} containing the parsed keys and values
     */
    public Map<String, Object> getDeserializeMap() {
        return this.jsonKeyValues;
    }
}
