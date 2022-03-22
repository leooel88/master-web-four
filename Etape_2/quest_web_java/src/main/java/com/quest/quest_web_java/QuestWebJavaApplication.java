package com.quest.quest_web_java;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.quest"})
public class QuestWebJavaApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuestWebJavaApplication.class, args);
	}

}
