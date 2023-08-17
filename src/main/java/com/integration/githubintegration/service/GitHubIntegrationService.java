package com.integration.githubintegration.service;

import com.integration.githubintegration.client.GitHubClient;
import com.integration.githubintegration.exception.BlankArgumentException;
import com.integration.githubintegration.mapper.RepoMapper;
import com.integration.githubintegration.model.dto.RepoAppDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GitHubIntegrationService {
    private final GitHubClient gitHubClient;
    private final RepoMapper repoMapper;

    public RepoAppDTO getRepositoryByUsernameAndRepoName(String owner, String repo) {
        if (owner.isBlank() || repo.isBlank()) {
            throw new BlankArgumentException("Blank argument is not forbidden");
        }
        return repoMapper.toDto(gitHubClient.getRepository(owner, repo));
    }

    public List<RepoAppDTO> getAllRepositoriesByOwner(String username) {
        if (username.isBlank()) {
            throw new BlankArgumentException("Blank argument is not forbidden");
        }
        return gitHubClient.getAllRepositories(username).stream()
                .map(repoMapper::toDto)
                .collect(Collectors.toList());
    }
}
