package com.shopme.checkout.phonepe;
public class PhonePeBaseResponse {
	
	private boolean success;
    private String code;
    private String message;
    private PhonePeResponseData data;
    
    public PhonePeBaseResponse(){
    	
    }
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public PhonePeResponseData getData() {
		return data;
	}
	public void setData(PhonePeResponseData data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "PhonePeBaseResponse [success=" + success + ", code=" + code + ", message=" + message + ", data=" + data
				+ "]";
	}
    
	
	
    
    
}

/*

	{
	  "success": true,
	  "code": "PAYMENT_SUCCESS",
	  "message": "Your request has been successfully completed.",
	  "data": {
	    "merchantId": "PGTESTPAYUAT",
	    "merchantTransactionId": "MT7850590068188104",
	    "transactionId": "T2111221437456190170379",
	    "amount": 100,
	    "state": "COMPLETED",
	    "responseCode": "SUCCESS",
	    "paymentInstrument": {
	      "type": "CARD",
	      "cardType": "DEBIT_CARD",
	      "pgTransactionId": "b9090242ac120002",
	      "bankTransactionId": "e57a658e9e1011ec",
	      "pgAuthorizationCode": "9cf3ef4932bf9e05",
	      "arn": "339482773927",
	      "bankId": "SBIN",
	      "brn": "Dummy_Bank_Ref_Number"
	     }
	  	}
	}
*/