package io.github.fabiocintra.equipment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record EquipmentRequest (
        @NotBlank(message = "Field 'name' don't blank")
        @Size(min = 3, max = 300, message = "Field 'name' must contain between 3 and 250 chars")
        String name,

        @NotNull(message = "Field 'totalQuantity' don't null")
        int totalQuantity
) {}
