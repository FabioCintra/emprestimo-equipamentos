package io.github.fabiocintra.loan;

import io.github.fabiocintra.equipment.EquipmentModel;
import io.github.fabiocintra.equipment.EquipmentRepository;
import io.github.fabiocintra.user.UserModel;
import io.github.fabiocintra.utils.exceptions.LoanException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
        EquipmentModel equipment = loan.getEquipment();

        if (user.getLoans().size() == 3){
            throw new LoanException("The user's allowed loan limit has been reached");
        }

        for (LoanModel l : user.getLoans()){
            EquipmentModel e = l.getEquipment();
            if (e.getId().equals(equipment.getId())){
                throw new LoanException("The user's equipment has been reached");
            }
        }

        if (equipment.getAvaliableQuantity() == 0){
            throw new LoanException("The equipment has no available quantity");
        }

        LocalDateTime dateReturn = LocalDateTime.now().plusWeeks(1);
        loan.setDateReturn(dateReturn);

        repository.save(loan);

        /**
         * Atualizando os campos BorrowedQuantity, AvaliableQuantity e TotalQuantity
         */
        equipment.updateEquipmentAfterLoan();
        equipmentRepository.save(equipment);

    }

}
