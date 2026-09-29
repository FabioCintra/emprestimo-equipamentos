package io.github.fabiocintra.user;

import static org.junit.jupiter.api.Assertions.*;

import io.github.fabiocintra.user.dto.UserUpdateRequest;
import io.github.fabiocintra.utils.exceptions.DataExistsInTheSystemException;
import io.github.fabiocintra.utils.exceptions.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

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
        user.setUsername("henrique@gmail.com");
        user.setPassword("96028196fa");
        user.setName("Fabio Henrique Silva Cintra");
        user.setCpf("12345678900");

        userResult = new UserModel();
        userResult.setUsername("henrique@gmail.com");
        userResult.setPassword("96028196fa");
        userResult.setName("Fabio Henrique Silva Cintra");
        userResult.setCpf("12345678900");
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
                .when(repository.existsByCpf("12345678900"))
                .thenReturn(true);

        assertThrows(DataExistsInTheSystemException.class, () -> service.createUser(user));
        Mockito.verify(repository).existsByCpf("12345678900");
        Mockito.verify(repository, Mockito.never()).save(Mockito.any());

    }

    @Test
    void naoDeveSalvarUsuarioQuandoUsernameJaEstiverCadastrado(){

        Mockito
                .when(repository.existsByUsername("henrique@gmail.com"))
                .thenReturn(true);

        assertThrows(DataExistsInTheSystemException.class, () -> service.createUser(user));
        Mockito.verify(repository).existsByUsername("henrique@gmail.com");
        Mockito.verify(repository, Mockito.never()).save(Mockito.any());

    }

    @Test
    void deveAtualizarUsuario(){

        UserUpdateRequest userRequest = new UserUpdateRequest(
                "ea7ab246-3340-4bb1-b3aa-01a75300802e",
                null,
                null,
                null
        );

        UUID id = UUID.fromString("ea7ab246-3340-4bb1-b3aa-01a75300802e");

        user.setId(id);
        userResult.setId(id);

        Mockito
                .when(repository.findById(id))
                .thenReturn(Optional.ofNullable(user));

        Mockito
                .when(repository.save(user))
                .thenReturn(userResult);

        var userUpdated = service.updateUser(userRequest);

        assertNotNull(userUpdated);
        Mockito.verify(repository).save(user);

    }

    @Test
    void naoDeveAtualizarUsuarioQuandoIdForIncorreto(){
        UUID id = UUID.randomUUID();
        UserUpdateRequest userRequest = new UserUpdateRequest(
                id.toString(),
                null,
                null,
                null
        );

        Mockito
                .when(repository.findById(id))
                .thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> service.updateUser(userRequest));
        Mockito.verify(repository, Mockito.never()).save(Mockito.any());
        Mockito.verify(repository).findById(id);
    }

    @Test
    void naoDeveAtualizarUsuarioQuandoUsernameJaEstiverCadastrado(){
        UserUpdateRequest userRequest = new UserUpdateRequest(
                "ea7ab246-3340-4bb1-b3aa-01a75300802e",
                "henrique@gmail.com",
                null,
                null
        );

        UUID id = UUID.fromString("ea7ab246-3340-4bb1-b3aa-01a75300802e");
        user.setId(id);

        Mockito
                .when(repository.findById(id))
                .thenReturn(Optional.ofNullable(user));

        Mockito
                .when(repository.existsByUsername("henrique@gmail.com"))
                .thenReturn(true);

        assertThrows(DataExistsInTheSystemException.class, () -> service.updateUser(userRequest));
        Mockito.verify(repository, Mockito.never()).save(Mockito.any());
        Mockito.verify(repository).findById(id);
        Mockito.verify(repository).existsByUsername("henrique@gmail.com");
    }
}
