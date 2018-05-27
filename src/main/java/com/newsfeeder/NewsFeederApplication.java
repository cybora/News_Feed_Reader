package com.newsfeeder;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class NewsFeederApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsFeederApplication.class, args);
	}

}
