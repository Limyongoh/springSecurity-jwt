package com.todo.settingProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class SettingProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SettingProjectApplication.class, args);
	}

}
