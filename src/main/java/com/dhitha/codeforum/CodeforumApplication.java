package com.dhitha.codeforum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CodeforumApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeforumApplication.class, args);
	}

}
