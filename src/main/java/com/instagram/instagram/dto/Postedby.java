package com.instagram.instagram.dto;

import java.util.List;

public class Postedby {
	String text;
	User postedby;
	
   public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}


public User getPostedby() {
	return postedby;
}

public void setPostedby(User postedby) {
	this.postedby = postedby;
}

public Postedby() {
	super();
	this.postedby = postedby;
}
}
