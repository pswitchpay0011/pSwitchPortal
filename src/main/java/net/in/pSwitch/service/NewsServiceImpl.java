package net.in.pSwitch.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import net.in.pSwitch.authentication.LoginUser;
import net.in.pSwitch.authentication.LoginUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import net.in.pSwitch.model.ProductNews;
import net.in.pSwitch.dto.ProductNewsDTO;
import net.in.pSwitch.repository.ProductNewsRepository;
import net.in.pSwitch.repository.RoleRepository;
import net.in.pSwitch.utility.StringLiteral;

@Service
public class NewsServiceImpl implements NewsService {

	Logger logger = LoggerFactory.getLogger(BannerService.class);

	@Autowired
	private ProductNewsRepository newsRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	private BinderService binderService;
	@Autowired
	private FileStorageService storageService;

	@Override
	public ProductNews findById(Integer newsId) {
		ProductNews model = null;
		Optional<ProductNews> result = newsRepository.findById(newsId);
		if (result.isPresent()) {
			model = result.get();
		}
		return model;
	}

	@Override
	public ProductNews save(ProductNews banner) {
		banner = newsRepository.save(banner);
		return banner;
	}

	@Override
	public ProductNews enableNews(Integer newsId) {
		ProductNews model = null;
		Optional<ProductNews> result = newsRepository.findById(newsId);
		if (result.isPresent()) {
			model = result.get();
			model.setActive(1);
			newsRepository.save(model);
		}
		return model;
	}

	@Override
	public ProductNews disableNews(Integer newsId) {
		ProductNews model = null;
		Optional<ProductNews> result = newsRepository.findById(newsId);
		if (result.isPresent()) {
			model = result.get();
			model.setActive(0);
			newsRepository.save(model);
		}
		return model;
	}

	@Override
	public ProductNews deleteNews(Integer newsId) {
		ProductNews model = null;
		Optional<ProductNews> result = newsRepository.findById(newsId);
		if (result.isPresent()) {
			model = result.get();
			newsRepository.delete(model);
		}
		return model;
	}

	@Override
	public String saveNews(Model model, ProductNewsDTO newsDTO, @LoginUser LoginUserInfo loginUserInfo) {

		boolean isValid = true;
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<ProductNewsDTO>> violations = validator.validate(newsDTO);
		List<String> errorMessage = new ArrayList<>();
		for (ConstraintViolation<ProductNewsDTO> violation : violations) {
			logger.error(violation.getMessage());
			errorMessage.add(violation.getMessage());
			isValid = false;
		}

		if (!isValid) {
			model.addAttribute("updateError", errorMessage);
		} else {

			ProductNews productNews = null;
			if (newsDTO.getId() != null) {

				Optional<ProductNews> optional = newsRepository.findById(Integer.parseInt(newsDTO.getId()));
				if (optional != null) {
					productNews = optional.get();
				}
			}
			if (productNews == null) {
				productNews = new ProductNews();
			}

			productNews.setNews(newsDTO.getNews());
			newsRepository.save(productNews);

			return "redirect:/admin/productNews";
		}

		model = binderService.bindUserDetails(model,loginUserInfo);
		model.addAttribute(StringLiteral.KEY_ACTIVE_PAGE, StringLiteral.MENU_MANAGE_NEWS);
		return "addProductNews";
	}

	@Override
	public List<ProductNews> findAll() {
		return newsRepository.findAll();
	}

	@Override
	public List<ProductNews> findAllActive() {
		return newsRepository.findAllByActive(1l);
	}
}
