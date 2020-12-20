package com.kavgorodov.course;

import com.kavgorodov.course.security.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class CourseApplication {


	/*
	To do:
	1)Add error messages to properties file, not to change java code
	X)Add custom search by Baeldung
	 */

	public static void main(String[] args) {
		SpringApplication.run(CourseApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	SpringApplicationContext springApplicationContext() {
		return  new SpringApplicationContext();
	}

	@Bean(name = "AppProperties")
	AppProperties getAppProperties() {
		return  new AppProperties();
	}

}
