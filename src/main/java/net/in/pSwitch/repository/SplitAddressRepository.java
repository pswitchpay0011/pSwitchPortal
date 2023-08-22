package net.in.pSwitch.repository;

import net.in.pSwitch.model.api.SplitAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SplitAddressRepository extends JpaRepository<SplitAddress, Integer> {
}

