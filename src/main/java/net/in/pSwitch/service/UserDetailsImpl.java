package net.in.pSwitch.service;

import net.in.pSwitch.model.user.UserInfo;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {

	/**
	 *
	 */
	private static final long serialVersionUID = 1258334883964186558L;
	private UserInfo userInfo;

	public UserDetailsImpl(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public UserInfo getUserInfo() {
		return this.userInfo;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		if (null != userInfo.getRoles() && !userInfo.getRoles().getRoleCode().isEmpty()) {
			return Arrays.stream(userInfo.getRoles().getRoleCode().split(","))
					.map(role -> new SimpleGrantedAuthority("ROLE_" + role)).collect(Collectors.toList());
		}
		return new ArrayList<>();
	}

	@Override
	public String getPassword() {
		return userInfo.getPwd();
	}

	@Override
	public String getUsername() {
		return userInfo.getUserPSwitchId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return userInfo.getIsActive() == 1;
	}

	@Override
	public boolean isAccountNonLocked() {
		return userInfo.isAccountNonLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return userInfo.getIsActive() == 1;
	}

	@Override
	public boolean isEnabled() {
		return userInfo.isEnabled();
	}

	public String getFullName() {
		return userInfo.getFirstName() + " " + userInfo.getLastName();
	}
}
