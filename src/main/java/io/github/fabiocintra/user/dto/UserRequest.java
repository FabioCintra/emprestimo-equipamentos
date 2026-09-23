package io.github.fabiocintra.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

public record UserRequest(
        @NotBlank(message = "Field 'username' don't blank")
        @Size(min = 3, max = 250, message = "Field 'username' must contain between 3 and 250 chars")
        String username,

        @NotBlank(message = "Field 'password' don't blank")
        @Size(min = 8, max = 100, message = "Field 'password' must contain between 8 and 100 chars")
        String password,

        @NotBlank(message = "Field 'cpf' don't blank")
        @CPF
        String cpf,

        @NotBlank(message = "Field 'name' don't blank")
        @Size(min = 3, max = 250, message = "Field 'name' must contain between 3 and 250 chars")
        String name
)
{}
