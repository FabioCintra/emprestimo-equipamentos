package io.github.fabiocintra.equipment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface EquipmentRepository extends JpaRepository<EquipmentModel, UUID>, JpaSpecificationExecutor<EquipmentModel> {

    boolean existsById(UUID id);

}
