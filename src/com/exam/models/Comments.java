package com.exam.models;

public class Comments extends Entity{
/*
            + TABLE_COMMENTS + "(" + KEY_ID + " INTEGER PRIMARY KEY," 
    		+ KEY_PERFORMER + " INTEGER," 
    		+ KEY_PARENT + " INTEGER," 
    		+ KEY_CONTENT + " TEXT," 
    		+ KEY_CREATED_AT +" DATETIME"
    		+ KEY_UPDATED_AT +" DATETIME" + ")";
 * */
	
	int performer;
	int parent;
	int status;
	String created_at;
	String updated_at;
	String content;
	
	public int getPerformer() {
		return performer;
	}
	public void setPerformer(int performer) {
		this.performer = performer;
	}
	public int getParent() {
		return parent;
	}
	public void setParent(int parent) {
		this.parent = parent;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getCreated_at() {
		return created_at;
	}
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	public String getUpdated_at() {
		return updated_at;
	}
	public void setUpdated_at(String updated_at) {
		this.updated_at = updated_at;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Comments(int id, int performer, int parent, int status,
			String created_at, String updated_at, String content) {
		super(id);
		this.performer = performer;
		this.parent = parent;
		this.status = status;
		this.created_at = created_at;
		this.updated_at = updated_at;
		this.content = content;
	}
	public Comments() {
		// TODO Auto-generated constructor stub
	}

}
