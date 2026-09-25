package io.github.fabiocintra.user;

import static org.junit.jupiter.api.Assertions.*;

import io.github.fabiocintra.utils.Utils;
import io.github.fabiocintra.utils.exceptions.DataExistsInTheSystemException;
import io.github.fabiocintra.utils.exceptions.ThisIsNotACPFException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    UserService service;

    @Mock
    UserRepository repository;

    UserModel user, userResult;

    @BeforeEach
    void setUp(){
        user = new UserModel();
        user.setUsername("Henrique@admin.com");
        user.setPassword("admin123");
        user.setName("Henrique");
        user.setCpf("12345678900");

        userResult = new UserModel();
        userResult.setUsername("Henrique@admin.com");
        userResult.setPassword("admin123");
        userResult.setName("Henrique");
        userResult.setCpf("***.456.789-**");
    }

    @Test
    void deveSalvarUsuario(){

        Mockito
                .when(repository.save(Mockito.any()))
                .thenReturn(userResult);

        var userSaved = service.createUser(user);

        assertNotNull(userSaved);
        assertSame(userSaved, userResult);
        Mockito.verify(repository).save(Mockito.any());
    }

    @Test
    void naoDeveSalvarUsuarioQuandoCPFJaEstiverCadastrado(){

        Mockito
                .when(repository.existsByCpf("***.456.789-**"))
                .thenReturn(true);

        assertThrows(DataExistsInTheSystemException.class, () -> service.createUser(user));
        Mockito.verify(repository).existsByCpf("***.456.789-**");
        Mockito.verify(repository, Mockito.never()).save(Mockito.any());

    }

    @Test
    void naoDeveSalvarUsuarioQuandoUsernameJaEstiverCadastrado(){

        Mockito
                .when(repository.existsByUsername("Henrique@admin.com"))
                .thenReturn(true);

        assertThrows(DataExistsInTheSystemException.class, () -> service.createUser(user));
        Mockito.verify(repository).existsByUsername("Henrique@admin.com");
        Mockito.verify(repository, Mockito.never()).save(Mockito.any());

    }

    @Test
    void naoDeveSalvarUsuarioQuandoOCPFParaSerMascaradoForInvalido() {

        user.setCpf("1223");

        assertThrows(ThisIsNotACPFException.class, () -> service.createUser(user));
        Mockito.verify(repository, Mockito.never()).save(Mockito.any());

    }
}
