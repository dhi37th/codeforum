package com.dhitha.codeforum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
public class CodeforumApplication {

	public static void main(String[] args) {
		SpringApplication.run(CodeforumApplication.class, args);
	}

}
