// package com.banking;

// import org.junit.jupiter.api.Test;
// import org.springframework.boot.test.context.SpringBootTest;

// @SpringBootTest
// class BankingSystemApplicationTests {

// 	@Test
// 	void contextLoads() {
// 	}

// }


package com.banking;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import com.banking.repo.UserRepository;


@SpringBootTest
@ActiveProfiles("test")
class BankingSystemApplicationTests {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Test
    void contextLoads() {
    }

    @Test
    void verifyPasswordHash() {

    String hash = "$2a$10$i4S5ksAsnY2/XdCxIhO8O.RpqNBhAOq8Ri/i1M/kcJAYpYOhR5mO.";

    System.out.println("=================================");
    System.out.println("MATCH = " + passwordEncoder.matches("password", hash));
    System.out.println("=================================");
    } 

	@Autowired
	private UserRepository userRepository;

	@Test
	void verifySeedUser() {

		System.out.println("USER COUNT = " + userRepository.count());

		userRepository.findAll().forEach(u ->
			System.out.println(
				u.getId() + " | " +
				u.getEmail() + " | " +
				u.getRole()
			)
		);
	}

	@Autowired
	private javax.sql.DataSource dataSource;

	@Test
	void runDataSqlManually() throws Exception {

		org.springframework.jdbc.datasource.init.ResourceDatabasePopulator populator =
				new org.springframework.jdbc.datasource.init.ResourceDatabasePopulator(
						new org.springframework.core.io.ClassPathResource("data.sql"));

		populator.execute(dataSource);

		System.out.println("USER COUNT AFTER SQL = " + userRepository.count());
	}

}