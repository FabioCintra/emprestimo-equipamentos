package io.github.fabiocintra.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID> {

    boolean existsByUsername(String username);
    boolean existsByCpf(String cpf);

}
