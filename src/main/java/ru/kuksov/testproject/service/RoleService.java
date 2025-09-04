package ru.kuksov.testproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kuksov.testproject.model.Role;
import ru.kuksov.testproject.repository.RoleRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * Бизнес-логика работы с ролями
 */
@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    /**
     * Получение роли на основании ее названия. Если роль не существовала, то она создается.
     * @param roleName название роли
     * @return роль
     */
    public Role getOrCreateRole(String roleName) {
        Optional<Role> optionalRole = roleRepository.findRoleByRoleName(roleName);
        return optionalRole.orElseGet(() ->
                roleRepository.save(Role.builder()
                        .roleName(roleName)
                        .build()));
    }

    /**
     * Удаление роли на основании уникального номера
     * @param uuid уникальный номер
     */
    public void deleteById(UUID uuid) {
        roleRepository.deleteById(uuid);
    }
}
