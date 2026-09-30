package io.github.fabiocintra.loan;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LoanRepository extends JpaRepository<LoanModel, UUID> {
}
