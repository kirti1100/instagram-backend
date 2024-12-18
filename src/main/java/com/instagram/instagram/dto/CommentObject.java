package com.instagram.instagram.dto;

import org.bson.types.ObjectId;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentObject {
      public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getpostId() {
		return postId;
	}
	public void setObjectId(String postId) {
		postId = postId;
	}
	private String text;
      private String postId;
      private ObjectId id;
}
