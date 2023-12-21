package com.shopme.checkout.phonepe;

import java.io.IOException;
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
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.shopme.ControllerHelper;
import com.shopme.common.entity.Customer;
import com.shopme.security.CustomerUserDetails;

@Controller
public class PhonepeController {

	private static final String ALLOWED_CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_-";
	
	@Autowired private ControllerHelper controllerHelper;
	@Autowired PhonePeService phonePeService;
	
	//CustomerUserDetails customerUserDetails;

	@GetMapping("/initiate-payment")
	public void initiatePayment(HttpServletRequest req, HttpServletResponse res) {
		Customer customer = controllerHelper.getAuthenticatedCustomer(req);
	
		System.out.println("loggedCustomer username: "+customer.toString());
		System.out.println("loggedCustomer id: "+ controllerHelper.getAuthenticatedCustomerId(req));
		
		//System.out.println("loggedCustomer id: "+));
		
		String url=phonePeService.intiatePayment(customer);
		
		try {
			res.sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping(value = "/payment/callback", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE)
	public String paymentCallback(HttpServletRequest request) throws ExecutionException, InterruptedException {
		System.out.println("Payment Status");
		System.out.println("HTTP TYPE1 " + request.getMethod());

		System.out.println("size = " + request.getParameterMap().size());
		Map<String, String> formData = new HashMap<>(26);

		for (String parameterName : request.getParameterMap().keySet()) {
			formData.put(parameterName, request.getParameter(parameterName));
			System.out.println(parameterName + ": " + request.getParameter(parameterName));
		}

		if (formData.get("code").equals("PAYMENT_SUCCESS") && formData.get("transactionId") != null
				&& !formData.get("transactionId").isEmpty() && formData.get("merchantId") != null
				&& !formData.get("merchantId").isEmpty() && formData.get("providerReferenceId") != null
				&& !formData.get("providerReferenceId").isEmpty()) {
			
			System.out.println("make a fetch request to phonepe using merchant id and merchant "
					+ "transacion id and confirm success or failure of payment");
			boolean transactionSuccessful=phonePeService.checkStatusApiPhonePe(formData.get("code"),formData.get("transactionId"),formData.get("merchantId"), formData.get("amount"));
						
			if(transactionSuccessful) {
				return "checkout/place_order";
				//return "POST successful";
			}
		}
		return "POST1 failure";
	}

	

	@RequestMapping(value = "/payment/callback", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
	public String paymentCallback1(HttpServletRequest request) {
		System.out.println("Payment Status");

		// System.out.println(requestBody);
		System.out.println("HTTP TYPE2 " + request.getMethod());
		System.out.println("size = " + request.getParameterMap().size());
		for (String parameterName : request.getParameterMap().keySet()) {
			System.out.println(request.getParameter(parameterName));
		}

		return "GET1";
	}

	
}
