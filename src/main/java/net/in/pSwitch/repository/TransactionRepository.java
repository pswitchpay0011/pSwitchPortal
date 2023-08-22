package net.in.pSwitch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.in.pSwitch.model.Transaction;
import net.in.pSwitch.model.user.UserInfo;

@Repository
public interface TransactionRepository
		extends JpaRepository<Transaction, Integer>, JpaSpecificationExecutor<Transaction> {

	List<Transaction> findByToUser(UserInfo user);

	List<Transaction> findByFromUser(UserInfo user);

	@Query("SELECT u FROM Transaction u WHERE u.fromUser = ?1 OR u.toUser = ?1")
	public List<Transaction> searchUserTransactions(UserInfo userId);
}