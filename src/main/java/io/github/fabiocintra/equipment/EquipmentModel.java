package io.github.fabiocintra.equipment;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "equipment_tb")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    UUID id;

    @Column(name = "name")
    String name;

    @Column(name = "total_quantity")
    int totalQuantity;

    @Column(name = "borrowed_quantity")
    int borrowedQuantity;

    @Column(name = "avaliable_quantity")
    int avaliableQuantity;

    public EquipmentModel (String name, int totalQuantity) {
        this.name = name;
        this.totalQuantity = totalQuantity;
        this.borrowedQuantity = 0;
        this.avaliableQuantity = 0;
    }

}
