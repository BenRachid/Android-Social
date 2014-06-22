package com.exam.social;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
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
	ProgressDialog dialog;
	private SharedPreferences sharedPref;

	// JSON Response node names
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_UID = "uid";
	private static String KEY_FIRSTNAME = "first_name";
	private static String KEY_LASTNAME = "last_name";
	private static String KEY_EMAIL = "email";
	private static String KEY_CREATED_AT = "created_at";
	

	private class LoginAsyncTask extends AsyncTask<String, Void, String> {
		@Override
		protected void onPreExecute() {
			dialog = new ProgressDialog(LoginActivity.this);
			dialog.setMessage("En cours de communication");
			dialog.setCancelable(false);
			dialog.show();
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			dialog.dismiss();
		}

		@Override
		protected String doInBackground(String... params) {

			String email = inputEmail.getText().toString();
			String password = inputPassword.getText().toString();
			// TODO Auto-generated method stub
			final JSONObject json;
			try {
				CommunicationServices userFunction = new CommunicationServices(
						LoginActivity.this);
				json = userFunction.loginUser(email, password);

				Log.d("JSON", json.toString());
				// check for login response
				if (json.getString(KEY_SUCCESS) != null) {
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							loginErrorMsg.setText("");
						}
					});
					String res = json.getString(KEY_SUCCESS);
					if (Integer.parseInt(res) == 1) {
						// user successfully logged in
						// Store user details in SQLite Database
						DatabaseHelper db = new DatabaseHelper(
								getApplicationContext());
						JSONObject json_user = json.getJSONObject("user");
						// Clear all previous data in database
						userFunction.logoutUser(getApplicationContext());
						int uid = json.getInt(KEY_UID);
						db.addUser(json_user.getString(KEY_FIRSTNAME),
								json_user.getString(KEY_LASTNAME),
								json_user.getString(KEY_EMAIL),
								json.getString(KEY_UID),
								json_user.getString(KEY_CREATED_AT));
						try{
							JSONArray jarray=json.getJSONArray("friends");
							Log.i("la TAILLE ",String.valueOf(jarray.length()));
							for (int i = 0; i < jarray.length(); i++) {
								JSONObject obj=jarray.getJSONObject(i);
								db.createFriendsFromJsonObject("Friends", obj);
							}
						
						}
						catch(Exception e)
						{
							Log.e("FRIENDS",json.toString());
							e.printStackTrace();
						}
						Editor edit = sharedPref.edit();
						edit.putInt("uid", uid);
						edit.putString("mail", email);
						// edit.putString("password", password);
						edit.commit();
						Log.d("LOGIN", "User added");
						// Launch Dashboard Screen
						Intent dashboard = new Intent(
								getApplicationContext(),
								DashboardActivity.class);

						// Close all views before launching
						// Dashboard
						dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
						startActivity(dashboard);

						// Close Login Screen
						finish();
					} else {
						
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									try {
										loginErrorMsg
										.setText(json.getString(KEY_ERROR_MSG));
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							});

					}
				}

			} catch (IOException e1) {
				// TODO HANDLE MESSAGE FOR EXEPTION
				e1.printStackTrace();
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						loginErrorMsg
						.setText("Server communication failed");
					}
				});
				Log.e("IO", e1.getMessage());

			} catch (JSONException e1) {
				// TODO HANDLE MESSAGE FOR EXEPTION
				e1.printStackTrace();
				Log.e("JSON", e1.getMessage());
			}
			return null;
		}
	}
	
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
		sharedPref = getPreferences(MODE_PRIVATE);
		String tmp = sharedPref.getString("mail", "");
		if (tmp != null && !tmp.isEmpty()) {
			inputEmail.setText(tmp);
		}
		// Login button Click Event
		btnLogin.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {

				Log.d("Button", "Login");
				new LoginAsyncTask().execute();

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
