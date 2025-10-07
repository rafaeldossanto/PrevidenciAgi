package com.example.PrevidenciAgi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PrevidenciAgiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrevidenciAgiApplication.class, args);
	}

}
