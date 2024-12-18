package com.instagram.instagram.dto;

import java.util.List;

public class Post {
	String id;
 public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
String title;
 String body;
 String picture;
 User postedby;
 List<User> likes;
 List<Postedby> comments;

 public Post(String title, String body, String picture, User postedby, List<User> likes, List<Postedby> comments) {
	super();
	this.title = title;
	this.body = body;
	this.picture = picture;
	this.postedby = postedby;
	this.likes = likes;
	this.comments = comments;
}
public Post() {
	// TODO Auto-generated constructor stub
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public String getBody() {
	return body;
}
public void setBody(String body) {
	this.body = body;
}
public String getPicture() {
	return picture;
}
public void setPicture(String picture) {
	this.picture = picture;
}
public User getPostedby() {
	return postedby;
}
public void setPostedby(User postedby) {
	this.postedby = postedby;
}
public List<User> getLikes() {
	return likes;
}
public void setLikes(List<User> likes) {
	this.likes = likes;
}
public List<Postedby> getComments() {
	return comments;
}
public void setComments(List<Postedby> comments) {
	this.comments = comments;
}
}
