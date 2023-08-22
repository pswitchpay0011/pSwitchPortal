package net.in.pSwitch.repository.eko;

import net.in.pSwitch.model.eko.EKOBankList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EKOBankListRepository extends JpaRepository<EKOBankList, Integer>, JpaSpecificationExecutor<EKOBankList> {

}