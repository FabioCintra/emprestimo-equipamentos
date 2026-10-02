package io.github.fabiocintra.loan.dto;

import io.github.fabiocintra.equipment.dto.EquipmentToLoanResponse;
import io.github.fabiocintra.loan.Status;
import io.github.fabiocintra.user.dto.UserResponse;

import java.time.LocalDateTime;
import java.util.UUID;

public record LoanResponse(
   UUID id,
   Status status,
   LocalDateTime dateLoan,
   LocalDateTime dateReturn,
   UserResponse user,
   EquipmentToLoanResponse equipment
) {}
