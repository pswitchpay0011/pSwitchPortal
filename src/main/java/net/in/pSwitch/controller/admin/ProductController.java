package net.in.pSwitch.controller.admin;

import java.io.IOException;
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

import net.in.pSwitch.model.FileDB;
import net.in.pSwitch.model.Product;
import net.in.pSwitch.model.ProductFlexiCommission;
import net.in.pSwitch.model.ProductNews;
import net.in.pSwitch.model.Status;
import net.in.pSwitch.dto.ProductDTO;
import net.in.pSwitch.repository.ProductFlexiCommissionRepository;
import net.in.pSwitch.repository.ProductNewsRepository;
import net.in.pSwitch.repository.ProductRepository;
import net.in.pSwitch.repository.ProductTypeRepository;
import net.in.pSwitch.service.BinderService;
import net.in.pSwitch.service.FileStorageService;
import net.in.pSwitch.utility.StringLiteral;

@Controller
@RequestMapping("/admin/product")
public class ProductController {
	Logger logger = LoggerFactory.getLogger(ProductController.class);

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductTypeRepository productTypeRepository;
	@Autowired
	private ProductFlexiCommissionRepository pFCRepository;
	@Autowired
	private FileStorageService storageService;
	@Autowired
	private ProductNewsRepository newsRepository;
	@Autowired
	private BinderService binderService;

	@RequestMapping("/")
	public String productList(Model model, @LoginUser LoginUserInfo loginUserInfo) {
		model = binderService.bindUserDetails(model, loginUserInfo);
		List<Product> list = productRepository.findAll();
		model.addAttribute("productList", list);
		model.addAttribute(StringLiteral.KEY_ACTIVE_PAGE, StringLiteral.MENU_MANAGE_PRODUCT);
		return "product/productList";
	}

	/***
	 * addProduct method help to open UI for adding new product
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/add")
	public String addProduct(Model model, @LoginUser LoginUserInfo loginUserInfo) {
		model = binderService.bindUserDetails(model, loginUserInfo);
		model.addAttribute("productType", productTypeRepository.findAll());
		model.addAttribute(StringLiteral.KEY_ACTIVE_PAGE, StringLiteral.MENU_MANAGE_PRODUCT);
		return "add-product";
	}

	/***
	 * addProduct method help to open UI for adding new product
	 * 
	 * @param model
	 * @return
	 */
	@RequestMapping("/edit/{productId}")
	public String editProduct(Model model, @PathVariable("productId") Integer productId, @LoginUser LoginUserInfo loginUserInfo) {
		model = binderService.bindUserDetails(model, loginUserInfo);
		Optional<Product> optional = productRepository.findById(productId);
		model.addAttribute("product", optional.get());
		model.addAttribute("productType", productTypeRepository.findAll());
		model.addAttribute(StringLiteral.KEY_ACTIVE_PAGE, StringLiteral.MENU_MANAGE_PRODUCT);
		return "add-product";
	}

	/***
	 * saveProduct method help to Create/Update Product
	 * 
	 * @param model
	 * @param productDTO
	 * @return
	 */

	@PostMapping("/save")
	public String saveProduct(Model model, ProductDTO productDTO, @LoginUser LoginUserInfo loginUserInfo) {

		boolean isValid = true;
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<ProductDTO>> violations = validator.validate(productDTO);
		List<String> errorMessage = new ArrayList<>();
		for (ConstraintViolation<ProductDTO> violation : violations) {
			logger.error(violation.getMessage());
			errorMessage.add(violation.getMessage());
			isValid = false;
		}

		if (isValid) {
			FileDB brochureFile = null;
			if (productDTO.getBrochureFile() != null && productDTO.getBrochureFile().getOriginalFilename() != null
					&& !"".equals(productDTO.getBrochureFile().getOriginalFilename())) {
				try {
					brochureFile = storageService.store(productDTO.getBrochureFile());
					if (brochureFile != null) {
						logger.info("Uploaded the file successfully: " + brochureFile.getName());
					} else {
						logger.error("Could not upload the file: " + productDTO.getBrochureFile().getOriginalFilename()
								+ "!");
					}
				} catch (IOException e) {
					logger.error("Error", e);
				}
			}

			/*
			 * FileDB commissionFile = null; if (productDTO.getCommissionFile() != null &&
			 * productDTO.getCommissionFile().getOriginalFilename() != null &&
			 * !"".equals(productDTO.getCommissionFile().getOriginalFilename())) { try {
			 * commissionFile = storageService.store(productDTO.getCommissionFile()); if
			 * (commissionFile != null) { logger.info("Uploaded the file successfully: " +
			 * commissionFile.getName()); } else {
			 * logger.error("Could not upload the file: " +
			 * productDTO.getCommissionFile().getOriginalFilename() + "!"); } } catch
			 * (IOException e) { logger.error("Error", e); } }
			 */

			FileDB productImageFile = null;
			if (productDTO.getProductImageFile() != null
					&& productDTO.getProductImageFile().getOriginalFilename() != null
					&& !"".equals(productDTO.getProductImageFile().getOriginalFilename())) {
				try {
					productImageFile = storageService.store(productDTO.getProductImageFile());
					if (productImageFile != null) {
						logger.info("Uploaded the file successfully: " + productImageFile.getName());
					} else {
						logger.error("Could not upload the file: "
								+ productDTO.getProductImageFile().getOriginalFilename() + "!");
					}
				} catch (IOException e) {
					logger.error("Error", e);
				}
			}

			Product product = null;
			if (productDTO.getId() != null) {
				Optional<Product> optional = productRepository.findById(Integer.parseInt(productDTO.getId()));
				if (optional != null) {
					product = optional.get();
				}
			}

			if (product == null) {
				product = new Product();
			}

			if (productDTO.getBrochureFileId() == null && brochureFile == null) {
				errorMessage.add("Brochure File cannot be blank");
			} else if (productDTO.getProductImageFileId() == null && productImageFile == null) {
				errorMessage.add("Product File cannot be blank");
			} else {

				product.setProductName(productDTO.getProductName());
				product.setProductDescription(productDTO.getProductDescription());
				product.setProductType(productTypeRepository.findById(productDTO.getProductType()).get());
				product.setProductImageFile(
						productImageFile != null ? productImageFile.getId() : productDTO.getProductImageFileId());
				product.setBrochureFile(brochureFile != null ? brochureFile.getId() : productDTO.getBrochureFileId());

//			product.setCommissionFile(commissionFile.getId());
				product.setAmount(productDTO.getAmount());
				product.setMRP(productDTO.getMRP());
				product.setAdminMenu(productDTO.getAdminMenu());
				product.setRetailerMenu(productDTO.getRetailerMenu());
				product.setDistributorMenu(productDTO.getDistributorMenu());
				product.setSuperDistributorMenu(productDTO.getSuperDistributorMenu());
				product.setBusinessAssociateMenu(productDTO.getBusinessAssociateMenu());

				productRepository.save(product);

				if (productDTO.getId() == null) {
					ProductNews news = new ProductNews();
					news.setNews("New Product is added into " + product.getProductType().getName() + " with name: "
							+ product.getProductName());
					news.setActive(1);
					newsRepository.save(news);
				}

				return "redirect:/admin/product/";
			}
		}
		model.addAttribute("updateError", errorMessage);
		model.addAttribute("productType", productTypeRepository.findAll());
		model = binderService.bindUserDetails(model,loginUserInfo);
		return "add-product";
	}

	@RequestMapping("/enable/{productId}")
	public String enableProduct(Model model, @PathVariable("productId") Integer productId) {
		Optional<Product> optional = productRepository.findById(productId);
		if (optional != null) {
			Product info = optional.get();
			info.setStatus(Status.ACTIVE);
			productRepository.save(info);
		}
		return "redirect:/admin/product/";
	}

	@RequestMapping("/delete/{productId}")
	public String deleteProduct(Model model, @PathVariable("productId") Integer productId) {
		Optional<Product> optional = productRepository.findById(productId);
		if (optional != null) {
			Product info = optional.get();

			List<ProductFlexiCommission> list = pFCRepository.findAllByProduct(info);
			if (list != null && !list.isEmpty()) {
				list.forEach(commision -> {
					commision.setProduct(null);
					commision.setUser(null);
					commision = pFCRepository.save(commision);
					pFCRepository.delete(commision);
				});
			}

			productRepository.delete(info);

		}
		return "redirect:/admin/product/";
	}

	@RequestMapping("/disable/{productId}")
	public String disableProduct(Model model, @PathVariable("productId") Integer productId) {
		Optional<Product> optional = productRepository.findById(productId);
		if (optional != null) {
			Product info = optional.get();
			info.setStatus(Status.DISABLED);
			productRepository.save(info);
		}
		return "redirect:/admin/product/";
	}

}
