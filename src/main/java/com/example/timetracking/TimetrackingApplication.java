package com.example.timetracking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class TimetrackingApplication {

	public static void main(String[] args) {
		SpringApplication.run(TimetrackingApplication.class, args);
	}
}
