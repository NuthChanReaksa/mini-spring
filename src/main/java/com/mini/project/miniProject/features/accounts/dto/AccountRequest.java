package com.mini.project.miniProject.features.accounts.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record AccountRequest (
        String accountNumber,
        String accountName,
        BigDecimal accountBalance,
        String accountType,
        String userId
){
}
