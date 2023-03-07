package net.in.pSwitch.service;

import java.util.List;

import net.in.pSwitch.authentication.LoginUser;
import net.in.pSwitch.authentication.LoginUserInfo;
import org.springframework.ui.Model;

import net.in.pSwitch.model.ProductNews;
import net.in.pSwitch.dto.ProductNewsDTO;

public interface NewsService {

	ProductNews findById(Integer newsId);

	ProductNews save(ProductNews news);

	String saveNews(Model model, ProductNewsDTO newsDTO, LoginUserInfo loginUserInfo);

	ProductNews deleteNews(Integer newsId);

	ProductNews disableNews(Integer newsId);

	ProductNews enableNews(Integer newsId);

	List<ProductNews> findAll();

	List<ProductNews> findAllActive();

}
