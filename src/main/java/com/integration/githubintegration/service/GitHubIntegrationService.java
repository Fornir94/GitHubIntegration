package com.integration.githubintegration.service;

import com.integration.githubintegration.client.IntegrationClient;
import com.integration.githubintegration.model.RepoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GitHubIntegrationService {
    private final IntegrationClient integrationClient;

    public RepoDTO getRepositoryByOwnerAndRepoName(String owner, String repo) {
        return integrationClient.getRepository(owner, repo);
    }

    public List<RepoDTO> getAllRepositoriesByOwner(String username) {
        return integrationClient.getAllRepositories(username);
    }
}
