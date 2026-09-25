package io.github.fabiocintra.user;

import static io.github.fabiocintra.utils.Utils.*;

import io.github.fabiocintra.user.dto.UserResponse;
import io.github.fabiocintra.user.dto.UserUpdateRequest;
import io.github.fabiocintra.utils.exceptions.DataExistsInTheSystemException;
import io.github.fabiocintra.utils.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;

    public UserModel createUser(UserModel user){

        String cpf = user.getCpf().replace(".", "").replace("-", "");
        String username = user.getUsername();

        if (repository.existsByCpf(cpf)){
            throw new DataExistsInTheSystemException("CPF already registered!");
        }

        if (repository.existsByUsername(username)){
            throw new DataExistsInTheSystemException("Username already registered!");
        }

        user.setCpf(cpf);

        return repository.save(user);

    }

    public void updateUser(UserUpdateRequest request){

        UUID id = UUID.fromString(request.id());
        String password = request.password();
        String name = request.name();
        String username = request.username();

        UserModel userUpdated =  repository.findById(id).orElse(null);

        if (userUpdated == null){
            throw new UserNotFoundException("User not found! Verify the ID!");
        }

        if (username != null) {
            if (repository.existsByUsername(username)){
                throw new DataExistsInTheSystemException("Username already registered!");
            }
            userUpdated.setUsername(username);
        }

        if (password != null) {
            userUpdated.setPassword(password);
        }

        if (name != null) {
            userUpdated.setName(name);
        }

        repository.save(userUpdated);
    }

    public List<UserModel> findAll(){
        return repository
                .findAll()
                .stream()
                .map(user -> {
                        String cpf = user.getCpf();
                        String cpfMasked = maskCpf(cpf);
                        user.setCpf(cpfMasked);
                        return user;
                })
                .toList();
    }

}
