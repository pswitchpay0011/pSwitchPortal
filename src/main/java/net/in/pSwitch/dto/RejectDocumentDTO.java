package net.in.pSwitch.dto;

import javax.validation.constraints.NotNull;

public class RejectDocumentDTO {

	private String userId;
	private String attachmentId;

	@NotNull(message = "Rejection Reason cannot be blank")
	private String rejectionReason;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}

	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

}
