package com.shopme.checkout.phonepe;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shopme.ControllerHelper;
import com.shopme.address.AddressService;
import com.shopme.checkout.CheckoutInfo;
import com.shopme.checkout.CheckoutService;
import com.shopme.checkout.paypal.PayPalService;
import com.shopme.common.entity.Address;
import com.shopme.common.entity.CartItem;
import com.shopme.common.entity.Customer;
import com.shopme.common.entity.ShippingRate;
import com.shopme.order.OrderService;
import com.shopme.setting.SettingService;
import com.shopme.shipping.ShippingRateService;
import com.shopme.shoppingcart.ShoppingCartService;

@Service
public class PhonePeService {
	
	@Autowired private CheckoutService checkoutService;
	@Autowired private AddressService addressService;
	@Autowired private ShippingRateService shipService;
	@Autowired private ShoppingCartService cartService;
	@Autowired private OrderService orderService;
	@Autowired private SettingService settingService;
	
	
	private static final String ALLOWED_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-";
	
	public String intiatePayment(Customer customer) {
		
		CheckoutInfo checkoutInfo=getCheckoutInfo(customer);

		// Create an ObjectMapper
		ObjectMapper objectMapper = new ObjectMapper();

		// Create an ObjectNode to represent the JSON object
		ObjectNode jsonPayload = objectMapper.createObjectNode();

		// Add properties to the JSON object
		String merchantId="PGTESTPAYUAT";
		jsonPayload.put("merchantId", merchantId);
		String merchantTransactionId=getTranactionId();
		jsonPayload.put("merchantTransactionId", merchantTransactionId);
		jsonPayload.put("merchantUserId", customer.getId()); //MUID123
		long amount=(long) (checkoutInfo.getPaymentTotal()*100l);
		jsonPayload.put("amount", amount);
		jsonPayload.put("redirectUrl", "http://localhost:8083/Shopme/payment/callback");
		jsonPayload.put("redirectMode", "POST");
		jsonPayload.put("callbackUrl", "http://localhost:8083/Shopme/payment/callback");
		jsonPayload.put("mobileNumber", customer.getPhoneNumber()+"");

		// Create a nested ObjectNode for the "paymentInstrument"
		ObjectNode paymentInstrument = objectMapper.createObjectNode();
		paymentInstrument.put("type", "PAY_PAGE");

		// Add the nested ObjectNode to the main JSON object
		jsonPayload.set("paymentInstrument", paymentInstrument);

		String saltKey = "099eb0cd-02cf-4e2a-8aca-3e6c6aff0399";
		int saltIndex = 1;

		// Convert the ObjectNode to a JSON string
		try {
			String payloadJsonString = objectMapper.writeValueAsString(jsonPayload);

			// Encode the JSON string to base64
			String base64Payload = Base64.getEncoder().encodeToString(payloadJsonString.getBytes());

			// Create the request object
			ObjectNode requestObject = objectMapper.createObjectNode();
			requestObject.put("request", base64Payload);

			// Convert the requestObject to JSON string
			String jsonRequestObject = objectMapper.writeValueAsString(requestObject);

			String concatenatedString = base64Payload + "/pg/v1/pay" + saltKey;
			String sha256_val = generateSHA256Checksum(concatenatedString);
			String checksum = sha256_val + "###" + saltIndex;

			HttpRequest requestJavanet = HttpRequest.newBuilder()
					.uri(URI.create("https://api-preprod.phonepe.com/apis/pg-sandbox/pg/v1/pay"))
					.header("accept", "application/json").header("Content-Type", "application/json")
					.header("X-VERIFY", checksum).POST(HttpRequest.BodyPublishers.ofString(jsonRequestObject)).build();

			CompletableFuture<HttpResponse<String>> futureResponse = HttpClient.newHttpClient()
					.sendAsync(requestJavanet, HttpResponse.BodyHandlers.ofString());

			HttpResponse<String> response1 = futureResponse.join();
			// Read the response body
			String body = response1.body();
			System.out.println("Got REsponse");
			System.out.println(body);
			System.out.println("Res?");

			ObjectMapper objectMapper1 = new ObjectMapper();
			JsonNode jsonNode = objectMapper1.readTree(body);

			// Access the URL
			String url = jsonNode.path("data").path("instrumentResponse").path("redirectInfo").path("url").asText();

			// Print the URL
			System.out.println("URL: " + url);
			//res.sendRedirect(url);
			return url;

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return "something error";
	}
	
	public boolean checkStatusApiPhonePe(String code, String transactionId, String merchantId,String amount) throws InterruptedException{
		
		String saltKey = "099eb0cd-02cf-4e2a-8aca-3e6c6aff0399";
		int saltIndex = 1;
		
		String apiurl = "https://api-preprod.phonepe.com/apis/pg-sandbox/pg/v1/status/"+merchantId+"/"+ transactionId;

		String stringforSha = "/pg/v1/status/"+merchantId+"/" + transactionId + saltKey;

		String sha256_val = "";
		try {
			sha256_val = generateSHA256Checksum(stringforSha);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		String checksum = sha256_val + "###" + saltIndex;
		System.out.println(checksum);
		HttpRequest requestJavanet = HttpRequest.newBuilder()
				.uri(URI.create(apiurl))
				.header("accept", "application/json").header("Content-Type", "application/json")
				.header("X-VERIFY", checksum).header("X-MERCHANT-ID", transactionId)
				.method("GET", HttpRequest.BodyPublishers.noBody()).build();

		CompletableFuture<HttpResponse<String>> futureResponse = HttpClient.newHttpClient()
				.sendAsync(requestJavanet, HttpResponse.BodyHandlers.ofString());

		Thread.sleep(100);
		
		HttpResponse<String> response = futureResponse.join();
		String body = response.body();
		
		Thread.sleep(100);
	
		System.out.println("Java respone phonepe");
		System.out.println(body.isEmpty() + "-" + body + "-" + body.isBlank());
		System.out.println("Res?");
		ObjectMapper objectMapper = new ObjectMapper();
		try {
			PhonePeBaseResponse phonePeBaseResponse = objectMapper.readValue(body, PhonePeBaseResponse.class);
			System.out.println(phonePeBaseResponse);
			return phonePeBaseResponse.isSuccess();

		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public CheckoutInfo getCheckoutInfo(Customer customer) {
		Address defaultAddress = addressService.getDefaultAddress(customer);
		ShippingRate shippingRate = null;
		
		if (defaultAddress != null) {
			shippingRate = shipService.getShippingRateForAddress(defaultAddress);
		} else {
			shippingRate = shipService.getShippingRateForCustomer(customer);
		}
				
		List<CartItem> cartItems = cartService.listCartItems(customer);
		CheckoutInfo checkoutInfo = checkoutService.prepareCheckout(cartItems, shippingRate);
		
		return checkoutInfo;

	}
	
	public static String generateSHA256Checksum(String data) throws Exception {
		MessageDigest digest = MessageDigest.getInstance("SHA-256");
		byte[] hash = digest.digest(data.getBytes(StandardCharsets.UTF_8));

		StringBuilder builder = new StringBuilder();
		for (byte b : hash) {
			builder.append(String.format("%02x", b));
		}
		return builder.toString();
	}

	public String getTranactionId() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmssSSS");
		
		StringBuilder randomString = new StringBuilder(30);
        SecureRandom random = new SecureRandom();

        for (int i = 0; i <10; i++) {
            int randomIndex = random.nextInt(ALLOWED_CHARACTERS.length());
            char randomChar = ALLOWED_CHARACTERS.charAt(randomIndex);
            randomString.append(randomChar);
        }
        
        return dateFormat.format(new Date())+randomString.toString();
	}
}

