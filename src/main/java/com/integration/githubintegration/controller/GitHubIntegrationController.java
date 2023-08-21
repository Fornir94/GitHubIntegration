package com.integration.githubintegration.controller;

import com.integration.githubintegration.model.dto.RepoAppDTO;
import com.integration.githubintegration.service.GitHubIntegrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class GitHubIntegrationController {

    private final GitHubIntegrationService gitHubIntegrationService;

    @Operation(summary = "Get repository", tags = "Repository")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = RepoAppDTO.class))}),
            @ApiResponse(responseCode = "404", description = "Repository not found", content = @Content),
            @ApiResponse(responseCode = "403", description = "Forbidden", content = @Content),
            @ApiResponse(responseCode = "301", description = "Moved permanently", content = @Content)
    })
    @GetMapping("/{owner}/repositories/{repo}")
    public RepoAppDTO getRepositoryDetails(@PathVariable String owner, @PathVariable String repo) {
        return gitHubIntegrationService.getRepositoryByUsernameAndRepoName(owner, repo);
    }

    @Operation(summary = "Get All Repositories", tags = "Repository")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Ok",
                    content = {@Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = RepoAppDTO.class))}),
    })
    @GetMapping("{username}/repositories")
    public List<RepoAppDTO> getAllOwnerRepositories(@PathVariable String username) {
        return gitHubIntegrationService.getAllRepositoriesByOwner(username);
    }
}
