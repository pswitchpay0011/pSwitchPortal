package net.in.pSwitch.repository;


import net.in.pSwitch.model.database.CDMConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface CDMConfirmationRepository extends JpaRepository<CDMConfirmation, Integer>, JpaSpecificationExecutor<CDMConfirmation> {


    @Query("SELECT t FROM CDMConfirmation t where t.utr = ?1")
    CDMConfirmation findByUTR(String utr);
}
