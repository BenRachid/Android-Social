package com.exam.social;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.exam.helpers.CommunicationServices;
import com.exam.helpers.DatabaseHelper;
import com.exam.helpers.SessionManager;
import com.exam.models.Entity;
import com.exam.social.activities.DashboardActivity;

public class LoginActivity extends Activity {
	
	Button btnLogin;
	Button btnLinkToRegister;
	EditText inputEmail;
	EditText inputPassword;
	TextView loginErrorMsg;
	
	// JSON Response node names
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_UID = "uid";
	private static String KEY_NAME = "name";
	private static String KEY_EMAIL = "email";
	private static String KEY_CREATED_AT = "created_at";
	private static String KEY_GENDER = "gender";
	private static String KEY_BIRTH_DATE = "birth_date";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		// Importing all assets like buttons, text fields
		inputEmail = (EditText) findViewById(R.id.loginEmail);
		inputPassword = (EditText) findViewById(R.id.loginPassword);
		btnLogin = (Button) findViewById(R.id.btnLogin);
		btnLinkToRegister = (Button) findViewById(R.id.btnLinkToRegisterScreen);
		loginErrorMsg = (TextView) findViewById(R.id.login_error);

		// Login button Click Event
		btnLogin.setOnClickListener(new View.OnClickListener() {

			private AsyncTask<String, Void, String> loginAsyncTask;

			public void onClick(View view) {

				
				Log.d("Button", "Login");
				
				loginAsyncTask = new AsyncTask<String, Void, String>() {
					@Override
					protected void onPreExecute() {

					}
					@Override
					protected String doInBackground(String... params) {
						
						String email = inputEmail.getText().toString();
						String password = inputPassword.getText().toString();
						// TODO Auto-generated method stub
						JSONObject json;
						try {
							CommunicationServices userFunction= new CommunicationServices(LoginActivity.this);
							json = userFunction.loginUser(email, password);
							
							Log.d("JSON ERROR",json.toString());
							// check for login response
								if (json.getString(KEY_SUCCESS) != null) {
									loginErrorMsg.setText("");
									String res = json.getString(KEY_SUCCESS); 
									if(Integer.parseInt(res) == 1){
										// user successfully logged in
										// Store user details in SQLite Database
										DatabaseHelper db = new DatabaseHelper(getApplicationContext());
										JSONObject json_user = json.getJSONObject("user");
										SessionManager session;
										// Clear all previous data in database
										userFunction.logoutUser(getApplicationContext());
										db.addUser(json_user.getString(KEY_NAME), json_user.getString(KEY_EMAIL), json.getString(KEY_UID), json_user.getString(KEY_CREATED_AT), json_user.getString(KEY_BIRTH_DATE),json_user.getInt(KEY_GENDER));						
										Log.d("LOGIN","User added");
										// Launch Dashboard Screen
										Intent dashboard = new Intent(getApplicationContext(), DashboardActivity.class);
										
										// Close all views before launching Dashboard
										dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
										startActivity(dashboard);
										
										// Close Login Screen
										finish();
									}else{
										// Error in login
										loginErrorMsg.setText("Incorrect username/password");
									}
								}
							
							
						} catch (IOException e1) {
							// TODO HANDLE MESSAGE FOR EXEPTION
							e1.printStackTrace();
							Log.e("IO",e1.getMessage());
							
						} catch (JSONException e1) {
							// TODO HANDLE MESSAGE FOR EXEPTION
							e1.printStackTrace();
							Log.e("JSON",e1.getMessage());
						}
						return null;
					}
				}.execute();

			}
			
		});
		
		// Link to Register Screen
		btnLinkToRegister.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(),
						RegisterActivity.class);
				startActivity(i);
				finish();
			}
		});
	}

}
