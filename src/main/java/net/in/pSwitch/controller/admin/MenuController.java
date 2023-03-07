package net.in.pSwitch.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.in.pSwitch.model.Menu;
import net.in.pSwitch.dto.MenuDTO;
import net.in.pSwitch.repository.MenuRepository;
import net.in.pSwitch.service.BinderService;
import net.in.pSwitch.utility.StringLiteral;

@Controller
@RequestMapping("/admin/menu")
public class MenuController {

	Logger logger = LoggerFactory.getLogger(MenuController.class);

	@Autowired
	private MenuRepository menuRepository;
	@Autowired
	private BinderService binderService;

	@RequestMapping("/")
	public String pages(Model model, @LoginUser LoginUserInfo loginUserInfo) {
		model = binderService.bindUserDetails(model,loginUserInfo);
		List<Menu> list = menuRepository.findAll();
		model.addAttribute("menus", list);
		model.addAttribute(StringLiteral.KEY_ACTIVE_PAGE, StringLiteral.MENU_MANAGE_MENU);
		return "menu/menuList";
	}

	@RequestMapping(value = "/data", produces = "application/json")
	@ResponseBody
	public String menuList(Model model) {
		List<Menu> menuList = menuRepository.findAllByParentMenuIdAndIsSearchableOrderByMenuIdAsc(null, 1);
		List<Map<String, Object>> data = getMenu(menuList);
		String json = null;
		try {
			json = new ObjectMapper().writeValueAsString(data);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return json;
	}

	@RequestMapping("/add")
	public String addMenu(Model model, @LoginUser LoginUserInfo loginUserInfo ) {
		model = binderService.bindUserDetails(model, loginUserInfo);
		List<Menu> list = menuRepository.findAllOrderByMenuName();
		model.addAttribute("parentMenu", list);
		model.addAttribute(StringLiteral.KEY_ACTIVE_PAGE, StringLiteral.MENU_MANAGE_MENU);
		return "menu/add-menu";
	}

	@RequestMapping("/edit/{menuId}")
	public String editMenu(Model model, @PathVariable("menuId") Long menuId, @LoginUser LoginUserInfo loginUserInfo) {

		Optional<Menu> menu = menuRepository.findById(menuId);

		if (menu != null) {
			model = binderService.bindUserDetails(model,loginUserInfo);
			List<Menu> list = menuRepository.findAllOrderByMenuName();
			model.addAttribute("parentMenu", list);
			model.addAttribute(StringLiteral.KEY_ACTIVE_PAGE, StringLiteral.MENU_MANAGE_MENU);
			model.addAttribute("menu", menu.get());
			return "menu/add-menu";
		}

		return "redirect:/admin/menu/";
	}

	@PostMapping("/save")
	public String saveMenu(Model model, MenuDTO menuDTO, @LoginUser LoginUserInfo loginUserInfo) {
		boolean isValid = true;
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<MenuDTO>> violations = validator.validate(menuDTO);
		List<String> errorMessage = new ArrayList<>();
		for (ConstraintViolation<MenuDTO> violation : violations) {
			logger.error(violation.getMessage());
			errorMessage.add(violation.getMessage());
			isValid = false;
		}

		if (!isValid) {
			model.addAttribute("updateError", errorMessage);
		} else {
			if (menuDTO.getMenuId() == null && menuRepository.findByMenuName(menuDTO.getMenuName()) != null) {
				errorMessage.add("Duplicate Menu found, Please try some other menu name");
				model.addAttribute("updateError", errorMessage);
			} else {
				Menu menu = new Menu();
				if (menuDTO.getMenuId() != null) {
					Optional<Menu> optional = menuRepository.findById(Long.parseLong(menuDTO.getMenuId()));
					if (optional != null) {
						menu = optional.get();
					}
				}

				menu.setAdminMenu(menuDTO.getAdminMenu());
				menu.setDistributorMenu(menuDTO.getDistributorMenu());
				menu.setBusinessAssociateMenu(menuDTO.getBusinessAssociateMenu());
				menu.setIsActive(1);
				menu.setMenuName(menuDTO.getMenuName());
				menu.setPageUrl(menuDTO.getPageUrl());
				menu.setRetailerMenu(menuDTO.getRetailerMenu());
				menu.setMenuIcon(menuDTO.getMenuIcon());
				menu.setMenuType(menuDTO.getMenuType());
				if (menuDTO.getParentMenuId() != null && menuDTO.getParentMenuId() != 0l)
					menu.setParentMenuId(menuDTO.getParentMenuId());
				else
					menu.setParentMenuId(null);
				menu.setSuperDistributorMenu(menuDTO.getSuperDistributorMenu());
				menu.setIsSearchable(menuDTO.getIsSearchable() != null ? menuDTO.getIsSearchable() : 0);
				menuRepository.save(menu);
				return "redirect:/admin/menu/";
			}
		}

		model = binderService.bindUserDetails(model,loginUserInfo);
		model.addAttribute(StringLiteral.KEY_ACTIVE_PAGE, StringLiteral.MENU_MANAGE_MENU);
		return "menu/add-menu";
	}

	@RequestMapping("/enable/{menuId}")
	public String enableMenu(Model model, @PathVariable("menuId") Long menuId) {
		Optional<Menu> optional = menuRepository.findById(menuId);
		if (optional != null) {
			Menu info = optional.get();
			info.setIsActive(1);
			menuRepository.save(info);
		}
		return "redirect:/admin/menu/";
	}

	@RequestMapping("/disble/{menuId}")
	public String disbleMenu(Model model, @PathVariable("menuId") Long menuId) {
		Optional<Menu> optional = menuRepository.findById(menuId);
		if (optional != null) {
			Menu info = optional.get();
			info.setIsActive(0);
			menuRepository.save(info);
		}
		return "redirect:/admin/menu/";
	}

	@RequestMapping("/delete/{menuId}")
	public String deleteMenu(Model model, @PathVariable("menuId") Long menuId) {
		Optional<Menu> optional = menuRepository.findById(menuId);
		if (optional != null) {
			Menu info = optional.get();
			info.setIsActive(0);
			menuRepository.delete(info);
		}
		return "redirect:/admin/menu/";
	}

	private List getMenu(List<Menu> menuList) {

		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

		menuList.forEach(menu -> {
			Map map = new HashMap<>();
			map.put("id", menu.getMenuId());
			map.put("text", menu.getMenuName());

			if (menu.getMenuName().equalsIgnoreCase("Dashboard")) {
				map.put("selected", true);
			}

			List<Map<String, Object>> children = new ArrayList<Map<String, Object>>();
			if (menu.getMenus() != null && menu.getMenus().size() > 0) {

				map.put("text", " Menu : " + menu.getMenuName());

				Map childMenu = new HashMap<>();
				childMenu.put("id", menu.getMenuId());
				childMenu.put("text", menu.getMenuName());
				children.add(childMenu);
			}

			if (menu.getMenus() != null && !menu.getMenus().isEmpty()) {
				children.addAll(getMenu(menu.getMenus()));
				map.put("children", children);
			}

			data.add(map);
		});

		return data;
	}
}
