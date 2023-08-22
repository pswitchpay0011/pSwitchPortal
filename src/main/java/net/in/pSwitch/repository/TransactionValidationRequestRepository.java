package net.in.pSwitch.repository;

import net.in.pSwitch.model.database.TransactionValidation;
import net.in.pSwitch.model.request.TransactionValidationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface TransactionValidationRequestRepository extends JpaRepository<TransactionValidationRequest, Integer>, JpaSpecificationExecutor<TransactionValidationRequest> {

    @Query("SELECT t FROM TransactionValidationRequest t where t.utr = ?1")
    TransactionValidation findByUtr(String utr);
}
