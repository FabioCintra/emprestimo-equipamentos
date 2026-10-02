package io.github.fabiocintra.loan;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.github.fabiocintra.equipment.EquipmentModel;
import io.github.fabiocintra.user.UserModel;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.User;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.CurrentTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "loan_tb")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class LoanModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "date_loan")
    @CreationTimestamp
    private LocalDateTime dateLoan;

    @Column(name = "date_return")
    private LocalDateTime dateReturn;

    @Column(name = "status")
    @JdbcTypeCode(SqlTypes.NAMED_ENUM)
    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @JsonBackReference
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "equipment_id")
    @JsonBackReference
    private EquipmentModel equipment;

    public LoanModel(UserModel user, EquipmentModel equipment){
        this.user = user;
        this.equipment = equipment;
    }

}
