package io.github.fabiocintra.equipment;

import io.github.fabiocintra.equipment.dto.EquipmentRequest;
import io.github.fabiocintra.equipment.dto.EquipmentResponse;
import io.github.fabiocintra.equipment.dto.EquipmentUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/equipments")
@RequiredArgsConstructor
public class EquipmentController {

    private final EquipmentService service;
    private final EquipmentMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createEquipment(@RequestBody @Valid EquipmentRequest request){
        EquipmentModel newModel = mapper.toModel(request);
        service.createEquipment(newModel);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateEquipment(@PathVariable("id") String id,
                                @RequestBody @Valid EquipmentUpdateRequest request) {
        UUID uuid = UUID.fromString(id);
        EquipmentModel equipmentUpdated = mapper.toModelForUpdate(request);
        equipmentUpdated.setId(uuid);
        service.updateEquipment(equipmentUpdated);

    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EquipmentResponse> getEquipments(@RequestParam(name = "name", required = false) String name){
        return service.getAllEquipments(name).stream()
                .map(e -> mapper.toResponse(e))
                .toList();
    }

}
