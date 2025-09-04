package ru.kuksov.testproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kuksov.testproject.controller.dto.UserDetails;
import ru.kuksov.testproject.model.Role;
import ru.kuksov.testproject.model.User;
import ru.kuksov.testproject.repository.UserRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Бизнес-логика работы с данными пользователей
 */
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;

    /**
     * Удаление данных пользователя. Если пользователей с такой же ролью больше нет,
     * то также удаляется роль.
     * @param userUUID уникальный номер пользователя
     */
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

    /**
     * Поиск данных пользователя по уникальному номеру
     * @param userUUID уникальный номер
     * @return результат поиска пользователя
     */
    public Optional<User> findUserByUUID(UUID userUUID) {
        return userRepository.findById(userUUID);
    }

    /**
     * Создание пользователя
     * @param request данные пользователя
     * @return данные пользователя после создания
     */
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

    /**
     * Редактирует данные пользователя (если они не пустые)
     * @param user пользователь, данные которого редактируются
     * @param fio новые ФИО пользователя
     * @param phoneNumber новый номер телефона
     * @param avatar новая ссылка на аватарку
     * @param roleName новая роль
     * @return данные пользователя после редактирования
     */
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
