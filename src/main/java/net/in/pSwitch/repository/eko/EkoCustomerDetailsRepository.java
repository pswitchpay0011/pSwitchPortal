package net.in.pSwitch.repository.eko;

import net.in.pSwitch.eko.CustomerDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EkoCustomerDetailsRepository extends JpaRepository<CustomerDetails, String>, JpaSpecificationExecutor<CustomerDetails> {

}