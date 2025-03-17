package com.inditex.price_core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class PriceCoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(PriceCoreApplication.class, args);
	}

}
