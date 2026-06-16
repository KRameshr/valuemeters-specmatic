package com.banking;

import io.specmatic.test.SpecmaticJUnitSupport;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
    properties = {
        "server.port=9000"
    }
)
@ActiveProfiles("test")
public class BankingContractTest extends SpecmaticJUnitSupport {

    @BeforeAll
    public static void setup() {
        System.setProperty("host", "localhost");
        System.setProperty("port", "9000");
        System.setProperty("testBaseURL", "http://localhost:9000");
    }
}