package net.in.pSwitch.service;

import java.util.List;

import net.in.pSwitch.authentication.LoginUser;
import net.in.pSwitch.authentication.LoginUserInfo;
import org.springframework.ui.Model;

import net.in.pSwitch.model.Banner;
import net.in.pSwitch.dto.BannerDTO;

public interface BannerService {

	Banner findById(Integer bannerId);

	Banner save(Banner banner);
	
	String saveBanner(Model model, BannerDTO bannerDTO, LoginUserInfo loginUserInfo);

	Banner deleteBanner(Integer bannerId);

	Banner disableBanner(Integer bannerId);

	Banner enableBanner(Integer bannerId);

	List<Banner> findAll();
	
	List<Banner> findAllActive();

}
