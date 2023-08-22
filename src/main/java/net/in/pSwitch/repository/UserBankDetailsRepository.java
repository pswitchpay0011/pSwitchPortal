package net.in.pSwitch.repository;

import net.in.pSwitch.model.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.in.pSwitch.model.wallet.UserBankDetails;

import java.util.List;

@Repository
public interface UserBankDetailsRepository
		extends JpaRepository<UserBankDetails, Integer>, JpaSpecificationExecutor<UserBankDetails> {


	@Query("SELECT t FROM UserBankDetails t where t.userInfo = ?1")
	List<UserBankDetails> getUserBanksDetails(UserInfo userInfo);

}