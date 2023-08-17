package com.integration.githubintegration.exception;

import org.springframework.http.HttpStatus;

public class BlankArgumentException extends GitHubException {

    public BlankArgumentException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
