package net.in.pSwitch.service;

import net.in.pSwitch.authentication.LoginUserInfo;
import net.in.pSwitch.model.UserInfo;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletResponse;

public interface BinderService {

//	Model bindUserDetails(Model model);

	boolean bindCookieData(HttpServletResponse response);

	Boolean isUserLogin();

//	UserInfo getCurrentUser();

	UserInfo getCurrentUser(LoginUserInfo loginUserInfo);

	Model bindUserDetails(Model model, LoginUserInfo loginUserInfo);
}
