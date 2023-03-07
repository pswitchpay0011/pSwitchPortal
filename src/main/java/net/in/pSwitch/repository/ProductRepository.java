package net.in.pSwitch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import net.in.pSwitch.model.Product;
import net.in.pSwitch.model.ProductType;
import net.in.pSwitch.model.Status;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>, JpaSpecificationExecutor<Product> {

	List<Product> findAllByProductType(ProductType productType);

	List<Product> findAllByProductTypeAndStatus(ProductType productType, Status active);

}
