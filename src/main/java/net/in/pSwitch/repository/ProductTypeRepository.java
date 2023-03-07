package net.in.pSwitch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import net.in.pSwitch.model.ProductType;

@Repository
public interface ProductTypeRepository
		extends JpaRepository<ProductType, Integer>, JpaSpecificationExecutor<ProductType> {

	ProductType findByName(String name);

}
