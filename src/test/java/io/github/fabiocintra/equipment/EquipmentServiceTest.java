package io.github.fabiocintra.equipment;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

import io.github.fabiocintra.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class EquipmentServiceTest {

    @InjectMocks
    private EquipmentService service;

    @Mock
    private EquipmentRepository repository;

    @Test
    void deveSalvarUmEquipamento() {

        EquipmentModel equip =  new EquipmentModel(
                "Teste",
                100
        );

        service.createEquipment(equip);

        Mockito.verify(repository).save(equip);

    }

    @Test
    void shouldUpdateEquipment() {
        EquipmentModel equip =  new EquipmentModel(
            "Teste",
            null
        );

        UUID id = UUID.fromString("f2c68b79-4ab3-4357-9027-c61b715713b6");

        EquipmentModel persisted = new EquipmentModel();
        persisted.setId(id);
        persisted.setName("Original");
        persisted.setAvaliableQuantity(100);
        persisted.setTotalQuantity(100);
        persisted.setBorrowedQuantity(0);

        equip.setId(id);

        Mockito
                .when(repository.existsById(id))
                .thenReturn(true);

        Mockito
                .when(repository.findById(id))
                .thenReturn(Optional.of(persisted));

        service.updateEquipment(equip);

        Mockito.verify(repository).existsById(id);
        Mockito.verify(repository).findById(id);
        Mockito.verify(repository).save(persisted);

    }

    @Test
    void dontShouldUpdateEquipmentIfNotFound() {
        EquipmentModel equip =  new EquipmentModel(
                "Teste",
                null
        );
        equip.setId(UUID.fromString("f2c68b79-4ab3-4357-9027-c61b715713b6"));

        Mockito
                .when(repository.existsById(equip.getId()))
                .thenReturn(false);

        assertThrows(NotFoundException.class, () -> service.updateEquipment(equip));
        Mockito.verify(repository).existsById(equip.getId());
        Mockito.verify(repository, Mockito.never()).findById(equip.getId());
        Mockito.verify(repository, Mockito.never()).save(equip);

    }

    @Test
    void shouldReturnAListOfEquipmentIfNameIsNull() {
        List<EquipmentModel> equipments = new ArrayList<>();

        UUID id = UUID.fromString("f2c68b79-4ab3-4357-9027-c61b715713b6");
        EquipmentModel persisted = new EquipmentModel();
        persisted.setId(id);
        persisted.setName("Original");
        persisted.setAvaliableQuantity(100);
        persisted.setTotalQuantity(100);
        persisted.setBorrowedQuantity(0);

        equipments.add(persisted);

        Mockito
                .when(repository.findAll(any(Specification.class)))
                .thenReturn(equipments);

        var result = service.getAllEquipments(null);

        assertEquals(equipments, result);
        assertEquals(equipments.size(), result.size());
        Mockito.verify(repository).findAll(any(Specification.class));
    }

    @Test
    void shouldReturnAListOfEquipmentIfNameNotNullAndEquipmentsNameContainsThisName() {
        List<EquipmentModel> equipments = new ArrayList<>();

        UUID id = UUID.fromString("f2c68b79-4ab3-4357-9027-c61b715713b6");
        EquipmentModel persisted = new EquipmentModel();
        persisted.setId(id);
        persisted.setName("Original");
        persisted.setAvaliableQuantity(100);
        persisted.setTotalQuantity(100);
        persisted.setBorrowedQuantity(0);

        equipments.add(persisted);

        Mockito
                .when(repository.findAll(any(Specification.class)))
                .thenReturn(equipments);

        var result = service.getAllEquipments("igi");

        assertEquals(equipments, result);
        assertEquals(equipments.size(), result.size());
        Mockito.verify(repository).findAll(any(Specification.class));
    }

    @Test
    void dontShouldReturnAListOfEquipmentIfNameNotNullAndEquipmentsNameNotContainsThisName() {
        List<EquipmentModel> equipments = new ArrayList<>();

        UUID id = UUID.fromString("f2c68b79-4ab3-4357-9027-c61b715713b6");
        EquipmentModel persisted = new EquipmentModel();
        persisted.setId(id);
        persisted.setName("Original");
        persisted.setAvaliableQuantity(100);
        persisted.setTotalQuantity(100);
        persisted.setBorrowedQuantity(0);

        equipments.add(persisted);

        Mockito
                .when(repository.findAll(any(Specification.class)))
                .thenReturn(new ArrayList<>());

        var result = service.getAllEquipments("fac");

        assertNotEquals(equipments, result);
        assertNotEquals(equipments.size(), result.size());
        Mockito.verify(repository).findAll(any(Specification.class));
    }


}
