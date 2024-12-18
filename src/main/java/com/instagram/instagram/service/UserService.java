package com.instagram.instagram.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.instagram.instagram.dto.User;
import com.instagram.instagram.entity.UserEntity;


public interface UserService {
   public String finduser();
   public List<Map<String,Object>> findAll();
   public User findById(String id);
   public Map<String,Object> findUserById(String id);
   public User findByEmail(String email);
	public Map<String,Object>  followUser(String followId,String name);
	public Map<String,Object> unfollowUser(String followId,String name);
	public User updatePicture(String picture,String name);
	String findByIdandUpdate(String followId);
	//public User SignInUser(String user);
	public String verify(User user);
	public UserEntity signUpUser(User user);
	public User searchByEmail(String emailId, String name);

}
