package com.exam.helpers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.exam.models.CommentLikes;
import com.exam.models.Comments;
import com.exam.models.Entity;
import com.exam.models.Friends;
import com.exam.models.Notifications;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.util.Log;

public class CommunicationServices {
	Context context;
	public CommunicationServices(Context context) {
		this.context=context;
	}
	
    static InputStream is = null;
    static JSONObject jObj = null;
    static String json = "";
	private static final String KEY_SUCCESS = "success";
	private static final String KEY_ERROR = "error";
	private static final String KEY_ERROR_MSG = "error_msg";
	
	private static String loginURL = "http://10.10.100.4/login_api/";
//	private static String loginURL = "http://192.168.0.10/login_api/";
	private static String registerURL = "http://10.10.100.4/login_api/";
	private static String getEntityURL = "http://10.10.100.4/json_api/update";
	private static String syncURL = "http://10.10.100.4/json_api/sync";

	private static String login_tag = "login";
	private static String register_tag = "register";
	
	/**
	 * function make Login Request
	 * @param email
	 * @param password
	 * @throws JSONException 
	 * @throws IOException 
	 * */
	public JSONObject loginUser(String email, String password) throws IOException, JSONException{
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", login_tag));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		JSONObject json = makeHttpRequest(loginURL,"POST", params);
		// return json
		Log.d("JSON", json.toString());
		return json;
	}
	
	/**
	 * function make Login Request
	 * @param name
	 * @param email
	 * @param password
	 * @throws JSONException 
	 * @throws IOException 
	 * */
	public JSONObject registerUser( String first_name,String last_name, String email, String password) throws IOException, JSONException{
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", register_tag));
		params.add(new BasicNameValuePair("last_name", first_name));
		params.add(new BasicNameValuePair("first_name", last_name));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("password", password));
		
		// getting JSON Object
		JSONObject json = makeHttpRequest(registerURL, "POST", params);
		// return json
		return json;
	}
	
	/**
	 * Function get Login status
	 * */
	public static boolean isUserLoggedIn(Context context){
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
	
	public void proceedRefresh() throws IOException, JSONException
	{
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("tag", "Refresh"));
		JSONObject json = makeHttpRequest(getEntityURL,"POST", params);
		Log.d("REFRESH DB", json.toString());
		if (json.getString(KEY_SUCCESS) != null) {
			String res = json.getString(KEY_SUCCESS); 
			if (Integer.parseInt(res) == 1){
				JSONArray json_entities=null;
				JSONObject json_entity=null;
				HashSet<String> entitiesType = new HashSet<String>();
				
				entitiesType.add("Friends");
				entitiesType.add("Comments");
				entitiesType.add("CommentsLikes");
				entitiesType.add("Notifications");
				
				DatabaseHelper db = new DatabaseHelper(context);				
				for (String string : entitiesType) {
					json_entities= json.getJSONArray(string);
					for (int i = 0; i < json_entities.length(); i++) {
						json_entity=json_entities.getJSONObject(i);
						db.createFriendsFromJsonObject(string,json_entity);
					}
				}
			}
		
		}
	}
	
	public List<Entity> getEntities(String entityType, int amount) throws IOException,JSONException, InterruptedException{
		// Building Parameters
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		Log.d(entityType,"getEntities");
		params.add(new BasicNameValuePair("tag", entityType));
		params.add(new BasicNameValuePair("number", String.valueOf(amount)));
		ArrayList<Entity> entities = new ArrayList<Entity>();
		//Here if the Id is sent un parameters we will have a unique search and if not we will have a multiple one
		JSONObject json = makeHttpRequest(getEntityURL,"POST", params);
		// return json
		Log.d(entityType + " Communication", json.toString());
		if (json.getString(KEY_SUCCESS) != null) {
			//loginErrorMsg.setText("");
			String res = json.getString(KEY_SUCCESS); 
			if (Integer.parseInt(res) == 1){
					// user successfully logged in
					// Store user details in SQLite Database
					JSONArray json_array =null;
					JSONObject json_object=null;
					DatabaseHelper db = new DatabaseHelper(context);
					try
					{
					json_array= json.getJSONArray(entityType);
					}
					catch (JSONException e)
					{
						Log.w(entityType, " Parsing to JSON_Array failed");
						json_object = json.getJSONObject(entityType);
					}
					finally
					{
						//HERE FILL THE DB AND THEN RETURN THE DATA ************
						if(json_object != null)
						{
							
						}
						if(json_array != null)
						{
							
						}
					}

			    }
				else
				{
					throw new InterruptedException();
				}
		}
		
		return entities;
	}
    
    // function get json from url
    // by making HTTP POST or GET mehtod
    private JSONObject makeHttpRequest(String url, String method,
            List<NameValuePair> params)  throws IOException, JSONException {
 
        // Making HTTP request
        try {
 
            // check for request method
            if(method == "POST"){
                // request method is POST
                // defaultHttpClient
                DefaultHttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                if (params != null) {
                    httpPost.setEntity(new UrlEncodedFormEntity(params));
                }
                Log.e("PARAMETERS",params.toString());
                HttpResponse httpResponse = httpClient.execute(httpPost);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
 
            }else if(method == "GET"){
                // request method is GET
                DefaultHttpClient httpClient = new DefaultHttpClient();
                String paramString = URLEncodedUtils.format(params, "utf-8");
                url += "?" + paramString;
                HttpGet httpGet = new HttpGet(url);
 
                HttpResponse httpResponse = httpClient.execute(httpGet);
                HttpEntity httpEntity = httpResponse.getEntity();
                is = httpEntity.getContent();
            }           
 
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw e;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        }
 
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
        	Log.d("JSON",json);
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
            e.printStackTrace();
        }
 
        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
            throw e;
        }
 
        // return JSON String
        return jObj;
 
    }

	public List<Friends> getFriends(int id) {
		return null;
		// TODO Auto-generated method stub
		
		
	}

	public List<Comments> getComments(int uid) {
		return null;
		// TODO Auto-generated method stub
		
	}

	public List<CommentLikes> getCommentLikes(int uid) {
		return null;
		// TODO Auto-generated method stub
		
	}

	
	public List<Notifications> getNotifications(int uid) {
		return null;
		// TODO Auto-generated method stub
		
	}
}
