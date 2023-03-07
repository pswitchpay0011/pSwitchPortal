package net.in.pSwitch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.in.pSwitch.model.Menu;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long>, JpaSpecificationExecutor<Menu> {

	Menu findByMenuName(String menuName);

	@Query("SELECT m FROM Menu m WHERE  m.isActive = 1 order by m.menuName")
	List<Menu> findAllOrderByMenuName();
	
	List<Menu> findByParentMenuIdIsNull();

	List<Menu> findAllByParentMenuIdOrderByMenuIdAsc(Long parentId);
	
	List<Menu> findAllByParentMenuIdAndIsSearchableOrderByMenuIdAsc(Long parentId, long isSearchable);

	List<Menu> findAllByAdminMenuAndIsActiveOrderByMenuOrderAsc(long isAdmin, long isActive);

	List<Menu> findAllByRetailerMenuAndIsActiveOrderByMenuOrderAsc(long isRetailer, long isActive);

	List<Menu> findAllByDistributorMenuAndIsActiveOrderByMenuOrderAsc(long isDistributor, long isActive);

	List<Menu> findAllBySuperDistributorMenuAndIsActiveOrderByMenuOrderAsc(long isSuperDistributor, long isActive);

	List<Menu> findAllByBusinessAssociateMenuAndIsActiveOrderByMenuOrderAsc(long isBusinessAssociate, long isActive);

}
