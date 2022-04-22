package com.quest.quest_web_java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.quest"})
@EntityScan(basePackages = {"com.quest.etna.model"})
public class QuestWebJavaApplication {
	public static void main(String[] args) {
		SpringApplication.run(QuestWebJavaApplication.class, args);
	}	
}