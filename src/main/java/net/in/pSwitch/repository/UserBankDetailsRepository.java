package net.in.pSwitch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import net.in.pSwitch.model.wallet.UserBankDetails;

@Repository
public interface UserBankDetailsRepository
		extends JpaRepository<UserBankDetails, Integer>, JpaSpecificationExecutor<UserBankDetails> {

}