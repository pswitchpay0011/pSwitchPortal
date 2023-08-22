package net.in.pSwitch.repository.eko;

import net.in.pSwitch.eko.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EkoCustomerTransactionLimitRepository extends JpaRepository<Limit, String>, JpaSpecificationExecutor<Limit> {

}