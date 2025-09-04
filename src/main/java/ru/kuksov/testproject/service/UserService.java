package ru.kuksov.testproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kuksov.testproject.controller.dto.UserDetails;
import ru.kuksov.testproject.model.Role;
import ru.kuksov.testproject.model.User;
import ru.kuksov.testproject.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    public void deleteUser(UUID userUUID) {
        Optional<User> userOptional = userRepository.findById(userUUID);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            userRepository.deleteById(userUUID);
            Role role = user.getRole();
            Optional<User> anotherUser = userRepository.findUserByRole(role);
            if (anotherUser.isEmpty()) {
                roleService.deleteById(role.getUuid());
            }
        }
    }

    public Optional<User> findUserByUUID(UUID userUUID) {
        return userRepository.findById(userUUID);
    }

    public User createUser(UserDetails request) {
        Role role = roleService.getOrCreateRole(request.roleName());
        return userRepository.save(
                User.builder()
                .fio(request.fio())
                .phoneNumber(request.phoneNumber())
                .avatar(request.avatar())
                .role(role)
                .build()
        );
    }

    public User editUser(User user,
                         String fio,
                         String phoneNumber,
                         String avatar,
                         String roleName
                         ) {
        if (fio != null) {
            user.setFio(fio);
        }
        if (phoneNumber != null) {
            user.setPhoneNumber(phoneNumber);
        }
        if (avatar != null) {
            user.setAvatar(avatar);
        }
        if (roleName != null) {
            Role role = roleService.getOrCreateRole(roleName);
            user.setRole(role);
        }
        return userRepository.save(user);
    }

}
