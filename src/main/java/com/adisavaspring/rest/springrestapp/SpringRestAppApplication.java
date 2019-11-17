package com.adisavaspring.rest.springrestapp;

import com.adisavaspring.rest.springrestapp.security.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
public class SpringRestAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringRestAppApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder(){
		return new BCryptPasswordEncoder();
	}

//	I choose to use @Component the SpringApplicationContext class instead of:
//	@Bean
//	public SpringApplicationContext springApplicationContext() {
//		return new SpringApplicationContext();
//	}

//	@Bean(name = "AppProperties")
//	public AppProperties getApplicationProperties(){
//		return new AppProperties();
//	}
}
