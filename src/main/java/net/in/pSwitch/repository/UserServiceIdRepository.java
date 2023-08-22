package net.in.pSwitch.repository;

import net.in.pSwitch.model.UserServiceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserServiceIdRepository extends JpaRepository<UserServiceId, Integer> {
}

