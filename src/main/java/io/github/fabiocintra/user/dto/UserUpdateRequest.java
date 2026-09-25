package io.github.fabiocintra.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.UUID;

public record UserUpdateRequest(

        @NotBlank(message = "Field 'id' don't blank")
        @UUID
        String id,

        @Size(min = 3, max = 250, message = "Field 'username' must contain between 3 and 250 chars")
        String username,

        @Size(min = 8, max = 100, message = "Field 'password' must contain between 8 and 100 chars")
        String password,

        @Size(min = 3, max = 250, message = "Field 'name' must contain between 3 and 250 chars")
        String name
)
{}
