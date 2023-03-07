package net.in.pSwitch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import net.in.pSwitch.model.UserInfo;
import net.in.pSwitch.model.UserWallet;

@Repository
public interface UserWalletRepository extends JpaRepository<UserWallet, Integer>, JpaSpecificationExecutor<UserWallet> {

	UserWallet findByUser(UserInfo user);
}