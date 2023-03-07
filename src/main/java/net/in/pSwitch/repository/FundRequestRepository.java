package net.in.pSwitch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import net.in.pSwitch.model.FundRequest;
import net.in.pSwitch.model.UserInfo;

public interface FundRequestRepository  extends JpaRepository<FundRequest, Integer>, JpaSpecificationExecutor<FundRequest> {

	List<FundRequest> findByUser(UserInfo user);
	
	List<FundRequest> findByParentOrderByCreatedTsDesc(UserInfo parent);
	
}
