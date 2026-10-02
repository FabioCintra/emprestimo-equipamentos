package io.github.fabiocintra.user;

import org.springframework.data.jpa.domain.Specification;

public class UserSpecs {

    public static Specification<UserModel> nameLike(String name) {
        return (
                (root, query, cb) ->
                    cb.like(cb.upper(root.get("name")), "%" + name.toUpperCase() + "%")
        );
    }

}
