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

import net.in.pSwitch.model.Product;
import net.in.pSwitch.model.ProductFlexiCommission;
import net.in.pSwitch.model.user.UserInfo;
import net.in.pSwitch.dto.ProductFlexiCommissionDTO;
import net.in.pSwitch.repository.ProductFlexiCommissionRepository;
import net.in.pSwitch.repository.ProductRepository;
import net.in.pSwitch.repository.UserInfoRepository;
import net.in.pSwitch.service.BinderService;
import net.in.pSwitch.utility.StringLiteral;

@Controller
@RequestMapping("/admin/product/flexiCommission")
public class ProductFlexiCommissionController {
	Logger logger = LoggerFactory.getLogger(ProductFlexiCommissionController.class);

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductFlexiCommissionRepository pFCRepository;
	@Autowired
	private BinderService binderService;
	@Autowired
	private UserInfoRepository userInfoRepository;

	/*
	 * @RequestMapping("/") public String productList(Model model) { model =
	 * binderService.bindUserDetails(model); List<Product> list =
	 * productRepository.findAll(); model.addAttribute("productList", list);
	 * model.addAttribute(StringLiteral.KEY_ACTIVE_PAGE,
	 * StringLiteral.MENU_MANAGE_PRODUCT); return "product/productList"; }
	 */

	@RequestMapping("/{productId}")
	public String flexiCommission(Model model, @PathVariable("productId") Integer productId, @LoginUser LoginUserInfo loginUserInfo) {
		model = binderService.bindUserDetails(model, loginUserInfo);
		Optional<Product> optional = productRepository.findById(productId);
		Product product = null;
		if (optional != null && optional.isPresent()) {
			product = optional.get();
		}

		if (product != null) {
			model.addAttribute("product", product);
			List<ProductFlexiCommission> list = pFCRepository.findAllByProduct(product);
			model.addAttribute("commissionList", list);
			model.addAttribute(StringLiteral.KEY_ACTIVE_PAGE, StringLiteral.MENU_MANAGE_PRODUCT);
		}

		return "product/productFlexiCommission";
	}

	/***
	 * addProduct method help to open UI for adding new product
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/add/{productId}")
	public String addProduct(Model model, @PathVariable("productId") Integer productId, @LoginUser LoginUserInfo loginUserInfo) {
		model = binderService.bindUserDetails(model, loginUserInfo);

		Optional<Product> optional = productRepository.findById(productId);
		Product product = null;
		if (optional != null && optional.isPresent()) {
			product = optional.get();
		}
		model.addAttribute("product", product);
		return "product/addProductFlexiCommission";
	}

	/***
	 * addProduct method help to open UI for adding new product
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit/{flexiCommissionId}")
	public String editProduct(Model model, @PathVariable("flexiCommissionId") Integer flexiCommissionId, @LoginUser LoginUserInfo loginUserInfo) {
		model = binderService.bindUserDetails(model, loginUserInfo);
		Optional<ProductFlexiCommission> optional = pFCRepository.findById(flexiCommissionId);
		ProductFlexiCommission commission = null;

		if (optional != null && optional.isPresent()) {
			commission = optional.get();
			model.addAttribute("product", commission.getProduct());
			model.addAttribute("commission", commission);
		}

		model.addAttribute(StringLiteral.KEY_ACTIVE_PAGE, StringLiteral.MENU_MANAGE_PRODUCT);
		return "product/addProductFlexiCommission";
	}

	/***
	 * saveProduct method help to Create/Update Product
	 * 
	 * @param model
	 * @param commissionDTO
	 * @return
	 */

	@PostMapping("/save")
	public String saveProduct(Model model, ProductFlexiCommissionDTO commissionDTO, @LoginUser LoginUserInfo loginUserInfo) {

		boolean isValid = true;
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<ProductFlexiCommissionDTO>> violations = validator.validate(commissionDTO);
		List<String> errorMessage = new ArrayList<>();
		for (ConstraintViolation<ProductFlexiCommissionDTO> violation : violations) {
			logger.error(violation.getMessage());
			errorMessage.add(violation.getMessage());
			isValid = false;
		}

		Product product = null;
		if (commissionDTO.getProductId() != null) {
			Optional<Product> optional = productRepository.findById(Integer.parseInt(commissionDTO.getProductId()));
			if (optional != null && optional.isPresent()) {
				product = optional.get();
			}
		}

		ProductFlexiCommission flexiCommission = null;

		if (commissionDTO.getId() != null) {
			Optional<ProductFlexiCommission> optional = pFCRepository.findById(Integer.parseInt(commissionDTO.getId()));
			if (optional != null && optional.isPresent()) {
				flexiCommission = optional.get();
			}
		}
		model.addAttribute("commission", flexiCommission);

		if (isValid) {

			UserInfo userInfo = null;
			if (commissionDTO.getUserPSwitchId() != null) {
				userInfo = userInfoRepository.findByUserPSwitchId(commissionDTO.getUserPSwitchId());
			}

			if (flexiCommission == null) {
				flexiCommission = new ProductFlexiCommission();
			}

			ProductFlexiCommission result = pFCRepository.findAllByProductAndUser(product, userInfo);

			if (result != null && commissionDTO.getId() == null) {
				errorMessage.add("Flexi Commission for Product: " + product.getProductName() + " and User: "
						+ userInfo.getUserPSwitchId() + " is already exist.");
			} else if (product == null) {
				errorMessage.add("Invalid Product, Please select valid Product");
			} else if (userInfo == null) {
				errorMessage.add("Invalid User, Please Enter valid user id");
			} else {

				flexiCommission.setAmount(commissionDTO.getAmount());
				flexiCommission.setProduct(product);
				flexiCommission.setUser(userInfo);
				flexiCommission.setIsActive(1l);
				pFCRepository.save(flexiCommission);

				return "redirect:/admin/product/flexiCommission/" + product.getId();
			}
		}
		model.addAttribute("product", product);
		model.addAttribute("updateError", errorMessage);
		model = binderService.bindUserDetails(model, loginUserInfo);
		return "product/addProductFlexiCommission";
	}

	@RequestMapping("/enable/{productId}")
	public String enableProduct(Model model, @PathVariable("productId") Integer productId) {
		Optional<ProductFlexiCommission> optional = pFCRepository.findById(productId);
		ProductFlexiCommission info = null;
		if (optional != null) {
			info = optional.get();
			info.setIsActive(1l);
			pFCRepository.save(info);
		}
		return "redirect:/admin/product/flexiCommission/" + info.getProduct().getId();
	}

	@RequestMapping("/delete/{productId}")
	public String deleteProduct(Model model, @PathVariable("productId") Integer productId) {
		Optional<ProductFlexiCommission> optional = pFCRepository.findById(productId);
		Integer pID = null;
		ProductFlexiCommission info = null;
		if (optional != null) {
			info = optional.get();
			pID = info.getProduct().getId();
			info.setUser(null);
			info.setProduct(null);
			info = pFCRepository.save(info);
			pFCRepository.delete(info);
		}
		return "redirect:/admin/product/flexiCommission/" + pID;
	}

	@RequestMapping("/disable/{productId}")
	public String disableProduct(Model model, @PathVariable("productId") Integer productId) {
		Optional<ProductFlexiCommission> optional = pFCRepository.findById(productId);
		ProductFlexiCommission info = null;
		if (optional != null) {
			info = optional.get();
			info.setIsActive(0l);
			pFCRepository.save(info);
		}
		return "redirect:/admin/product/flexiCommission/" + info.getProduct().getId();
	}

}
