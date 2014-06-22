package com.exam.social.activities.comment;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.exam.social.AbstractActivity;
import com.exam.social.R;
import com.exam.social.R.id;
import com.exam.social.R.layout;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class EditCommentActivity extends AbstractActivity {

	EditText txtName;
	EditText txtPrice;
	EditText txtDesc;
	EditText txtCreatedAt;
	Button btnSave;
	Button btnDelete;

	String pid;

	// Progress Dialog
	private ProgressDialog pDialog;

	// single comment url
	private static final String url_comment_detials = "http://10.0.2.2/android_connect/get_comment_details.php";

	// url to update comment
	private static final String url_update_comment = "http://10.0.2.2/android_connect/update_comment.php";

	// url to delete comment
	private static final String url_delete_comment = "http://10.0.2.2/android_connect/delete_comment.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";
	private static final String TAG_COMMENT = "comment";
	private static final String TAG_PID = "pid";
	private static final String TAG_NAME = "name";
	private static final String TAG_PRICE = "price";
	private static final String TAG_DESCRIPTION = "description";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edit_comment);

		// save button
		btnSave = (Button) findViewById(R.id.btnSave);
		btnDelete = (Button) findViewById(R.id.btnDelete);

		// getting comment details from intent
		Intent i = getIntent();

		// getting comment id (pid) from intent
		pid = i.getStringExtra(TAG_PID);

		// Getting complete comment details in background thread
		new GetCommentDetails().execute();

		// save button click event
		btnSave.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// starting background task to update comment
				new SaveCommentDetails().execute();
			}
		});

		// Delete button click event
		btnDelete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// deleting comment in background thread
				new DeleteComment().execute();
			}
		});

	}

	/**
	 * Background Async Task to Get complete comment details
	 * */
	class GetCommentDetails extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(EditCommentActivity.this);
			pDialog.setMessage("Loading comment details. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Getting comment details in background thread
		 * */
		protected String doInBackground(String... params) {

			// updating UI from Background Thread
			runOnUiThread(new Runnable() {
				public void run() {
					// Check for success tag
					int success;
					/*try {
						// Building Parameters
						List<NameValuePair> params = new ArrayList<NameValuePair>();
						params.add(new BasicNameValuePair("pid", pid));

						// getting comment details by making HTTP request
						// Note that comment details url will use GET request
						JSONObject json = makeHttpRequest(
								url_comment_detials, "POST", params);

						// check your log for json response
						Log.d("Single Comment Details", json.toString());

						// json success tag
						success = json.getInt(TAG_SUCCESS);
						if (success == 1) {
							// successfully received comment details
							JSONArray commentObj = json
									.getJSONArray(TAG_COMMENT); // JSON Array

							// get first comment object from JSON Array
							JSONObject comment = commentObj.getJSONObject(0);

							// comment with this pid found
							// Edit Text
							// txtName = (EditText)
							// findViewById(R.id.inputName);
							// txtPrice = (EditText)
							// findViewById(R.id.inputPrice);
							// txtDesc = (EditText)
							// findViewById(R.id.inputDesc);

							// display comment data in EditText
							txtName.setText(comment.getString(TAG_NAME));
							txtPrice.setText(comment.getString(TAG_PRICE));
							txtDesc.setText(comment.getString(TAG_DESCRIPTION));

						} else {
							// comment with pid not found
							
						}
					} catch (JSONException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
					*/
				}
			});

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once got all details
			pDialog.dismiss();
		}
	}

	/**
	 * Background Async Task to Save comment Details
	 * */
	class SaveCommentDetails extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(EditCommentActivity.this);
			pDialog.setMessage("Saving comment ...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Saving comment
		 * */
		protected String doInBackground(String... args) {

			// getting updated data from EditTexts
			String name = txtName.getText().toString();
			String price = txtPrice.getText().toString();
			String description = txtDesc.getText().toString();

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair(TAG_PID, pid));
			params.add(new BasicNameValuePair(TAG_NAME, name));
			params.add(new BasicNameValuePair(TAG_PRICE, price));
			params.add(new BasicNameValuePair(TAG_DESCRIPTION, description));

			// sending modified data through http request
			// Notice that update comment url accepts POST method
			JSONObject json;
			/*try {
				json = jsonParser.makeHttpRequest(url_update_comment, "POST",
						params);
		

			// check json success tag
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// successfully updated
					Intent i = getIntent();
					// send result code 100 to notify about comment update
					setResult(100, i);
					finish();
				} else {
					// failed to update comment
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
			catch (IOException e) {
				e.printStackTrace();
			}
			*/
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once comment uupdated
			pDialog.dismiss();
		}
	}

	/*****************************************************************
	 * Background Async Task to Delete Comment
	 * */
	class DeleteComment extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(EditCommentActivity.this);
			pDialog.setMessage("Deleting Comment...");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Deleting comment
		 * */
		protected String doInBackground(String... args) {

			// Check for success tag
			int success;
			/*try {
				// Building Parameters
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				params.add(new BasicNameValuePair("pid", pid));

				// getting comment details by making HTTP request
				JSONObject json = jsonParser.makeHttpRequest(
						url_delete_comment, "POST", params);

				// check your log for json response
				Log.d("Delete Comment", json.toString());

				// json success tag
				success = json.getInt(TAG_SUCCESS);
				if (success == 1) {
					// comment successfully deleted
					// notify previous activity by sending code 100
					Intent i = getIntent();
					// send result code 100 to notify about comment deletion
					setResult(100, i);
					finish();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
*/
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once comment deleted
			pDialog.dismiss();

		}

	}
}
