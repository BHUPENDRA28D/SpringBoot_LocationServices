package com.example.SpringBoot_LocaitonSevices;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
//import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SpringBootLocaitonSevicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootLocaitonSevicesApplication.class, args);
	}

}
