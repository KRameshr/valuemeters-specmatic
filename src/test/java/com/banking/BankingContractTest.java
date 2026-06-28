// package com.banking;
// import io.specmatic.test.SpecmaticJUnitSupport;
// import org.junit.jupiter.api.BeforeAll;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.context.ActiveProfiles;

// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
// @ActiveProfiles("test")
// public class BankingContractTest extends SpecmaticJUnitSupport {
    
//     @BeforeAll
//     public static void setup() {
//         System.setProperty("testBaseURL", "http://localhost:9000");
//     }
// }



package com.banking;

import io.specmatic.test.SpecmaticJUnitSupport;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("test")
@Sql(scripts = "/data.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class BankingContractTest extends SpecmaticJUnitSupport {

    @BeforeAll
    public static void setup() {
        System.setProperty("testBaseURL", "http://localhost:9000");
    }
}