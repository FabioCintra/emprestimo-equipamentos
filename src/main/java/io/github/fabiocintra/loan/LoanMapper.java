package io.github.fabiocintra.loan;

import io.github.fabiocintra.equipment.EquipmentMapper;
import io.github.fabiocintra.equipment.EquipmentModel;
import io.github.fabiocintra.equipment.EquipmentRepository;
import io.github.fabiocintra.loan.dto.LoanRequest;
import io.github.fabiocintra.loan.dto.LoanResponse;
import io.github.fabiocintra.user.UserMapper;
import io.github.fabiocintra.user.UserModel;
import io.github.fabiocintra.user.UserRepository;
import io.github.fabiocintra.user.dto.UserLoanResponse;
import io.github.fabiocintra.utils.annotations.Mapper;
import io.github.fabiocintra.utils.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@Mapper
@RequiredArgsConstructor
public class LoanMapper {

    private final EquipmentRepository equipmentRepository;
    private final UserRepository userRepository;
    private final EquipmentMapper equipmentMapper;

    public LoanModel toModel(LoanRequest request){

        UUID userId = UUID.fromString(request.userId());
        UUID equipmentId = UUID.fromString(request.equipmentId());

        Optional<UserModel> user = userRepository.findByIdWithLoans(userId);
        Optional<EquipmentModel> equipment = equipmentRepository.findById(equipmentId);

        if (user.isEmpty()) {
            throw new NotFoundException("User not found!");
        }

        if (equipment.isEmpty()) {
            throw new NotFoundException("Equipment not found!");
        }

        return new LoanModel(
                user.get(),
                equipment.get()
        );
    }

    public LoanResponse toResponse(LoanModel model){

        UserModel user = model.getUser();

        return new LoanResponse(
          model.getId(),
          model.getStatus(),
          model.getDateLoan(),
          model.getDateReturn(),
          new UserLoanResponse(
                  user.getId(),
                  user.getUsername(),
                  user.getName(),
                  user.getCpf()
          ),
          equipmentMapper.toLoanResponse(model.getEquipment())
        );
    }

}
