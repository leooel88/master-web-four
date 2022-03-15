package com.quest.web_quest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@SpringBootApplication
@ComponentScan(basePackages = {"com.quest"})
public class WebQuestApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebQuestApplication.class, args);
	}

}
