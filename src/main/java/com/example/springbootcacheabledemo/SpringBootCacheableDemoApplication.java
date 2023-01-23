package com.example.springbootcacheabledemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class SpringBootCacheableDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootCacheableDemoApplication.class, args);
	}

}
