package net.in.pSwitch.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class ProductFlexiCommissionDTO {

	private String id;
	private String productId;

	@NotNull(message = "User ID cannot be blank")
	private String userPSwitchId;

	@NotNull(message = "Commission cannot be blank")
	@Min(value = 0)
	private Integer amount;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public String getUserPSwitchId() {
		return userPSwitchId;
	}

	public void setUserPSwitchId(String userPSwitchId) {
		this.userPSwitchId = userPSwitchId;
	}

}
