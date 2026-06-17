package com.banking;
import io.specmatic.test.SpecmaticJUnitSupport;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
public class BankingContractTest extends SpecmaticJUnitSupport {
    @BeforeAll
    public static void setup() {
        System.setProperty("testBaseURL", "http://localhost:9000");
    }
}
