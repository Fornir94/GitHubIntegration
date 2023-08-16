package com.integration.githubintegration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class GitHubIntegrationApplication {

	public static void main(String[] args) {
		SpringApplication.run(GitHubIntegrationApplication.class, args);
	}

}
