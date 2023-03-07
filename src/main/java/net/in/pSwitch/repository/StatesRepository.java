package net.in.pSwitch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.in.pSwitch.model.States;

import java.util.List;

@Repository
public interface StatesRepository extends JpaRepository<States, Long> {

    List<States> findByCountryIdOrderByNameAsc(Long countryId);

    States findByName(String name);
}
