package net.in.pSwitch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import net.in.pSwitch.model.BankList;

@Repository
public interface BankListRepository extends JpaRepository<BankList, Integer>, JpaSpecificationExecutor<BankList> {

}