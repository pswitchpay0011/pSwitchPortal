package net.in.pSwitch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import net.in.pSwitch.model.ProductNews;

@Repository
public interface ProductNewsRepository extends JpaRepository<ProductNews, Integer>, JpaSpecificationExecutor<ProductNews> {
	List<ProductNews> findAllByActive(long active);
}