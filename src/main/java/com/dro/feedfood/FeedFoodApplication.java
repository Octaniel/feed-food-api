package com.dro.feedfood;

import com.dro.feedfood.config.property.SpringApiProperty;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@EnableConfigurationProperties(SpringApiProperty.class)
@SpringBootApplication
public class FeedFoodApplication {

	public static void main(String[] args) {
		SpringApplication.run(FeedFoodApplication.class, args);
	}

}
