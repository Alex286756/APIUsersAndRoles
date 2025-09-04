package ru.kuksov.testproject.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.kuksov.testproject.controller.dto.UserDetails;
import ru.kuksov.testproject.controller.dto.UserIdAndDetails;
import ru.kuksov.testproject.model.User;
import ru.kuksov.testproject.service.UserService;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/createNewUser")
    public ResponseEntity<UserIdAndDetails> createUser(@RequestBody @Valid UserDetails request) {
        User user = userService.createUser(request);
        return convertUserToResponseEntity(user);
    }

    @GetMapping("/users")
    public ResponseEntity<UserIdAndDetails> getUser(
            @RequestParam(name = "userID") UUID userUUID
    ) {
        Optional<User> optionalUser = userService.findUserByUUID(userUUID);
        return optionalUser.map(this::convertUserToResponseEntity).orElseGet(() -> ResponseEntity
                .notFound()
                .build());
    }

    @PutMapping(value = "/userDetailsUpdate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserIdAndDetails> updateUserDetailsFromJson(
            @RequestBody @Valid UserIdAndDetails request
    ) {
        Optional<User> optionalUser = userService
                .findUserByUUID(UUID.fromString(request.uuid()));
        if (optionalUser.isPresent()) {
            User correctedUser = userService.editUser(optionalUser.get(),
                    request.fio(),
                    request.phoneNumber(),
                    request.avatar(),
                    request.roleName()
            );
            return convertUserToResponseEntity(correctedUser);
        } else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @PutMapping(value = "/userDetailsUpdate")
    public ResponseEntity<UserIdAndDetails> updateUserDetailsFromParams(
            @RequestParam(name = "uuid") UUID userUUID,
            @RequestParam(name = "fio", required = false) String fio,
            @RequestParam(name = "phonenumber", required = false) String phoneNumber,
            @RequestParam(name = "avatar", required = false) String avatar,
            @RequestParam(name = "rolename", required = false) String roleName
    ) {
        Optional<User> optionalUser = userService.findUserByUUID(userUUID);
        if (optionalUser.isPresent()) {

            User correctedUser = userService.editUser(optionalUser.get(),
                    fio, phoneNumber, avatar, roleName);
            return convertUserToResponseEntity(correctedUser);
        } else {
            return ResponseEntity
                    .notFound()
                    .build();
        }
    }

    @DeleteMapping("/users")
    public void deleteUser(
            @RequestParam(name = "userID") UUID userUUID
    ) {
        userService.deleteUser(userUUID);
    }

    private ResponseEntity<UserIdAndDetails> convertUserToResponseEntity(User user) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(new UserIdAndDetails(
                        user.getUuid().toString(),
                        user.getFio(),
                        user.getPhoneNumber(),
                        user.getAvatar(),
                        user.getRole().getRoleName()
                ));
    }
}
