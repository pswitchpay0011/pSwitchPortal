package net.in.pSwitch.dao;

import java.util.ArrayList;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import net.in.pSwitch.model.Role;
import net.in.pSwitch.model.UserInfo;

public class UserInfoSpecifications implements org.springframework.data.jpa.domain.Specification<UserInfo> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String userQuery;
	Role role;

	public UserInfoSpecifications(String queryString) {
		this.userQuery = ("" + queryString).toLowerCase();
	}

	public UserInfoSpecifications(Role role, String queryString) {
		this.role = role;
		this.userQuery = ("" + queryString).toLowerCase();
	}

	@Override
	public Predicate toPredicate(Root<UserInfo> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
		ArrayList<Predicate> orPredicates = new ArrayList<>();

		ArrayList<Predicate> predicates = new ArrayList<>();

		if (userQuery != null && userQuery != "") {
			orPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("firstName")), '%' + userQuery + '%'));
			orPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("lastName")), '%' + userQuery + '%'));
			orPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("address")), '%' + userQuery + '%'));
			orPredicates.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("username")), '%' + userQuery + '%'));
			orPredicates
					.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("mobileNumber")), '%' + userQuery + '%'));
			orPredicates
					.add(criteriaBuilder.like(criteriaBuilder.lower(root.get("userPSwitchId")), '%' + userQuery + '%'));
			
			predicates.add(criteriaBuilder.or(orPredicates.toArray(new Predicate[orPredicates.size()])));
		}

		if (role != null) {
			predicates.add(criteriaBuilder.equal(root.get("roles"), role));
		}

		return (!predicates.isEmpty() ? criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]))
				: null);
	}
}