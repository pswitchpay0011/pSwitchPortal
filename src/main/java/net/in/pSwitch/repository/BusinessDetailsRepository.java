package net.in.pSwitch.repository;

import net.in.pSwitch.model.BusinessDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessDetailsRepository extends JpaRepository<BusinessDetails, Long> {


}
