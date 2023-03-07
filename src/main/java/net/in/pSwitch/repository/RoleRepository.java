package net.in.pSwitch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.in.pSwitch.model.Role;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleCode(String roleCode);
    List<Role> findByRoleCodeNot(String roleCode);
}
