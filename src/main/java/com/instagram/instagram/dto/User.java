package com.instagram.instagram.dto;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.instagram.instagram.entity.UserEntity;


public class User {
  public String name;
  public String email;
  public String picture;
  public String password;
  public String id;
  public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public List<String> followers;
  public List<String> following;
  
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getPicture() {
	return picture;
}
public void setPicture(String picture) {
	this.picture = picture;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
public List<String> getFollowers() {
	return followers;
}
public void setFollowers(List<String> list) {
	this.followers = list;
}
public List<String> getFollowing() {
	return following;
}
public void setFollowing(List<String> following) {
	this.following = following;
}
public User(String name, String email, String picture, String password, List<String> followers, List<String> following) {
	super();
	this.name = name;
	this.email = email;
	this.picture = picture;
	this.password = password;
	this.followers = followers;
	this.following = following;
}
public User() {
	// TODO Auto-generated constructor stub
}
public UserEntity setuserEntity(User user) {
	UserEntity userentity=new UserEntity();
	userentity.setEmail(user.getEmail());
	userentity.setName(user.getName());
	userentity.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
	userentity.setPicture(user.getPicture());
	userentity.setFollowers(user.getFollowers());
	userentity.setFollowing(user.getFollowing());
	return userentity;
}

}
