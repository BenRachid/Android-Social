package com.exam.models;

public class Notifications {
/*
 *     		+ TABLE_NOTIFICATIONS+ "(" + KEY_ID + " INTEGER PRIMARY KEY," 
    		+ KEY_PERFORMER + " INTEGER," 
    		+ KEY_TARGET + " INTEGER," 
    		+ KEY_TYPE + " INTEGER," + ")";
    		
    		
    		// MAYBE ADD A SEEN FOR NOTIFICATIONS TO STORE THEM ALL
 * */
	
	int id;
	int performer;
	int target;
	int type;
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
	public Notifications(int id, int performer, int target, int type) {
		super();
		this.id = id;
		this.performer = performer;
		this.target = target;
		this.type = type;
	}
	public Notifications() {
		super();
	}
	
	
	}
