package io.github.fabiocintra.loan;

import static org.junit.jupiter.api.Assertions.*;

import io.github.fabiocintra.equipment.EquipmentModel;
import io.github.fabiocintra.equipment.EquipmentRepository;
import io.github.fabiocintra.user.UserModel;
import io.github.fabiocintra.utils.exceptions.LoanException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class LoanServiceTest {

    @InjectMocks
    LoanService service;

    @Mock
    LoanRepository repository;

    @Mock
    EquipmentRepository equipmentRepository;

    UserModel user;
    EquipmentModel equip;
    LoanModel loan;
    Collection<Status> statuses;

    @BeforeEach
    void setUp() {
        user = new UserModel();
        user.setUsername("henrique@gmail.com");
        user.setPassword("96028196fa");
        user.setName("Fabio Henrique Silva Cintra");
        user.setCpf("12345678900");

        equip =  new EquipmentModel();
        equip.setId(UUID.fromString("9ad0a3a0-7536-4274-9643-e5ed1528c9dd"));
        equip.setName("Computer");
        equip.setTotalQuantity(10);
        equip.setBorrowedQuantity(0);
        equip.setAvaliableQuantity(10);

        statuses = List.of(
                Status.BORROWED,
                Status.LATE
        );

        loan = new LoanModel();
        loan.setUser(user);
        loan.setEquipment(equip);

    }

    @Test
    void shouldCreateLoan() {

        Mockito
                .when(repository.countByUserAndStatusIn(user, statuses))
                .thenReturn(0);

        Mockito
                .when(repository.existsByUserAndStatusInAndEquipment(user, statuses, equip))
                .thenReturn(false);

        Mockito
                .when(repository.save(loan))
                .thenReturn(loan);

        Mockito
                .when(equipmentRepository.save(equip))
                .thenReturn(equip);


        service.createLoan(loan);

        Mockito.verify(repository, Mockito.times(1)).save(loan);
        Mockito.verify(equipmentRepository, Mockito.times(1)).save(equip);
        Mockito.verify(repository, Mockito.times(1))
                .countByUserAndStatusIn(user, statuses);
        Mockito.verify(repository, Mockito.times(1))
                .existsByUserAndStatusInAndEquipment(user, statuses, equip);

    }

    @Test
    void dontShouldCreateLoanIfActiveLoansGreaterThanOrEqualToThree() {

        Mockito
                .when(repository.countByUserAndStatusIn(user, statuses))
                .thenReturn(3);

        assertThrows(LoanException.class, () -> service.createLoan(loan));

        Mockito.verify(repository, Mockito.never()).save(loan);
        Mockito.verify(equipmentRepository, Mockito.never()).save(equip);
        Mockito.verify(repository, Mockito.times(1))
                .countByUserAndStatusIn(user, statuses);
        Mockito.verify(repository, Mockito.times(1))
                .existsByUserAndStatusInAndEquipment(user, statuses, equip);

    }

    @Test
    void dontShouldCreateLoanIfAlreadyBorrowedIsTrue() {

        Mockito
                .when(repository.countByUserAndStatusIn(user, statuses))
                .thenReturn(0);

        Mockito
                .when(repository.existsByUserAndStatusInAndEquipment(user, statuses, equip))
                .thenReturn(true);


        assertThrows(LoanException.class, () -> service.createLoan(loan));

        Mockito.verify(repository, Mockito.never()).save(loan);
        Mockito.verify(equipmentRepository, Mockito.never()).save(equip);
        Mockito.verify(repository, Mockito.times(1))
                .countByUserAndStatusIn(user, statuses);
        Mockito.verify(repository, Mockito.times(1))
                .existsByUserAndStatusInAndEquipment(user, statuses, equip);

    }

    @Test
    void dontShouldCreateLoanIfAvaliableQuantityLessThanOrEqualToZero() {

        equip.setAvaliableQuantity(0);

        Mockito
                .when(repository.countByUserAndStatusIn(user, statuses))
                .thenReturn(0);

        Mockito
                .when(repository.existsByUserAndStatusInAndEquipment(user, statuses, equip))
                .thenReturn(false);


        assertThrows(LoanException.class, () -> service.createLoan(loan));

        Mockito.verify(repository, Mockito.never()).save(loan);
        Mockito.verify(equipmentRepository, Mockito.never()).save(equip);
        Mockito.verify(repository, Mockito.times(1))
                .countByUserAndStatusIn(user, statuses);
        Mockito.verify(repository, Mockito.times(1))
                .existsByUserAndStatusInAndEquipment(user, statuses, equip);

    }

    @Test
    void shouldReturnLoan() {

        UUID id = UUID.fromString("28cefae0-64e7-4b72-9a39-520a4fc3e2c1");
        loan.setId(id);
        loan.setDateLoan(LocalDateTime.now().minusWeeks(1));
        loan.setDateReturn(LocalDateTime.now());
        equip.setBorrowedQuantity(1);
        equip.setAvaliableQuantity(9);

        Mockito
                .when(repository.findByIdWithUserAndEquipmentAndStatusIn(
                        id,
                        statuses
                ))
                .thenReturn(Optional.of(loan));

        Mockito
                .when(repository.save(loan))
                .thenReturn(loan);

        Mockito
                .when(equipmentRepository.save(equip))
                .thenReturn(equip);

        service.returnLoan(id);

        Mockito.verify(repository, Mockito.times(1)).save(loan);
        Mockito.verify(equipmentRepository, Mockito.times(1)).save(equip);
        Mockito.verify(repository, Mockito.times(1))
                .findByIdWithUserAndEquipmentAndStatusIn(id,statuses);

    }

    @Test
    void dontShouldReturnLoanIfNotExists() {

        UUID id = UUID.fromString("28cefae0-64e7-4b72-9a39-520a4fc3e2c1");
        loan.setId(id);

        Mockito
                .when(repository.findByIdWithUserAndEquipmentAndStatusIn(
                        id,
                        statuses
                ))
                .thenReturn(Optional.empty());

        assertThrows(LoanException.class, () -> service.returnLoan(id));

        Mockito.verify(repository, Mockito.never()).save(loan);
        Mockito.verify(equipmentRepository, Mockito.never()).save(equip);
        Mockito.verify(repository, Mockito.times(1))
                .findByIdWithUserAndEquipmentAndStatusIn(id,statuses);

    }

    @Test
    void dontShouldReturnLoanIfBorrowedQuantityLessThanOrEqualToZero() {

        UUID id = UUID.fromString("28cefae0-64e7-4b72-9a39-520a4fc3e2c1");
        loan.setId(id);

        Mockito
                .when(repository.findByIdWithUserAndEquipmentAndStatusIn(
                        id,
                        statuses
                ))
                .thenReturn(Optional.of(loan));

        assertThrows(LoanException.class, () -> service.returnLoan(id));

        Mockito.verify(repository, Mockito.never()).save(loan);
        Mockito.verify(equipmentRepository, Mockito.never()).save(equip);
        Mockito.verify(repository, Mockito.times(1))
                .findByIdWithUserAndEquipmentAndStatusIn(id,statuses);

    }

}
