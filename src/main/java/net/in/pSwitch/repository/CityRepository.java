package net.in.pSwitch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.in.pSwitch.model.City;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    List<City> findByStateId(Long stateId);

    City findByName(String name);
}
