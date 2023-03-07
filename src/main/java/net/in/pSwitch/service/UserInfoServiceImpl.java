package net.in.pSwitch.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.in.pSwitch.controller.ApplicationController;
import net.in.pSwitch.dao.UserInfoSpecifications;
import net.in.pSwitch.model.Role;
import net.in.pSwitch.model.UserInfo;
import net.in.pSwitch.repository.RoleRepository;
import net.in.pSwitch.repository.UserInfoRepository;
import net.in.pSwitch.utility.StringLiteral;
import net.in.pSwitch.utility.Utility;

@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

	Logger logger = LoggerFactory.getLogger(ApplicationController.class);

	@Autowired
	private UserInfoRepository userInfoRepository;
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public Page<UserInfo> findAll(String queryString, Pageable pageable) {

		UserInfoSpecifications specifications = new UserInfoSpecifications(queryString);

		return userInfoRepository.findAll(specifications, pageable);
	}

	@Override
	public Page<UserInfo> findAllByRole(Role role, String queryString, Pageable pageable) {
		UserInfoSpecifications specifications = new UserInfoSpecifications(role, queryString);
		return userInfoRepository.findAll(specifications, pageable);
	}

	@Override
	public String getDataForDatatable(Map<String, Object> params) {
		int draw = params.containsKey("draw") ? Integer.parseInt(params.get("draw").toString()) : 1;
		int length = params.containsKey("length") ? Integer.parseInt(params.get("length").toString()) : 30;
		int start = params.containsKey("start") ? Integer.parseInt(params.get("start").toString()) : 30;
		int currentPage = start / length;

		String sortName = "userId";
		String dataTableOrderColumnIdx = params.get("order[0][column]").toString();
		String dataTableOrderColumnName = "columns[" + dataTableOrderColumnIdx + "][data]";
		if (params.containsKey(dataTableOrderColumnName))
			sortName = params.get(dataTableOrderColumnName).toString();
		String sortDir = params.containsKey("order[0][dir]") ? params.get("order[0][dir]").toString() : "asc";

		Sort.Order sortOrder = new Sort.Order((sortDir.equals("desc") ? Sort.Direction.DESC : Sort.Direction.ASC),
				sortName);
		Sort sort = Sort.by(sortOrder);

		Pageable pageRequest = PageRequest.of(currentPage, length, sort);

		String queryString = (String) (params.get("search[value]"));
		String searchRole = (String) (params.get("searchRole"));

		Role role = null;
		if (searchRole != null) {
			role = roleRepository.findByRoleCode(searchRole);
		}
		Page<UserInfo> customers = null;
		if (role != null) {
			customers = findAllByRole(role, queryString, pageRequest);
		} else {
			customers = findAll(queryString, pageRequest);
		}

		long totalRecords = customers.getTotalElements();

		List<Map<String, Object>> cells = new ArrayList<>();
		customers.forEach(customer -> {

			if (!"Admin".equalsIgnoreCase(customer.getUserPSwitchId())) {
				Map<String, Object> cellData = new HashMap<>();
				cellData.put("userId", customer.getUserId());
				cellData.put("userPSwitchId", customer.getUserPSwitchId());
				cellData.put("firstName", (customer.getFirstName() + " " + customer.getLastName()).trim());
				cellData.put("roles", customer.getRoles().getRoleName());
				cellData.put("createdDate", Utility.getFormatedDate(customer.getCreatedDate()));
				cellData.put("managerName",
						customer.getParent() != null
								? (customer.getParent().getFirstName() + " " + customer.getParent().getLastName())
										.trim()
								: "NA");
				cellData.put("city", customer.getCity());
				cellData.put("country", customer.getCountry());
				cellData.put("mobileNumber", customer.getMobileNumber());
				String status = "";
				if (customer.getIsActive() != null && 1l == customer.getIsActive()) {
					status = "<span class='badge badge-success'>Active</span>";
				} else {
					status = "<span class='badge badge-danger'>Disable</span>";
				}

				String verfication = "";

				if (customer.getAgreementAccept() != null && 1l == customer.getAgreementAccept()) {
					verfication = "<span class='badge badge-success'>Profile Completed</span>";
				} else {
					verfication = "<span class='badge badge-danger'>Profile In-Completed</span>";
				}

				if (customer.getIsVerified() != null && 1l == customer.getIsVerified()) {
					verfication += " <span class='badge badge-success'>Verified</span>";
				} else if (customer.getIsVerified() != null && 2l == customer.getIsVerified()) {
					verfication += " <span class='badge badge-danger'>Rejected</span>";
				} else {
					verfication += " <span class='badge badge-info'>Pending</span>";
				}

				String changeAction = "";
				if (StringLiteral.ROLE_CODE_RETAILER.equalsIgnoreCase(customer.getRoles().getRoleCode())
						|| StringLiteral.ROLE_CODE_DISTRIBUTOR.equalsIgnoreCase(customer.getRoles().getRoleCode())
						|| StringLiteral.ROLE_CODE_SUPER_DISTRIBUTOR
								.equalsIgnoreCase(customer.getRoles().getRoleCode())) {
					changeAction = "<a href='/admin/user/changeManager/" + customer.getUserId() + "'"
							+ " class='dropdown-item'><i class='fas fa-users-cog'></i>" + "Change Manager</a>";
				}

				String action = "<div class='list-icons'>" + "	<div class='dropdown'>"
						+ "		<a href='#' class='list-icons-item' data-toggle='dropdown'>"
						+ "			<i class='icon-menu9'></i>" + "		</a>"
						+ "		<div class='dropdown-menu dropdown-menu-right'>"
						+ "			<a href='/admin/user/enable/" + customer.getUserId() + "'"
						+ "				class='dropdown-item'><i class='icon-checkmark'></i>"
						+ "				Enabled</a> <a " + "href='/admin/user/disble/" + customer.getUserId() + "'"
						+ "				class='dropdown-item'><i class='icon-blocked'></i>"
						+ "				Disabled</a>" + "" + "			<div th:classappend='"
						+ ((customer.getAgreementAccept() == null || customer.getAgreementAccept() == 0) ? "'d-none'"
								: "''")
						+ "'>" + "				<a href='/admin/user/verified/" + customer.getUserId() + "'"
						+ "					class='dropdown-item'><i class='fas fa-user-check'></i>"
						+ "					Verified</a> <a " + "href='/admin/user/reject/" + customer.getUserId() + "'"
						+ "					class='dropdown-item'><i class='fas fa-user-times'></i>"
						+ "					Not-Verified</a> " + changeAction + "</div></div></div></div>";

				cellData.put("action", action);
				cellData.put("status", status);
				cellData.put("verfication", verfication);
				cells.add(cellData);
			}
		});

		Map<String, Object> jsonMap = new HashMap<>();

		jsonMap.put("draw", draw);
		jsonMap.put("recordsTotal", totalRecords);
		jsonMap.put("recordsFiltered", totalRecords);
		jsonMap.put("data", cells);

		String json = null;
		try {
			json = new ObjectMapper().writeValueAsString(jsonMap);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return json;
	}

	public void increaseFailedAttempts(UserInfo user) {
		Integer newFailAttempts = user.getFailedAttempt() + 1;
		userInfoRepository.updateFailedAttempts(newFailAttempts, user.getUserPSwitchId());
	}

	public void resetFailedAttempts(String userPSwitchId) {
		userInfoRepository.updateFailedAttempts(0, userPSwitchId);
	}

	public void lock(UserInfo user) {
		user.setAccountNonLocked(false);
		user.setLockTime(new Date());

		userInfoRepository.save(user);
	}

	public boolean unlockWhenTimeExpired(UserInfo user) {
		long lockTimeInMillis = user.getLockTime().getTime();
		long currentTimeInMillis = System.currentTimeMillis();

		if (lockTimeInMillis + LOCK_TIME_DURATION < currentTimeInMillis) {
			user.setAccountNonLocked(true);
			user.setLockTime(null);
			user.setFailedAttempt(0);

			userInfoRepository.save(user);

			return true;
		}

		return false;
	}

	@Override
	public UserInfo findByUserPSwitchId(String userPSwitchId) {
		return userInfoRepository.findByUserPSwitchId(userPSwitchId);
	}

}
