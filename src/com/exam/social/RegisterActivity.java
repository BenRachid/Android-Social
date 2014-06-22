package com.exam.social;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import com.exam.helpers.CommunicationServices;
import com.exam.helpers.DatabaseHelper;
import com.exam.helpers.SessionManager;
import com.exam.social.activities.DashboardActivity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends Activity {
	Button btnRegister;
	Button btnLinkToLogin;
	EditText inputFirstName;
	EditText inputLastName;
	EditText inputEmail;
	EditText inputPassword;
	TextView registerErrorMsg;
	private AsyncTask<String, Void, String> registerAsyncTask;
	ProgressDialog dialog;
	// JSON Response node names
	private static String KEY_SUCCESS = "success";
	private static String KEY_ERROR = "error";
	private static String KEY_ERROR_MSG = "error_msg";
	private static String KEY_UID = "uid";
	private static String KEY_FIRSTNAME = "first_name";
	private static String KEY_LASTNAME = "last_name";
	private static String KEY_EMAIL = "email";
	private static String KEY_CREATED_AT = "created_at";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register);

		// Importing all assets like buttons, text fields
		inputFirstName = (EditText) findViewById(R.id.firstName);
		inputLastName = (EditText) findViewById(R.id.lastName);
		inputEmail = (EditText) findViewById(R.id.registerEmail);
		inputPassword = (EditText) findViewById(R.id.registerPassword);
		btnRegister = (Button) findViewById(R.id.btnRegister);
		btnLinkToLogin = (Button) findViewById(R.id.btnLinkToLoginScreen);
		registerErrorMsg = (TextView) findViewById(R.id.register_error);

		// Register Button Click event
		btnRegister.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Log.d("Button", "Register");
				registerAsyncTask.execute();
			}
		});

		// Link to Login Screen
		btnLinkToLogin.setOnClickListener(new View.OnClickListener() {

			public void onClick(View view) {
				Intent i = new Intent(getApplicationContext(),
						LoginActivity.class);
				startActivity(i);
				// Close Registration View
				finish();
			}
		});

		registerAsyncTask = new AsyncTask<String, Void, String>() {
			@Override
			protected void onPreExecute() {
				dialog = new ProgressDialog(RegisterActivity.this);
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

				String first_name = inputFirstName.getText().toString();
				String last_name = inputLastName.getText().toString();
				String email = inputEmail.getText().toString();
				String password = inputPassword.getText().toString();
				CommunicationServices userFunction = new CommunicationServices(
						RegisterActivity.this);
				JSONObject json;
				try {
					json = userFunction.registerUser(first_name, last_name,
							email, password);

					if (json.getString(KEY_SUCCESS) != null) {
						runOnUiThread(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								registerErrorMsg.setText("");
							}
						});

						String res = json.getString(KEY_SUCCESS);
						if (Integer.parseInt(res) == 1) {
							// user successfully registred
							// Store user details in SQLite Database
							DatabaseHelper db = new DatabaseHelper(
									getApplicationContext());
							JSONObject json_user = json.getJSONObject("user");

							// Clear all previous data in database
							userFunction.logoutUser(getApplicationContext());
							db.addUser(json_user.getString(KEY_FIRSTNAME),
									json_user.getString(KEY_LASTNAME),
									json_user.getString(KEY_EMAIL),
									json.getString(KEY_UID),
									json_user.getString(KEY_CREATED_AT));
							// Launch Dashboard Screen
							Intent dashboard = new Intent(
									getApplicationContext(),
									DashboardActivity.class);
							// Close all views before launching Dashboard
							dashboard.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
							startActivity(dashboard);
							// Close Registration Screen
							finish();
						} else {
							// Error in registration
							runOnUiThread(new Runnable() {
								@Override
								public void run() {
									// TODO Auto-generated method stub
									registerErrorMsg
											.setText("Error occured in registration");
								}
							});

						}
					}
				} catch (IOException e1) {
					// TODO HANDLE MESSAGE FOR EXEPTION
					e1.printStackTrace();
					Log.e("IO", e1.getMessage());

				} catch (JSONException e1) {
					// TODO HANDLE MESSAGE FOR EXEPTION
					e1.printStackTrace();
					Log.e("JSON", e1.getMessage());
				}
				return null;
			}
		};
	}
}
