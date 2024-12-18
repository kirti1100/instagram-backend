package com.instagram.instagram.entity;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection="posts")
public class PostEntity {
	@Id
	@Field(name="_id")
	ObjectId id;
	String title;
	 String body;
	 String picture;
	 ObjectId postedby;
	 List<ObjectId> likes;
	 List<CommentsEntity> comments;

	 public List<CommentsEntity> getComments() {
		return comments;
	}
	public void setComments(List<CommentsEntity> comments) {
		this.comments = comments;
	}
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
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
	public ObjectId getPostedby() {
		return postedby;
	}
	public void setPostedby(ObjectId postedby) {
		this.postedby = postedby;
	}
	public List<ObjectId> getLikes() {
		return likes;
	}
	public void setLikes(List<ObjectId> likes) {
		this.likes = likes;
	}
}
