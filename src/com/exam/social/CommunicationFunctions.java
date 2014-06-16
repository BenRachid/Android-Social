package com.exam.social;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.exam.helpers.DatabaseHelper;
import com.exam.helpers.JSONParser;

import android.content.Context;

public class CommunicationFunctions {
	
	private JSONParser jsonParser;
	
	private static String loginURL = "http://10.0.2.2/login_api/login";
	private static String registerURL = "http://10.0.2.2/login_api/register";
	private static String updateURL = "http://10.0.2.2/json_api/update";
	private static String syncURL = "http://10.0.2.2/json_api/sync";
	
	private static String login_tag = "login";
	private static String register_tag = "register";
	
	// constructor
	public CommunicationFunctions(){
		jsonParser = new JSONParser();
	}
	
	/**
	 * function make Login Request
	 * @param email
	 * @param password
	 * */
	public JSONObject loginUser(String email, String password){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", login_tag));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		JSONObject json = jsonParser.makeHttpRequest(loginURL,"POST", params);
		// return json
		// Log.e("JSON", json.toString());
		return json;
	}
	
	/**
	 * function make Login Request
	 * @param name
	 * @param email
	 * @param password
	 * */
	public JSONObject registerUser(String name, String email, String password){
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", register_tag));
		params.add(new BasicNameValuePair("lastname", name));
		params.add(new BasicNameValuePair("firstname", name));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		
		// getting JSON Object
		JSONObject json = jsonParser.makeHttpRequest(registerURL, "POST", params);
		// return json
		return json;
	}
	
	/**
	 * Function get Login status
	 * */
	public boolean isUserLoggedIn(Context context){
		DatabaseHelper db = new DatabaseHelper(context);
		int count = db.getRowCount();
		if(count > 0){
			// user logged in
			return true;
		}
		return false;
	}
	
	/**
	 * Function to logout user
	 * Reset Database
	 * */
	public boolean logoutUser(Context context){
		DatabaseHelper db = new DatabaseHelper(context);
		db.resetTables();
		return true;
	}
	
}
