package com.integration.githubintegration.service;

import com.integration.githubintegration.client.IntegrationClient;
import com.integration.githubintegration.model.RepoDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
public class GitHubIntegrationServiceTest {

    @Mock
    IntegrationClient integrationClient;
    @InjectMocks
    GitHubIntegrationService gitHubIntegrationService;

    @Test
    void getRepositoryByOwnerAndRepoName_DataCorrect_RepositoryFound() {
        RepoDTO repoDTO = new RepoDTO();
        repoDTO.setName("reporepo");
        repoDTO.setId(1234);
        repoDTO.setFull_name("Fornir94/reporepo");
        Mockito.when(integrationClient.getRepository(eq("Fornir94"), eq("reporepo"))).thenReturn(repoDTO);

        var result = gitHubIntegrationService.getRepositoryByOwnerAndRepoName("Fornir94", "reporepo");

        Assertions.assertEquals("reporepo", result.getName());
        Assertions.assertEquals("Fornir94/reporepo", result.getFull_name());
    }

    @Test
    void getAllRepositoriesByOwner_DataCorrect_RepositoriesReturned() {
        RepoDTO repoDTO = new RepoDTO();
        repoDTO.setName("Repo1");
        repoDTO.setId(1234);
        RepoDTO repoDTO1 = new RepoDTO();
        repoDTO1.setName("Repo2");
        repoDTO1.setId(4321);
        List<RepoDTO> repoDTOList = new ArrayList<>();
        repoDTOList.add(repoDTO1);
        repoDTOList.add(repoDTO);

        Mockito.when(integrationClient.getAllRepositories(eq("Fornir94"))).thenReturn(repoDTOList);

        var result = gitHubIntegrationService.getAllRepositoriesByOwner("Fornir94");

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(1234, result.get(1).getId());
        Assertions.assertEquals(4321, result.get(0).getId());
    }
}
