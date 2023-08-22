package net.in.pSwitch.repository;

import net.in.pSwitch.model.api.AadhaarVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AadhaarRepository extends JpaRepository<AadhaarVerification, String> {
}

