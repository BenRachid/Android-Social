package com.exam.models;

public class CommentLikes {

/*
	
 */
	int id;
	int performer;
	int target;
	int type;
	
	
	public CommentLikes() {
		super();
	}

	public CommentLikes(int id, int performer, int target, int type) {
		super();
		this.id = id;
		this.performer = performer;
		this.target = target;
		this.type = type;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getPerformer() {
		return performer;
	}
	public void setPerformer(int performer) {
		this.performer = performer;
	}
	public int getTarget() {
		return target;
	}
	public void setTarget(int target) {
		this.target = target;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
}