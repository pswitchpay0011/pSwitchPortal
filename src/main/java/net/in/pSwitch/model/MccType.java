package net.in.pSwitch.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "mcc_type")
public class MccType implements Serializable {

	private static final long serialVersionUID = -3846410452642551284L;

	@Id
	@Column(name = "id")
	private long id;

	@Column(name = "mcc_type")
	private String mccType;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMccType() {
		return mccType;
	}

	public void setMccType(String mccType) {
		this.mccType = mccType;
	}
}
