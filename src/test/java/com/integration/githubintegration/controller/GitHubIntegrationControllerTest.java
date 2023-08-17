package com.integration.githubintegration.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.integration.githubintegration.model.dto.RepoAppDTO;
import com.integration.githubintegration.model.Owner;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, properties = {"server.port=7778"})
@AutoConfigureWireMock(port = 6666)
public class GitHubIntegrationControllerTest {

    @Autowired
    WireMockServer wireMockServer;
    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    RestTemplate restTemplate;

    @Test
    void getRepositoryDetails_DataCorrect_RepositoryReturned() throws Exception {
        String repo = "testRepo";
        String owner = "testOwner";
        RepoAppDTO repoAppDTO = new RepoAppDTO();
        repoAppDTO.setName("Repo1");
        repoAppDTO.setId(1234);
        Owner ownerr = new Owner();
        ownerr.setLogin("xD");
        wireMockServer.stubFor(get(urlEqualTo("/" + owner + "/Repositories/" + repo))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(repoAppDTO))));

        ResponseEntity<RepoAppDTO> responseEntity = restTemplate.exchange("http://localhost:6666/" + owner + "/Repositories/" + repo, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });

        Assertions.assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
        Assertions.assertEquals(repoAppDTO.getName(), responseEntity.getBody().getName());
        Assertions.assertEquals(repoAppDTO.getId(), responseEntity.getBody().getId());
    }

    @Test
    void getRepositoryDetails_RepositoryWithThisInformationNotFound_ExceptionThrown() throws Exception {
        String owner = "nonexistent";
        String repo = "nonexistent";
        stubFor(get(urlEqualTo("/" + owner + "/Repositories/" + repo))
                .willReturn(aResponse().withStatus(404)));

        HttpClientErrorException exception = assertThrows(HttpClientErrorException.class, () -> restTemplate.exchange("http://localhost:6666/" + owner + "/Repositories/" + repo, HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        }));

        assertThat(exception.getStatusCode()).isEqualTo(HttpStatusCode.valueOf(404));
        assertThat(exception.getMessage()).contains("404 Not Found: [no body]");
    }


    @Test
    void getAllRepositoryDetails_DataCorrect_RepositoriesReturned() throws Exception {
        RepoAppDTO repoAppDTO = new RepoAppDTO();
        repoAppDTO.setName("Repo1");
        repoAppDTO.setId(1234);
        RepoAppDTO repoAppDTO2 = new RepoAppDTO();
        repoAppDTO.setName("Repo2");
        repoAppDTO.setId(4321);
        Owner owner = new Owner();
        owner.setLogin("xD");
        String username = "Usernametest";
        List<RepoAppDTO> repoAppDTOList = new ArrayList<>();
        repoAppDTOList.add(repoAppDTO);
        repoAppDTOList.add(repoAppDTO2);

        wireMockServer.stubFor(get(urlEqualTo("/" + username + "/Repositories/"))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withBody(objectMapper.writeValueAsString(repoAppDTOList))));

        ResponseEntity<List<RepoAppDTO>> responseEntity = restTemplate.exchange("http://localhost:6666/" + username + "/Repositories/", HttpMethod.GET, null, new ParameterizedTypeReference<>() {
        });

        Assertions.assertEquals(HttpStatusCode.valueOf(200), responseEntity.getStatusCode());
        Assertions.assertEquals(2, responseEntity.getBody().size());
        Assertions.assertEquals(repoAppDTO.getId(), responseEntity.getBody().get(0).getId());
    }
}
