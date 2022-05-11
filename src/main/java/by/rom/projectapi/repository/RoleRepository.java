package by.rom.projectapi.repository;

import by.rom.projectapi.model.ERole;
import by.rom.projectapi.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(ERole name);
}
