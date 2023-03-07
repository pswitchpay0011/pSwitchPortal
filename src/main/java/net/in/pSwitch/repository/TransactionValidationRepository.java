package net.in.pSwitch.repository;

import net.in.pSwitch.model.database.TransactionValidation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface TransactionValidationRepository extends JpaRepository<TransactionValidation, Integer>, JpaSpecificationExecutor<TransactionValidation> {

    @Query("SELECT t FROM TransactionValidation t where t.utr = ?1")
    TransactionValidation findByUtr(String utr);
}
