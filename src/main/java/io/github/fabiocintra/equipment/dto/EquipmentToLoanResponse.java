package io.github.fabiocintra.equipment.dto;

import java.util.UUID;

public record EquipmentToLoanResponse(
        UUID id,
        String name
) {
}
