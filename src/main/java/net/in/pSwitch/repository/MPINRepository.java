package net.in.pSwitch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import net.in.pSwitch.model.MPIN;
import net.in.pSwitch.model.UserInfo;

@Repository
public interface MPINRepository extends JpaRepository<MPIN, Integer>, JpaSpecificationExecutor<MPIN> {

	MPIN findByUser(UserInfo user);
}