package net.in.pSwitch.repository;

import net.in.pSwitch.model.api.PanCardVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PanCardRepository extends JpaRepository<PanCardVerification, String> {

}
