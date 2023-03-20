package com.sleeptracker.sleeptracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

@PropertySources({
    @PropertySource("classpath:application.properties"),
    @PropertySource(value = "file:${user.dir}/.env", ignoreResourceNotFound = true)
})
@SpringBootApplication
public class SleeptrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SleeptrackerApplication.class, args);
	}

}
