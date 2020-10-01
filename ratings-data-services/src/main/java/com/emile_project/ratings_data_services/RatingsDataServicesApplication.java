package com.emile_project.ratings_data_services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class RatingsDataServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(RatingsDataServicesApplication.class, args);
	}

}
