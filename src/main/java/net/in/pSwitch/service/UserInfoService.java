package net.in.pSwitch.service;

import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import net.in.pSwitch.model.Role;
import net.in.pSwitch.model.UserInfo;

public interface UserInfoService {

	public static final int MAX_FAILED_ATTEMPTS = 3;
	public static final long LOCK_TIME_DURATION = 24 * 60 * 60 * 1000; // 24 hours

	Page<UserInfo> findAll(String queryString, Pageable pageable);

	Page<UserInfo> findAllByRole(Role role, String queryString, Pageable pageable);
	
	public UserInfo findByUserPSwitchId(String userPSwitchId);

	public String getDataForDatatable(Map<String, Object> params);

	public void increaseFailedAttempts(UserInfo user);

	public void resetFailedAttempts(String userPSwitchId);

	public void lock(UserInfo user);

	public boolean unlockWhenTimeExpired(UserInfo user);
}
