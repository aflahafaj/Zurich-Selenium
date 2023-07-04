package com.rds.rpa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.google.common.annotations.VisibleForTesting;

@SpringBootApplication
@EnableScheduling
@VisibleForTesting
public class ManulifeRpaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ManulifeRpaApplication.class, args);
	}

}
