package com.exam.models;
/**
 class used to store performed actions with their dates will be used to optimize the sync operations
 */
public class Action extends Entity{
	int performer;
	//action should be commentAdd/Update/Remove, FriendAdd/Request/Delete and selfUpdate
	String action;
	//taget Id  -not necessary for selfUpdate
	int extInt;
	String created_at;
	int status;
	
	public int getPerformer() {
		return performer;
	}
	public void setPerformer(int performer) {
		this.performer = performer;
	}
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
	}
	public int getExtInt() {
		return extInt;
	}
	public void setExtInt(int extInt) {
		this.extInt = extInt;
	}
	public String getPerformed_at() {
		return created_at;
	}
	public void setPerformed_at(String performed_at) {
		this.created_at = performed_at;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Action(int id, int performer, String action, int extInt,
			String created_at, int status) {
		super(id);
		this.performer = performer;
		this.action = action;
		this.extInt = extInt;
		this.created_at = created_at;
		this.status = status;
	}
	
	public Action() {
		super();
	}
	
}
