package com.instagram.instagram.entity;

import java.util.Collection;
import java.util.List;


import org.bson.types.ObjectId;
import org.springframework.aot.generate.Generated;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.instagram.instagram.dto.User;


@Document(collection="users")
public class UserEntity {
	@Id
	@Field(name="_id")
	public ObjectId id;
	
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String name;
		public String email;
		  public String picture;
		  public String password;
		  public List<String> followers;
	
		  public List<String> following;
//	//@Generated  
//    private Role rule;

	  public String getName() {
		return name;
	}
	//public String get_id() {}
	//public void set_id(String _id) {}
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
	public void setFollowers(List<String> followers) {
		this.followers = followers;
	}
	public List<String> getFollowing() {
		return following;
	}
	public void setFollowing(List<String> following) {
		this.following = following;
	}
	public User setUser(UserEntity userobject) {
		User user= new User();
         

		user.setId(userobject.getId().toString());
		user.setName(userobject.getName());
		user.setEmail(userobject.getEmail());
		user.setFollowers(userobject.getFollowers());
		user.setFollowing(userobject.getFollowing());
		user.setPassword(userobject.getPassword());
		user.setPicture(userobject.getPicture());
		return user;
    
	}
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		// TODO Auto-generated method stub
//		return List.of(new SimpleGrantedAuthority(rule.name()));
//	}
//	@Override
//	public String getUsername() {
//		// TODO Auto-generated method stub
//		return email;
//	}
//	@Override
//	public boolean isAccountNonExpired() {
//		// TODO Auto-generated method stub
//		return true;
//	}
//	@Override
//	public boolean isAccountNonLocked() {
//		// TODO Auto-generated method stub
//		return true;
//	}
//	@Override
//	public boolean isCredentialsNonExpired() {
//		// TODO Auto-generated method stub
//		return true;
//	}
//	@Override
//	public boolean isEnabled() {
//		// TODO Auto-generated method stub
//		return true;
//	}
}
