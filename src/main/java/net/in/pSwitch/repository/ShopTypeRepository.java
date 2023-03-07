package net.in.pSwitch.repository;

import net.in.pSwitch.model.ShopType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopTypeRepository extends JpaRepository<ShopType, Long> {

//    List<ShopType> findById(Long stateId);

//    ShopType findById(String name);
}
