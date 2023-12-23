package com.shopme.checkout.phonepe;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PhonePeResponseData {
	public String merchantId;
    public String merchantTransactionId;
    public String transactionId;
    public double amount;
    public String state;
    public String responseCode;
    public String responseCodeDescription;
    public PaymentInstrument paymentInstrument;
    
	public PhonePeResponseData() {
		super();
	}
	@Override
	public String toString() {
		return "PhonePeResponseData [merchantId=" + merchantId + ", merchantTransactionId=" + merchantTransactionId
				+ ", transactionId=" + transactionId + ", amount=" + amount + ", state=" + state + ", responseCode="
				+ responseCode + ", responseCodeDescription=" + responseCodeDescription + ", paymentInstrument="
				+ paymentInstrument + "]";
	}
    
    
    
}
