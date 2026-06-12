package com.banking;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import io.specmatic.test.SpecmaticJUnitSupport;

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT,
    properties = {
        "server.port=8080",
        "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration,org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration"
    }
)
@ActiveProfiles("test")
public class BankingContractTest extends SpecmaticJUnitSupport {

    static {
        System.setProperty("host", "localhost");
        System.setProperty("port", "8080");
        System.setProperty("endpointsAPI", "http://localhost:8080/api-docs");
    }

    @Override
    public void contractTest() throws Throwable {
        super.contractTest();
    }
}