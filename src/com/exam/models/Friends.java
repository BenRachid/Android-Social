package com.exam.models;

public class Friends extends Entity{
/*
 *     		+ TABLE_FRIENDS+ "(" + KEY_ID + " INTEGER PRIMARY KEY,"
			+ KEY_NAME + " TEXT,"
			+ KEY_EMAIL + " TEXT UNIQUE,"
			+ KEY_UID + " TEXT,"
			+ KEY_PHOTO + " TEXT," + ")";
 * */
	
	//this name is the nickname given by the user himself to a friend like in skype
	String name;
	String email;
	//still not implemented
	String photo;

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
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	
	public Friends(int id, String name, String email, String photo) {
		super(id);
		this.name = name;
		this.email = email;
		this.photo = photo;
	}
	public Friends() {
		super();
	}
	
}
