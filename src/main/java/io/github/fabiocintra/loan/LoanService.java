package io.github.fabiocintra.loan;

import io.github.fabiocintra.equipment.EquipmentModel;
import io.github.fabiocintra.equipment.EquipmentRepository;
import io.github.fabiocintra.user.UserModel;
import io.github.fabiocintra.utils.exceptions.LoanException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import static io.github.fabiocintra.loan.LoanSpecs.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository repository;
    private final EquipmentRepository equipmentRepository;


    /**
     * REGRAS DE NEGOCIO:
     * 1 - Cada usuario so pode ter no maximo 3 emprestimos por vez, se tentar pegar outro emprestimmo sera impossibilitado
     * 2 - Se o equipamento nao tiver disponivel, nao pode empresta-lo
     * 3 - Usuario nao pode pegar emprestado mais de uma vez o mesmo item
     */
    public void createLoan(LoanModel loan){

        UserModel user = loan.getUser();
        int activeLoans = repository.countByUserAndStatusIn(
                user,
                List.of(
                        Status.BORROWED,
                        Status.LATE
                )
        );
        EquipmentModel equipment = loan.getEquipment();
        boolean alreadyBorrowed = repository.existsByUserAndStatusInAndEquipment(
                user,
                List.of(
                        Status.BORROWED,
                        Status.LATE
                ),
                equipment
        );

        if (activeLoans >= 3){
            throw new LoanException("The user's allowed loan limit has been reached");
        }

        if (alreadyBorrowed){
            throw new LoanException("The user's equipment has been reached");
        }

        if (equipment.getAvaliableQuantity() <= 0){
            throw new LoanException("The equipment has no available quantity");
        }

        LocalDateTime dateReturn = LocalDateTime.now().plusWeeks(1);
        loan.setDateReturn(dateReturn);
        loan.setStatus(Status.BORROWED);

        repository.save(loan);

        /**
         * Atualizando os campos BorrowedQuantity, AvaliableQuantity e TotalQuantity
         */
        equipment.updateEquipmentAfterLoan();
        equipmentRepository.save(equipment);

    }

    public void returnLoan(UUID id){

        Optional<LoanModel> loanPersisted = repository
                .findByIdWithUserAndEquipmentAndStatusIn(
                        id,
                        List.of(Status.BORROWED, Status.LATE)
                );

        if (loanPersisted.isEmpty()){
            throw new LoanException("The loan with id " + id + " does not exist");
        }

        LoanModel loan = loanPersisted.get();
        EquipmentModel equipment = loan.getEquipment();

        if (equipment.getBorrowedQuantity() <= 0){
            throw new LoanException("The equipment has no borrowed quantity");
        }

        /**
         * Atualizando o status do emprestimo
         */
        LocalDate dateReturn = loan.getDateReturn().toLocalDate();
        Status status = (dateReturn.isBefore(LocalDate.now())) ? Status.LATE_RETURN : Status.RETURNED;
        loan.setStatus(status);
        repository.save(loan);

        /**
         * Atualizando a disponibilidade do equipamento
         */
        equipment.updateEquipmentAfterReturnLoan();
        equipmentRepository.save(equipment);

    }


    /**
     * Depois adicionar paginacoa
     *
     * Retorna todos os emprestimos.
     *
     * - Se o id vir preenchido, entao vai retornar somente os emprestimos de um determinado usuario
     * - Se status vir preenchido filtra pelo status que esta preenchido
     */
    public List<LoanModel> getLoans(String id, Status status) {
        Specification<LoanModel> specification = Specification.where(
                (root, query, cb) -> cb.conjunction()
        );

        if(id != null) {
            UUID userId = UUID.fromString(id);
            specification = specification.and(userIdEquals(userId));
        }

        if(status != null) {
            specification = specification.and(statusEquals(status));
        }

        return repository.findAll(specification);

    }

}
