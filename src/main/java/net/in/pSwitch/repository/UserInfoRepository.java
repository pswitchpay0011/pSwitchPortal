package net.in.pSwitch.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import net.in.pSwitch.model.user.Role;
import net.in.pSwitch.model.user.UserInfo;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer>, JpaSpecificationExecutor<UserInfo> {

	long countByRoles(Role role);

	UserInfo findByUsername(String username);

	@Query("SELECT u FROM UserInfo u where u.username = ?1 AND u.mobileNumber = ?2 AND u.roles.roleCode = ?3")
	List<UserInfo> isUserExist(String email, String mobile, String role);

	UserInfo findByVerificationCode(String verificationCode);

	@Modifying
	@Query("UPDATE UserInfo SET isActive = 1 WHERE userId = ?1")
	void enableUser(Integer userId);
	@Modifying
	@Query("UPDATE UserInfo SET accountState = ?2, modifiedDate = ?3 WHERE userId = ?1")
	void updateAccountState(Integer userId, String accountState, LocalDateTime modifiedDateTime);

	@Query("SELECT u FROM UserInfo u WHERE lower(u.firstName) like lower(concat('%', ?1,'%'))"
			+ " OR lower(u.lastName) like lower(concat('%', ?1,'%'))"
			+ " OR lower(u.userPSwitchId) like lower(concat('%', ?1,'%'))"
			+ " OR lower(u.username) like lower(concat('%', ?1,'%'))")
	public List<UserInfo> search(String keyword);

	List<UserInfo> findByRolesNot(Role role);

	List<UserInfo> findByIsVerified(Long isVerified);

	List<UserInfo> findByIsActive(Long isActive);

	List<UserInfo> findByRoles(Role role);

	@Query("SELECT u FROM UserInfo u where u.userMapping.createdBy = ?1 and u.roles = ?2")
	List<UserInfo> findByRoles(Integer createdBy, Role role, Sort sort);

	@Query("SELECT u FROM UserInfo u where u.userMapping.parentUser = ?1 and u.roles = ?2")
	List<UserInfo> findChildUser(Integer mDistributor, Role role, Sort sort);

	UserInfo findByUserPSwitchId(String username);

	List<UserInfo> findByRolesAndState(Role findByRoleCode, Long states);

	@Query("SELECT u FROM UserInfo u where u.userMapping.createdBy = ?1")
	Page<UserInfo> findAllCreatedBy(Integer createdBy, Specification specifications, Pageable pageable);

	@Query("UPDATE UserInfo u SET u.failedAttempt = ?1 WHERE u.userPSwitchId = ?2")
	@Modifying
	public void updateFailedAttempts(Integer failAttempts, String userPSwitchId);

//	@Query("select u FROM UserInfo u WHERE u.username = ?1 OR u.userPSwitchId = ?1 OR u.mobileNumber = ?1 OR u.userPSwitchId = ?1")
	@Query("select u FROM UserInfo u WHERE u.userPSwitchId = ?1")
	public UserInfo findUserForLogin(String userPSwitchId);
}