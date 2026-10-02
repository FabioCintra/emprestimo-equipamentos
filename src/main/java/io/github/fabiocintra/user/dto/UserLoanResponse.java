package io.github.fabiocintra.user.dto;

import java.util.UUID;

public record UserLoanResponse(
        UUID id,
        String username,
        String name,
        String cpf
) {
}
