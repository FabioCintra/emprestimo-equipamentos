package io.github.fabiocintra.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.github.fabiocintra.loan.LoanModel;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "user_tb")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "cpf")
    private String cpf;

    @OneToMany(
            mappedBy = "user"
    )
    @JsonManagedReference
    private List<LoanModel> loans;

    public UserModel(String username, String password, String name, String cpf) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.cpf = cpf;
    }
}
