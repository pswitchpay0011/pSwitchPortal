package net.in.pSwitch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import net.in.pSwitch.model.CompanyBankDetails;

@Repository
public interface CompanyBankDetailsRepository
		extends JpaRepository<CompanyBankDetails, Integer>, JpaSpecificationExecutor<CompanyBankDetails> {
	
	CompanyBankDetails findByAccountNumber(String accountNumber);

}