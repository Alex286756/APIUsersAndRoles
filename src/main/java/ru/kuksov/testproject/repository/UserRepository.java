package ru.kuksov.testproject.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.kuksov.testproject.model.Role;
import ru.kuksov.testproject.model.User;

import java.util.Optional;
import java.util.UUID;

/**
 * Класс работы с БД (таблица пользователей)
 */
@Repository
public interface UserRepository extends CrudRepository<User, UUID> {

    Optional<User> findUserByRole(Role role);
}
