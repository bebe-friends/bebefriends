package com.bbf.bebefriends;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.bbf.bebefriends")
public class BebefriendsApplication {

	public static void main(String[] args) {
		SpringApplication.run(BebefriendsApplication.class, args);
	}

}