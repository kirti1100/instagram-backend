package com.instagram.instagram.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

//import org.apache.juli.logging.Log;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators.Log;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.instagram.instagram.dto.User;
import com.instagram.instagram.entity.UserEntity;
import com.instagram.instagram.repository.UserRepo;
import com.instagram.instagram.service.UserService;
import com.instagram.instagram.service.UserServiceImpl;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@Controller
public class UserController {
	@Autowired(required = true)
	//@Qualifier("userservice")
	public UserRepo useService;
	@Autowired
	public UserService user;
	
	@GetMapping("getalluser")
     public List<Map<String,Object>> getMapping(){
		return user.findAll();
    	 
     }
	@GetMapping("test")
    public List<Map<String,Object>> testMapping(){
		return user.findAll();
   	 
    }
	@GetMapping("/loginId")
    public List<Map<String,Object>> getlogin(){
		return user.findAll();
   	 
    }

	@GetMapping("/user/{userId}")
	public Map<String,Object> findbyobjectId(@PathVariable String userId){
		//this.user=new UserServiceImpl();
		Map<String,Object> mapObject=user.findUserById(userId);
		return mapObject;
		}

    @PutMapping("/search")
    public ResponseEntity<User> getUserByEmail(@RequestBody String emailId,HttpServletRequest request) {
    	System.out.println("check emailid"+emailId);
    	User userobj= user.searchByEmail(emailId,request.getUserPrincipal().getName());
    	if(userobj!=null) {
    		return ResponseEntity.ok(userobj);
    	}else {
    		return ResponseEntity.notFound().build();
    	}
    	//return user.findByEmail(emailId);
    }
    
    @PutMapping("/follow")
    public ResponseEntity<Map<String,Object>> updateUserFollowers(@RequestBody String followId,HttpServletRequest request){
    	Map<String,Object> userobj = user.followUser(followId,request.getUserPrincipal().getName());
    	if(userobj!=null) {
    		return ResponseEntity.ok(userobj);
    	}else {
    		return ResponseEntity.notFound().build();

    	}
    }
    
    @PutMapping("/unfollow")
    public ResponseEntity<Map<String,Object>> unFollowUser(@RequestBody String followId,HttpServletRequest request){

    	Map<String,Object> userobj = user.unfollowUser(followId,request.getUserPrincipal().getName());
    	if(userobj!=null) {
    		return ResponseEntity.ok(userobj);
    	}else {
    		return ResponseEntity.notFound().build();

    	}
    }
    
    @PutMapping("/createprofile")
    public ResponseEntity<Object> updatePicture(@RequestBody String picture,HttpServletRequest request){


    	User userobj = user.updatePicture(picture,request.getUserPrincipal().getName());
    	if(userobj!=null) {
    		return ResponseEntity.ok(userobj);
    	}else {
    		return ResponseEntity.notFound().build();

    	}
    	
    }
}
