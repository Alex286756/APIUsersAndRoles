package ru.kuksov.testproject.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kuksov.testproject.model.Role;

import java.util.Optional;
import java.util.UUID;

/**
 * Класс работы с БД (таблица ролей)
 */
@Repository
public interface RoleRepository extends CrudRepository<Role, UUID> {

    Optional<Role> findRoleByRoleName(String roleName);
}
