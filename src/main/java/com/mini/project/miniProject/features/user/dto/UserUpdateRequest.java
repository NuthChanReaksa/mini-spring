package com.mini.project.miniProject.features.user.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record UserUpdateRequest(
        String fullName,
        String gender,
        String profileImage,
        String phoneNumber,
        String cityOrProvince,
        String khanOrDistrict,
        String sangkatOrCommune,
        String employeeType,
        String companyName,
        String mainSourceOfIncome,
        BigDecimal monthlyIncomeRange,
        String studentCardId

) {
}
