package net.in.pSwitch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import net.in.pSwitch.model.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {

}
