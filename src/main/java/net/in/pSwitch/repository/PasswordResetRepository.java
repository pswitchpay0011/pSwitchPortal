package net.in.pSwitch.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import net.in.pSwitch.model.Category;
import net.in.pSwitch.model.PasswordResetToken;

@Repository
public interface PasswordResetRepository
		extends JpaRepository<PasswordResetToken, Integer>, JpaSpecificationExecutor<Category> {

	PasswordResetToken findByToken(String token);

}