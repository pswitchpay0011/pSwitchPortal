package net.in.pSwitch.repository.eko;

import net.in.pSwitch.eko.model.Recipient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EkoBeneficiaryRepository extends JpaRepository<Recipient, Integer>, JpaSpecificationExecutor<Recipient> {

}