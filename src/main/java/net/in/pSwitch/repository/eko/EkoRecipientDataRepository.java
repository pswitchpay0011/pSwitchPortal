package net.in.pSwitch.repository.eko;

import net.in.pSwitch.eko.model.RecipientData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EkoRecipientDataRepository extends JpaRepository<RecipientData, String>, JpaSpecificationExecutor<RecipientData> {

}