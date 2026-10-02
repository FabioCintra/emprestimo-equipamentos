package io.github.fabiocintra.loan;

import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class LoanSpecs {

    public static Specification<LoanModel> userIdEquals(UUID userId) {
        return (root, query, cb) ->
                cb.equal(root.get("user").get("id"), userId);
    }

    public static Specification<LoanModel> statusEquals(Status status) {
        return (root, query, cb) ->
                cb.equal(root.get("status"), status);
    }

}
