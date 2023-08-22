package net.in.pSwitch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import net.in.pSwitch.model.Product;
import net.in.pSwitch.model.ProductFlexiCommission;
import net.in.pSwitch.model.user.UserInfo;

@Repository
public interface ProductFlexiCommissionRepository
		extends JpaRepository<ProductFlexiCommission, Integer>, JpaSpecificationExecutor<ProductFlexiCommission> {

	List<ProductFlexiCommission> findAllByProduct(Product product);

	ProductFlexiCommission findAllByProductAndUser(Product product, UserInfo userInfo);

}
