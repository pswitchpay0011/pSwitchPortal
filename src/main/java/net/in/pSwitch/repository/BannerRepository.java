package net.in.pSwitch.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import net.in.pSwitch.model.Banner;
import net.in.pSwitch.model.Status;

public interface BannerRepository extends JpaRepository<Banner, Integer>, JpaSpecificationExecutor<Banner> {

	List<Banner> findAllByStatus(Status active);

}
