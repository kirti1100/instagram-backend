package com.instagram.instagram.util;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.instagram.instagram.dto.User;
import com.instagram.instagram.entity.PostEntity;
@Component
public class ResponseHandler {
	
	public ResponseEntity<Object> generateResponse(String errormessage,HttpStatusCode status,Object respObject,String successmessage){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("error", errormessage);
		map.put("message", successmessage);
		map.put("user", respObject);
		return new ResponseEntity<>(map,status);
	}
	
	public ResponseEntity<Object> generateSignInResponse(String errormessage,HttpStatusCode status,String token,User user,String successmessage){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("error", errormessage);
		map.put("message", successmessage);
		map.put("token", token);
		map.put("user", user);
		return new ResponseEntity<>(map,status);
	}
	
	public ResponseEntity<Object> generatePostResponse(String errormessage,HttpStatusCode status,Object obj,String successmessage){
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("error", errormessage);
		map.put("message", successmessage);
		map.put("post", obj);
		return new ResponseEntity<>(map,status);
	}

}
