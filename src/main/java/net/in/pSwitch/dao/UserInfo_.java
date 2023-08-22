package net.in.pSwitch.dao;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

import net.in.pSwitch.model.user.Role;
import net.in.pSwitch.model.user.UserInfo;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(UserInfo.class)
public abstract class UserInfo_ {

	public static volatile SingularAttribute<UserInfo, String> firstName;
	public static volatile SingularAttribute<UserInfo, String> lastName;
	public static volatile SingularAttribute<UserInfo, Role> roles;
}
