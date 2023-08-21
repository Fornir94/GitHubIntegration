package com.integration.githubintegration.service;

import com.integration.githubintegration.client.GitHubClient;
import com.integration.githubintegration.mapper.RepoMapper;
import com.integration.githubintegration.model.dto.RepoAppDTO;
import com.integration.githubintegration.model.dto.RepoGitDTO;
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
    GitHubClient gitHubClient;
    @Mock
    RepoMapper repoMapper;
    @InjectMocks
    GitHubIntegrationService gitHubIntegrationService;

    @Test
    void getRepositoryByOwnerAndRepoName_DataCorrect_RepositoryFound() {
        RepoAppDTO repoAppDTO = new RepoAppDTO();
        repoAppDTO.setName("reporepo");
        repoAppDTO.setId(1234);
        repoAppDTO.setFullName("Fornir94/reporepo");
        RepoGitDTO repoGitDTO = new RepoGitDTO();
        repoGitDTO.setName("reporepo");
        repoGitDTO.setId(1234);
        repoGitDTO.setFullName("Fornir94/reporepo");
        Mockito.when(gitHubClient.getRepository(eq("Fornir94"), eq("reporepo"))).thenReturn(repoGitDTO);
        Mockito.when(repoMapper.toDto(eq(repoGitDTO))).thenReturn(repoAppDTO);


        var result = gitHubIntegrationService.getRepositoryByUsernameAndRepoName("Fornir94", "reporepo");

        Assertions.assertEquals("reporepo", result.getName());
        Assertions.assertEquals("Fornir94/reporepo", result.getFullName());
    }

    @Test
    void getAllRepositoriesByOwner_DataCorrect_RepositoriesReturned() {
        RepoGitDTO repoGitDTO = new RepoGitDTO();
        repoGitDTO.setName("Repo1");
        repoGitDTO.setId(1234);
        RepoGitDTO repoGitDTO1 = new RepoGitDTO();
        ;
        repoGitDTO1.setName("Repo2");
        repoGitDTO1.setId(4321);
        List<RepoGitDTO> repoGitDTOS = new ArrayList<>();
        repoGitDTOS.add(repoGitDTO1);
        repoGitDTOS.add(repoGitDTO);
        RepoAppDTO repoAppDTO = new RepoAppDTO();
        repoAppDTO.setName("Repo1");
        repoAppDTO.setId(1234);
        RepoAppDTO repoAppDTO1 = new RepoAppDTO();
        repoAppDTO1.setName("Repo2");
        repoAppDTO1.setId(4321);

        Mockito.when(gitHubClient.getAllRepositories(eq("Fornir94"))).thenReturn(repoGitDTOS);
        Mockito.when(repoMapper.toDto(eq(repoGitDTO))).thenReturn(repoAppDTO);
        Mockito.when(repoMapper.toDto(eq(repoGitDTO1))).thenReturn(repoAppDTO1);

        var result = gitHubIntegrationService.getAllRepositoriesByOwner("Fornir94");

        Assertions.assertEquals(2, result.size());
        Assertions.assertEquals(1234, result.get(1).getId());
        Assertions.assertEquals(4321, result.get(0).getId());
    }
}
