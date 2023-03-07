package net.in.pSwitch.controller.admin;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.in.pSwitch.authentication.LoginUser;
import net.in.pSwitch.authentication.LoginUserInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.in.pSwitch.model.UserInfo;
import net.in.pSwitch.repository.RoleRepository;
import net.in.pSwitch.repository.UserInfoRepository;
import net.in.pSwitch.service.BinderService;
import net.in.pSwitch.service.UserInfoService;
import net.in.pSwitch.utility.StringLiteral;
import net.in.pSwitch.utility.Utility;

@Controller
@RequestMapping("/admin/user")
public class UserController {

	Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserInfoService userInfoService;
	@Autowired
	private BinderService binderService;
	@Autowired
	private UserInfoRepository userInfoRepository;
	@Autowired
	RoleRepository roleRepository;

	@RequestMapping(value = "/data", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public String getDataForDatatable(@RequestParam Map<String, Object> params) {
		return userInfoService.getDataForDatatable(params);
	}

	@RequestMapping(value = "/userIdData", produces = "application/json")
	@ResponseBody
	public String menuList(Model model, String _type, String q) {

		Specification<UserInfo> specification = new Specification<UserInfo>() {

			@Override
			public Predicate toPredicate(Root<UserInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

//				ArrayList<Predicate> orPredicates = new ArrayList<>();
//
//				ArrayList<Predicate> predicates = new ArrayList<>();
//
//				if (q != null && q != "") {
//					orPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), "%" + q + "%"));
//					orPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), "%" + q + "%"));
//					orPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("address")), "%" + q + "%"));
//					orPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("username")), "%" + q + "%"));
//					orPredicates
//							.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("mobileNumber")), "%" + q + "%"));
//					orPredicates
//							.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("userPSwitchId")), "%" + q + "%"));
//
//					predicates.add(criteriaBuilder.or(orPredicates.toArray(new Predicate[orPredicates.size()])));
//				}
//
////				predicates.add(criteriaBuilder.notEqual(root.get("roles"),
////						roleRepository.findByRoleCode(StringLiteral.ROLE_CODE_ADMIN)));
//
//				return (!predicates.isEmpty()
//						? criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]))
//						: null);

				return criteriaBuilder.like(criteriaBuilder.lower(root.<String>get("userPSwitchId")), "'%" + q + "%'");

				/*
				 * ArrayList<Predicate> orPredicates = new ArrayList<>();
				 * 
				 * if (q != null && q != "") {
				 * orPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(
				 * "firstName")), '%' + q + '%'));
				 * orPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(
				 * "lastName")), '%' + q + '%'));
				 * orPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get(
				 * "username")), '%' + q + '%')); orPredicates
				 * .add(criteriaBuilder.like(criteriaBuilder.lower(root.get("mobileNumber")),
				 * '%' + q + '%')); orPredicates
				 * .add(criteriaBuilder.like(criteriaBuilder.lower(root.get("userPSwitchId")),
				 * '%' + q + '%')); }
				 * 
				 * return (!orPredicates.isEmpty() ?
				 * criteriaBuilder.and(orPredicates.toArray(new Predicate[orPredicates.size()]))
				 * : null);
				 */
			}
		};

		List<UserInfo> userList = userInfoRepository.search(q == null ? "" : q);
		List<Map<String, Object>> data = new ArrayList<>();
		userList.forEach(user -> {
			Map<String, Object> item = new HashMap<>();
			item.put("id", user.getUserPSwitchId());
			item.put("text", user.getFullName()
					+ (user.getUserPSwitchId() != null ? " (" + user.getUserPSwitchId() + ")" : " (NA)"));
			data.add(item);
		});

		String json = null;
		try {
			json = new ObjectMapper().writeValueAsString(data);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return json;
	}

	@RequestMapping("/")
	public String userList(Model model, @LoginUser LoginUserInfo loginUserInfo) {
		model = binderService.bindUserDetails(model, loginUserInfo);
		model.addAttribute(StringLiteral.KEY_ACTIVE_PAGE, StringLiteral.MENU_MANAGE_USER);
		return "admin/newUserList"; 
	}
	
	@RequestMapping("/{role}")
	public String userList(Model model, @PathVariable("role") String role, @LoginUser LoginUserInfo loginUserInfo) {
		model = binderService.bindUserDetails(model,loginUserInfo);

		model.addAttribute("searchRole", role);
		model.addAttribute(StringLiteral.KEY_ACTIVE_PAGE, StringLiteral.MENU_MANAGE_USER);
		return "newUserList";
	}

	@RequestMapping("/changeManager/{userId}")
	public String changeManager(Model model, @PathVariable("userId") Integer userId, @LoginUser LoginUserInfo loginUserInfo) {
		model = binderService.bindUserDetails(model,loginUserInfo);

		Optional<UserInfo> optional = userInfoRepository.findById(userId);
		UserInfo userInfo = null;
		if (optional != null) {
			userInfo = optional.get();
			List<UserInfo> list = userInfoRepository.findByRolesAndState(
					roleRepository.findByRoleCode(Utility.getParentRole(userInfo.getRoles().getRoleCode())),
					userInfo.getState());
			model.addAttribute("userList", list);
			model.addAttribute("userInfo", userInfo);
		}

		model.addAttribute(StringLiteral.KEY_ACTIVE_PAGE, StringLiteral.MENU_MANAGE_USER);
		return "changeManager";
	}

	@RequestMapping("/updateManager")
	public String changeManager(Model model, String id, String managerId) {
		// model = binderService.bindUserDetails(model);

		Optional<UserInfo> userOptional = userInfoRepository.findById(Integer.parseInt(id));
		Optional<UserInfo> managerOptional = userInfoRepository.findById(Integer.parseInt(managerId));
		UserInfo userInfo = null;
		UserInfo managerInfo = null;
		if (userOptional != null) {
			userInfo = userOptional.get();
		}
		if (managerOptional != null) {
			managerInfo = managerOptional.get();
		}

		userInfo.setParent(managerInfo);
		userInfoRepository.save(userInfo);
		return "redirect:/admin/user/";
	}

	@GetMapping("/enable/{userId}")
	public String enableUser(Model model, @PathVariable("userId") Integer userId) {
		Optional<UserInfo> optional = userInfoRepository.findById(userId);
		if (optional != null) {
			UserInfo info = optional.get();
			info.setIsActive(1l);
			userInfoRepository.save(info);
		}
		return "redirect:/admin/user/";
	}

	@GetMapping("/disable/{userId}")
	public String disableUser(Model model, @PathVariable("userId") Integer userId) {
		Optional<UserInfo> optional = userInfoRepository.findById(userId);
		if (optional != null) {
			UserInfo info = optional.get();
			info.setIsActive(0l);
			// info.setIsVerified(0);
			userInfoRepository.save(info);
		}
		return "redirect:/admin/user/";
	}

	@GetMapping("/verified/{userId}")
	public String verifiedUser(Model model, @PathVariable("userId") Integer userId) {
		Optional<UserInfo> optional = userInfoRepository.findById(userId);
		if (optional != null) {
			UserInfo info = optional.get();
			info.setIsVerified(1l);
			userInfoRepository.save(info);
		}
		return "redirect:/admin/user/";
	}

	@GetMapping("/reject/{userId}")
	public String notVerifiedUser(Model model, @PathVariable("userId") Integer userId) {
		Optional<UserInfo> optional = userInfoRepository.findById(userId);
		if (optional != null) {
			UserInfo info = optional.get();
			info.setIsVerified(2l);
			userInfoRepository.save(info);
		}
		return "redirect:/admin/user/";
	}
}
