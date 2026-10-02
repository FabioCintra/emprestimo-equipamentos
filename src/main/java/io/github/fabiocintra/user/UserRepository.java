package io.github.fabiocintra.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserModel, UUID>, JpaSpecificationExecutor<UserModel> {

    boolean existsByUsername(String username);
    boolean existsByCpf(String cpf);

    @Query ("""
            SELECT DISTINCT u from UserModel as u 
            LEFT JOIN FETCH u.loans 
            WHERE u.id = :id
        """)
    Optional<UserModel> findByIdWithLoans(@Param("id") UUID id);

}
