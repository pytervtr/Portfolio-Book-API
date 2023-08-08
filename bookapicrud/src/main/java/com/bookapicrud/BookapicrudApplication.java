package com.bookapicrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BookapicrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookapicrudApplication.class, args);
	}

}
