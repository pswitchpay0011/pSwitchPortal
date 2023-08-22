package net.in.pSwitch.service;

import java.util.Map;

import net.in.pSwitch.authentication.LoginUserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.in.pSwitch.model.user.Role;
import net.in.pSwitch.model.user.UserInfo;

public interface UserInfoService {

	public static final int MAX_FAILED_ATTEMPTS = 3;
	public static final long LOCK_TIME_DURATION = 24 * 60 * 60 * 1000; // 24 hours

	Page<UserInfo> findAll(String queryString, Pageable pageable, Integer createdBy);

	Page<UserInfo> findAllByRole(Role role, String queryString, Pageable pageable,  Integer createdBy);
	
	public UserInfo findByUserPSwitchId(String userPSwitchId);

	public String getDataForDatatable(Map<String, Object> params, LoginUserInfo loginUserInfo);

	public void increaseFailedAttempts(UserInfo user);

	public void resetFailedAttempts(String userPSwitchId);

	public void lock(UserInfo user);

	public boolean unlockWhenTimeExpired(UserInfo user);
}
