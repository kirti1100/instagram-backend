package com.instagram.instagram.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class CommentsEntity {
    public String text;
    public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public ObjectId postedby;
    @Id
	@Field(name="_id")
    public ObjectId id;
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public ObjectId getPostedby() {
		return postedby;
	}
	public void setPostedby(ObjectId postedby) {
		this.postedby = postedby;
	}
}
