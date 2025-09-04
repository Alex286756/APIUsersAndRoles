package ru.kuksov.testproject.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.kuksov.testproject.model.Role;
import ru.kuksov.testproject.repository.RoleRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role getOrCreateRole(String roleName) {
        Optional<Role> optionalRole = roleRepository.findRoleByRoleName(roleName);
        return optionalRole.orElseGet(() ->
                roleRepository.save(Role.builder()
                        .roleName(roleName)
                        .build()));
    }

    public void deleteById(UUID uuid) {
        roleRepository.deleteById(uuid);
    }
}
