package io.github.fabiocintra.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "user_tb")
@NoArgsConstructor
@AllArgsConstructor
@Data
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

    public UserModel(String username, String password, String name, String cpf) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.cpf = cpf;
    }
}
