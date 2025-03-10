package com.mini.project.miniProject.features.auth.dto;

import lombok.Builder;

@Builder
public record AuthResponse(
        String userId,
        String accessToken,
        String refreshToken
) {
}
