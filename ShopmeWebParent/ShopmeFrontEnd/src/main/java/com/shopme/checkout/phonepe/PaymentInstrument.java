package com.shopme.checkout.phonepe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PaymentInstrument {
	String type;
	String cardType;
	String pgTransactionId;
	String bankTransactionId;
	String pgAuthorizationCode;
	String arn;
	String bankId;
	String brn;
	String utr;
	String pgServiceTransactionId;
	
	public PaymentInstrument() {
		super();
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getCardType() {
		return cardType;
	}

	public void setCardType(String cardType) {
		this.cardType = cardType;
	}

	public String getPgTransactionId() {
		return pgTransactionId;
	}

	public void setPgTransactionId(String pgTransactionId) {
		this.pgTransactionId = pgTransactionId;
	}

	public String getBankTransactionId() {
		return bankTransactionId;
	}

	public void setBankTransactionId(String bankTransactionId) {
		this.bankTransactionId = bankTransactionId;
	}

	public String getPgAuthorizationCode() {
		return pgAuthorizationCode;
	}

	public void setPgAuthorizationCode(String pgAuthorizationCode) {
		this.pgAuthorizationCode = pgAuthorizationCode;
	}

	public String getArn() {
		return arn;
	}

	public void setArn(String arn) {
		this.arn = arn;
	}

	public String getBankId() {
		return bankId;
	}

	public void setBankId(String bankId) {
		this.bankId = bankId;
	}

	public String getBrn() {
		return brn;
	}

	public void setBrn(String brn) {
		this.brn = brn;
	}

	public String getUtr() {
		return utr;
	}

	public void setUtr(String utr) {
		this.utr = utr;
	}

	public String getPgServiceTransactionId() {
		return pgServiceTransactionId;
	}

	public void setPgServiceTransactionId(String pgServiceTransactionId) {
		this.pgServiceTransactionId = pgServiceTransactionId;
	}

	@Override
	public String toString() {
		return "PaymentInstrument [type=" + type + ", cardType=" + cardType + ", pgTransactionId=" + pgTransactionId
				+ ", bankTransactionId=" + bankTransactionId + ", pgAuthorizationCode=" + pgAuthorizationCode + ", arn="
				+ arn + ", bankId=" + bankId + ", brn=" + brn + ", utr=" + utr + ", pgServiceTransactionId="
				+ pgServiceTransactionId + "]";
	}
	
	
	
	
	
	
	
}
