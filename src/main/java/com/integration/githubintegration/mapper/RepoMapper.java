package com.integration.githubintegration.mapper;

import com.integration.githubintegration.model.dto.RepoAppDTO;
import com.integration.githubintegration.model.dto.RepoGitDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RepoMapper {

    RepoAppDTO toDto(RepoGitDTO repoGitDTO);
}
