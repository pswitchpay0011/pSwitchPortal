package net.in.pSwitch.service;

import net.in.pSwitch.authentication.LoginUserInfo;
import net.in.pSwitch.model.user.UserInfo;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

public interface BinderService {

//	Model bindUserDetails(Model model);

	boolean bindCookieData(HttpServletResponse response);

	Boolean isUserLogin();

//	UserInfo getCurrentUser();

	UserInfo getCurrentUser(LoginUserInfo loginUserInfo);

	Model bindUserDetails(Model model, LoginUserInfo loginUserInfo);

	double walletBalance(LoginUserInfo loginUserInfo);

	String getAxisVirtualAccountNo(LoginUserInfo loginUserInfo);

	List<Map> getUserVirtualAccountDetails(LoginUserInfo loginUserInfo);
}
