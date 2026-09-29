package io.github.fabiocintra.equipment;

import org.springframework.data.jpa.domain.Specification;

public class Specs {

    public static Specification<EquipmentModel> nameLike(String name){
        return (root, query, cb) ->
            cb.like(cb.upper(root.get("name")), "%" + name.toUpperCase() + "%");

    }

}
