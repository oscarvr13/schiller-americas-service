package com.shiller.americas;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableCircuitBreaker
@EnableHystrixDashboard
@EnableFeignClients("com.shiller.americas.controller.client")
@SpringBootApplication
public class PersonMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PersonMicroserviceApplication.class, args);
	}
}
