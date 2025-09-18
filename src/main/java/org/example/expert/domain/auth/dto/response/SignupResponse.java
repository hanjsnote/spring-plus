package org.example.expert.domain.auth.dto.response;

import lombok.Getter;

@Getter
public class SignupResponse {

    private final String bearerToken;
    private final Long userId;

    public SignupResponse(String bearerToken, Long userId) {
        this.bearerToken = bearerToken;
        this.userId = userId;
    }
}
