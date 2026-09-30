package io.github.fabiocintra.loan.dto;

import jakarta.validation.constraints.NotBlank;

public record LoanRequest(
        @NotBlank(message = "Field userdId dont blank!")
        String userId,
        @NotBlank(message = "Field equipmentId dont blank!")
        String equipmentId
){}
