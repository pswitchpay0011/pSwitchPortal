package net.in.pSwitch.repository;

import net.in.pSwitch.model.GSTINData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GSTINRepository extends JpaRepository<GSTINData, Integer>, JpaSpecificationExecutor<GSTINData> {

	@Query("SELECT g FROM GSTINData g where g.userId = ?1")
	GSTINData findByUser(Integer userId);
}