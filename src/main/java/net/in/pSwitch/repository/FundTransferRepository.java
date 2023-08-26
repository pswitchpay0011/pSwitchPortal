package net.in.pSwitch.repository;

import net.in.pSwitch.model.FundTransfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FundTransferRepository
		extends JpaRepository<FundTransfer, Integer>, JpaSpecificationExecutor<FundTransfer> {

	@Query("SELECT u FROM FundTransfer u WHERE u.transferredFromUserId = ?1 ORDER BY createdOn DESC")
	public List<FundTransfer> fetchTransferreFundsList(Integer userId);

	@Procedure("PR_GetBalanceAmount")
	Map getUserBalance(Integer userId);
}