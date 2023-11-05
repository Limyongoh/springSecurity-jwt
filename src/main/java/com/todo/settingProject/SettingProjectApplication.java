package com.todo.settingProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing //JpaAuditing 기능을 사용하기 위해 추가 - @createDate, @modifyDate.....
@SpringBootApplication
@ConfigurationPropertiesScan
public class SettingProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SettingProjectApplication.class, args);
	}

}
