package net.in.pSwitch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import net.in.pSwitch.model.user.Role;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByRoleCode(String roleCode);
    List<Role> findByRoleCodeNot(String roleCode);

    @Query( "select r from Role r where roleCode in :roleCode order by id desc" )
    List<Role> findByRoleCodes(@Param("roleCode") List<String> roleCode);
}
