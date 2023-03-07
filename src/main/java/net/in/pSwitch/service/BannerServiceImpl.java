package net.in.pSwitch.service;

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
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import net.in.pSwitch.model.Banner;
import net.in.pSwitch.model.FileDB;
import net.in.pSwitch.model.Status;
import net.in.pSwitch.dto.BannerDTO;
import net.in.pSwitch.repository.BannerRepository;
import net.in.pSwitch.repository.RoleRepository;

@Service
public class BannerServiceImpl implements BannerService {

	Logger logger = LoggerFactory.getLogger(BannerService.class);

	@Autowired
	private BannerRepository bannerRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	private BinderService binderService;
	@Autowired
	private FileStorageService storageService;

	@Override
	public Banner findById(Integer bannerId) {
		Banner model = null;
		Optional<Banner> result = bannerRepository.findById(bannerId);
		if (result.isPresent()) {
			model = result.get();
		}
		return model;
	}

	@Override
	public Banner save(Banner banner) {
		banner = bannerRepository.save(banner);
		return banner;
	}

	@Override
	public Banner enableBanner(Integer bannerId) {
		Banner model = null;
		Optional<Banner> result = bannerRepository.findById(bannerId);
		if (result.isPresent()) {
			model = result.get();
			model.setStatus(Status.ACTIVE);
			bannerRepository.save(model);
		}
		return model;
	}

	@Override
	public Banner disableBanner(Integer bannerId) {
		Banner model = null;
		Optional<Banner> result = bannerRepository.findById(bannerId);
		if (result.isPresent()) {
			model = result.get();
			model.setStatus(Status.DISABLED);
			bannerRepository.save(model);
		}
		return model;
	}

	@Override
	public Banner deleteBanner(Integer bannerId) {
		Banner model = null;
		Optional<Banner> result = bannerRepository.findById(bannerId);
		if (result.isPresent()) {
			model = result.get();
			bannerRepository.delete(model);
		}
		return model;
	}

	@Override
	public String saveBanner(Model model, BannerDTO bannerDTO, @LoginUser LoginUserInfo loginUserInfo) {

		boolean isValid = true;
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validator = factory.getValidator();
		Set<ConstraintViolation<BannerDTO>> violations = validator.validate(bannerDTO);
		List<String> errorMessage = new ArrayList<>();
		for (ConstraintViolation<BannerDTO> violation : violations) {
			logger.error(violation.getMessage());
			errorMessage.add(violation.getMessage());
			isValid = false;
		}

		if (isValid) {
			FileDB bannerImage = null;
			if (bannerDTO.getBannerImage() != null && bannerDTO.getBannerImage().getOriginalFilename() != null
					&& !"".equals(bannerDTO.getBannerImage().getOriginalFilename())) {
				try {
					bannerImage = storageService.store(bannerDTO.getBannerImage());
					if (bannerImage != null) {
						logger.info("Uploaded the file successfully: " + bannerImage.getName());
					} else {
						logger.error(
								"Could not upload the file: " + bannerDTO.getBannerImage().getOriginalFilename() + "!");
					}
				} catch (IOException e) {
					logger.error("Error", e);
				}
			}

			Banner banner = null;
			if (bannerDTO.getId() != null) {
				Optional<Banner> optional = bannerRepository.findById(Integer.parseInt(bannerDTO.getId()));
				if (optional.isPresent()) {
					banner = optional.get();
				}
			}

			if (banner == null) {
				banner = new Banner();
			}

			banner.setBannerName(bannerDTO.getBannerName());
			banner.setBannerDescription(bannerDTO.getBannerDescription());
			banner.setBannerImage(bannerImage.getId());
			banner.setAdminMenu(bannerDTO.getAdminMenu());
			banner.setRetailerMenu(bannerDTO.getRetailerMenu());
			banner.setDistributorMenu(bannerDTO.getDistributorMenu());
			banner.setSuperDistributorMenu(bannerDTO.getSuperDistributorMenu());
			banner.setBusinessAssociateMenu(bannerDTO.getBusinessAssociateMenu());

			bannerRepository.save(banner);

			return "redirect:/admin/bannerList";
		} else {
			model.addAttribute("updateError", errorMessage);
		}

		model = binderService.bindUserDetails(model,loginUserInfo);
		return "addBanner";
	}

	@Override
	public List<Banner> findAll() {
		return bannerRepository.findAll();
	}

	@Override
	public List<Banner> findAllActive() {
		return bannerRepository.findAllByStatus(Status.ACTIVE);
	}
}
