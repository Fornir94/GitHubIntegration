package com.integration.githubintegration.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.integration.githubintegration.model.Owner;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RepoGitDTO {

    private int id;
    private String name;
    @JsonProperty("full_name")
    private String fullName;
    private Owner owner;
}
