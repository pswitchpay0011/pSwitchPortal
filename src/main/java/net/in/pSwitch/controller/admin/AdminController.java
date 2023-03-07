package net.in.pSwitch.controller.admin;

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
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import net.in.pSwitch.model.Banner;
import net.in.pSwitch.model.Menu;
import net.in.pSwitch.model.ProductNews;
import net.in.pSwitch.model.ProductType;
import net.in.pSwitch.dto.BannerDTO;
import net.in.pSwitch.dto.ProductNewsDTO;
import net.in.pSwitch.dto.ProductTypeDTO;
import net.in.pSwitch.repository.MenuRepository;
import net.in.pSwitch.repository.ProductNewsRepository;
import net.in.pSwitch.repository.ProductTypeRepository;
import net.in.pSwitch.repository.RoleRepository;
import net.in.pSwitch.service.BannerService;
import net.in.pSwitch.service.BinderService;
import net.in.pSwitch.service.NewsService;
import net.in.pSwitch.utility.StringLiteral;

@Controller
@RequestMapping("/admin")
public class AdminController {

	Logger logger = LoggerFactory.getLogger(AdminController.class);

	@Autowired
	RoleRepository roleRepository;
	@Autowired
	private MenuRepository menuRepository;
	@Autowired
	private BinderService binderService;
	@Autowired
	private ProductTypeRepository productTypeRepository;
	@Autowired
	private ProductNewsRepository newsRepository;
	@Autowired
	private BannerService bannerService;
	@Autowired
	private NewsService newsService;

	@RequestMapping(value = "/")
	public String home(Model model) {
		return "redirect:/index";
	}

	@RequestMapping("/productNews")
	public String productNews(Model model, @LoginUser LoginUserInfo loginUserInfo) {
		model = binderService.bindUserDetails(model, loginUserInfo);
		List<ProductNews> list = newsService.findAll();
		model.addAttribute("newsList", list);
		model.addAttribute(StringLiteral.KEY_ACTIVE_PAGE, StringLiteral.MENU_MANAGE_NEWS);
		return "productNews";
	}

	@RequestMapping("/productTypeList")
	public String productTypeList(Model model, @LoginUser LoginUserInfo loginUserInfo) {
		model = binderService.bindUserDetails(model, loginUserInfo);
		List<ProductType> list = productTypeRepository.findAll();
		model.addAttribute("productTypeList", list);
		model.addAttribute(StringLiteral.KEY_ACTIVE_PAGE, StringLiteral.MENU_MANAGE_PRODUCT_TYPE);
		return "productTypeList";
	}

	@RequestMapping("/bannerList")
	public String bannerList(Model model, @LoginUser LoginUserInfo loginUserInfo) {
		model = binderService.bindUserDetails(model,loginUserInfo);
		List<Banner> list = bannerService.findAll();
		model.addAttribute("bannerList", list);
		model.addAttribute(StringLiteral.KEY_ACTIVE_PAGE, StringLiteral.MENU_MANAGE_BANNER);
		return "bannerList";
	}

	/***
	 * addBanner method help to open UI for adding new banner
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/addBanner")
	public String addBanner(Model model, @LoginUser LoginUserInfo loginUserInfo) {
		model = binderService.bindUserDetails(model, loginUserInfo);
		model.addAttribute(StringLiteral.KEY_ACTIVE_PAGE, StringLiteral.MENU_MANAGE_BANNER);
		return "addBanner";
	}

	/***
	 * addProduct method help to open UI for adding new product
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/editBanner/{bannerId}")
	public String editBanner(Model model, @PathVariable("bannerId") Integer bannerId, @LoginUser LoginUserInfo loginUserInfo) {
		model = binderService.bindUserDetails(model, loginUserInfo);
		Banner banner = bannerService.findById(bannerId);
		model.addAttribute("banner", banner);
		model.addAttribute(StringLiteral.KEY_ACTIVE_PAGE, StringLiteral.MENU_MANAGE_BANNER);
		return "addBanner";
	}

	/***
	 * saveProduct method help to Create/Update Product
	 * 
	 * @param model
	 * @param productDTO
	 * @return
	 */

	@PostMapping("/saveBanner")
	public String saveBanner(Model model, BannerDTO bannerDTO, @LoginUser LoginUserInfo loginUserInfo) {
		return bannerService.saveBanner(model, bannerDTO, loginUserInfo);
	}

	@RequestMapping("/enableBanner/{bannerId}")
	public String enableBanner(Model model, @PathVariable("bannerId") Integer bannerId) {
		bannerService.enableBanner(bannerId);
		return "redirect:/admin/bannerList";
	}

	@RequestMapping("/disableBanner/{bannerId}")
	public String disableBanner(Model model, @PathVariable("bannerId") Integer bannerId) {
		bannerService.disableBanner(bannerId);
		return "redirect:/admin/bannerList";
	}

	@RequestMapping("/deleteBanner/{bannerId}")
	public String deleteBanner(Model model, @PathVariable("bannerId") Integer bannerId) {
		bannerService.deleteBanner(bannerId);
		return "redirect:/admin/bannerList";
	}

	@RequestMapping("/addProductType")
	public String addProductType(Model model, @LoginUser LoginUserInfo loginUserInfo) {
		model = binderService.bindUserDetails(model,loginUserInfo);
		model.addAttribute(StringLiteral.KEY_ACTIVE_PAGE, StringLiteral.MENU_MANAGE_PRODUCT_TYPE);
		return "addProductType";
	}

	@RequestMapping("/addProductNews")
	public String addProductNews(Model model, @LoginUser LoginUserInfo loginUserInfo) {
		model = binderService.bindUserDetails(model,loginUserInfo);
		model.addAttribute(StringLiteral.KEY_ACTIVE_PAGE, StringLiteral.MENU_MANAGE_NEWS);
		return "addProductNews";
	}

	@RequestMapping("/news/edit/{newsId}")
	public String addProductNews(Model model, @PathVariable("newsId") Integer newsId, @LoginUser LoginUserInfo loginUserInfo) {
		model = binderService.bindUserDetails(model,loginUserInfo);
		model.addAttribute("newsItem", newsRepository.findById(newsId).get());
		model.addAttribute(StringLiteral.KEY_ACTIVE_PAGE, StringLiteral.MENU_MANAGE_NEWS);
		return "addProductNews";
	}

	@PostMapping("/saveProductNews")
	public String saveProductNews(Model model, ProductNewsDTO productNewsDTO, @LoginUser LoginUserInfo loginUserInfo) {

		boolean isValid = true;
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<ProductNewsDTO>> violations = validator.validate(productNewsDTO);
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
			if (productNewsDTO.getId() != null) {

				Optional<ProductNews> optional = newsRepository.findById(Integer.parseInt(productNewsDTO.getId()));
				if (optional != null) {
					productNews = optional.get();
				}
			}
			if (productNews == null) {
				productNews = new ProductNews();
			}

			productNews.setNews(productNewsDTO.getNews());
			newsRepository.save(productNews);

			return "redirect:/admin/productNews";
		}

		model = binderService.bindUserDetails(model, loginUserInfo);
		model.addAttribute(StringLiteral.KEY_ACTIVE_PAGE, StringLiteral.MENU_MANAGE_NEWS);
		return "addProductNews";
	}

	@PostMapping("/saveProductType")
	public String saveProductType(Model model, ProductTypeDTO productTypeDTO, @LoginUser LoginUserInfo loginUserInfo) {
		boolean isValid = true;
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<ProductTypeDTO>> violations = validator.validate(productTypeDTO);
		List<String> errorMessage = new ArrayList<>();
		for (ConstraintViolation<ProductTypeDTO> violation : violations) {
			logger.error(violation.getMessage());
			errorMessage.add(violation.getMessage());
			isValid = false;
		}

		if (!isValid) {
			model.addAttribute("updateError", errorMessage);
		} else {

			if (productTypeDTO.getId() == null && (productTypeRepository.findByName(productTypeDTO.getName()) != null
					|| menuRepository.findByMenuName(productTypeDTO.getName()) != null)) {
				errorMessage.add("Product Type or Menu with same existed, Please try another name");
				model.addAttribute("updateError", errorMessage);
			} else {
				ProductType productType = new ProductType();
				if (productTypeDTO.getId() != null) {
					Optional<ProductType> optional = productTypeRepository
							.findById(Integer.parseInt(productTypeDTO.getId()));
					if (optional != null) {
						productType = optional.get();
					}
				}

				productType.setName(productTypeDTO.getName());
				productType.setIsActive(1l);
				productType.setProductTypeMenuId(Long.parseLong(productTypeDTO.getProductTypeMenuId()));
				productType = productTypeRepository.save(productType);

				Menu menu = null;
				if (productTypeDTO.getMenuId() != null) {
					Optional<Menu> optional = menuRepository.findById(Long.parseLong(productTypeDTO.getMenuId()));
					if (optional != null) {
						menu = optional.get();
					}
				}

				if (menu == null) {
					menu = new Menu();
				}

				menu.setAdminMenu(productTypeDTO.getAdminMenu());
				menu.setRetailerMenu(productTypeDTO.getRetailerMenu());
				menu.setDistributorMenu(productTypeDTO.getDistributorMenu());
				menu.setSuperDistributorMenu(productTypeDTO.getSuperDistributorMenu());
				menu.setBusinessAssociateMenu(productTypeDTO.getBusinessAssociateMenu());
				menu.setIsActive(1);
				menu.setMenuName(productTypeDTO.getName());
				menu.setPageUrl("/service/product/" + productType.getId());

				// Menu parentMenu =
				// menuRepository.findByMenuName(StringLiteral.MENU_POS_TERMINAL);

				Menu parentMenu = null;
				Optional<Menu> optionalParentMenu = menuRepository
						.findById(Long.parseLong(productTypeDTO.getProductTypeMenuId()));
				if (optionalParentMenu != null) {
					parentMenu = optionalParentMenu.get();
				}

				menu.setParentMenuId(parentMenu.getMenuId());
				menu = menuRepository.save(menu);
				return "redirect:/admin/productTypeList";
			}
		}

		model = binderService.bindUserDetails(model, loginUserInfo);
		model.addAttribute(StringLiteral.KEY_ACTIVE_PAGE, StringLiteral.MENU_MANAGE_MENU);
		return "addProductType";
	}

	@RequestMapping("/editProductType/{productTypeId}")
	public String editProductType(Model model, @PathVariable("productTypeId") Integer productTypeId, @LoginUser LoginUserInfo loginUserInfo) {

		Optional<ProductType> productType = productTypeRepository.findById(productTypeId);

		if (productType != null) {
			model = binderService.bindUserDetails(model, loginUserInfo);
			ProductType type = productType.get();
			Menu menu = menuRepository.findByMenuName(type.getName());

			try {
				Menu parentMenu = null;

				Optional<Menu> parentMenuOption = menuRepository.findById(type.getProductTypeMenuId());
				if (parentMenuOption != null) {
					parentMenu = parentMenuOption.get();
				}

				model.addAttribute("PTM", parentMenu);
			} catch (Exception e) {
				// logger.error("Error", e);
			}

			model.addAttribute("menu", menu);
			model.addAttribute(StringLiteral.KEY_ACTIVE_PAGE, StringLiteral.MENU_MANAGE_PRODUCT_TYPE);
			model.addAttribute("productType", type);
			return "addProductType";
		}

		return "redirect:/admin/productTypeList";
	}

	@RequestMapping("/news/enable/{newsId}")
	public String enableNews(Model model, @PathVariable("newsId") Integer newsId) {
		Optional<ProductNews> optional = newsRepository.findById(newsId);
		if (optional != null) {
			ProductNews info = optional.get();
			info.setActive(1);
			newsRepository.save(info);
		}
		return "redirect:/admin/productNews";
	}

	@RequestMapping("/news/disable/{newsId}")
	public String disableNews(Model model, @PathVariable("newsId") Integer newsId) {
		Optional<ProductNews> optional = newsRepository.findById(newsId);
		if (optional != null) {
			ProductNews info = optional.get();
			info.setActive(0);
			newsRepository.save(info);
		}
		return "redirect:/admin/productNews";
	}

	@RequestMapping("/disableProductType/{productTypeId}")
	public String disableProductType(Model model, @PathVariable("productTypeId") Integer productTypeId) {
		Optional<ProductType> optional = productTypeRepository.findById(productTypeId);
		if (optional != null) {
			ProductType info = optional.get();
			info.setIsActive(0l);
			productTypeRepository.save(info);
		}
		return "redirect:/admin/productTypeList";
	}

	@RequestMapping("/deleteProductType/{productTypeId}")
	public String deleteProductType(Model model, @PathVariable("productTypeId") Integer productTypeId) {
		Optional<ProductType> optional = productTypeRepository.findById(productTypeId);
		if (optional != null) {
			ProductType info = optional.get();
			info.setIsActive(0l);
			productTypeRepository.delete(info);
		}
		return "redirect:/admin/productTypeList";
	}

	@RequestMapping("/enableProductType/{productTypeId}")
	public String enableProductType(Model model, @PathVariable("productTypeId") Integer productTypeId) {
		Optional<ProductType> optional = productTypeRepository.findById(productTypeId);
		if (optional != null) {
			ProductType info = optional.get();
			info.setIsActive(1l);
			productTypeRepository.save(info);
		}
		return "redirect:/admin/productTypeList";
	}

}
