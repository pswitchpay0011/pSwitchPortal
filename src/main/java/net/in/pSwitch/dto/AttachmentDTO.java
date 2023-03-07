package net.in.pSwitch.dto;

import javax.validation.constraints.NotNull;

import org.springframework.web.multipart.MultipartFile;

import net.in.pSwitch.model.DocumentType;

public class AttachmentDTO {

	private String userId;

	@NotNull(message = "Document Type cannot be blank")
	private DocumentType documentType;

	@NotNull(message = "Document Number cannot be blank")
	private String documentNumber;

	@NotNull(message = "Pancard Image cannot be blank")
	private MultipartFile imgDocument;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public DocumentType getDocumentType() {
		return documentType;
	}

	public void setDocumentType(DocumentType documentType) {
		this.documentType = documentType;
	}

	public String getDocumentNumber() {
		return documentNumber;
	}

	public void setDocumentNumber(String documentNumber) {
		this.documentNumber = documentNumber;
	}

	public MultipartFile getImgDocument() {
		return imgDocument;
	}

	public void setImgDocument(MultipartFile imgDocument) {
		this.imgDocument = imgDocument;
	}

}
