package com.application.muksullang;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MuksullangApplication {

	public static void main(String[] args) {
		SpringApplication.run(MuksullangApplication.class, args);
	}

}
