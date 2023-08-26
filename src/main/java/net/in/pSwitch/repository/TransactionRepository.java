package net.in.pSwitch.repository;

import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.stereotype.Repository;

import net.in.pSwitch.model.Transactions;

@Repository
public interface TransactionRepository
		extends JpaRepository<Transactions, Integer>, JpaSpecificationExecutor<Transactions> {

//	List<Transaction> findByToUser(UserInfo user);

//	List<Transaction> findByFromUser(UserInfo user);

//	@Query("SELECT u FROM Transaction u WHERE u.createdBy = ?1 OR u.userid = ?1")
//	public List<Transaction> searchUserTransactions(Integer userId);

	@Procedure("PR_GetBalanceAmount")
	Map getUserBalance(Integer userId);
}