package com.instagram.instagram.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;

//import org.springframework.beans.factory.annotation.Autowired(required=true);	
//import org.springframework.beans.factory.annotation.Autowired(required=true)



import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.instagram.instagram.dto.Post;
import com.instagram.instagram.dto.User;
import com.instagram.instagram.entity.UserEntity;
import com.instagram.instagram.repository.UserRepo;


@Service
public class UserServiceImpl implements UserService{
   @Autowired(required=true)
   public UserRepo userrepo;
   
   @Autowired
   public PostServiceImpl postservice;
   
   @Autowired
   public JWTService jwtserivce;
   
   @Autowired
   public PasswordEncoder encode;
   
   @Autowired
   public AuthenticationManager authManager;
   
   
   
	@Override
	public String finduser() {
		// TODO Auto-generated method stub
		
		for (UserEntity customer : userrepo.findAll()) { System.out.println(customer.getId());}
		

		return "working"; 
		

	}
	@Override
	public List<Map<String,Object>> findAll() {
		// TODO Auto-generated method stub
		List<UserEntity> listUser=userrepo.findAll();
        List<Map<String,Object>> userlist=new ArrayList<>();
        for(UserEntity user:listUser) {
        	Map<String,Object> mapObject=this.findUserById(user.id.toHexString());
        	userlist.add(mapObject);
        }
		return userlist;
	}
	
	public Map<String,Object> findUserById(String userId){
		Map<String,Object> mapObject=new HashMap<>();
		User user=this.findById(userId);
		List<Post> post=postservice.getmypost(user.getEmail());
		mapObject.put("user", user);
		mapObject.put("post", post);
		return mapObject;
	}
	@Override
	public User findById(String userId) {
		Optional<UserEntity> userentity = userrepo.findById(new ObjectId(userId));
		//create dto object
		User user= new User();
		//get data from userentity object
        
        if(userentity.isPresent()) {
        UserEntity userobject = userentity.get();	
      //3 List<Post> post= postservice.getuserpost(userobject.getEmail());
        user= userobject.setUser(userobject);
		return user;
		}else {
			return null;
		}
        
		
	}
    public User findByEmail(String email) {
    	System.out.println("breakhere1");
		Optional<UserEntity> userentity = userrepo.findByEmail(email);
		System.out.println("breakhere2"+userentity);
		User user= new User();
      
        if(userentity.isPresent()) {
        	System.out.println("breakhere3"+userentity);
        	Optional< UserEntity> userobject = Optional.ofNullable(userentity.get());
        	System.out.println("breakhere4"+userentity);
    		user.setId(userentity.get().getId().toString());
    		user.setName(userentity.get().getName());
    		user.setEmail(userobject.get().getEmail());
    		user.setFollowers(userobject.get().getFollowers());
    		user.setFollowing(userobject.get().getFollowing());
    		user.setPassword(userobject.get().getPassword());
    		user.setPicture(userobject.get().getPicture());
    		return user;
        }
         return user;
    }
	//@Override
	@Override
	public Map<String,Object> followUser(String followId,String name) {
		Optional<UserEntity> loggedInuser = userrepo.findByEmail(name);
		Optional<UserEntity> userentity = userrepo.findById(new ObjectId(followId));
		if(userentity.isPresent() && loggedInuser.isPresent()) {
		 //update followed user followers
			UserEntity userobject = userentity.get();
	        List<String> newlist= userobject.getFollowers();
	        newlist.add(loggedInuser.get().getId().toString());
	        userobject.setFollowers(newlist);
	        userrepo.save(userobject);
	      //update loggedin user following
	        UserEntity loggeduserentity=loggedInuser.get();
			List<String> loggedinList= loggeduserentity.getFollowing();
	        loggedinList.add(followId);
	        loggeduserentity.setFollowing(loggedinList);
	        userrepo.save(loggeduserentity);
	        Map<String,Object> mapObject=this.followUnfollowUser(userobject,loggeduserentity);
	        return mapObject;
		}
		return null;
	}
	
	@Override
	public Map<String,Object> unfollowUser(String unfollowId,String name) {
		// TODO Auto-generated method stub
		Optional<UserEntity> loggedInuser = userrepo.findByEmail(name);
		Optional<UserEntity> userentity = userrepo.findById(new ObjectId(unfollowId));
		if(userentity.isPresent() && loggedInuser.isPresent()) {
			UserEntity userobject = userentity.get();
	        List<String> newlist= userobject.getFollowers();
	        newlist.remove(loggedInuser.get().getId().toString());
	        userobject.setFollowers(newlist);
	        userrepo.save(userobject);
	        //update loggedInuser
	        UserEntity loggeduserentity=loggedInuser.get();
			List<String> loggedinList= loggeduserentity.getFollowing();
	        loggedinList.remove(unfollowId);
	        loggeduserentity.setFollowing(loggedinList);
	        userrepo.save(loggeduserentity);
	        Map<String,Object> mapObject=this.followUnfollowUser(userobject,loggeduserentity);
			return mapObject;
		}
		return null;
        
	
	}
	
	public Map<String,Object> followUnfollowUser(UserEntity user,UserEntity loggedInUser){
		Map<String,Object> mapObject=new HashMap<>();
		mapObject.put("user", user);
		mapObject.put("logedinUser", loggedInUser);
		return mapObject;
	}
	@Override
	public User updatePicture(String picture,String email) {
		Optional<UserEntity> loggedInuser = userrepo.findByEmail(email);
		UserEntity userentity=new UserEntity();
		if(loggedInuser.isPresent()) {
			loggedInuser.get().setPicture(picture);
			userrepo.save(loggedInuser.get());
			return userentity.setUser(loggedInuser.get());
		}
		return null;
	}
	@Override
	public String findByIdandUpdate(String followId) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public String verify(User user) {
		try {
			Authentication authentication= authManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
			if(authentication.isAuthenticated()) {
				String token= jwtserivce.jsonWebToken(user.getEmail());
				return token;
				
				
			}
		}catch(Exception e) {
			System.out.println("exception occured"+e);
		}
		return null;
	}
	@Override
	public UserEntity signUpUser(User user) {
		//Optional<User> existUser=Optional.ofNullable(this.findByEmail(user.getEmail()));
		User existUser=this.findByEmail(user.getEmail());
		System.out.println("existuser"+existUser);
		if(existUser!=null) {
			System.out.println("existusernot"+existUser);
			return null;
		}
		UserEntity userEntity= user.setuserEntity(user);
		
		return userrepo.save(userEntity);
	}
	@Override
	public User searchByEmail(String emailId, String name) {
		userrepo.findByNameContainingIgnoreCase(emailId);
		return null;
	}
	
}
