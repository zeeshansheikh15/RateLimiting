package com.test.header;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class TestHeaderApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestHeaderApplication.class, args);
	}

}
