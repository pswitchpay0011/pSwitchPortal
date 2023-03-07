package net.in.pSwitch.dto;

public class OTPGenerateData {

	private String apikey;
	private String senderid;
	private String number;
	private String message;
	private String format;

	public String getApikey() {
		return apikey;
	}

	public void setApikey(String apikey) {
		this.apikey = apikey;
	}

	public String getSenderid() {
		return senderid;
	}

	public void setSenderid(String senderid) {
		this.senderid = senderid;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

}
