package com.se.besearchapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class BeSearchappApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(BeSearchappApplication.class, args);
	}

}
