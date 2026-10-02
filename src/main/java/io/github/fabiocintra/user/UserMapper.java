package io.github.fabiocintra.user;

import io.github.fabiocintra.loan.LoanMapper;
import io.github.fabiocintra.user.dto.UserRequest;
import io.github.fabiocintra.user.dto.UserResponse;
import io.github.fabiocintra.utils.annotations.Mapper;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;

@Mapper
@RequiredArgsConstructor
public class UserMapper {

    private final UserService service;
    private final LoanMapper loanMapper;

    public UserModel toEntity(UserRequest request) {
        return new UserModel(
                request.username(),
                request.password(),
                request.name(),
                request.cpf()
        );
    }

    public UserResponse toResponse(UserModel model) {
        return new UserResponse(
                model.getId(),
                model.getUsername(),
                model.getName(),
                model.getCpf()
        );
    }

}
