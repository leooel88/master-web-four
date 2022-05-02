package com.buybricks.buybricks;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.buybricks.app"})
@EntityScan(basePackages = {"com.buybricks.app.model"})
public class BuybricksApplication {
	public static void main(String[] args) {
		SpringApplication.run(BuybricksApplication.class, args);
	}

}
