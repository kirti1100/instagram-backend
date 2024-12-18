package com.instagram.instagram.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.instagram.instagram.dto.User;
import com.instagram.instagram.entity.UserEntity;
import com.instagram.instagram.service.UserService;
import com.instagram.instagram.util.ResponseHandler;

@Controller
@RestController
public class AuthController {
	
	@Autowired
	private UserService userservice;
	
	@Autowired
	private ResponseHandler responseHandler;
	
	private static String errorMsg;
	
	private static String successMsg;
	
	@PostMapping("signin")
	public ResponseEntity<Object> userSignIn(@RequestBody User user) {
		String email=user.getEmail();
		String password=user.getPassword();
		if(email==null || password==null) {
			errorMsg="please provide email or password";
			return responseHandler.generateSignInResponse(errorMsg,HttpStatusCode.valueOf(201),null,user,successMsg);
			//return new ResponseEntity<String>("please provide email or password", HttpStatusCode.valueOf(401));
		}
		String verifytoken= userservice.verify(user);
		if(verifytoken==null) {
			errorMsg="invalid email or password";
			return responseHandler.generateSignInResponse(errorMsg,HttpStatusCode.valueOf(201),null,user,successMsg);
			//return new ResponseEntity<String>("invalid email or password", HttpStatusCode.valueOf(401));
		}
		successMsg="sign In successfully";
		User userlogin=userservice.findByEmail(email);
		
		return responseHandler.generateSignInResponse(errorMsg,HttpStatusCode.valueOf(201),verifytoken,userlogin,successMsg);
		//return new ResponseEntity<String>(verifytoken, HttpStatusCode.valueOf(200));
	}
	
	@PostMapping("/signup")
	public ResponseEntity<Object> userSignUp(@RequestBody User user){
		String email=user.getEmail();
		String password=user.getPassword();
		String name= user.getName();
		String errormessage=null;
		String successmesaage=null;
		if(name==null || email==null || password==null) {
			errormessage="please provide all fields";
		}
		UserEntity entityObj=userservice.signUpUser(user);
		if(entityObj==null) {
			errormessage="email already exist";
		}else {
			successmesaage="successfully updated";
			//String verifytoken= userservice.verify(user);
			
		}
		
		return responseHandler.generateResponse(errormessage,HttpStatusCode.valueOf(201),entityObj,successmesaage);
	}

}
