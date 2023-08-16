package com.integration.githubintegration.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class GitHubIntegrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getRepositoryDetails_DataCorrect_RepositoryReturned() throws Exception {
        mockMvc.perform(get("/github-integration/Fornir94/Repositories/medical"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.name").value("medical"))
                .andExpect(jsonPath("$.full_name").value("Fornir94/medical"));
    }

    @Test
    void getAllRepositoryDetails_DataCorrect_RepositoriesReturned() throws Exception {
        mockMvc.perform(get("/github-integration/Fornir94/Repositories"))
                .andDo(print())
                .andExpect(status().is(200))
                .andExpect(jsonPath("$").isArray());
    }
}
