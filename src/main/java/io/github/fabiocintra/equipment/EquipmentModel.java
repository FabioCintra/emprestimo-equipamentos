package io.github.fabiocintra.equipment;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.github.fabiocintra.loan.LoanModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "equipment_tb")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EquipmentModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "name")
    private String name;

    @Column(name = "total_quantity")
    private Integer totalQuantity;

    @Column(name = "borrowed_quantity")
    private Integer borrowedQuantity;

    @Column(name = "avaliable_quantity")
    private Integer avaliableQuantity;

    @OneToMany(
            mappedBy = "equipment"
    )
    @JsonManagedReference
    private List<LoanModel> loans;

    public EquipmentModel (String name, Integer totalQuantity) {
        this.name = name;
        this.totalQuantity = totalQuantity;
        this.borrowedQuantity = 0;
        this.avaliableQuantity = totalQuantity;
    }

    public void updateEquipmentAfterLoan() {
        if (avaliableQuantity <= 0) {
            throw new IllegalStateException("No equipment available.");
        }

        borrowedQuantity++;
        avaliableQuantity--;
    }

    public void updateEquipmentAfterReturnLoan() {
        if (borrowedQuantity <= 0) {
            throw new IllegalStateException("No borrowed equipment to return.");
        }

        borrowedQuantity--;
        avaliableQuantity++;
    }


}
