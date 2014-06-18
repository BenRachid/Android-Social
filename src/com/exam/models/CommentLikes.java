package com.exam.models;

public class CommentLikes extends Entity{

/*
	
 */
	int performer;
	int target;
	int type;
	
	
	public CommentLikes() {
		super();
	}

	public CommentLikes(int id, int performer, int target, int type) {
		super(id);
		this.performer = performer;
		this.target = target;
		this.type = type;
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