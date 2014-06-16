package com.exam.social.activities.comment;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import com.exam.helpers.JSONParser;
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

public class NewCommentActivity extends Activity {

	// Progress Dialog
	private ProgressDialog pDialog;

	JSONParser jsonParser = new JSONParser();
	EditText inputName;
	EditText inputPrice;
	EditText inputContent;

	// url to create new Comment
	private static String url_create_comment = "http://10.0.2.2/android_connect/create_comment.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_comment);

		// Edit Text
		inputName = (EditText) findViewById(R.id.commentContent);

		// Create button
		Button btnCreateComment = (Button) findViewById(R.id.btnCreateComment);

		// button click event
		btnCreateComment.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				// creating new Comment in background thread
				new CreateNewComment().execute();
			}
		});
	}

	/**
	 * Background Async Task to Create new Comment
	 * */
	class CreateNewComment extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(NewCommentActivity.this);
			pDialog.setMessage("Creating Comment..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(true);
			pDialog.show();
		}

		/**
		 * Creating Comment
		 * */
		protected String doInBackground(String... args) {
			
			String content = inputContent.getText().toString();

			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			
			// add the performer and the created_at***********************
			
			params.add(new BasicNameValuePair("content", content));

			// getting JSON Object
			// Note that create Comment url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_create_comment,
					"POST", params);
			
			// check log cat for response
			Log.d("Create Response", json.toString());

			// check for success tag
			try {
				int success = json.getInt(TAG_SUCCESS);

				if (success == 1) {
					// successfully created comment
					Intent i = new Intent(getApplicationContext(), CommentsActivity.class);
					startActivity(i);
					
					// closing this screen
					finish();
				} else {
					// failed to create Comment
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			pDialog.dismiss();
		}

	}
}
