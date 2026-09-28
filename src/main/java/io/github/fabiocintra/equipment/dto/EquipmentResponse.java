package io.github.fabiocintra.equipment.dto;

import java.util.UUID;

public record EquipmentResponse(
        UUID id,
        String name,
        int totalQuantity,
        int borrowedQuantity,
        int avaliableQuantity
) {
}
