package net.in.pSwitch.repository;

import net.in.pSwitch.model.database.ConfirmTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ConfirmTransactionRepository extends JpaRepository<ConfirmTransaction, Integer>, JpaSpecificationExecutor<ConfirmTransaction> {


	@Query("SELECT t FROM ConfirmTransaction t where t.utr = ?1")
	ConfirmTransaction findByUtr(String utr);
}