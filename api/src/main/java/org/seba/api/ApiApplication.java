package org.seba.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {
		"org.seba.api",
		"org.seba.bll.services",
		"org.seba.dal.repositories",
		"org.seba.il.utils.jwt",
		"org.seba.il.filters",
		"org.seba.il.configs"
})
@EnableJpaRepositories(basePackages = "org.seba.dal.repositories")
@EntityScan(basePackages = "org.seba.dl.entities")
public class ApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

}

