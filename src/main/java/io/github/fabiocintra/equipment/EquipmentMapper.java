package io.github.fabiocintra.equipment;

import io.github.fabiocintra.equipment.dto.EquipmentRequest;
import io.github.fabiocintra.equipment.dto.EquipmentResponse;
import io.github.fabiocintra.equipment.dto.EquipmentUpdateRequest;
import io.github.fabiocintra.utils.annotations.Mapper;

@Mapper
public class EquipmentMapper {

    public EquipmentModel toModel(EquipmentRequest request){
        return new EquipmentModel(
                request.name(),
                request.totalQuantity()
        );
    }

    public EquipmentModel toModelForUpdate(EquipmentUpdateRequest request){
        return new EquipmentModel(
                request.name(),
                request.totalQuantity()
        );
    }

    public EquipmentResponse toResponse(EquipmentModel model){
        return new EquipmentResponse(
                model.getId(),
                model.getName(),
                model.getTotalQuantity(),
                model.getBorrowedQuantity(),
                model.getAvaliableQuantity()
        );
    }

}
