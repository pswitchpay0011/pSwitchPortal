package net.in.pSwitch.repository;

import net.in.pSwitch.model.database.ConfirmTransaction;
import net.in.pSwitch.model.request.ConfirmTransactionRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface ConfirmTransactionRequestRepository extends JpaRepository<ConfirmTransactionRequest, Integer>, JpaSpecificationExecutor<ConfirmTransactionRequest> {


	@Query("SELECT t FROM ConfirmTransactionRequest t where t.utr = ?1")
	ConfirmTransaction findByUtr(String utr);
}
