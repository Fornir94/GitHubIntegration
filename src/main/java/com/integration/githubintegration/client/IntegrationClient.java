package com.integration.githubintegration.client;

import com.integration.githubintegration.model.RepoDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "Fornir94", url = "https://api.github.com")
public interface IntegrationClient {

    @GetMapping("/repos/{owner}/{repo}")
    RepoDTO getRepository(@PathVariable String owner, @PathVariable String repo);

    @GetMapping("/users/{username}/repos")
    List<RepoDTO> getAllRepositories(@PathVariable String username);
}
