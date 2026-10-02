package io.github.fabiocintra.loan;

import io.github.fabiocintra.equipment.EquipmentModel;
import io.github.fabiocintra.user.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface LoanRepository extends JpaRepository<LoanModel, UUID> {

    @Query("""
        SELECT l from LoanModel as l 
        LEFT JOIN FETCH l.user
        LEFT JOIN FETCH l.equipment 
        WHERE l.id = :id
        AND l.status IN :statues
    """)
    Optional<LoanModel> findByIdWithUserAndEquipmentAndStatusIn(@Param("id") UUID id,
                                                     @Param("statues") Collection<Status> statuses);

    int countByUserAndStatusIn(UserModel user, Collection<Status> statuses);

    boolean existsByUserAndStatusInAndEquipment(UserModel user, Collection<Status> statuses, EquipmentModel equipment);

}
