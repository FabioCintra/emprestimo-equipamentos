package io.github.fabiocintra.equipment;

import io.github.fabiocintra.utils.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static io.github.fabiocintra.equipment.Specs.*;

@Service
@RequiredArgsConstructor
public class EquipmentService {

    private final EquipmentRepository repository;

    public void createEquipment(EquipmentModel model){
        model.setAvaliableQuantity(model.getTotalQuantity());
        repository.save(model);
    }

    public void updateEquipment(EquipmentModel model){

        String newName = model.getName();
        Integer newTotalQuantity = model.getTotalQuantity();
        UUID id = model.getId();

        if(!repository.existsById(id)){
            throw new NotFoundException("Equipment not found!");
        }

        EquipmentModel equipmentPersisted = repository.findById(id).get();

        if(newName != null){
            equipmentPersisted.setName(newName);
        }
        if(newTotalQuantity != null){
            equipmentPersisted.setTotalQuantity(newTotalQuantity);
        }

        equipmentPersisted.setAvaliableQuantity(newTotalQuantity);

        repository.save(equipmentPersisted);

    }


    /*
    * DEPOIS ADICIONAR PAGINACAO
     */
    public List<EquipmentModel> getAllEquipments(String name){
        Specification<EquipmentModel> spec = Specification.where(
                (root, criteriaQuery, cb) -> cb.conjunction()
        );

        if(name != null){
            spec = spec.and(nameLike(name));
        }

        return repository.findAll(spec);
    }

}
