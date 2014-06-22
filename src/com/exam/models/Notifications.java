package com.exam.models;

public class Notifications extends Entity{
/*
This class is used to stock our Notifications 
the message will be deduced from the Type.
we've got 3 different type 0-friendInvite,1X-postCommented, 2X-comment-like 
 * */
	
	int performer;
	int target;
	int type;

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
	public Notifications(int id, int performer, int target, int type) {
		super(id);
		this.performer = performer;
		this.target = target;
		this.type = type;
	}
	public Notifications() {
		super();
	}
	
	
	}
