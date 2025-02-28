package com.mini.project.miniProject.features.accounts.dto;

import com.mini.project.miniProject.features.user.dto.UserResponse;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record AccountResponse(
        String id,
        String accountNumber,
        String accountName,
        BigDecimal accountBalance,
        UserResponse user,
        String accountType
) {
}
