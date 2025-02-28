package com.mini.project.miniProject.features.auth.dto;


import lombok.Builder;

@Builder
public record AuthRequest(
        String email,
        String password
) {
}
