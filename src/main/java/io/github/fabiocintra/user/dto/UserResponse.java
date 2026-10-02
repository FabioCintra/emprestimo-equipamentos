package io.github.fabiocintra.user.dto;

import io.github.fabiocintra.loan.LoanMapper;
import io.github.fabiocintra.loan.LoanModel;
import io.github.fabiocintra.loan.dto.LoanResponse;

import java.util.List;
import java.util.UUID;

public record UserResponse(
        UUID id,
        String username,
        String name,
        String cpf
) {
}
