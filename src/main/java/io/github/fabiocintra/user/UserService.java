package io.github.fabiocintra.user;

import static io.github.fabiocintra.utils.Utils.*;
import io.github.fabiocintra.utils.exceptions.DataExistsInTheSystemException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public UserModel createUser(UserModel user){

        String cpf = user.getCpf();
        String username = user.getUsername();

        if (repository.existByCpf(cpf)){
            throw new DataExistsInTheSystemException("CPF already registered!");
        }

        if (repository.existByUsername(username)){
            throw new DataExistsInTheSystemException("Username already registered!");
        }

        String cpfMasked = maskCpf(cpf);
        user.setCpf(cpfMasked);

        return repository.save(user);

    }

}
