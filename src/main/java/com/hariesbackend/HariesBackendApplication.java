package com.hariesbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
@SpringBootApplication
public class HariesBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(HariesBackendApplication.class, args);
	}

}
