package net.in.pSwitch.controller;

import net.in.pSwitch.authentication.LoginUser;
import net.in.pSwitch.authentication.LoginUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import net.in.pSwitch.repository.MenuRepository;
import net.in.pSwitch.repository.ProductRepository;
import net.in.pSwitch.repository.ProductTypeRepository;
import net.in.pSwitch.repository.RoleRepository;
import net.in.pSwitch.repository.UserInfoRepository;
import net.in.pSwitch.service.BinderService;
import net.in.pSwitch.service.FileStorageService;
import net.in.pSwitch.utility.StringLiteral;

@Controller
@RequestMapping("/retailer")
public class RetailerController {

	@Autowired
	RoleRepository roleRepository;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private MenuRepository menuRepository;
	@Autowired
	private BinderService binderService;
	@Autowired
	private UserInfoRepository userInfoRepository;
	@Autowired
	private ProductTypeRepository productTypeRepository;
	@Autowired
	private FileStorageService storageService;

	@RequestMapping("/associates")
	public String associates(Model model, @LoginUser LoginUserInfo loginUserInfo) {
		model = binderService.bindUserDetails(model,loginUserInfo);
		model.addAttribute(StringLiteral.KEY_ACTIVE_PAGE, StringLiteral.MENU_MANAGE_ASSOCIATES);
		return "associateList";
	}

}
