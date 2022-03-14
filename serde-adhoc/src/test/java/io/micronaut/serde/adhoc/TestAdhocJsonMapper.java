package io.micronaut.serde.adhoc;

import io.micronaut.core.type.Argument;
import io.micronaut.serde.adhoc.pojo.Account;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;

public class TestAdhocJsonMapper {

    @Test
    void testJacksonWriteBook() throws IOException {
        AdhocJsonMapper objectMapper = new AdhocJsonMapper();
        Account acc = objectMapper.readValue(Thread.currentThread().getContextClassLoader().getResourceAsStream("simple_file.json"), Argument.of(Account.class));
        assertNotNull(acc);


    }
}
