package com.quest.quest_web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.quest"})
public class QuestWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuestWebApplication.class, args);
	}

}
