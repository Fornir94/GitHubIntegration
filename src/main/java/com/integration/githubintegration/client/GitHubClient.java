package com.integration.githubintegration.client;

import com.integration.githubintegration.model.dto.RepoAppDTO;
import com.integration.githubintegration.model.dto.RepoGitDTO;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(value = "GitHubClient", url = "${github.api.client.url}")
@Headers("Accept: application/vnd.github+json")
public interface GitHubClient {

    @GetMapping("/repos/{owner}/{repo}")
    RepoGitDTO getRepository(@PathVariable String owner, @PathVariable String repo);

    @GetMapping("/users/{username}/repos")
    List<RepoGitDTO> getAllRepositories(@PathVariable String username);
}
