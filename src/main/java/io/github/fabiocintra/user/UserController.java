package io.github.fabiocintra.user;

import io.github.fabiocintra.user.dto.UserRequest;
import io.github.fabiocintra.user.dto.UserResponse;
import io.github.fabiocintra.user.dto.UserUpdateRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;
    private final UserMapper mapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse createUser(@RequestBody @Valid UserRequest request){
        UserModel user = mapper.toEntity(request);
        UserModel userPersisted = service.createUser(user);
        UserResponse userResponse = mapper.toResponse(userPersisted);
        return userResponse;
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateUser(@Valid @RequestBody UserUpdateRequest request){
        service.updateUser(request);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<UserResponse> findAllUser(){
        return service
                .findAll()
                .stream()
                .map(user -> mapper.toResponse(user))
                .toList();
    }

}
