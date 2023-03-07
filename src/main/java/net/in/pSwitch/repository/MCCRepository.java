package net.in.pSwitch.repository;

import net.in.pSwitch.model.MccType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MCCRepository extends JpaRepository<MccType, Long> {

    @Query("SELECT m FROM MccType m where m.id <> 1 order by m.mccType")
    List<MccType> findAllOrderByMccType();

//    ShopType findById(String name);
}
