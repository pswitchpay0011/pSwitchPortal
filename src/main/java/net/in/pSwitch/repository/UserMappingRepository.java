package net.in.pSwitch.repository;

import net.in.pSwitch.model.user.UserMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserMappingRepository extends JpaRepository<UserMapping, Integer>, JpaSpecificationExecutor<UserMapping> {


}